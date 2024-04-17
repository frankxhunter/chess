package Logic;

import java.util.ArrayList;
import java.util.Iterator;

public class Board implements StatusBoard{
    private ArrayList<ArrayList<Square>> squareList;
    private int height;
    private int width;

    public Board(int height, int width) {
        if (height > 0 || width > 0) {
            this.height = height;
            this.width = width;
            squareList = new ArrayList<ArrayList<Square>>(width);
            for (int i = 0; i < this.width; i++) {
                ArrayList<Square> aux = new ArrayList<Square>(height);
                squareList.add(aux);

                for (int j = 0; j < this.height; j++) {
                    aux.add(new Square(new Position(i, j), null));
                }
            }
        }
    }

    /* Esta función se utiliza para colocar las piezas del juego en el tablero */
    public void putPiece(Piece piece, Position pos) {
        squareList.get(pos.getPosX()).get(pos.getPosY()).setPiece(piece);
    }

    public Color whoIsHere(Position pos){
        return squareList.get(pos.getPosX()).get(pos.getPosY()).colorPieceOccuped();
    }

    /* Esta función imprime en consola el tablero completo */
    public void printBoard() {
        for (ArrayList<Square> e : this.squareList) {
            for (Square s : e) {
                System.out.print("(" + s.toString() + ")-");
            }
            System.out.println();
        }
    }

    /* Obtiene todos los movimientos de una pieza especifica */
    public ArrayList<Position> getMovesOfPiece(Position position) {
        Square square = squareList.get(position.getPosX()).get(position.getPosY());
        return square.getMovesOfPiece();
    }

    /* Permite mover una pieza a una posición */
    public boolean movePiece(Position initialPosition, Position finalPosition) {
        // Obtener los posibles movimientos de la posición inicial
        ArrayList<Position> possiblesMoves = getMovesOfPiece(initialPosition);
        Iterator<Position> it = possiblesMoves.iterator();
        boolean found = false;
        // Iterar hasta encontrar la posición final entre los posibles movimientos o que se acaben los movimientos
        while (it.hasNext() && !found)
            if (it.next().isEqual(finalPosition))
                found = true;

        // Si se encontró la posición entre los posibles movimientos, realizar el movimiento
        if (found) {
            Piece piece = squareList.get(initialPosition.getPosX()).get(initialPosition.getPosY()).removePiece();
            squareList.get(finalPosition.getPosX()).get(finalPosition.getPosY()).setPiece(piece);
        }
        return found;
    }
}
