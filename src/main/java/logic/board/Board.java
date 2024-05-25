package logic.board;



import logic.board.utils.Color;
import logic.board.utils.Position;
import logic.board.utils.UnCommitMove;
import logic.exceptions.UnCommitMoveException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class Board implements StatusBoard {
    private ArrayList<ArrayList<Square>> squareList;
    private final ArrayList<Piece> piecesList = new ArrayList<Piece>();
    private int height;
    private int width;

    // Indica si hay un movimiento sin guardar
    private UnCommitMove unCommitMove = null;

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

    // GETTERS AND SETTERS

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }


    // OTHER METHODS


    public Color whoIsHere(Position pos) {
        if (!hasPosition(pos)) {
            return null;
        }
        return getSquare(pos).colorPieceOccuped();
    }

    public boolean hasPosition(Position pos) {
        return (pos.getPosX() >= 0 && pos.getPosX() < width) && (pos.getPosY() >= 0 && pos.getPosY() < height);
    }

    /* Esta función se utiliza para colocar las piezas del juego en el tablero */
    public void putPiece(Piece piece, Position pos) {
        this.getSquare(pos).setPiece(piece);
        piece.setBoard(this);
        piece.setInitialPosition(pos);
        this.piecesList.add(piece);
    }

    public ArrayList<Position[]> getMoves() {
        ArrayList<Position[]> moves = new ArrayList<Position[]>();
        for (Piece p : piecesList) {
            for (Position pos : p.getMoves()) {
                moves.add(new Position[]{p.getPosition(), pos});
            }
        }
        return moves;
    }

    public ArrayList<Position[]> getMoves(Color color) {
        ArrayList<Position[]> moves = new ArrayList<Position[]>();
        Iterator<Piece> it = piecesList.iterator();
        while (it.hasNext()) {
            Piece piece = it.next();
            if (piece.getColor() == color) {
                for (Position pos : piece.getMoves()) {
                    moves.add(new Position[]{piece.getPosition(), pos});
                }
            }
        }
        return moves;
    }

    public ArrayList<Position> getDangerSquares(Color color) {
        ArrayList<Position> squares = new ArrayList<Position>();
        Iterator<Piece> it = piecesList.iterator();
        while (it.hasNext()) {
            Piece piece = it.next();
            if (piece.getColor() == color) {
                for (Position pos : piece.getMoves()) {
                    squares.add(pos);
                }
            }
        }
        return squares;
    }


    /* Obtiene todos los movimientos de una pieza especifica */
    public ArrayList<Position> getMovesOfPiece(Position position) {
        Square square = getSquare(position);
        return square.getMovesOfPiece();
    }

    /* Permite mover una pieza a una posición */
    public void movePiece(Position initialPosition, Position finalPosition) throws UnCommitMoveException {
        // Por ahora no se realizara validacion en este metodo, esto podria cambiar


        /*
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


         */
        if (this.unCommitMove != null) {
            throw new UnCommitMoveException("There a uncommit move, you must a rollback or a commit");
        }
        Piece piece = getSquare(initialPosition).removePiece();
        Piece capturedPiece = getSquare(finalPosition).removePiece();

        if (capturedPiece != null) {
            this.piecesList.remove(capturedPiece);
        }
        getSquare(finalPosition).setPiece(piece);
        piece.doMove(finalPosition);

    }

    public void doUnCommitMove(Position initialPosition, Position finalPosition) throws UnCommitMoveException {
        if (this.unCommitMove != null) {
            throw new UnCommitMoveException("There a uncommit move, you must a rollback or a commit");
        }
        Piece piece = getSquare(initialPosition).removePiece();
        Piece capturedPiece = getSquare(finalPosition).removePiece();

        if (capturedPiece != null) {
            this.piecesList.remove(capturedPiece);
        }
        this.getSquare(finalPosition).setPiece(piece);
        piece.setPosition(finalPosition);

        this.unCommitMove = new UnCommitMove(initialPosition, finalPosition, capturedPiece);
    }

    public void rollBack() {
        if (this.unCommitMove != null) {
            Piece piece = this.getSquare(this.unCommitMove.getFinalPosition()).removePiece();
            piece.setPosition(this.unCommitMove.getInitialPosition());
            this.getSquare(this.unCommitMove.getInitialPosition()).setPiece(piece);

            if (this.unCommitMove.getCapturedPiece() != null) {
                this.piecesList.add(this.unCommitMove.getCapturedPiece());
                this.getSquare(this.unCommitMove.getFinalPosition()).setPiece(this.unCommitMove.getCapturedPiece());
            }

            this.unCommitMove = null;
        }
    }

    public void commit() {
        this.unCommitMove = null;
    }

    /* Esta función imprime en consola el tablero completo */
    public void printBoard() {
        int i = squareList.get(0).size();
        ListIterator<ArrayList<Square>> it = squareList.listIterator(squareList.size());
        System.out.println("  ______________________");
        while (it.hasPrevious()) {

            System.out.print((i--) + " ");
            for (Square s : it.previous()) {
                System.out.print("" + s.toString() + " ");
            }
            System.out.println();
        }
        System.out.println("  ______________________");
        System.out.println("  1  2  3  4  5  6  7  8 ");
    }

    public Piece getPiece(Position position) {
        return squareList.get(position.getPosY()).get(position.getPosX()).getPiece();
    }

    public Square getSquare(Position position) {
        return squareList.get(position.getPosY()).get(position.getPosX());
    }

    // Permite obtener todas las pieces de un tipo y un color en el tablero
    public ArrayList<Piece> getPiecesByType(String type, Color color) {
        ArrayList<Piece> pieces = new ArrayList<Piece>();
        for (Piece p : piecesList) {
            if (p.getColor() == color && p.getClass().getName().contains(type)) {
                pieces.add(p);
            }
        }
        return pieces;
    }

    public Piece getPieceByType(String type, Color color) {
        Piece piece = null;
        for (Piece p : piecesList) {
            if (p.getColor() == color && p.getClass().getName().contains(type)) {
                piece = p;
                break;
            }
        }
        return piece;
    }

    // Metodo para eliminar una pieza del tablero
    public void deletePiece(Position position) {
        Piece piece = this.squareList.get(position.getPosY()).get(position.getPosX()).removePiece();
        piecesList.remove(piece);
    }

    // Este metodo sirve para cambiar una piece por otra
    public void changePiece(Piece piece) {
        if (piece.getPosition() != null && this.hasPosition(piece.getPosition())) {

            Square square = this.getSquare(piece.getPosition());
            Piece removed = square.removePiece();
            square.setPiece(piece);
            this.piecesList.remove(removed);
            this.piecesList.add(piece);

        }
    }


}
