package Logic;
import java.util.ArrayList;

public class Rook extends Piece {
    public Rook(Color color){
        super(color);
        setSimbology("R");
    }

    @Override
    public ArrayList<Position> getMoves() {
        ArrayList<Position> moves = new ArrayList<Position>();

        int moveX = 1;
        int moveY= 0;
        Position nexPosition = this.getPosition().increaseBy(moveX, moveY);
        while(this.getBoard().whoIsHere(nexPosition)!= null && this.getBoard().whoIsHere(nexPosition) != this.getColor()){
            moves.add(nexPosition);
            moveX++;
            nexPosition = this.getPosition().increaseBy(moveX, moveY);
        }
        moveX = 0;
        while(this.getBoard().whoIsHere(nexPosition)!= null && this.getBoard().whoIsHere(nexPosition) != this.getColor()){
            moves.add(nexPosition);
            moveX--;
            nexPosition = this.getPosition().increaseBy(moveX, moveY);
        }
        moveX = 0;
        while(this.getBoard().whoIsHere(nexPosition)!= null && this.getBoard().whoIsHere(nexPosition) != this.getColor()){
            moves.add(nexPosition);
            moveY--;
            nexPosition = this.getPosition().increaseBy(moveX, moveY);
        }
        moveY = 0;
        while(this.getBoard().whoIsHere(nexPosition)!= null && this.getBoard().whoIsHere(nexPosition) != this.getColor()){
            moves.add(nexPosition);
            moveY++;
        }
        return moves;
      
    }

}
