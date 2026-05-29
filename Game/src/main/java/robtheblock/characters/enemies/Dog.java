package robtheblock.characters.enemies;

import robtheblock.characters.Position;  

/**
 * represents the dog that reacts to a toy when stept on
 */
public class Dog extends Enemy {
    /**
     * states for the dog
     */
    private enum DogState{
        SLEEPING, GOING_TO_TOY, RETURNING, WAITING
    }
    
    private DogState state;
    private Position toy;
    private Position homePosition;
    public float waitCounter;
    private ChaseAlgorithm chaseAlgoritm = new ChaseAlgorithm();
  

 
    /**
     * movement speed for the dog
     */
    private static float MOVESPEED = 600f;

    /**
     * constuctor for the dog
     * @param x x coordinate
     * @param y y coordiante
     * @param levelHeight height of the level
     */
    public Dog(int x, int y, int levelHeight) {
        super(x,y,MOVESPEED,levelHeight);
        this.toy = null;
        this.homePosition = new Position(x,y);
        this.state = DogState.SLEEPING;
        this.waitCounter = 0;
        
    }
    
    /**
     * upadate teh dogs movement given the state
     */
    @Override
    public void move() {
        switch (state) {
            //dog is not moving
            case SLEEPING:
                break;

            //toy has been stepped on
            case GOING_TO_TOY:
                chase(toy);

                //when it gets to the toy it goes into the state wait
                if (getPosition().getX() == toy.getX() &&
                    getPosition().getY() == toy.getY()){
                    waitCounter= 30; 
                    state = DogState.WAITING;
                }
                
                break;

                //returning back to the "sleeping" position
            case RETURNING:
                chase(homePosition);
                //once dog is back to original position it returns to sleeping state
                if (getPosition().getX() == homePosition.getX() &&
                    getPosition().getY() == homePosition.getY()){
                    state = DogState.SLEEPING;
                }
                break;
                
            case WAITING:
                //wait counter to return to position
                waitCounter--;
                
                //return when wait is over
                if(waitCounter <=0){
                    state = DogState.RETURNING;
                }
                break;

            default:
                break;
        }
    }
    
    /**
     * starts the dogs chase to a position
     * @param toyPosition postion of the toy
     */
    public void startChase(Position toyPosition) {
        toy = new Position(toyPosition.getX(), toyPosition.getY());
        state = DogState.GOING_TO_TOY;
    }


    /**
     * the dogs moves towards the toy target
     * 
     * @param target position the dog will move to
     */
    private void chase(Position target ){
        Position next = chaseAlgoritm.nextStep(getPosition(), target);

        getPosition().setX(next.getX());
        getPosition().setY(next.getY());
        setTarget(next.getX(), next.getY());
        
    }
        
}