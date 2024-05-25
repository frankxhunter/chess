package logic.boardChess.pieces;


import logic.board.Piece;
import logic.board.Vector;
import logic.board.utils.Color;

public class Queen extends Piece {
    public Queen(Color color) {
        super(color);
        setSimbology(new String[]{"♛", "♕"});
        getVectors().add(new Vector(0, 1, 0, true));
        getVectors().add(new Vector(0, -1, 0, true));
        getVectors().add(new Vector(1, 0, 0, true));
        getVectors().add(new Vector(-1, 0, 0, true));

        getVectors().add(new Vector(1, 1, 0, true));
        getVectors().add(new Vector(1, -1, 0, true));
        getVectors().add(new Vector(-1, 1, 0, true));
        getVectors().add(new Vector(-1, -1, 0, true));
    }
}
