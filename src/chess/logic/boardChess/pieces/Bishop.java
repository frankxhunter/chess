package chess.logic.boardChess.pieces;

import chess.logic.board.Piece;
import chess.logic.board.Vector;
import chess.logic.board.utils.Color;

public class Bishop extends Piece {
    public Bishop(Color color) {
        super(color);
        setSimbology(new String[]{"♝", "♗"});
        getVectors().add(new Vector(1, 1, 0, true));
        getVectors().add(new Vector(1, -1, 0, true));
        getVectors().add(new Vector(-1, 1, 0, true));
        getVectors().add(new Vector(-1, -1, 0, true));
    }
}
