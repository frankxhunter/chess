package chess.logic;

import java.util.Iterator;

// TODO Convectir los vectores en una clase iterable
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

    public Iterator<Position> iterator(Position pos){return new Itr(pos);}

    private class Itr implements Iterator<Position>{
        public int realizedMove;
        public Position lastPostion;

        public Itr(Position pos){
            lastPostion = pos;
        }

        public boolean hasNext(){
            return realizedMove < magnitude || largeMove;
        }

        public Position next(){
            if(hasNext()){
               lastPostion = lastPostion.increaseBy(posX, posY);
               return lastPostion;
            }
            return null;
        }
    }
}
