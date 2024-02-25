public class Vector {
    private int posX, posY;
    private int magnitude; 

    public Vector(int posX, int posY, int magnitude){
        this.posX = posX;
        this.posY = posY;
        if( magnitude >= 0){
            this.magnitude = magnitude;
        }
    }

    public Position  getNextMove(Position position){
        if( magnitude > 0){
            Position next = new Position(position.getPosX() + this.posX,position.getPosY() + this.posY);
            this.magnitude--;
            return next; 
        }else{
            return null;
        }
    }
}
