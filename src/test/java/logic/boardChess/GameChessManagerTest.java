package logic.boardChess;

import logic.tools.translators.JsonTranslator;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameChessManagerTest {
    private GameChessManager gameChessManager;

    @BeforeEach
    void setUp() {
        this.gameChessManager = new GameChessManager();
    }

    @AfterEach
    void tearDown() {
        this.gameChessManager = null;
    }

    @Test
    void testStatusGame(){
        System.out.println(this.gameChessManager.statusGame());
    }
    @Test
    void testCurrentMoves(){
        System.out.println(this.gameChessManager.currentMoves());
    }
    @Test
    void testDoMove(){
        String moveData = "{\n" +
                "      \"initialPosition\": {\n" +
                "        \"x\": 1,\n" +
                "        \"y\": 0\n" +
                "      },\n" +
                "      \"finalPosition\": {\n" +
                "        \"x\": 2,\n" +
                "        \"y\": 2\n" +
                "      }\n" +
                "    }";

        String moveDataIncorrect = "{\n" +
                "      \"initial\": {\n" +
                "        \"x\": 1,\n" +
                "        \"y\": 0\n" +
                "      },\n" +
                "      \"finalPosition\": {\n" +
                "        \"x\": 2,\n" +
                "        \"y\": 2\n" +
                "      }\n" +
                "    }";
        JSONObject correctResult = new JSONObject(this.gameChessManager.doMove(moveData));
        JSONObject inCorrectResult = new JSONObject(this.gameChessManager.doMove(moveDataIncorrect));

        assertTrue(correctResult.getBoolean("correct"));
        assertFalse(inCorrectResult.getBoolean("correct"));

    }

    @Test
    void testDoPromotion(){
        JsonTranslator ts = new JsonTranslator();
        this.gameChessManager.doMove(ts.moveToJsonPlus(4,2,4,4));
        this.gameChessManager.doMove(ts.moveToJsonPlus(5,7,5,5));
        this.gameChessManager.doMove(ts.moveToJsonPlus(4,4,5,5));
        this.gameChessManager.doMove(ts.moveToJsonPlus(3,7,3,5));
        this.gameChessManager.doMove(ts.moveToJsonPlus(5,5,5,6));
        this.gameChessManager.doMove(ts.moveToJsonPlus(3,5,3,4));
        this.gameChessManager.doMove(ts.moveToJsonPlus(5,6,6,7));
        this.gameChessManager.doMove(ts.moveToJsonPlus(3,4,3,3));

        this.gameChessManager.doMove(ts.moveToJsonPlus(6,7,7,8));

        // Jugada lista para hacer coronacion
        String result = this.gameChessManager.doMove(ts.moveToJsonPlus(3,3,3,1));
        assertEquals(result, "{\n" +
                "  \"success\": false,\n" +
                "  \"error\": \"There is a pawn awating promotion\"\n" +
                "}");
        JSONObject promotion = new JSONObject();
        promotion.put("promotionTo", "Queen");
        result  = this.gameChessManager.doPromotion(promotion.toString(2));
        assertEquals(result, "{\"success\": true}");
        //System.out.println(result);

        //ts.printGame(this.gameChessManager.statusGame());
    }

}