package robtheblock.levelTests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import robtheblock.level.Level;
import robtheblock.level.PatrolFactory;
import robtheblock.level.levels.Level1;
import robtheblock.level.levels.Level2;
import robtheblock.level.levels.Level3;
import robtheblock.level.levels.Level4;

import java.util.ArrayList;

/**
 * Unit tests for the Level class.
 */
public class LevelTest {

    @Test
    void level1CreationTest() {
        Level level = new Level1();
        assertEquals(10, level.getTotalRegularRewards());
        assertEquals(4, level.getHomeowners().size());
        assertNotNull(level.getDog());
        assertNotNull(level.geDogToy());
    }

    @Test
    void level2CreationTest() {
        Level level = new Level2();
        assertEquals(12, level.getTotalRegularRewards());
        assertEquals(7, level.getHomeowners().size());
    }

    @Test
    void level3CreationTest() {
        Level level = new Level3();
        assertEquals(15, level.getTotalRegularRewards());
        assertEquals(10, level.getHomeowners().size());
    }

    @Test
    void level4CreationTest() {
        Level level = new Level4();
        assertEquals(20, level.getTotalRegularRewards());
        assertEquals(11, level.getHomeowners().size());
    }
    
    @Test
    void validMoveBoundaryTest() {
        Level level = new Level1();
        int w = level.getWidth();
        int h = level.getHeight();

        assertTrue(level.isValidMove(1, 1)); 
        assertFalse(level.isValidMove(-1, 0));
        assertFalse(level.isValidMove(w, 0));
        assertFalse(level.isValidMove(0, -1));
        assertFalse(level.isValidMove(0, h)); 
    }

    @Test
    void levelRewardsTest() {
        Level level = new Level1();
        
        level.setBonusRewardPosition(5, 5, 100);
        assertFalse(level.getRewards().isEmpty());

        int initialSize = level.getRewards().size();
        level.removeReward(5, 5);
        assertEquals(initialSize-1, level.getRewards().size());
    }

    @Test
    void exitWalkabilityTest() {
        Level level = new Level1();
        int ex = level.getExitX();
        int ey = level.getExitY();

        level.openExit();
        assertTrue(level.getTile(ex, ey).isWalkable());

        level.openExit();
        assertTrue(level.getTile(ex, ey).isWalkable());
    }

    @Test
    void exitTest() {
        Level level = new Level1();
        int ex = level.getExitX();
        int ey = level.getExitY();

        assertTrue(level.isExit(ex, ey));
        assertFalse(level.isExit(0, 0));
    }

    @Test
    void isExitConditionsTest() {
        Level level = new Level1();
        int ex = level.getExitX();
        int ey = level.getExitY();

        assertTrue(level.isExit(ex, ey));
        assertFalse(level.isExit(ex, ey - 1));
        assertFalse(level.isExit(ex - 1, ey));
    }
    
    @Test
    void levelCompleteTest() {
        Level level = new Level1();
        int target = level.getTotalRegularRewards();

        assertFalse(level.checkComplete(target - 1)); 
        assertTrue(level.checkComplete(target)); 
        assertTrue(level.checkComplete(target + 1)); 
    }

    @Test
    void enemyPatrolPathTest() {
        Level level = new Level1();
        int initialSize = level.getHomeowners().size();

        level.setHomeowner(10, 10, PatrolFactory.createLargeLoopPath(10, 10));
        assertEquals(initialSize + 1, level.getHomeowners().size());

        level.setHomeowner(10, 10, PatrolFactory.createLinePath(10, 10));
        assertEquals(initialSize + 2, level.getHomeowners().size());

        level.setHomeowner(10, 10, new ArrayList<>());
        assertEquals(initialSize + 3, level.getHomeowners().size());
    }

    @Test
    void basicLevelGettersTest() {
        Level level = new Level1();
        assertEquals(30, level.getWidth());
        assertEquals(26, level.getHeight());
        assertNotNull(level.getDog());
        assertNotNull(level.geDogToy());
        assertNotNull(level.getRewards());
        assertNotNull(level.getDog());
        assertNotNull(level.geDogToy()); 
        assertNotNull(level.getRewards());
    }

    @Test
    void bonusRewardTest() {
        Level level = new Level1();

        level.setBonusRewardPosition(1, 10, 100);
        assertNotNull(level.getRewards());
    }

    @Test
    void removeRewardTest() {
        Level level = new Level1();
        
        level.setBonusRewardPosition(1, 1, 50);
        robtheblock.objects.Reward reward = level.getRewards().get(0);
        int targetX = reward.getPosition().getX();
        int targetY = reward.getPosition().getY();

        level.removeReward(targetX, targetY);
        
        assertFalse(level.getTile(targetX, targetY).hasItem());
    }

    @Test
    void patrolPathTest() {
        Level level = new Level1();
        int initialCount = level.getHomeowners().size();

        level.setHomeowner(10, 10, PatrolFactory.createLargeLoopPath(10, 10));
        level.setHomeowner(10, 10, PatrolFactory.createLinePath(10, 10));
        level.setHomeowner(10, 10, new ArrayList<>());

        assertEquals(initialCount + 3, level.getHomeowners().size());
    }
}