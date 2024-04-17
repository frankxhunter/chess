package Logic;

import java.util.ArrayList;

public abstract class Piece {
    private Color color;
    private String simbology = null;
    private StatusBoard board;
    private Position position;

    public Piece(Color color) {
        this.color = color;
    }
    public String getSimbology() {
        return simbology;
    }
    public Position getPosition() {
        return position;
    }
    public void setPosition(Position position) {
        this.position = position;
    }

    public Color getColor() {
        return color;
    }

    public void setSimbology(String simbology) {
        this.simbology = simbology;
    }

    public abstract ArrayList<Position> getMoves();

    public StatusBoard getBoard() {
        return board;
    }

    @Override
    public String toString() {
        return simbology + (color == Color.BLACK ? "º" : " ");
    }

}
