package chess.logic.pieces;

import chess.logic.Color;
import chess.logic.Position;
import chess.logic.StatusBoard;
import chess.logic.Vector;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class Piece {
    private Color color;
    private String[] simbology = null;
    private StatusBoard board;
    private Position position = null;
    private ArrayList<Vector> vectors = new ArrayList<>();

    public Piece(Color color) {

        this.color = color;

    }

    // GETTERS AND SETTERS
    public void setSimbology(String[] simbology) {
        this.simbology = simbology;
    }

    public String[] getSimbology() {
        return simbology;
    }

    public Color getColor() {
        return color;
    }

    public ArrayList<Vector> getVectors() { return vectors; }

    public void setBoard(StatusBoard board) {
        this.board = board;
    }

    public StatusBoard getBoard() {
        return board;
    }

    public Position getPosition() {
        return position;
    }

    // OTHER METHODS

    public void setInitialPosition(Position pos){
        if(this.position == null)
            this.position = pos;
    }

    public void setPosition(Position position) {
        if(this.position != null)
            this.position = position;
    }

    public ArrayList<Position> getMoves() {
        ArrayList<Position> moves = new ArrayList<>();

        for (Vector v : vectors){
            Iterator<Position> it = v.iterator(this.getPosition());
            boolean condition = true;
            Position nextPosition;
            while(it.hasNext() && condition){
                nextPosition = it.next();
                if(this.getBoard().hasPosition(nextPosition)
                        && this.getBoard().whoIsHere(nextPosition) != this.getColor()){

                    moves.add(nextPosition);
                }else{
                    condition= false;
                }
            }
        }

        return moves;

    }

    @Override
    public String toString() {
        return color == Color.WHITE ? simbology[0] : simbology[1];
    }

}
