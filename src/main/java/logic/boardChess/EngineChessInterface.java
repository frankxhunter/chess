package logic.boardChess;

import logic.board.EngineWithPromove;

public interface EngineChessInterface extends EngineWithPromove {
    boolean isKingInCheck();
}
