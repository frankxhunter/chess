package chess.logic.boardChess.pieces;

import chess.logic.board.Piece;
import chess.logic.board.Vector;
import chess.logic.board.utils.Color;

public class Rook extends Piece {
    public Rook(Color color) {
        super(color);
        setSimbology(new String[]{"♜", "♖"});
        this.getVectors().add(new Vector(1, 0, 0, true));
        this.getVectors().add(new Vector(-1, 0, 0, true));
        this.getVectors().add(new Vector(0, 1, 0, true));
        this.getVectors().add(new Vector(0, -1, 0, true));
    }

}
