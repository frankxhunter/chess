package logic.boardChess;

import logic.board.EngineWithPromove;
import logic.board.Piece;
import logic.board.utils.StateGame;
import org.json.JSONObject;

import java.util.ArrayList;

public class GameChessManager {

    private EngineWithPromove engineGame;

    private StateGame stateGame;

    // TODO hacer el controlador del tiempo
    // private Cronometro

    public GameChessManager(){
        this.engineGame = new EngineChess(8, 8, true);
        this.stateGame = StateGame.IN_PROCCESS;
    }



    public String statusGame(){
        JSONObject gameJson = new JSONObject();

        //Establecer el largo y el ancho del tablero
        gameJson.put("width", this.engineGame.getWidthBoard());
        gameJson.put("height", this.engineGame.getHeightBoard());

        //Establece el estado actual del juego
        gameJson.put("statusGame", this.stateGame);

        //Establecer la piezas con su posicion respectiva
        JSONObject piecesJson[] = this.getPiecesJson();
        gameJson.put("pieces", piecesJson);
        return gameJson.toString();
    }

    private JSONObject[]  getPiecesJson() {
        ArrayList<Piece> listPieces = this.engineGame.getPieces();
        JSONObject piecesJson []= new JSONObject[listPieces.size()];
        for (int i = 0; i < piecesJson.length; i++) {
            piecesJson[i] = this.getPieceJson(listPieces.get(i));
        }
        return piecesJson;
    }

    private JSONObject getPieceJson(Piece piece) {
        JSONObject pieceJson = new JSONObject();
        pieceJson.put("type", piece.getClass().getSimpleName());
        pieceJson.put("color", piece.getColor());
        pieceJson.put("x", piece.getPosition().getPosX());
        pieceJson.put("y", piece.getPosition().getPosY());
        return pieceJson;
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
