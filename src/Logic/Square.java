package Logic;
import java.util.ArrayList;

public class Square {
    private Position position; 
    private Piece piece; 

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

    public ArrayList<Vector> getVectors(){
        if(piece != null){
            return piece.getVectors();
        }
        return null;
    }

    @Override
    public String toString(){
        return piece != null ? piece.toString(): "  ";
    }

    public Piece removePiece(){
        Piece piece = this.piece;
        this.piece = null;
        return piece;
    }


}
