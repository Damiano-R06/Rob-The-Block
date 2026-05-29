package robtheblock.characters.enemies;
import java.util.ArrayList;
import java.util.List; 

import robtheblock.characters.Position; 
import robtheblock.level.*;


/**
 * is the homeowner and how ot moves along a path that it patrols
 */
public class Homeowner extends Enemy {

    private ChaseAlgorithm chaseAlgorithm = new ChaseAlgorithm();

    
    /**
    * the lost of postions for the homeowner to patrol
    */
    private List<Position> patrolPath;

    /**
     * the index for the patrol
     */
    private int patrolIndex;

  
    /**
     * constructor for the homeowner at the starting locations
     * 
     * @param x starting x cord
     * @param y starting y cord
     * @param levelHeight height of the level
     */
    public Homeowner(int x, int y, int levelHeight) {
        super(x, y, 200f, levelHeight);
        this.patrolPath = new ArrayList<>();
        this.patrolIndex = 0;
        
    }

    /**
     * sets the patrol path for the homeowner
     * If the patrol path is not empty the poition is updated
     * 
     * @param patrolPath list of postions for the homeowner
     */
    public void setPatrolPath(List<Position> patrolPath){
        this.patrolPath = patrolPath;
        this.patrolIndex = 0;

        //Start at the first patrol point if it exists
         if (!patrolPath.isEmpty()){
            currentPosition = patrolPath.get(0);
         }
    }
    

    /**
     * move the homeowner to the next postions
     * If the path is empty then return
     */
    @Override
    public void move() {
        if(patrolPath == null || patrolPath.isEmpty()){
            return;
        }

        //move to the next partol coordinates
        patrolIndex = (patrolIndex + 1) % patrolPath.size();
        currentPosition = patrolPath.get(patrolIndex);

        //update the sprite to move 
        setTarget(currentPosition.getX(), currentPosition.getY());

    }

    /**
     * chase class that chases the burglar
     * 
     * @param target the burglar
     * @param level level that they are on
     */
    public void chase(Position target, Level level){

        Position next = chaseAlgorithm.nextStep(getPosition(), target);

        if(level.isValidMove(next.getX(),next.getY())){
            getPosition().setX(next.getX());
            getPosition().setY(next.getY());
            setTarget(next.getX(), next.getY());
        }
        
    }

}