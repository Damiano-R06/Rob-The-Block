package robtheblock.characters.enemies;

import robtheblock.characters.Position;

/**
 * chase algorithm for the homeowner and also the dog
 */
public class ChaseAlgorithm {
    public Position nextStep(Position current, Position target){
        
        int nextX = current.getX();
        int nextY = current.getY();

        if(current.getX()<target.getX()){
            nextX++;
            
        }else if(current.getX()>target.getX()){
            nextX --;
            
        }else if(current.getY()<target.getY()){
            nextY++;
             
        }else if(current.getY()>target.getY()){
            nextY--;
             
        }

        return new Position(nextX, nextY);
    }
}
