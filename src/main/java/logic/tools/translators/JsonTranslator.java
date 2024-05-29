package logic.tools.translators;

import logic.board.Piece;
import logic.board.Square;
import logic.board.utils.Color;
import logic.board.utils.Position;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.ListIterator;

public class JsonTranslator implements TranslatorChess {

    public String statusGameTranslator(int width, int height, ArrayList<Piece> listPieces){
        JSONObject gameJson = new JSONObject();

        //Establecer el largo y el ancho del tablero
        gameJson.put("width", width);
        gameJson.put("height", height);

        gameJson.put("pieces",this.piecesTranslator(listPieces));

        return gameJson.toString(2);
    }

    public String currentMovesTranslator(ArrayList<Position[]> moves, Color turnPlayer, boolean pendingPromotion){
        JSONObject currentMovesJson = new JSONObject();

        JSONObject currentMoves[] = new JSONObject[moves.size()];
        for (int i = 0; i < moves.size(); i++) {
            currentMoves[i] = this.moveTransalator(moves.get(i));
        }
        currentMovesJson.put("playerTurn", turnPlayer);
        currentMovesJson.put("moves",currentMoves);
        currentMovesJson.put("pendingPromotion", pendingPromotion);
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
        pieceJson.put("simbology",
                piece.getColor() == Color.WHITE?
                        piece.getSimbology()[0]: piece.getSimbology()[1]);

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

    public String promotionTranslatorToString(String promotionData)  {
        JSONObject promotionJson = new JSONObject(promotionData);
        return promotionJson.getString("promotionTo");
    }

    public String error(String message){
        JSONObject error = new JSONObject();
        error.put("success", false );
        error.put("error", message);

        return error.toString(2);
    }

    @Override
    public String success() {
        JSONObject error = new JSONObject();
        error.put("success", true );
        return error.toString(2);
    }

    // Metodo q crea json de movimientos para los test, en teoria no debe ser necesario en el codigo final
    public String moveToJson(int posXInicial, int posYInicial, int posXFinal, int posYFinal){
        JSONObject moveJson = new JSONObject();
        JSONObject initialPosition = new JSONObject();
        JSONObject finalPosition = new JSONObject();

        initialPosition.put("x", posXInicial);
        initialPosition.put("y", posYInicial);

        finalPosition.put("x", posXFinal);
        finalPosition.put("y", posYFinal);

        moveJson.put("initialPosition", initialPosition);
        moveJson.put("finalPosition", finalPosition);

        return moveJson.toString(2);
    }
    public String moveToJsonPlus(int posXInicial, int posYInicial, int posXFinal, int posYFinal){
        return moveToJson(posXInicial -1,posYInicial -1,
                posXFinal -1, posYFinal -1);
    }

    // Este metodo es para imprimir el tablero en pantalla, tambien es para pruebas
    public void printGame(String dataGame){
        JSONObject jsonDataGame = new JSONObject(dataGame);
        int width = jsonDataGame.getInt("width");
        int height = jsonDataGame.getInt("height");
        JSONArray piecesJson = jsonDataGame.getJSONArray("pieces");

        ArrayList<ArrayList<String>> listPieces = new ArrayList<>();
        for(int i = 0;  i < width; i++){
            ArrayList<String> temp = new ArrayList<>();
            for(int j = 0;  j < height; j++){
                temp.add("🕳");
            }
            listPieces.add(temp);
        }

        for(int i = 0 ; i< piecesJson.length(); i++){
            JSONObject jo = (JSONObject) piecesJson.get(i);
            JSONObject positionJson = jo.getJSONObject("position");
            Position position = new Position(positionJson.getInt("x"),positionJson.getInt("y"));

            listPieces.get(position.getPosY()).set(position.getPosX(), jo.getString("simbology"));
        }




        int i = listPieces.get(0).size();
        ListIterator<ArrayList<String>> it = listPieces.listIterator(listPieces.size());
        System.out.println("  ______________________");
        while (it.hasPrevious()) {

            System.out.print((i--) + " ");
            for (String s : it.previous()) {
                System.out.print("" + s  + " ");
            }
            System.out.println();
        }
        System.out.println("  ______________________");
        System.out.println("  1  2  3  4  5  6  7  8 ");
    }



}
