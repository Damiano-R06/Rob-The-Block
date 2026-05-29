package robtheblock.objects;

import robtheblock.characters.Burglar;
import robtheblock.characters.Position;
 /**
  * Represent the bonus reward when player steps on
  */
public class BonusReward extends Reward{
    //bounes points for the user
    protected int bonusPoints;

    
    /**
     * constructor for the bonus points
     * @param position of the reward
     * @param bonusPoints number of points
     */
    public BonusReward(Position position, int bonusPoints){
        super(position, "Bonus Reward");
        this.bonusPoints = bonusPoints;
    }

    
    /**
     * getter for the bonus points 
     * @return  the value of the bonus points
     */
    public int getBonusPoints(){
        return bonusPoints;
    } 

    /**
     * gives the player the reward when stepped on
     */
    @Override
    public void onPlayerStep(Burglar burglar){
        burglar.updateScore(bonusPoints);
    }
    
}
