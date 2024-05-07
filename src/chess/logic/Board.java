package chess.logic;

import chess.logic.pieces.Piece;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

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
    public void
    putPiece(Piece piece, Position pos) {
        getSquare(pos).setPiece(piece);
        piece.setBoard(this);
        piece.setInitialPosition(pos);
    }

    public Color whoIsHere(Position pos){
        if(!hasPosition(pos)){
            return null;
        }
        return getSquare(pos).colorPieceOccuped();
    }

    /* Esta función imprime en consola el tablero completo */
    public void printBoard() {
        int i = 8;
        ListIterator<ArrayList<Square>> it =squareList.listIterator(squareList.size());
        System.out.println("  ______________________");
        while(it.hasPrevious())
        {

            System.out.print((i--)+" ");
            for (Square s : it.previous()) {
                System.out.print(""+s.toString()+" ");
            }
            System.out.println();
        }
        System.out.println("  ______________________");
        System.out.println("  1  2  3  4  5  6  7  8 ");
    }

    /* Obtiene todos los movimientos de una pieza especifica */
    public ArrayList<Position> getMovesOfPiece(Position position) {
        Square square = getSquare(position);
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
            Piece piece = getSquare(initialPosition).removePiece();
            getSquare(finalPosition).setPiece(piece);
            piece.setPosition(finalPosition);
        }
        return found;
    }
    public boolean hasPosition(Position pos){
        return (pos.getPosX()>=0 && pos.getPosX() <width) && (pos.getPosY() >= 0 && pos.getPosY()<height);
    }

    public Piece getPiece(Position position) {
        return squareList.get(position.getPosY()).get(position.getPosX()).getPiece();
    }
    public Square getSquare(Position position ){
        return squareList.get(position.getPosY()).get(position.getPosX());
    }
}
