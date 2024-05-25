package chess.logic;

import chess.logic.board.utils.Position;
import chess.logic.boardChess.EngineChess;
import chess.logic.exceptions.IlegalMoveException;

import java.util.ArrayList;
import java.util.Scanner;


public class main {
    public static void main(String[] args) {
        EngineChess chess = new EngineChess(8, 8, true);
        try (Scanner scan = new Scanner(System.in)) {

            while (true) {
                chess.printBoard();
                System.out.println("Cual pieza quieres mover ");
                System.out.print("Selecciona la posición horizontal: ");
                int posX = scan.nextInt() - 1;

                System.out.print("Selecciona la posición vertical: ");
                int posY = scan.nextInt() - 1;

                Position initialPosition = new Position(posX, posY);
                ArrayList<Position> moves = chess.getMovesOfPiece(initialPosition);
                int i = 0;
                System.out.println("Selecciona uno de los siguientes movimientos para la pieza que seleccionaste:");
                if (moves != null) {
                    System.out.println(chess.getPiece(initialPosition));
                    if (moves.size() > 0) {
                        for (Position e : moves) {
                            System.out.println(i++ + ": " + e.toString(1));
                        }
                        System.out.println("Elige un movimiento(0-" + (i - 1) + "): ");
                        int moveSelected = scan.nextInt();
                        try {
                            chess.doMove(initialPosition, moves.get(moveSelected));
                            System.out.println("El movimiento fue realizado con éxito");
                        } catch (IlegalMoveException e) {
                            System.out.println("No es posible realizar dicho movimiento");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else
                        System.out.println("La pieza seleccionada no tiene movimientos");
                } else {
                    System.out.println("No hay ninguna pieza en la posicion seleccionada");
                }
            }
        } catch (Error e) {
            e.printStackTrace();
        }
    }
}
