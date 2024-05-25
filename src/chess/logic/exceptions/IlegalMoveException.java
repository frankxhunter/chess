package chess.logic.exceptions;

public class IlegalMoveException extends Exception {
    public IlegalMoveException() {
        super();
    }

    public IlegalMoveException(String message) {
        super(message);
    }
}

