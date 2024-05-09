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

    public EngineChess(int height, int width) {
        this.board = new Board(height, width);
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

        this.turnPlayer = Color.WHITE;
        this.refreshCurrentMoves();
    }

    public ArrayList<Position[]> getCurrentMoves() {
        return currentMoves;
    }

    // TODO terminar metodo
    private void refreshCurrentMoves() {
        this.currentMoves = board.getMoves(turnPlayer);

        // Agregar movimientos especiales

        // Revisar cuales no se pueden hacer pq provocan autojacke
    }

    // Permite saber si este movimiento es posible
    private boolean hasThisMove(Position initialPosition, Position finalPosition) {
        boolean found = false;
        Iterator<Position[]> it = currentMoves.iterator();
        while(it.hasNext() && !found){
            Position [] move = it.next();
            if(move[0].isEqual(initialPosition) && move[1].isEqual(finalPosition) ){
                found = true;
            }
        }

        return found;
    }


    public void doMove(Position initialPosition, Position finalPosition) throws IlegalMoveException{
        if(!hasThisMove(initialPosition, finalPosition)){
            throw new IlegalMoveException("This move is not allowed");
        }
        board.movePiece(initialPosition, finalPosition);
        refreshCurrentMoves();
    }

    public void printBoard() {
        board.printBoard();
    }

    public ArrayList<Position> getMovesOfPiece(Position position) {
        return board.getMovesOfPiece(position);
    }

    public Piece getPiece(Position position) {
        return board.getPiece(position);
    }
}
