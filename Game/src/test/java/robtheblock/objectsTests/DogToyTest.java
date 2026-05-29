package robtheblock.objectsTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import robtheblock.characters.Burglar;
import robtheblock.characters.Position;
import robtheblock.objects.DogToy;

/**
 * Unit tests for DogToy.
 */
public class DogToyTest {

    private Burglar burglar;

    @BeforeEach
    void setUp() {
        burglar = new Burglar(2, 2, 0, 30);
    }

    @Test
    void getPosition_ReturnsCorrectPosition() {
        Position pos = new Position(3, 5);
        DogToy dogToy = new DogToy(pos);
        assertEquals(pos, dogToy.getPosition());
    }

    @Test
    void onPlayerStep_DoesNotChangeScore() {
        DogToy dogToy = new DogToy(new Position(1, 1));
        dogToy.onPlayerStep(burglar);
        assertEquals(30, burglar.getScore());
    }

    @Test
    void onPlayerStep_DoesNotIncrementRewardCounter() {
        DogToy dogToy = new DogToy(new Position(1, 1));
        dogToy.onPlayerStep(burglar);
        assertEquals(0, burglar.getRewardsCollected());
    }
}