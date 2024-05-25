package logic.boardChess;

import logic.board.EngineWithPromove;
import logic.board.utils.StateGame;

public class GameChessManager {

    private EngineWithPromove engineGame;

    private StateGame stateGame;

    // TODO hacer el controlador del tiempo
    // private Cronometro

    public GameChessManager(){
        this.engineGame = new EngineChess(8, 8, true);
        this.stateGame = StateGame.IN_PROCCESS;
    }

    public String stateGame(){
        return "";
    }
    public String currentMove(){
        return "";
    }
    public String doMove(String move){
        return "";
    }

    private void refreshStateGame(){
        // TODO
    }
}
