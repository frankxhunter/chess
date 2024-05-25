package logic.board;


import logic.board.utils.Position;
import logic.exceptions.IllegalMoveException;
import logic.exceptions.IllegalPromoveException;
import logic.exceptions.PendingPromoveException;

public interface EngineWithPromove extends Engine {

    void doMove(Position initialPosition, Position finalPosition) throws IllegalMoveException, PendingPromoveException;

    void piecePromotion(String typePiece) throws IllegalPromoveException;
}
