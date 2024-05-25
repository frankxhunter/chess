package logic.board;


import logic.board.utils.Color;
import logic.board.utils.Position;

public interface StatusBoard {
    Color whoIsHere(Position pos);

    boolean hasPosition(Position nextPosition);
}
