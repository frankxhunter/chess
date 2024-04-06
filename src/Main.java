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
            System.out.print("Selecciona la posición en las X: ");
            int posX = scan.nextInt();

            System.out.print("Selecciona la posición en las Y: ");
            int posY = scan.nextInt();

            Position initialPosition = new Position(posX, posY); 
            ArrayList<Position> moves = chess.getMovesOfPiece(initialPosition);
            int i = 0;
            System.out.println("Selecciona uno de los siguientes movimientos para la pieza que seleccionaste:");
            for (Position e : moves) {
                System.out.println(i++ + ": "+ e.toString());
            }
            System.out.println("Elige un movimiento(0-"+(i-1)+"): ");
            int moveSelected = scan.nextInt();
            boolean result = chess.movePiece(initialPosition, moves.get(moveSelected));
            if(result){
                System.out.println("El movimiento fue realizado con éxito");
            }else{
                System.out.println("No es posible realizar dicho movimiento");
            }
        }
    }
}
