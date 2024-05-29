package logic.boardChess;

import logic.board.utils.Color;
import logic.board.utils.Position;
import logic.board.utils.StateGame;
import logic.exceptions.IllegalMoveException;
import logic.exceptions.IllegalPromoveException;
import logic.tools.translators.JsonTranslator;
import logic.tools.translators.TranslatorChess;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GameChessManager {

    private EngineChessInterface engineGame;

    private StateGame stateGame;

    private TranslatorChess translator = new JsonTranslator();

    // TODO hacer el controlador del tiempo
    //Cronometro

    public GameChessManager(){
        this.engineGame = new EngineChess(8, 8, true);
        refreshStateGame();
    }

    public String statusGame(){
    return this.translator.statusGameTranslator(this.engineGame.getWidthBoard(),
            this.engineGame.getHeightBoard(), this.engineGame.getPieces());
    }

    public String currentMoves(){
       return this.translator.currentMovesTranslator(this.engineGame.getCurrentMoves(),
               this.engineGame.getTurnPlayer(),
               this.engineGame.havePiecePendingPromotion());
    }

    public String doMove(String moveData){
        String result = "";
        try {
            Position[] move = this.translator.moveTranslatorToString(moveData);
            this.engineGame.doMove(move[0], move[1]);
            refreshStateGame();

            // All data is correct
            result = translator.success();
        }catch (JSONException e){
            // Error en el formato de entrada
            result = this.translator.error("The formatter is incorrect: " + e.getMessage());
        } catch (IllegalMoveException e) {
            // Error en la informacion enviada
            result = this.translator.error( e.getMessage());

        }

        return result;
    }

    public String doPromotion (String promotionData){
        String result = "";
        try{
            String promotionTo = translator.promotionTranslatorToString(promotionData);
            this.engineGame.piecePromotion(promotionTo);
            result = translator.success();
        } catch (IllegalPromoveException e) {
            result = translator.error(e.getMessage());
        }
        return result;
    }

    private void refreshStateGame(){
        ArrayList<Position[]> currentMoves = this.engineGame.getCurrentMoves();
        Color turnPlayer = this.engineGame.getTurnPlayer();

        if(currentMoves.size() > 0){
            stateGame = StateGame.IN_PROCCESS;
        }
        else if(!this.engineGame.isKingInCheck()){
            stateGame = StateGame.DRAW;
        }
        else if(currentMoves.size()==0){
            if(turnPlayer == Color.BLACK){
                stateGame = StateGame.WHITE_WIN;
            }
            else{
                stateGame = StateGame.BLACK_WIN;
            }
        }

    }
}
