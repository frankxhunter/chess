package Logic;

import java.util.ArrayList;

public class Game {
    private Board board;

    public Game(int height, int width){
        this.board = new Board(height, width);
        board.putPiece(new Rook(Color.BLACK), new Position(0, 0));
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
