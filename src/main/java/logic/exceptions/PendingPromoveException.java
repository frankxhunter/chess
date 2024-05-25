package logic.exceptions;


public class PendingPromoveException extends IllegalMoveException {
    public PendingPromoveException() {
        super();
    }

    public PendingPromoveException(String message) {
        super(message);
    }
}