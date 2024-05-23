package chess.logic.utils;

import chess.logic.Color;

public class Utils {
    public static Color changeColor(Color color){
        return color == Color.WHITE ? Color.BLACK: Color.WHITE;
    }
}
