package logic.boardChess.pieces;


import logic.board.Piece;
import logic.board.Vector;
import logic.board.utils.Color;
import logic.board.utils.Position;
import logic.exceptions.IllegalPromoveException;

import java.util.ArrayList;
import java.util.Iterator;

public class Pawn extends Piece {
    private final ArrayList<Vector> vectorsToCapture = new ArrayList<Vector>();

    public Pawn(Color color) {
        super(color);
        setSimbology(new String[]{"♟", "♙"});
        int direction = color == Color.BLACK ? -1 : 1;
        getVectors().add(new Vector(0, 1 * direction, 1, false));
        getVectors().add(new Vector(0, 2 * direction, 1, false));

        vectorsToCapture.add(new Vector(1, 1 * direction, 1, false));
        vectorsToCapture.add(new Vector(-1, 1 * direction, 1, false));
    }

    // TODO ver si se puede hacer mas sencilla o si asi esta bien
    @Override
    public ArrayList<Position> getMoves() {

        ArrayList<Position> moves = new ArrayList<Position>();

        for (Vector v : getVectors()) {
            Iterator<Position> it = v.iterator(this.getPosition());
            boolean condition = true;
            Position nextPosition;
            while (it.hasNext() && condition) {
                nextPosition = it.next();
                Color color = this.getBoard().whoIsHere(nextPosition);
                if (this.getBoard().hasPosition(nextPosition)
                        && color == null) {

                    moves.add(nextPosition);
                } else {
                    condition = false;
                }
            }
        }

        for (Vector v : vectorsToCapture) {
            Iterator<Position> it = v.iterator(this.getPosition());
            boolean condition = true;
            Position nextPosition;
            while (it.hasNext() && condition) {
                nextPosition = it.next();
                Color color = this.getBoard().whoIsHere(nextPosition);
                if (this.getBoard().hasPosition(nextPosition)
                        && color != this.getColor() && color != null) {

                    moves.add(nextPosition);
                } else {
                    condition = false;
                }
            }
        }

        return moves;

    }

    public Piece promove(String typeOfPiece) throws IllegalPromoveException {
        Piece piece = null;
        if (typeOfPiece == "Rook") {
            piece = new Rook(this.getColor());
        } else if (typeOfPiece == "Bishop") {
            piece = new Bishop(this.getColor());
        } else if (typeOfPiece == "Queen") {
            piece = new Queen(this.getColor());
        } else if (typeOfPiece == "Knight") {
            piece = new Knight(this.getColor());
        } else {
            throw new IllegalPromoveException("Invalid piece for pawn promotion");
        }

        piece.doMove(this.getPosition());
        piece.setBoard(this.getBoard());
        return piece;

    }
}
