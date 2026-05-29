package robtheblock.objectsTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import robtheblock.characters.Burglar;
import robtheblock.characters.Position;
import robtheblock.objects.BonusReward;

/**
 * Unit tests for BonusReward.
 */
public class BonusRewardTest {

    private Burglar burglar;

    @BeforeEach
    void setUp() {
        burglar = new Burglar(2, 2, 10, 0);
    }

    @Test
    void getBonusPoints_ReturnsCorrectValue() {
        BonusReward bonus = new BonusReward(new Position(3, 3), 50);
        assertEquals(50, bonus.getBonusPoints());
    }

    @Test
    void getRewardType_ReturnsBonusReward() {
        BonusReward bonus = new BonusReward(new Position(3, 3), 50);
        assertEquals("Bonus Reward", bonus.getRewardType());
    }

    @Test
    void getPosition_ReturnsCorrectPosition() {
        Position pos = new Position(2, 4);
        BonusReward bonus = new BonusReward(pos, 50);
        assertEquals(pos, bonus.getPosition());
    }

    @Test
    void onPlayerStep_IncreasesScore() {
        BonusReward bonus = new BonusReward(new Position(3, 3), 50);
        bonus.onPlayerStep(burglar);
        assertEquals(50, burglar.getScore());
    }

    @Test
    void onPlayerStep_DoesNotIncrementRewardCounter() {
        BonusReward bonus = new BonusReward(new Position(3, 3), 50);
        bonus.onPlayerStep(burglar);
        assertEquals(0, burglar.getRewardsCollected());
    }

    @Test
    void onPlayerStep_MultipleCollections_AccumulatesScoreOnly() {
        BonusReward bonus1 = new BonusReward(new Position(1, 1), 50);
        BonusReward bonus2 = new BonusReward(new Position(2, 2), 25);
        bonus1.onPlayerStep(burglar);
        bonus2.onPlayerStep(burglar);
        assertEquals(75, burglar.getScore());
        assertEquals(0, burglar.getRewardsCollected());
    }
}