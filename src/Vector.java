public class Vector {
    private int posX, posY;
    private int magnitude= 0; 
    private boolean largeMove; 

    public Vector(int posX, int posY, int magnitude, boolean largeMove) {
        this.posX = posX;
        this.posY = posY;
        this.largeMove = largeMove;
        if( magnitude >= 0){
            this.magnitude = magnitude;
        }
    }

    public Position  getNextMove(Position position){
        if( magnitude > 0 || largeMove){
            Position next = new Position(position.getPosX() + this.posX,position.getPosY() + this.posY);
            if(!largeMove)
            this.magnitude--;
            return next; 
        }else{
            return null;
        }
    }
}
