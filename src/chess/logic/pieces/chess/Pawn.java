package chess.logic.pieces.chess;

import chess.logic.Color;
import chess.logic.Position;
import chess.logic.Vector;
import chess.logic.pieces.Piece;

import java.util.ArrayList;
import java.util.Iterator;

public class Pawn extends Piece {
    private boolean firstMove = false;
    private boolean doubleStep = false;
    private ArrayList<Vector> vectorsToCapture = new ArrayList<>();
    public Pawn(Color color) {
        super(color);
        setSimbology(new String[]{"♟","♙"});
        int direction = color == color.BLACK ? -1: 1;
        getVectors().add(new Vector(0, 1*direction , 1 , false));
        getVectors().add(new Vector(0, 2*direction, 1, false));

        vectorsToCapture.add(new Vector(1, 1*direction, 1, false));
        vectorsToCapture.add(new Vector(-1, 1*direction, 1, false));
    }
    @Override
    public void setPosition(Position position) {
        super.setPosition(position);
        if (firstMove == false) {
            firstMove = true;
            getVectors().remove(1);
        }


    }
    // TODO ver si se puede hacer mas sencilla o si asi esta bien
    @Override
    public ArrayList<Position> getMoves() {

        ArrayList<Position> moves = new ArrayList<>();

        for (Vector v : getVectors()){
            Iterator<Position> it = v.iterator(this.getPosition());
            boolean condition = true;
            Position nextPosition;
            while(it.hasNext() && condition){
                nextPosition = it.next();
                Color color = this.getBoard().whoIsHere(nextPosition);
                if(this.getBoard().hasPosition(nextPosition)
                        && color == null){

                    moves.add(nextPosition);
                }else{
                    condition= false;
                }
            }
        }

        for (Vector v : vectorsToCapture){
            Iterator<Position> it = v.iterator(this.getPosition());
            boolean condition = true;
            Position nextPosition;
            while(it.hasNext() && condition){
                nextPosition = it.next();
                Color color = this.getBoard().whoIsHere(nextPosition);
                if(this.getBoard().hasPosition(nextPosition)
                        && color != this.getColor() && color != null){

                    moves.add(nextPosition);
                }else{
                    condition= false;
                }
            }
        }

        return moves;

    }

    @Override
    public void doMove(Position finalPosition) {
        if (getPosition().getPosY() - finalPosition.getPosY() == 2) {
            doubleStep = true;
        }
        else{
            doubleStep = false;
        }
        super.doMove(finalPosition);

    }
}
