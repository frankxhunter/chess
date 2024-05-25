package chess.logic.board;

import chess.logic.board.utils.Color;
import chess.logic.board.utils.Position;

import java.util.ArrayList;

public class Square {
    private final Position position;
    private Piece piece;

    // GETTERS AND SETTERS

    public Square(Position position, Piece piece) {
        this.piece = piece;
        this.position = position;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() {
        return piece;
    }

    public Position getPosition() {
        return position;
    }

    // Other methods

    @Override
    public String toString() {
        return piece != null ? piece.toString() : "🕳";
    }

    public Piece removePiece() {
        Piece piece = this.piece;
        this.piece = null;
        return piece;
    }

    public Color colorPieceOccuped() {
        if (piece != null) {
            return piece.getColor();
        }
        return null;
    }

    public ArrayList<Position> getMovesOfPiece() {
        if (piece == null) {
            return null;
        }
        return piece.getMoves();
    }


}
