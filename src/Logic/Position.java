package Logic;

public class Position {
    private int posX, posY;

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

    public boolean isEqual(Position pos) {
        if (pos.getPosX() == posX && pos.getPosY() == posY)
            return true;
        return false;
    }

    @Override
    public String toString() {
        return "(" + posX + ", " + posY + ")";
    }
}
