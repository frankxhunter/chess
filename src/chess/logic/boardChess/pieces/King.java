package chess.logic.boardChess.pieces;

import chess.logic.board.Piece;
import chess.logic.board.Vector;
import chess.logic.board.utils.Color;
import chess.logic.board.utils.Position;

public class King extends Piece {
    private boolean firstmove = false;

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

    @Override
    public void setPosition(Position position) {
        super.setPosition(position);
        this.firstmove = true;
    }
}
