package test;

import chess.logic.board.utils.Color;
import chess.logic.board.utils.Position;
import chess.logic.boardChess.EngineChess;
import chess.logic.boardChess.pieces.*;
import chess.logic.exceptions.IlegalMoveException;
import chess.logic.exceptions.IllegalPromoveException;
import chess.logic.exceptions.PendingPromoveException;
import chess.logic.tools.Tools;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EngineChessTest {
    private EngineChess game;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        game = null;

    }

    @org.junit.jupiter.api.Test
    void getCurrentMoves() {
        game = new EngineChess(8, 8, true);

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
        for (Position[] positions : positionsList) {
            boolean found = false;
            for (int i = 0; i < game.getCurrentMoves().size() && !found; i++) {
                Position[] temporalPosition = game.getCurrentMoves().get(i);
                if (positions[0].equals(temporalPosition[0]) && positions[1].equals(temporalPosition[1])) {
                    found = true;
                }
            }
            assertTrue(found);
        }

    }

    @org.junit.jupiter.api.Test
    void doMove() {
        try {
            // Se probaran diferentes movimientos de diferentes piezas
            game = new EngineChess(8, 8, true);
            //game.printBoard();
            //game.getCurrentMoves().forEach(e -> System.out.println(e[0]+""+ e[1]));

            game.doMove(new Position(3, 1), new Position(3, 3));
            assertTrue(game.getPiece(new Position(3, 3)) instanceof Pawn);

            game.doMove(new Position(4, 6), new Position(4, 4));
            assertTrue(game.getPiece(new Position(4, 4)) instanceof Pawn);

            game.doMove(new Position(6, 0), new Position(5, 2));
            assertTrue(game.getPiece(new Position(5, 2)) instanceof Knight);
            //game.printBoard();
        } catch (Exception e) {
            e.printStackTrace();
            fail("No deberia saltar ninguna exception");
        }
    }

    // Probando el peon al paso
    @Test
    void checkEnPassant() {
        game = new EngineChess(4, 4, false);
        try {
            game.setupPiece(new Pawn(Color.WHITE), new Position(1, 1));
            game.setupPiece(new Pawn(Color.BLACK), new Position(2, 3));
            game.doMove(new Position(1, 1), new Position(1, 3));

            // Realizar movimiento de peon al paso
            game.doMove(new Position(2, 3), new Position(1, 2));

            assertNull(game.getPiece(new Position(1, 3)));
            assertTrue(game.getPiece(new Position(1, 2)) instanceof Pawn);
        } catch (Exception e) {
            e.printStackTrace();

            fail("No deberia saltar ninguna exception");
        }
        //game.getCurrentMoves().forEach(e -> System.out.println(e[0]+""+ e[1]));
        //game.printBoard();
    }

    // Probando el enroque
    @Test
    void checkCastle() {
        game = new EngineChess(8, 8, false);
        game.setupPiece(new King(Color.WHITE), new Position(3, 0));
        game.setupPiece(new Rook(Color.WHITE), new Position(7, 0));
        game.setupPiece(new Queen(Color.BLACK), new Position(4, 7));
        game.setupPiece(new Rook(Color.WHITE), new Position(0, 0));

        // game.printBoard();
        // game.getMovesOfPiece(new Position(3,0)).forEach(e -> System.out.println(e.toString(1)));

        // Comprobar que es posible hacer los moviemientos de enroque
        assertFalse(Tools.hasThisMove(game.getMovesOfPiece(new Position(3, 0)), new Position(5, 0)));
        assertTrue(Tools.hasThisMove(game.getMovesOfPiece(new Position(3, 0)), new Position(1, 0)));

        // Comprobar q el movimiento funciona
        assertThrows(IlegalMoveException.class, () -> {
            game.doMove(new Position(3, 0), new Position(5, 0));
        });
        try {
            game.doMove(new Position(3, 0), new Position(1, 0));
        } catch (Exception e) {
            fail("Este movimiento deberia estar permitido");
        }
        //game.printBoard();


    }

    // Este test comprueba la funcionalidad de coronacion del peon
    @Test
    void promovedPawn() {
        game = new EngineChess(8, 8, false);
        game.setupPiece(new King(Color.BLACK), new Position(3, 0));
        game.setupPiece(new Pawn(Color.WHITE), new Position(3, 6));
        try {
            game.doMove(new Position(3, 6), new Position(3, 7));
        } catch (Exception e) {
            e.printStackTrace();
            fail("No deberia saltar ninguna exception");
        }
        assertThrows(PendingPromoveException.class, () -> {
            game.doMove(new Position(3, 0), new Position(3, 1));
        });
        try {
            game.promovePawn("Knight");
        } catch (IllegalPromoveException e) {
            e.printStackTrace();
            fail("The promotion is correct, but something fail");
        }
        game.printBoard();

    }

    // Aqui se prueba si los movimientos permitidos son legales, y no provocan un jacke propio
    @Test
    void legalMoves() {
        game = new EngineChess(8, 8, false);
        game.setupPiece(new King(Color.WHITE), new Position(3, 0));
        game.setupPiece(new Knight(Color.WHITE), new Position(3, 1));
        //game.setupPiece(new Rook(Color.BLACK), new Position(3, 7));
        game.setupPiece(new Rook(Color.BLACK), new Position(4, 7));
        game.setupPiece(new Bishop(Color.BLACK), new Position(6, 3));

        game.printBoard();
        game.getCurrentMoves().forEach(e -> System.out.println(e[0].toString(1) + "" + e[1].toString(1)));
    }


}