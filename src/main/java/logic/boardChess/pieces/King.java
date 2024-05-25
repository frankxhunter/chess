package logic.boardChess.pieces;


import logic.board.Piece;
import logic.board.Vector;
import logic.board.utils.Color;
public class King extends Piece {

    public King(Color color) {
        super(color);
        setSimbology(new String[]{"♚", "♔"});
        getVectors().add(new Vector(0, 1, 1, false));
        getVectors().add(new Vector(0, -1, 1, false));
        getVectors().add(new Vector(1, 0, 1, false));
        getVectors().add(new Vector(-1, 0, 1, false));

        getVectors().add(new Vector(1, 1, 1, false));
        getVectors().add(new Vector(-1, -1, 1, false));
        getVectors().add(new Vector(-1, 1, 1, false));
        getVectors().add(new Vector(1, -1, 1, false));

    }


}
