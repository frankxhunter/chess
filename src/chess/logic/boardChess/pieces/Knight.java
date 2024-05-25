package chess.logic.boardChess.pieces;

import chess.logic.board.Piece;
import chess.logic.board.Vector;
import chess.logic.board.utils.Color;

public class Knight extends Piece {
    public Knight(Color color) {
        super(color);
        setSimbology(new String[]{"♞", "♘"});
        getVectors().add(new Vector(2, 1, 1, false));
        getVectors().add(new Vector(2, -1, 1, false));
        getVectors().add(new Vector(1, 2, 1, false));
        getVectors().add(new Vector(-1, 2, 1, false));

    }
}
