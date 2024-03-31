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


}
