import java.util.ArrayList;

public class Rook extends Piece {
    public Rook(Color color){
        super(color);
    }

    @Override
    public ArrayList<Vector> getVectors() {
        ArrayList<Vector> vectors = new ArrayList<Vector>();
        vectors.add(new Vector(1,0, 0 , true));
        vectors.add(new Vector(1,1, 0 , true));
        vectors.add(new Vector(-1,0, 0 , true));
        vectors.add(new Vector(-1,-1, 0 , true));

        return vectors; 
    }
}
