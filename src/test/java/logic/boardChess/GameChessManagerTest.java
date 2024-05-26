package logic.boardChess;

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

}