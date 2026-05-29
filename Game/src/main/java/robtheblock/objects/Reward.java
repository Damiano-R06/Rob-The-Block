package robtheblock.objects;

import robtheblock.characters.Burglar;
import robtheblock.characters.Position;

/**
 * represents the reward the player can collect 
 * different types of rewards
 */
public abstract class Reward extends GameObject{
    //type of reward for the player
    protected String rewardType;
   
    /**
     * constructor for reward 
     * 
     * @param position of the rewards
     * @param rewardType reward type 
     */
    public Reward(Position position, String rewardType){
        super(position);
        this.rewardType = rewardType;
    }

    /**
     * gets the reward type 
     */
    public String getRewardType(){
        return rewardType;
    }

   public void onPlayerStep(Burglar burglar){}
    
}
 