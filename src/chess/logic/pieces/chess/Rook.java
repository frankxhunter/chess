package chess.logic.pieces.chess;

import chess.logic.Color;
import chess.logic.Vector;
import chess.logic.pieces.Piece;

public class Rook extends Piece {
    public Rook(Color color){
        super(color);
        setSimbology(new String[]{"♜","♖"});
        this.getVectors().add(new Vector(1, 0, 0, true));
        this.getVectors().add(new Vector(-1, 0, 0, true));
        this.getVectors().add(new Vector(0, 1, 0, true));
        this.getVectors().add(new Vector(0, -1, 0, true));
    }

}
