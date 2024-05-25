package chess.logic.exceptions;

public class IllegalPromoveException extends Exception {
    public IllegalPromoveException() {
        super();
    }

    public IllegalPromoveException(String message) {
        super(message);
    }
}