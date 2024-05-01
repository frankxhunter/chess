package chess.logic;

import chess.logic.pieces.chess.*;

import java.util.ArrayList;

public class Game {
    private Board board;

    public Game(int height, int width){
        this.board = new Board(height, width);
        board.putPiece(new Rook(Color.WHITE), new Position(0, 0));
        board.putPiece(new Knight(Color.WHITE), new Position(1, 0));
        board.putPiece(new Bishop(Color.WHITE), new Position(2, 0));
        board.putPiece(new King(Color.WHITE), new Position(3, 0));
        board.putPiece(new Queen(Color.WHITE), new Position(4, 0));
        board.putPiece(new Bishop(Color.WHITE), new Position(5, 0));
        board.putPiece(new Knight(Color.WHITE), new Position(6, 0));
        board.putPiece(new Rook(Color.WHITE), new Position(7, 0));


        for (int i = 0; i<8; i++)
            board.putPiece(new Pawn(Color.WHITE), new Position(i, 1));
    }

    public void printBoard(){
        board.printBoard();
    }

    public ArrayList<Position> getMovesOfPiece(Position position){
        return board.getMovesOfPiece(position);
    }

    public boolean movePiece(Position initialPosition, Position finalPosition){
        boolean result = false;
        // TODO Revisar si se cumplen las condiciones del juego
        if(true){
            result = board.movePiece(initialPosition, finalPosition);
        }
        return result;
    }


}
