package chess.logic.tools;

import chess.logic.board.utils.Color;
import chess.logic.board.utils.Position;

import java.util.ArrayList;

public class Tools {
    public static Color changeColor(Color color) {
        return color == Color.WHITE ? Color.BLACK : Color.WHITE;
    }
    public static boolean hasThisMove(ArrayList<Position> moves, Position move) {
        boolean found = false;
        for (Position m : moves) {
            if (m.equals(move)) {
                found = true;
                break;
            }
        }
        return found;
    }

}
