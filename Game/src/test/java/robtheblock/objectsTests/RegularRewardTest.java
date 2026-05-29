package robtheblock.objectsTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import robtheblock.characters.Burglar;
import robtheblock.characters.Position;
import robtheblock.objects.RegularReward;

/**
 * Unit tests for RegularReward.
 */
public class RegularRewardTest {

    private Burglar burglar;

    @BeforeEach
    void setUp() {
        burglar = new Burglar(2, 2, 10, 0);
    }

    @Test
    void getRegularPoints_ReturnsCorrectValue() {
        RegularReward reward = new RegularReward(new Position(1, 1), 20);
        assertEquals(20, reward.getRegularPoints());
    }

    @Test
    void getRewardType_ReturnsRegularReward() {
        RegularReward reward = new RegularReward(new Position(1, 1), 20);
        assertEquals("Regular Reward", reward.getRewardType());
    }

    @Test
    void getPosition_ReturnsCorrectPosition() {
        Position pos = new Position(4, 6);
        RegularReward reward = new RegularReward(pos, 10);
        assertEquals(pos, reward.getPosition());
    }

    @Test
    void onPlayerStep_IncreasesScore() {
        RegularReward reward = new RegularReward(new Position(1, 1), 25);
        reward.onPlayerStep(burglar);
        assertEquals(25, burglar.getScore());
    }

    @Test
    void onPlayerStep_IncrementsRewardCounter() {
        RegularReward reward = new RegularReward(new Position(1, 1), 25);
        reward.onPlayerStep(burglar);
        assertEquals(1, burglar.getRewardsCollected());
    }

    @Test
    void onPlayerStep_MultipleCollections_AccumulatesScoreAndCounter() {
        RegularReward reward1 = new RegularReward(new Position(1, 1), 10);
        RegularReward reward2 = new RegularReward(new Position(2, 2), 15);
        reward1.onPlayerStep(burglar);
        reward2.onPlayerStep(burglar);
        assertEquals(25, burglar.getScore());
        assertEquals(2, burglar.getRewardsCollected());
    }
}