package logic.board;


import logic.board.utils.Position;
import logic.exceptions.IllegalMoveException;

import java.util.ArrayList;

public interface Engine {
    void setupPiece(Piece piece, Position pos);
    ArrayList<Position[]> getCurrentMoves();
    void doMove(Position initialPosition, Position finalPosition) throws IllegalMoveException;
    ArrayList<Position> getMovesOfPiece(Position position);
    Piece getPiece(Position position);

}