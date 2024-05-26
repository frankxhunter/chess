package logic.tools.translators;

import logic.board.Piece;
import logic.board.utils.Color;
import logic.board.utils.Position;

import java.util.ArrayList;

public interface Translator {
     String statusGameTranslator(int width, int height, ArrayList<Piece> listPieces);

    String currentMovesTranslator(ArrayList<Position[]> moves, Color turnPlayer);

    Position[] moveTranslatorToString(String moveData);
}
