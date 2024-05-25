import logic.board.Board;
import logic.board.Piece;
import logic.board.utils.Color;
import logic.board.utils.Position;
import logic.boardChess.pieces.*;
import logic.exceptions.UnCommitMoveException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private Board board;
    @BeforeEach
    void setUp() {
        board = new Board(8, 8);
        board.putPiece(new Rook(Color.WHITE), new Position(0, 0));
        board.putPiece(new Knight(Color.WHITE), new Position(1, 0));
        board.putPiece(new Bishop(Color.WHITE), new Position(2, 0));
        board.putPiece(new King(Color.WHITE), new Position(3, 0));
        board.putPiece(new Queen(Color.WHITE), new Position(4, 0));
        board.putPiece(new Bishop(Color.WHITE), new Position(5, 0));
        board.putPiece(new Knight(Color.WHITE), new Position(6, 0));
        board.putPiece(new Rook(Color.WHITE), new Position(7, 0));
        for (int i = 0; i < 8; i++)
            board.putPiece(new Pawn(Color.WHITE), new Position(i, 1));

        board.putPiece(new Rook(Color.BLACK), new Position(0, 7));
        board.putPiece(new Knight(Color.BLACK), new Position(1, 7));
        board.putPiece(new Bishop(Color.BLACK), new Position(2, 7));
        board.putPiece(new King(Color.BLACK), new Position(3, 7));
        board.putPiece(new Queen(Color.BLACK), new Position(4, 7));
        board.putPiece(new Bishop(Color.BLACK), new Position(5, 7));
        board.putPiece(new Knight(Color.BLACK), new Position(6, 7));
        board.putPiece(new Rook(Color.BLACK), new Position(7, 7));

        for (int i = 0; i < 8; i++)
            board.putPiece(new Pawn(Color.BLACK), new Position(i, 6));
    }

    @AfterEach
    void tearDown() {
        board = null;

    }

    @Test
    void getPiecesByType() {
        ArrayList<Piece> pawns = board.getPiecesByType("Pawn", Color.BLACK);
        assertEquals(pawns.size(), 8);

        for (Piece p :
                pawns) {
            assertTrue(p instanceof Pawn);
            System.out.println(p.getClass().getSimpleName());
        }
    }

    @Test
    public void unCommitMove() {
        try {
            board.movePiece(new Position(4, 1), new Position(4, 3));
            board.movePiece(new Position(5, 6), new Position(5, 4));
            board.printBoard();

            board.doUnCommitMove(new Position(4, 3), new Position(5, 4));
            //assertThrows(UnCommitMoveException.class, ()->{
            //    board.movePiece(new Position(4, 1), new Position(4, 3));
            //});
            board.printBoard();
            board.rollBack();
            board.printBoard();
        } catch (UnCommitMoveException e) {
            e.printStackTrace();
            fail("No deberia saltar ninguna exception");

        }
    }
}