package logic.tools;


import logic.board.utils.Color;
import logic.board.utils.Position;

import java.util.ArrayList;
import java.util.Iterator;

public class Tools {
    public static Color changeColor(Color color) {
        return color == Color.WHITE ? Color.BLACK : Color.WHITE;
    }
    public static boolean hasThisFinalMove(ArrayList<Position> moves, Position move) {
        boolean found = false;
        for (Position m : moves) {
            if (m.equals(move)) {
                found = true;
                break;
            }
        }
        return found;
    }

    // Permite saber si este movimiento es posible
    public static boolean hasThisMove(Position initialPosition, Position finalPosition, ArrayList<Position[]> moves) {
        boolean found = false;
        Iterator<Position[]> it = moves.iterator();
        while (it.hasNext() && !found) {
            Position[] move = it.next();
            if (move[0].equals(initialPosition) && move[1].equals(finalPosition)) {
                found = true;
            }
        }

        return found;
    }


}
