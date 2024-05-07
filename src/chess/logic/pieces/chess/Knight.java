package chess.logic.pieces.chess;

import chess.logic.Color;
import chess.logic.Vector;
import chess.logic.pieces.Piece;

public class Knight extends Piece {
    public Knight(Color color) {
        super(color);
        setSimbology(new String[]{"♞", "♘"});
        getVectors().add(new Vector(2, 1 , 1 , false));
        getVectors().add(new Vector(2, -1 , 1 , false));
        getVectors().add(new Vector(1, 2 , 1 , false));
        getVectors().add(new Vector(-1, 2 , 1 , false));

    }
}
