package robtheblock.objects;

import robtheblock.characters.Burglar;
import robtheblock.characters.Position;

/**
 * represents the objects 
 */
public abstract class GameObject {
    //takes the poistion from the position class in characters
    protected Position position;

    
    /**
     * constructor for the position
     * @param position for the object
     */
    public GameObject(Position position){
        this.position = position;
    }

    
    /**
     * gets positon for the characters
     * @return postion of the object
     */
    public Position getPosition(){
        return position;
    }

    public void onPlayerStep(Burglar burglar){}

   
 
}
