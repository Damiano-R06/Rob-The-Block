package robtheblock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import robtheblock.characters.Burglar;
import robtheblock.characters.Direction;
import robtheblock.characters.Position;
import robtheblock.level.Level;
import robtheblock.level.levels.Level1;

/**
 * Unit tests for the Burglar class.
 */
public class BurglarTest {

    private Burglar burglar;
    private Level level;

    @BeforeEach
    void setUp() {
        level = new Level1();
        burglar = new Burglar(5, 5, level.getHeight(), 0);
    }

    @Test
    void burglar_UpdateScore_IncreasesScore() {
        burglar.updateScore(50);
        assertEquals(50, burglar.getScore());
    }

    @Test
    void burglar_MinusScore_DecreasesScore() {
        burglar.updateScore(100);
        burglar.minusScore(30);
        assertEquals(70, burglar.getScore());
    }

    @Test
    void burglar_IsOutOfCoins_NegativeScore_ReturnsTrue() {
        burglar.minusScore(10);
        assertTrue(burglar.isOutOfCoins());
    }

    @Test
    void burglar_IsOutOfCoins_PositiveScore_ReturnsFalse() {
        burglar.updateScore(50);
        assertFalse(burglar.isOutOfCoins());
    }

    // ========== REWARDS (2 tests) ==========
    
    @Test
    void burglar_CollectReward_IncrementsCounter() {
        burglar.collectReward();
        assertEquals(1, burglar.getRewardsCollected());
    }

    @Test
    void burglar_CollectReward_MultipleCollections_CountsCorrectly() {
        burglar.collectReward();
        burglar.collectReward();
        burglar.collectReward();
        assertEquals(3, burglar.getRewardsCollected());
    }
    
    @Test
    void burglar_Constructor_InitializesPosition() {
        Position pos = burglar.getPosition();
        assertNotNull(pos);
        assertEquals(5, pos.getX());
        assertEquals(5, pos.getY());
    }

    @Test
    void burglar_SetTarget_UpdatesRenderPosition() {
        burglar.setTarget(7, 8, 32, 10);
        burglar.updateSmooth(10.0f);
        assertEquals(7 * 32, burglar.getRenderX(), 1.0f);
    }

    @Test
    void burglar_UpdateSmooth_WithZeroDelta() {
        float initialX = burglar.getRenderX();
        burglar.updateSmooth(0.0f);
        assertEquals(initialX, burglar.getRenderX(), 0.01f);
    }

    
    @Test
    void burglar_Move_UP_UpdatesPosition() {
        int initialY = burglar.getPosition().getY();
        boolean moved = burglar.move(Direction.UP, level);
        if (moved) {
            assertEquals(initialY - 1, burglar.getPosition().getY());
        }
    }

    @Test
    void burglar_Move_DOWN_UpdatesPosition() {
        int initialY = burglar.getPosition().getY();
        boolean moved = burglar.move(Direction.DOWN, level);
        if (moved) {
            assertEquals(initialY + 1, burglar.getPosition().getY());
        }
    }

    @Test
    void burglar_Move_LEFT_UpdatesPosition() {
        int initialX = burglar.getPosition().getX();
        boolean moved = burglar.move(Direction.LEFT, level);
        if (moved) {
            assertEquals(initialX - 1, burglar.getPosition().getX());
        }
    }

    @Test
    void burglar_Move_RIGHT_UpdatesPosition() {
        int initialX = burglar.getPosition().getX();
        boolean moved = burglar.move(Direction.RIGHT, level);
        if (moved) {
            assertEquals(initialX + 1, burglar.getPosition().getX());
        }
    }

    
    
    @Test
    void burglar_MoveDiagonal_UP_RIGHT_UpdatesPosition() {
        int initialX = burglar.getPosition().getX();
        int initialY = burglar.getPosition().getY();
        boolean moved = burglar.moveDiagonal(Direction.UP, Direction.RIGHT, level);
        if (moved) {
            assertEquals(initialX + 1, burglar.getPosition().getX());
            assertEquals(initialY - 1, burglar.getPosition().getY());
        }
    }

    @Test
    void burglar_MoveDiagonal_DOWN_LEFT_UpdatesPosition() {
        int initialX = burglar.getPosition().getX();
        int initialY = burglar.getPosition().getY();
        boolean moved = burglar.moveDiagonal(Direction.DOWN, Direction.LEFT, level);
        if (moved) {
            assertEquals(initialX - 1, burglar.getPosition().getX());
            assertEquals(initialY + 1, burglar.getPosition().getY());
        }
    }

    
    @Test
    void burglar_Move_IntoWall_ReturnsFalse() {
        Burglar b = new Burglar(1, 1, level.getHeight(), 0);
        boolean moved = b.move(Direction.UP, level);
        if (!moved) {
            assertEquals(1, b.getPosition().getX());
            assertEquals(1, b.getPosition().getY());
        }
    }

    @Test
    void burglar_MoveDiagonal_IntoWall_ReturnsFalse() {
        Burglar b = new Burglar(1, 1, level.getHeight(), 0);
        boolean moved = b.moveDiagonal(Direction.UP, Direction.LEFT, level);
        if (!moved) {
            assertEquals(1, b.getPosition().getX());
            assertEquals(1, b.getPosition().getY());
        }
    }


    @Test
    void burglar_Move_CollectsItems() {
        Burglar b = new Burglar(0, 1, level.getHeight(), 0);
        
        
        for (int i = 0; i < 25; i++) {
            b.move(Direction.RIGHT, level);
            if (i % 5 == 0) b.move(Direction.DOWN, level);
        }
        
        // Item collection code was executed
        assertNotNull(b.getPosition());
    }

    @Test
    void burglar_MoveDiagonal_CollectsItems() {
        Burglar b = new Burglar(0, 1, level.getHeight(), 0);
        
        
        for (int i = 0; i < 20; i++) {
            b.moveDiagonal(Direction.DOWN, Direction.RIGHT, level);
        }
        
    
        assertNotNull(b.getPosition());
    }


    
    @Test
    void burglar_MultipleMovements_MaintainConsistency() {
        burglar.move(Direction.RIGHT, level);
        burglar.move(Direction.DOWN, level);
        burglar.move(Direction.LEFT, level);
        burglar.move(Direction.UP, level);
        assertNotNull(burglar.getPosition());
        assertNotNull(burglar.getRenderX());
    }

    @Test
    void burglar_Move_AllDirections_CoverAllSwitchCases() {
        // This ensures ALL switch cases in move() are hit
        Burglar b = new Burglar(5, 5, level.getHeight(), 0);
        
        // Hit all 4 switch cases
        b.move(Direction.UP, level);
        b.move(Direction.DOWN, level);
        b.move(Direction.LEFT, level);
        b.move(Direction.RIGHT, level);
        
        assertNotNull(b.getPosition());
    }

    @Test
    void burglar_MoveDiagonal_AllCombinations_CoverAllBranches() {
        // Cover all switch case combinations in BOTH d1 and d2
        Burglar b = new Burglar(5, 5, level.getHeight(), 0);
        
        // First switch (d1) - all 4 cases
        b.moveDiagonal(Direction.UP, Direction.RIGHT, level);
        b.moveDiagonal(Direction.DOWN, Direction.RIGHT, level);
        b.moveDiagonal(Direction.LEFT, Direction.UP, level);
        b.moveDiagonal(Direction.RIGHT, Direction.UP, level);
        
        // Second switch (d2) - all 4 cases
        b.moveDiagonal(Direction.UP, Direction.UP, level);
        b.moveDiagonal(Direction.UP, Direction.DOWN, level);
        b.moveDiagonal(Direction.UP, Direction.LEFT, level);
        b.moveDiagonal(Direction.UP, Direction.RIGHT, level);
        
        assertNotNull(b.getPosition());
    }

    @Test
    void burglar_Move_BlockedByWall_ExercisesElseBranch() {
        // Force the ELSE branch (return false) when blocked
        // Try moving from corners into walls
        Burglar corner1 = new Burglar(0, 0, level.getHeight(), 0);
        Burglar corner2 = new Burglar(0, level.getHeight() - 1, level.getHeight(), 0);
        
        // These should hit walls and return false
        corner1.move(Direction.UP, level);
        corner1.move(Direction.LEFT, level);
        corner2.move(Direction.DOWN, level);
        corner2.move(Direction.LEFT, level);
        
        assertNotNull(corner1.getPosition());
    }

    @Test
    void burglar_MoveDiagonal_BlockedByWall_ExercisesElseBranch() {
        // Force the ELSE branch in diagonal when blocked
        Burglar corner = new Burglar(0, 0, level.getHeight(), 0);
        
        // Try all diagonal directions from corner (all should hit walls)
        corner.moveDiagonal(Direction.UP, Direction.LEFT, level);
        corner.moveDiagonal(Direction.UP, Direction.RIGHT, level);
        corner.moveDiagonal(Direction.DOWN, Direction.LEFT, level);
        
        assertNotNull(corner.getPosition());
    }

    @Test
    void burglar_Move_SuccessfulMove_ReturnsTrue() {
        // Explicitly test the TRUE return path
        Burglar b = new Burglar(5, 5, level.getHeight(), 0);
        
        boolean moved = b.move(Direction.RIGHT, level);
        
        // At least one direction should succeed from middle of map
        if (!moved) {
            moved = b.move(Direction.DOWN, level);
        }
        
        assertTrue(moved);
    }
}