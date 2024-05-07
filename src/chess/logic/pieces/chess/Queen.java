package chess.logic.pieces.chess;

import chess.logic.Color;
import chess.logic.Vector;
import chess.logic.pieces.Piece;

public class Queen extends Piece {
    public Queen(Color color) {
        super(color);
        setSimbology(new String[]{"♛", "♕"});
        getVectors().add(new Vector(0 , 1, 0,true));
        getVectors().add(new Vector(0 , -1, 0,true));
        getVectors().add(new Vector(1 , 0, 0,true));
        getVectors().add(new Vector(-1 , 0, 0,true));

        getVectors().add(new Vector(1 , 1, 0,true));
        getVectors().add(new Vector(1 , -1, 0,true));
        getVectors().add(new Vector(-1 , 1, 0,true));
        getVectors().add(new Vector(-1 , -1, 0,true));
    }
}
