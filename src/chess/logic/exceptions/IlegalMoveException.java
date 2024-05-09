package chess.logic.exceptions;

public class IlegalMoveException extends RuntimeException{
    public IlegalMoveException(){
        super();
    }
    public IlegalMoveException(String message){
        super(message);
    }
}

