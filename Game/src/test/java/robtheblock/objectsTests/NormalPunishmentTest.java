package robtheblock.objectsTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import robtheblock.characters.Burglar;
import robtheblock.objects.NormalPunishment;

/**
 * Unit tests for NormalPunishment.
 */
public class NormalPunishmentTest {

    private Burglar burglar;

    @BeforeEach
    void setUp() {
        //Burglar(startX, startY, levelHeight, startingScore)
        burglar = new Burglar(2, 2, 10, 50);
    }

    @Test
    void onPlayerStep_DeductsScore() {
        NormalPunishment punishment = new NormalPunishment(5);
        punishment.onPlayerStep(burglar);
        int score = burglar.getScore();
        assertEquals(45, score);
    }
    @Test
    void getPointReduction_ReturnsCorrectValue() {
        NormalPunishment punishment = new NormalPunishment(5);
        assertEquals(5, punishment.getPointReduction());
    }

    @Test
    void onPlayerStep_LargePenalty_DeductsCorrectly() {
        NormalPunishment punishment = new NormalPunishment(50);
        punishment.onPlayerStep(burglar);
        assertEquals(0, burglar.getScore());
    }

    @Test
    void onPlayerStep_ScoreCanGoBelowZero() {
        NormalPunishment punishment = new NormalPunishment(100);
        punishment.onPlayerStep(burglar);
        assertTrue(burglar.getScore() < 0);
    }

    @Test
    void onPlayerStep_MultiplePenalties_AccumulatesDeductions() {
        NormalPunishment punishment = new NormalPunishment(10);
        punishment.onPlayerStep(burglar);
        punishment.onPlayerStep(burglar);
        assertEquals(30, burglar.getScore());
    }


    @Test
    void onPlayerStep_DoesNotAffectRewardCounter() {
        NormalPunishment punishment = new NormalPunishment(10);
        punishment.onPlayerStep(burglar);
        assertEquals(0, burglar.getRewardsCollected());
    }
}