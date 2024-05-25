package chess.logic.board;

import chess.logic.board.utils.Position;

import java.util.Iterator;

public class Vector {
    private final int pointX;
    private final int pointY;
    private int magnitude = 0;
    private final boolean largeMove;

    public Vector(int posX, int posY, int magnitude, boolean largeMove) {
        this.pointX = posX;
        this.pointY = posY;
        this.largeMove = largeMove;
        if (magnitude >= 0) {
            this.magnitude = magnitude;
        }
    }

    public Iterator<Position> iterator(Position pos) {
        return new Itr(pos);
    }

    private class Itr implements Iterator<Position> {
        public int realizedMove;
        public Position lastPostion;

        public Itr(Position pos) {
            lastPostion = pos;
        }

        public boolean hasNext() {
            return realizedMove < magnitude || largeMove;
        }

        public Position next() {
            if (hasNext()) {
                lastPostion = lastPostion.increaseBy(pointX, pointY);
                realizedMove++;
                return lastPostion;
            }
            return null;
        }
    }
}
