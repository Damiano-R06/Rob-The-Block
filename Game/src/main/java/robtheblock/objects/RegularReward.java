package robtheblock.objects;
 
import robtheblock.characters.Burglar;
import robtheblock.characters.Position;
 
/**
 * Regular reward that COUNTS toward level completion.
 * 
 * NEW BEHAVIOR:
 * - Increments burglar's rewardsCollected counter (required to open exit door)
 * - Adds coins to burglar's score
 * 
 * This is different from BonusReward which only adds coins without incrementing counter.
 */
public class RegularReward extends Reward {
    private int points;  // Coin value of this reward
 
    /**
     * Creates a regular reward at the specified position.
     * 
     * @param position Position on grid
     * @param points Coin value (10, 15, 20, or 25 depending on level)
     */
    public RegularReward(Position position, int points){
        super(position, "Regular Reward");
        this.points = points;
    }
 
    /**
     * Gets the coin value of this reward.
     * 
     * @return Point value
     */
    public int getRegularPoints(){
        return points;
    }
 
    /**
     * Called when burglar steps on this reward.
     * Does TWO things:
     * 1. Increments rewardsCollected counter (toward level completion)
     * 2. Adds coins to score
     * 
     * @param burglar The burglar collecting this reward
     */
    @Override
    public void onPlayerStep(Burglar burglar){
        burglar.collectReward();
        burglar.updateScore(points);  // Adds coins
    } 
}
