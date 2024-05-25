package chess.logic.board;

import chess.logic.board.utils.Color;
import chess.logic.board.utils.Position;

public interface StatusBoard {
    Color whoIsHere(Position pos);

    boolean hasPosition(Position nextPosition);
}
