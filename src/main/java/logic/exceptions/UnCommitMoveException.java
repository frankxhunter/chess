package logic.exceptions;

public class UnCommitMoveException extends Exception {
    public UnCommitMoveException() {
        super();
    }

    public UnCommitMoveException(String message) {
        super(message);
    }
}