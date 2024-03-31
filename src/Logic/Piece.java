package Logic;
import java.util.ArrayList;

public abstract class Piece {
    private Color color; 
    private String simbology = null;

    public Piece(Color color){
        this.color = color;
    }
    public Color getColor() {
        return color;
    }
    public void setSimbology(String simbology) {
        this.simbology = simbology;
    }
   
    public abstract ArrayList<Vector> getVectors();

    @Override
    public String toString() {
        return  simbology + (color == Color.BLACK? "º": " ") ;
    }
        
}
