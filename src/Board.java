import java.util.ArrayList;

public class Board {
    private ArrayList<ArrayList<Square>> squareList; 
    private int height; 
    private int width;

    public Board(int height, int width){
        if(height > 0 || width > 0){
            this.height = height;
            this.width = width;
            squareList = new ArrayList<ArrayList<Square>>(width);
            for(int i = 0; i < width; i++){
                ArrayList<Square> aux = new ArrayList<Square>(height);
                squareList.add(aux);
                
                for(int j = 0; j < height; j++){
                    aux.add(new Square(new Position(i, j), null));
                }
            }
        }
    } 

    public void printBoard(){
        for(ArrayList<Square> e : this.squareList){
            for(Square s : e){
                System.out.print("(" + s.getPosition().getPosX() + "," + s.getPosition().getPosY() + ")-");
            }
            System.out.println();
        }
    }

    public ArrayList<Position> getMovesOfPiece(Position position){
        Square square = squareList.get(position.getPosX()).get(position.getPosY());
        ArrayList<Vector> vectors = square.getVectors(); 
        if(vectors == null){
            return null;
        }
        ArrayList<Position> moves = new ArrayList<>();
        for(Vector v : vectors){
            Position aux = v.getNextMove(position);

            while(aux != null && aux.getPosX() <= width && aux.getPosY() <= height){
                moves.add(aux);
                aux = v.getNextMove(aux);
            }
        }
        return moves; 

    }
}
