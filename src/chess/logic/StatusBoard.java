package chess.logic;

public interface StatusBoard {
    Color whoIsHere(Position pos);

    boolean hasPosition(Position nextPosition);
}
