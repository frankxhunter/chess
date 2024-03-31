import java.util.ArrayList;
import java.util.Scanner;

import Logic.Game;
import Logic.Position;

public class Main {
    public static void main(String[] args) {
        Game chess = new Game(8, 8);
        Scanner scan = new Scanner(System.in);
        while (true != false) {
            chess.printBoard();
            System.out.println("Cual pieza quieres mover: ");
            System.out.print("Selecciona la posicion en las X: ");
            int posX = scan.nextInt();

            System.out.print("Selecciona la posicion en las Y: ");
            int posY = scan.nextInt();

            ArrayList<Position> moves = chess.getMovesOfPiece(new Position(posX, posY));
            int i = 0;
            System.out.println("Selecciona uno de los siguientes movimientos para la pieza que seleccionaste:");
            for (Position e : moves) {
                System.out.println(i++ + ": "+ e.toString());
            }
        }
    }
}
