package chess.logic.exceptions;

public class PendingPromoveException extends Exception {
    public PendingPromoveException() {
        super();
    }

    public PendingPromoveException(String message) {
        super(message);
    }
}