package chess.logic;

public class Move {
    private Position initialPosition;
    private Position finalPosition;

    public Move(Position initialPosition, Position finalPosition) {
        this.initialPosition = initialPosition;
        this.finalPosition = finalPosition;
    }

    public Position getInitialPosition() {
        return initialPosition;
    }

    public void setInitialPosition(Position initialPosition) {
        this.initialPosition = initialPosition;
    }

    public Position getFinalPosition() {
        return finalPosition;
    }

    public void setFinalPosition(Position finalPosition) {
        this.finalPosition = finalPosition;
    }
}
