package logic.boardChess;


import logic.board.Board;
import logic.board.Piece;
import logic.board.utils.Color;
import logic.board.utils.Position;
import logic.boardChess.pieces.*;
import logic.exceptions.IllegalMoveException;
import logic.exceptions.IllegalPromoveException;
import logic.exceptions.PendingPromoveException;
import logic.exceptions.UnCommitMoveException;
import logic.tools.Tools;

import java.util.ArrayList;

public class EngineChess implements EngineChessInterface {
    private Board board;
    private Color turnPlayer;

    public int getWidthBoard(){
        return this.board.getWidth();
    }
    public int getHeightBoard(){
        return this.board.getHeight();
    }
    public ArrayList<Piece> getPieces(){
        return this.board.getPieces();
    }

    public Color getTurnPlayer() {
        return turnPlayer;
    }

    public boolean isKingInCheck() {
        return kingInCheck;
    }

    //Indica si actualmente el rey esta en jaque
    private boolean kingInCheck = false;

    private ArrayList<Position> dangerSquares;

    private ArrayList<Position[]> currentMoves;
    private final Position[] lastMove = new Position[2];

    //Variables para los movimientos especiales
    private ArrayList<Position[]> enPassant;
    private ArrayList<Position[]> castle;

    //Variable que indica si hay un peon coronable
    private Piece pawnReadyToPromove = null;

    public EngineChess(int height, int width, boolean setup) {
        this.board = new Board(height, width);
        this.turnPlayer = Color.WHITE;
        if (setup) {
            boardSetup();
        }
        refreshCurrentMoves();

    }

    public void setupPiece(Piece piece, Position pos) {
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

    private void refreshCurrentMoves() {
        if (pawnReadyToPromove != null) {
            this.currentMoves = null;
        }
        else {
            this.currentMoves = board.getMoves(turnPlayer);
            this.dangerSquares = board.getDangerSquares(Tools.changeColor(turnPlayer));
            this.kingInCheck = calculateIsKingInCheck();
            checkEnPassant();
            checkCastle();
            this.currentMoves.addAll(enPassant);
            this.currentMoves.addAll(castle);

            this.validateLegalMovesConsideringCheck();
        }
    }

    public void doMove(Position initialPosition, Position finalPosition)
            throws PendingPromoveException, IllegalMoveException {
        // Lo primero es revisar si hay un peon listo para coronar, si es asi, se debe hacer esto primero
        if (pawnReadyToPromove != null) {
            throw new PendingPromoveException("There is a pawn awating promotion");
        }
        if (!Tools.hasThisMove(initialPosition, finalPosition, this.currentMoves)) {
            throw new IllegalMoveException("This move is not allowed");
        }
        try {
            // Revisar si el movimiento es peon al paso
            if (Tools.hasThisMove(initialPosition, finalPosition, this.enPassant)) {
                this.doEnPassant(initialPosition, finalPosition);
            }
            if (Tools.hasThisMove(initialPosition, finalPosition, this.castle)) {
                this.doCastle(initialPosition, finalPosition);
            } else {
                // Realizar el movimiento normal
                board.movePiece(initialPosition, finalPosition);
            }
        }catch(UnCommitMoveException e){
            e.printStackTrace();
        }
        // Actualizar el ultimo movimiento

        lastMove[0] = initialPosition;
        lastMove[1] = finalPosition;

        // Revisar si el ultimo movimiento fue un peon coronado
        // Si no ya puedes cambiar de turno y actualizar la lista de movimientos
        if (!checkPawnIsPendingToPromove()) {
            changeTurn();
            refreshCurrentMoves();
        }


    }

    public ArrayList<Position> getMovesOfPiece(Position position) {
        ArrayList<Position> finalsPositions = new ArrayList<Position>();
        for (Position[] p : this.currentMoves) {
            if (p[0].equals(position)) {
                finalsPositions.add(p[1]);
            }
        }
        return finalsPositions;
    }

    public Piece getPiece(Position position) {
        return board.getPiece(position);
    }

    public boolean havePiecePendingPromotion() {
        return pawnReadyToPromove != null;
    }

    private void changeTurn() {
        turnPlayer = turnPlayer == Color.WHITE ? Color.BLACK : Color.WHITE;
    }

    private boolean calculateIsKingInCheck() {
        //Comprueba si el rey esta en jacke y actualiza la jugada correspondiente
        boolean kingInCheck = false;
        Piece king = this.board.getPieceByType("King", turnPlayer);
        if (king != null && board.getDangerSquares(Tools.changeColor(turnPlayer)).contains(king.getPosition())) {
            kingInCheck = true;
        }
        return kingInCheck;

    }

    // Metodo que valida la lista de movimientos actuales para descartar las q ponen al rey en jaque
    private void validateLegalMovesConsideringCheck() {
        ArrayList<Position[]> legalMoves = new ArrayList<Position[]>();
        for (Position[] positions : this.currentMoves) {
            try {
                this.board.doUnCommitMove(positions[0], positions[1]);
                if (!calculateIsKingInCheck()) {
                    legalMoves.add(positions);
                }
                this.board.rollBack();
            } catch (UnCommitMoveException exception) {
                exception.printStackTrace();
            }
        }
        this.currentMoves = legalMoves;
    }
    /////////////////////////////////////////////////////////////////////////////////////////
    // Estos metodos son los que detectan los movimientos especiales

    // Metodo para detectar si hay una peones al paso
    private void checkEnPassant() {
        enPassant = new ArrayList<Position[]>();

        if (lastMove[0] != null) {
            Piece lastMovePiece = getPiece(lastMove[1]);

            // Comprobar si la ultima pieza que se movio es un peon, y su fue un doble step
            if (lastMovePiece instanceof Pawn &&
                    Math.abs(lastMove[0].getPosY() - lastMove[1].getPosY()) == 2) {

                //Obtener las piezas que estan en los laterales y comprobar si son peones
                ArrayList<Position> checkIsPawn = new ArrayList<Position>();
                checkIsPawn.add(lastMovePiece.getPosition().increaseBy(-1, 0));
                checkIsPawn.add(lastMovePiece.getPosition().increaseBy(1, 0));

                for (Position p : checkIsPawn) {
                    Piece piece = getPiece(p);
                    // Si se cumple agregamos el movimiento con la posicion del peon lateral como posicion inicial
                    // y como posicion final la posicion de detras del peon q hizo el doble step
                    if (piece != null && piece instanceof Pawn
                            && piece.getColor() != lastMovePiece.getColor()) {
                        enPassant.add(new Position[]
                                {piece.getPosition(),
                                        lastMovePiece.getPosition().
                                                increaseBy(0, lastMovePiece.getColor() == Color.BLACK ? 1 : -1)});
                    }
                }
            }
        }


    }

    // Metodo para realizar el movimiento de peon al paso
    private void doEnPassant(Position initialPosition, Position finalPosition) throws UnCommitMoveException {
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
    private void checkCastle() {
        this.castle = new ArrayList<Position[]>();
        Piece king = this.board.getPieceByType("King", turnPlayer);
        // Se comprueba que el rey no se haya movido y que no este en jaque
        if (king != null && !king.isHasMoved() && !this.kingInCheck) {
            ArrayList<Piece> rooks = this.board.getPiecesByType("Rook", turnPlayer);
            for (Piece rook : rooks) {
                // Calculo las casillas de diferencia que hay entre el rey y la torre en el eje X
                int intermediareSquares = Math.abs(king.getPosition().getPosX() - rook.getPosition().getPosX()) - 1;

                // Compruebo que la torre no se ha movido y que el rey y la torre esten a la misma altura y
                // que haya 2 o mas casillas de diferencia
                if (!rook.isHasMoved() &&
                        king.getPosition().getPosY() == rook.getPosition().getPosY()
                        && intermediareSquares >= 2) {
                    // Calculo el punto donde debera ir el rey
                    int kingMoveDistance = (intermediareSquares / 2) + 1;
                    // Ahora debo iterar desde la posicion del rey hasta la posicion la torre
                    int kingDirection = king.getPosition().getPosX() < rook.getPosition().getPosX() ? 1 : -1;
                    Position temporalPosition = king.getPosition();
                    boolean conditions = true;
                    for (int i = 0; i < intermediareSquares && conditions; i++) {
                        temporalPosition = temporalPosition.increaseBy(kingDirection, 0);


                        Piece temporalPiece = this.getPiece(temporalPosition);
                        if (temporalPiece != null) {
                            conditions = false;

                            // Se comprueba q ninguna de las casillas por las que pasa el rey estan amenazadas
                        } else if (i < kingMoveDistance && dangerSquares.contains(temporalPosition)) {
                            conditions = false;
                        }
                    }
                    // Si se cumplieron las condiciones se agrega el movimiento a las lista de enroque
                    if (conditions) {
                        this.castle.add(new Position[]
                                {king.getPosition(),
                                        king.getPosition().increaseBy(kingDirection * kingMoveDistance, 0)});
                    }

                }
            }
        }

    }

    private void doCastle(Position initialPosition, Position finalPosition) throws UnCommitMoveException {
        this.board.movePiece(initialPosition, finalPosition);
        // Buscar cual es la torre que le corresponde
        boolean found = false;
        int searchDirecion = initialPosition.getPosX() < finalPosition.getPosX() ? 1 : -1;
        Position currentPosition = finalPosition;
        while (!found) {
            currentPosition = currentPosition.increaseBy(searchDirecion, 0);
            if (this.getPiece(currentPosition) instanceof Rook) {
                found = true;
            }
        }
        this.board.movePiece(currentPosition, finalPosition.increaseBy(searchDirecion * (-1), 0));

    }

    // Metodo para revisar si hay un peon pendiente de coronar
    private boolean checkPawnIsPendingToPromove() {
        // La revision se basara en el ultimo movimiento, por tanto debe estar actualizado
        Piece piece = this.getPiece(lastMove[1]);
        if (piece instanceof Pawn) {
            int heightBoard = this.board.getHeight();
            int positionY = piece.getPosition().getPosY();
            if (positionY == 0 || positionY == heightBoard - 1) {
                this.pawnReadyToPromove = piece;
            }
        }
        return this.pawnReadyToPromove != null;
    }

    public void piecePromotion(String typePiece) throws IllegalPromoveException {
        if(this.pawnReadyToPromove == null){
            throw new IllegalPromoveException("There are no pawns available for promotion");
        }
        Piece piecePromoved = ((Pawn) pawnReadyToPromove).promove(typePiece);
        this.board.changePiece(piecePromoved);
        this.pawnReadyToPromove = null;

        changeTurn();
        refreshCurrentMoves();

    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    public void printBoard() {
        board.printBoard();
    }

}

