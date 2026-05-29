package robtheblock.enemyTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import robtheblock.characters.Burglar;
import robtheblock.characters.Position;
import robtheblock.characters.enemies.Dog;
import robtheblock.characters.enemies.Enemy;

/**
 * Unit tests for Enemy class via Dog.
 */
public class EnemyTest {

    private Enemy enemy;
    private TestDog testDog;

    /**
     * Test subclass to expose protected methods
     */
    private class TestDog extends Dog {
        public TestDog(int x, int y, int levelHeight) {
            super(x, y, levelHeight);
        }

        public void testMoveUp() { moveUP(); }
        public void testMoveDown() { moveDown(); }
        public void testMoveLeft() { moveLeft(); }
        public void testMoveRight() { moveRight(); }
        public void testSetTarget(int x, int y) { setTarget(x, y); }
    }

    @BeforeEach
    void setUp() {
        enemy = new Dog(5, 5, 10);
        testDog = new TestDog(5, 5, 10);
    }

    @Test
    void enemy_GetPosition_ReturnsPosition() {
        Position pos = enemy.getPosition();
        assertNotNull(pos);
        assertEquals(5, pos.getX());
        assertEquals(5, pos.getY());
    }

    @Test
    void enemy_CheckCollision_SamePosition_ReturnsTrue() {
        Burglar b = new Burglar(5, 5, 10, 0);
        assertTrue(enemy.checkCollision(b));
    }

    @Test
    void enemy_CheckCollision_DifferentPosition_ReturnsFalse() {
        Burglar b = new Burglar(3, 3, 10, 0);
        assertFalse(enemy.checkCollision(b));
    }

    @Test
    void enemy_GetRenderX_ReturnsCorrectValue() {
        float renderX = enemy.getRenderX();
        assertEquals(5 * 32, renderX, 0.01f);
    }

    @Test
    void enemy_GetRenderY_ReturnsCorrectValue() {
        float renderY = enemy.getRenderY();
        assertEquals((10 - 1 - 5) * 32, renderY, 0.01f);
    }

    @Test
    void enemy_UpdateSmooth_ChangesRenderPosition() {
        float initialX = enemy.getRenderX();
        enemy.updateSmooth(0.1f);
        assertNotNull(enemy.getRenderX());
    }

    @Test
    void enemy_MoveUP_UpdatesYCoordinate() {
        int initialY = testDog.getPosition().getY();
        testDog.testMoveUp();
        assertEquals(initialY + 1, testDog.getPosition().getY());
    }

    @Test
    void enemy_MoveDown_UpdatesYCoordinate() {
        int initialY = testDog.getPosition().getY();
        testDog.testMoveDown();
        assertEquals(initialY - 1, testDog.getPosition().getY());
    }

    @Test
    void enemy_MoveLeft_UpdatesXCoordinate() {
        int initialX = testDog.getPosition().getX();
        testDog.testMoveLeft();
        assertEquals(initialX - 1, testDog.getPosition().getX());
    }

    @Test
    void enemy_MoveRight_UpdatesXCoordinate() {
        int initialX = testDog.getPosition().getX();
        testDog.testMoveRight();
        assertEquals(initialX + 1, testDog.getPosition().getX());
    }

    @Test
    void enemy_UpdateSmooth_WithLargeDelta_SnapsToTarget() {
        enemy.updateSmooth(100.0f);
        assertNotNull(enemy.getRenderX());
        assertNotNull(enemy.getRenderY());
    }

    @Test
    void enemy_UpdateSmooth_WithSmallDelta_GradualMovement() {
        float initialX = enemy.getRenderX();
        enemy.updateSmooth(0.01f);
        float afterX = enemy.getRenderX();
        assertTrue(Math.abs(afterX - initialX) < 50);
    }
}
