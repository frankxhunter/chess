package logic.boardChess.pieces;


import logic.board.Piece;
import logic.board.Vector;
import logic.board.utils.Color;
public class Knight extends Piece {
    public Knight(Color color) {
        super(color);
        setSimbology(new String[]{"♞", "♘"});
        getVectors().add(new Vector(2, 1, 1, false));
        getVectors().add(new Vector(2, -1, 1, false));

        getVectors().add(new Vector(-2, -1, 1, false));
        getVectors().add(new Vector(-2, 1, 1, false));

        getVectors().add(new Vector(1, 2, 1, false));
        getVectors().add(new Vector(-1, 2, 1, false));

        getVectors().add(new Vector(1, -2, 1, false));
        getVectors().add(new Vector(-1, -2, 1, false));

    }
}
