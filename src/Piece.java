import java.util.ArrayList;

public abstract class Piece {
    private Color color; 

    public Piece(Color color){
        this.color = color;
    }
    public Color getColor() {
        return color;
    }
   
    public abstract ArrayList<Position> getMoves();

}
