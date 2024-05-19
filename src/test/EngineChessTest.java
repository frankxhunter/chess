package test;

import chess.logic.EngineChess;
import chess.logic.Position;
import chess.logic.pieces.chess.Knight;
import chess.logic.pieces.chess.Pawn;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EngineChessTest {
    private EngineChess game;
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        game = new EngineChess(8, 8);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        game = null;

    }

    @org.junit.jupiter.api.Test
    void getCurrentMoves() {
        ArrayList<Position[]> positionsList = new ArrayList<>();


        // Agregando los pares de posiciones como arrays de dos elementos
        positionsList.add(new Position[]{new Position(1, 0), new Position(2, 2)});
        positionsList.add(new Position[]{new Position(1, 0), new Position(0, 2)});
        positionsList.add(new Position[]{new Position(6, 0), new Position(7, 2)});
        positionsList.add(new Position[]{new Position(6, 0), new Position(5, 2)});
        positionsList.add(new Position[]{new Position(0, 1), new Position(0, 2)});
        positionsList.add(new Position[]{new Position(0, 1), new Position(0, 3)});
        positionsList.add(new Position[]{new Position(1, 1), new Position(1, 2)});
        positionsList.add(new Position[]{new Position(1, 1), new Position(1, 3)});
        positionsList.add(new Position[]{new Position(2, 1), new Position(2, 2)});
        positionsList.add(new Position[]{new Position(2, 1), new Position(2, 3)});
        positionsList.add(new Position[]{new Position(3, 1), new Position(3, 2)});
        positionsList.add(new Position[]{new Position(3, 1), new Position(3, 3)});
        positionsList.add(new Position[]{new Position(4, 1), new Position(4, 2)});
        positionsList.add(new Position[]{new Position(4, 1), new Position(4, 3)});
        positionsList.add(new Position[]{new Position(5, 1), new Position(5, 2)});
        positionsList.add(new Position[]{new Position(5, 1), new Position(5, 3)});
        positionsList.add(new Position[]{new Position(6, 1), new Position(6, 2)});
        positionsList.add(new Position[]{new Position(6, 1), new Position(6, 3)});
        positionsList.add(new Position[]{new Position(7, 1), new Position(7, 2)});
        positionsList.add(new Position[]{new Position(7, 1), new Position(7, 3)});

        //game.getCurrentMoves().forEach(e -> System.out.println(e[0]+""+e[1]));

        // Comprabar que tengan la misma cantidad de elementos
        assertEquals(positionsList.size(), game.getCurrentMoves().size());

        // Comprobar que tenga los mismos movimientos
        for (Position[] positions : positionsList){
            boolean found = false;
            for (int i=0 ; i< game.getCurrentMoves().size() && !found; i++){
                Position[] temporalPosition = game.getCurrentMoves().get(i);
                if( positions[0].isEqual(temporalPosition[0]) && positions[1].isEqual(temporalPosition[1])){
                    found = true;
                }
            }
            assertTrue(found);
        }

    }

    @org.junit.jupiter.api.Test
    void doMove() {
        // Se probaran diferentes movimientos de diferentes piezas
        game.printBoard();
        game.doMove(new Position(3,1), new Position(3, 3));
        assertTrue(game.getPiece(new Position(3, 3)) instanceof Pawn);

        game.doMove(new Position(4,6), new Position(4, 4));
        assertTrue(game.getPiece(new Position(4, 4)) instanceof Pawn);

        game.doMove(new Position(6,0), new Position(5, 2));
        assertTrue(game.getPiece(new Position(5, 2)) instanceof Knight);
        game.printBoard();

    }


}