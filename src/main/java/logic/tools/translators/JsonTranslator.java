package logic.tools.translators;

import logic.board.Piece;
import logic.board.utils.Color;
import logic.board.utils.Position;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonTranslator implements Translator {

    public String statusGameTranslator(int width, int height, ArrayList<Piece> listPieces){
        JSONObject gameJson = new JSONObject();

        //Establecer el largo y el ancho del tablero
        gameJson.put("width", width);
        gameJson.put("height", height);

        gameJson.put("pieces",this.piecesTranslator(listPieces));

        return gameJson.toString(2);
    }

    public String currentMovesTranslator(ArrayList<Position[]> moves, Color turnPlayer){
        JSONObject currentMovesJson = new JSONObject();

        JSONObject currentMoves[] = new JSONObject[moves.size()];
        for (int i = 0; i < moves.size(); i++) {
            currentMoves[i] = this.moveTransalator(moves.get(i));
        }
        currentMovesJson.put("playerTurn", turnPlayer);
        currentMovesJson.put("moves",currentMoves);
        return currentMovesJson.toString(2);
    }

    public JSONObject[] piecesTranslator(ArrayList<Piece> listPieces) {
        JSONObject piecesJson []= new JSONObject[listPieces.size()];
        for (int i = 0; i < piecesJson.length; i++) {
            piecesJson[i] = this.pieceTranslator(listPieces.get(i));
        }
        return piecesJson;
    }

    public JSONObject pieceTranslator(Piece piece) {
        JSONObject pieceJson = new JSONObject();
        pieceJson.put("type", piece.getClass().getSimpleName());
        pieceJson.put("color", piece.getColor());
        pieceJson.put("position",this.getPositionJson(piece.getPosition()) );
        return pieceJson;
    }
    public JSONObject moveTransalator(Position[] positions) {
        JSONObject positionsJson = new JSONObject();

        positionsJson.put("initialPosition", this.getPositionJson(positions[0]));
        positionsJson.put("finalPosition", this.getPositionJson(positions[1]));

        return positionsJson;
    }
    public JSONObject getPositionJson(Position position) {
        JSONObject positionJson = new JSONObject();
        positionJson.put("x",position.getPosX() );
        positionJson.put("y", position.getPosY());


        return positionJson;
    }

    public Position[] moveTranslatorToString(String moveData)  {
            JSONObject moveJson = new JSONObject(moveData);
            JSONObject initialPosition = moveJson.getJSONObject("initialPosition");
            JSONObject finalPosition = moveJson.getJSONObject("finalPosition");

            Position[] move = new Position[2];
            move[0] = new Position(initialPosition.getInt("x"), initialPosition.getInt("y"));
            move[1] = new Position(finalPosition.getInt("x"), finalPosition.getInt("y"));
        return move;
    }
}
