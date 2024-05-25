package logic.board.utils;


public class Position {
    private final int posX;
    private final int posY;

    public Position(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Position) {
            Position pos = (Position) obj;
            return pos.getPosX() == posX && pos.getPosY() == posY;
        }

        return false;
    }

    public Position increaseBy(int movesX, int movesY) {
        return new Position(posX + movesX, posY + movesY);
    }

    @Override
    public String toString() {
        return "(" + posX + ", " + posY + ")";
    }

    // Este metodo se utiliza para que devuelva la posicion aumentandole en un determinado valor
    // Es util para que no comenzar a contar desde cero
    public String toString(int add) {
        return "(" + (posX + add) + ", " + (posY + add) + ")";
    }

}
