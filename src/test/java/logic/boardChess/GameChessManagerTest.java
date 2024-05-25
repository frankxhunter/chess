package logic.boardChess;

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

}