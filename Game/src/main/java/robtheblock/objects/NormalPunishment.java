package robtheblock.objects;

import robtheblock.characters.Burglar;

/**
 * Standard punishment that reduces the player's score.
 * 
 * NEW BEHAVIOR:
 * - Subtracts coins from score (score can go negative)
 * - Checks if out of coins (currently just logs warning)
 * 
 * Used for dog proximity punishment (5 coins) and other penalties.
 */
public class NormalPunishment extends Punishment {
    protected int pointReduction;  // Amount of coins to subtract

    /**
     * Creates a punishment with the specified point reduction.
     * 
     * @param pointReduction Coins to subtract (typically 5, 25, or 50)
     */
    public NormalPunishment(int pointReduction){
        super();
        this.pointReduction = pointReduction;
    }

    /**
     * Gets the coin penalty amount.
     * 
     * @return Point reduction value
     */
    public int getPointReduction(){
        return pointReduction;
    }

    /**
     * Called when burglar steps on or triggers this punishment.
     * Subtracts coins and checks if out of coins.
     * 
     * NOTE: Score can go negative! No game over triggered.
     * 
     * @param burglar The burglar being punished
     */
    @Override
    public void onPlayerStep(Burglar burglar){
        burglar.minusScore(pointReduction);
    }
} 