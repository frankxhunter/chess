package chess.logic;

import chess.logic.pieces.Piece;

public class UnCommitMove extends Move{
    private Piece capturedPiece;

    public UnCommitMove(Position initialPosition, Position finalPosition, Piece capturedPiece) {
        super(initialPosition, finalPosition);
        this.capturedPiece = capturedPiece;
    }

    public Piece getCapturedPiece() {
        return capturedPiece;
    }

    public void setCapturedPiece(Piece capturedPiece) {
        this.capturedPiece = capturedPiece;
    }
}
