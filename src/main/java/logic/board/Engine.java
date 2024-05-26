package logic.board;


import logic.board.utils.Color;
import logic.board.utils.Position;
import logic.exceptions.IllegalMoveException;

import java.util.ArrayList;

public interface Engine {
    void setupPiece(Piece piece, Position pos);
    ArrayList<Position[]> getCurrentMoves();
    void doMove(Position initialPosition, Position finalPosition) throws IllegalMoveException;
    ArrayList<Position> getMovesOfPiece(Position position);
    ArrayList<Piece> getPieces();
    Piece getPiece(Position position);

    int getWidthBoard();
    int getHeightBoard();

    Color getTurnPlayer();
}
