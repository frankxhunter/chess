package chess.logic;

import chess.logic.exceptions.IlegalMoveException;
import chess.logic.pieces.Piece;
import chess.logic.pieces.chess.*;

import java.util.ArrayList;
import java.util.Iterator;

public class EngineChess {
    private Board board;
    private Color turnPlayer;
    private ArrayList<Position[]> currentMoves;
    private Position [] lastMove=new Position[2];


    //Variables para los movimientos especiales
    private ArrayList<Position[]> enPassant;
    private ArrayList<Position[]> castle;

    


    public EngineChess(int height, int width, boolean setup) {
        this.board = new Board(height, width);
        this.turnPlayer = Color.WHITE;
        if(setup){
           boardSetup();
       }
        refreshCurrentMoves();

    }
    public void setupPiece(Piece piece, Position pos){
        board.putPiece(piece, pos);
        refreshCurrentMoves();
    }

    private void boardSetup() {
        board.putPiece(new Rook(Color.WHITE), new Position(0, 0));
        board.putPiece(new Knight(Color.WHITE), new Position(1, 0));
        board.putPiece(new Bishop(Color.WHITE), new Position(2, 0));
        board.putPiece(new King(Color.WHITE), new Position(3, 0));
        board.putPiece(new Queen(Color.WHITE), new Position(4, 0));
        board.putPiece(new Bishop(Color.WHITE), new Position(5, 0));
        board.putPiece(new Knight(Color.WHITE), new Position(6, 0));
        board.putPiece(new Rook(Color.WHITE), new Position(7, 0));
        for (int i = 0; i < 8; i++)
            board.putPiece(new Pawn(Color.WHITE), new Position(i, 1));

        board.putPiece(new Rook(Color.BLACK), new Position(0, 7));
        board.putPiece(new Knight(Color.BLACK), new Position(1, 7));
        board.putPiece(new Bishop(Color.BLACK), new Position(2, 7));
        board.putPiece(new King(Color.BLACK), new Position(3, 7));
        board.putPiece(new Queen(Color.BLACK), new Position(4, 7));
        board.putPiece(new Bishop(Color.BLACK), new Position(5, 7));
        board.putPiece(new Knight(Color.BLACK), new Position(6, 7));
        board.putPiece(new Rook(Color.BLACK), new Position(7, 7));

        for (int i = 0; i < 8; i++)
            board.putPiece(new Pawn(Color.BLACK), new Position(i, 6));

    }

    public ArrayList<Position[]> getCurrentMoves() {
        return currentMoves;
    }

    // TODO terminar metodo
    private void refreshCurrentMoves() {
        this.currentMoves = board.getMoves(turnPlayer);
        checkEnPassant();
        checkCastle();
        this.currentMoves.addAll(enPassant);
        this.currentMoves.addAll(castle);



        // Agregar movimientos especiales

        // Revisar cuales no se pueden hacer pq provocan autojacke
    }

    public void doMove(Position initialPosition, Position finalPosition) throws IlegalMoveException{
        if(!hasThisMove(initialPosition, finalPosition, this.currentMoves)){
            throw new IlegalMoveException("This move is not allowed");
        }
        // Revisar si el movimiento es peon al paso
        if(hasThisMove(initialPosition, finalPosition, this.enPassant)){
            this.doEnPassant(initialPosition, finalPosition);
        }
        if(hasThisMove(initialPosition, finalPosition, this.castle)){
            this.doCastle(initialPosition, finalPosition);
        }
        else{
            // Realizar el movimiento normal
            board.movePiece(initialPosition, finalPosition);
        }
        // Cambiar de turno, actualizar el ultimo movimiento, la lista de movimientos posibles
        changeTurn();

        lastMove[0] = initialPosition;
        lastMove[1] = finalPosition;

        refreshCurrentMoves();



    }

    // Permite saber si este movimiento es posible
    private boolean hasThisMove(Position initialPosition, Position finalPosition, ArrayList<Position[]> moves) {
        boolean found = false;
        Iterator<Position[]> it = moves.iterator();
        while(it.hasNext() && !found){
            Position [] move = it.next();
            if(move[0].isEqual(initialPosition) && move[1].isEqual(finalPosition) ){
                found = true;
            }
        }

        return found;
    }


    public ArrayList<Position> getMovesOfPiece(Position position) {
        return board.getMovesOfPiece(position);
    }

    public Piece getPiece(Position position) {
        return board.getPiece(position);
    }

    private void changeTurn(){
        turnPlayer = turnPlayer == Color.WHITE ? Color.BLACK : Color.WHITE;
    }

    /////////////////////////////////////////////////////////////////////////////////////////
    // Estos metodos son los que detectan los movimientos especiales

    // Metodo para detectar si hay una peones al paso
    private void checkEnPassant(){
        enPassant = new ArrayList<>();
        
        if(lastMove[0] != null){
            Piece lastMovePiece = getPiece(lastMove[1]);

            // Comprobar si la ultima pieza que se movio es un peon, y su fue un doble step
            if(lastMovePiece instanceof Pawn &&
                    Math.abs(lastMove[0].getPosY()-lastMove[1].getPosY()) == 2){

            //Obtener las piezas que estan en los laterales y comprobar si son peones
            ArrayList<Position> checkIsPawn = new ArrayList<>();
            checkIsPawn.add(lastMovePiece.getPosition().increaseBy(-1, 0));
            checkIsPawn.add(lastMovePiece.getPosition().increaseBy(1, 0));
            
            for (Position p : checkIsPawn){
                Piece piece = getPiece(p);
                // Si se cumple agregamos el movimiento con la posicion del peon lateral como posicion inicial
                // y como posicion final la posicion de detras del peon q hizo el doble step
                if (piece != null && piece instanceof Pawn
                        && piece.getColor() != lastMovePiece.getColor() ){
                    enPassant.add(new Position[]
                            {piece.getPosition(),
                                    lastMovePiece.getPosition().
                                            increaseBy(0, lastMovePiece.getColor() == Color.BLACK? 1: -1)});
                }
            }
        }
    }


    }

    // Metodo para realizar el movimiento de peon al paso
    private void doEnPassant(Position initialPosition, Position finalPosition) {
        this.board.movePiece(initialPosition, finalPosition);
        // Eliminar el peon que esta en la ultima posicion
        this.board.deletePiece(this.lastMove[1]);
    }

    // Metodo para comprobar que si el rey puede hacer enroque
    /* Condiciones que se deben cumplir
    - El rey y la torre involucrados no deben haber sido movidos previamente
    - No debe haber piezas entre el rey y la torre
    - El rey no puede estar en jaque
    - El rey no puede pasar a través de una casilla atacada
    - El rey no puede terminar en jaque
     */
    private void checkCastle(){
        this.castle = new ArrayList<>();
        Piece king = this.board.getPiecesByType("King", turnPlayer).get(0);
        // TODO Comprobar que el rey no esta en jaque
        if(!king.isHasMoved()){
            ArrayList<Piece> rooks = this.board.getPiecesByType("Rooks", turnPlayer );
            for (Piece rook: rooks){
                // Calculo las casillas de diferencia que hay entre el rey y la torre en el eje X
                int intermediareSquares = Math.abs(king.getPosition().getPosX() - rook.getPosition().getPosX());

                // Compruebo que la torre no se ha movido y que el rey y la torre esten a la misma altura y
                // que haya 2 o mas casillas de diferencia
                if (!rook.isHasMoved() &&
                        king.getPosition().getPosY() == rook.getPosition().getPosY()
                        && intermediareSquares >= 2) {
                    // Calculo el punto donde debera ir el rey
                    int kingMoveDistance = intermediareSquares % 2 == 0 ?
                            intermediareSquares / 2: (intermediareSquares /2) +1;
                    // Ahora debo iterar desde la posicion del rey hasta la posicion la torre
                    int kingDirection = king.getPosition().getPosX() - rook.getPosition().getPosX() < 0? 1: -1;
                    Position temporalPosition = king.getPosition();
                    boolean conditions = true;
                    for (int i = 0; i< intermediareSquares && conditions; i++){
                        temporalPosition.increaseBy(kingDirection,0);

                        // TODO comprobar que la casillas hasta donde avanza el rey no esten amenazadas,
                        // creo que esto se puede hacer obteniendo los proximos movimientos del rival
                        if (this.board.getPiece(temporalPosition) != null){
                            conditions= false;
                        }
                    }
                    // Si se cumplieron las condiciones se agrega el movimiento a las lista de enroque
                    this.castle.add(new Position[]
                            {king.getPosition(),
                                    king.getPosition().increaseBy(kingDirection * kingMoveDistance, 0)});

                }
            }
        }
    }

    private void doCastle(Position initialPosition, Position finalPosition) {
        this.board.movePiece(initialPosition, finalPosition);
        // Buscar cual es la torre que le corresponde
        boolean found = false;
        int searchDirecion = initialPosition.getPosX() < initialPosition.getPosX() ? 1: -1;
        Position currentPosition = finalPosition;
        while( !found ){
            currentPosition = currentPosition.increaseBy(searchDirecion, 0);
            if(this.getPiece(currentPosition) instanceof Rook){
                found = true;
            }
        }
        this.board.movePiece(currentPosition, finalPosition.increaseBy(searchDirecion*(-1),0));

    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    public void printBoard() {
        board.printBoard();
    }

}

