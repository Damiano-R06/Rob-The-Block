package robtheblock.enemyTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import robtheblock.characters.Position;
import robtheblock.characters.enemies.Dog;

/**
 * Unit tests for the Dog enemy class.
 */
public class DogTest {

    private Dog dog;

    @BeforeEach
    void setUp() {
        dog = new Dog(5, 5, 10);
    }

    @Test
    void dog_Constructor_InitializesPosition() {
        Position pos = dog.getPosition();
        assertNotNull(pos);
        assertEquals(5, pos.getX());
        assertEquals(5, pos.getY());
    }

    @Test
    void dog_InitialState_DoesNotMove() {
        Position initial = new Position(dog.getPosition().getX(), dog.getPosition().getY());
        dog.move();
        assertEquals(initial.getX(), dog.getPosition().getX());
        assertEquals(initial.getY(), dog.getPosition().getY());
    }

    @Test
    void dog_Chase_MovesTowardEast() {
        Position toyPos = new Position(7, 5);
       
        dog.startChase(toyPos);
        dog.move();
        dog.move();
        assertEquals(7, dog.getPosition().getX());
    }

    @Test
    void dog_Chase_MovesTowardWest() {
        Position toyPos = new Position(3, 5);
       
        dog.startChase(toyPos);
        dog.move();
        dog.move();
        assertEquals(3, dog.getPosition().getX());
    }

    @Test
    void dog_Chase_MoveTowardSouth() {
        Position toyPos = new Position(5, 4);
        dog.startChase(toyPos);
        dog.move();
        assertEquals(4, dog.getPosition().getY());
    }

    @Test
    void dog_Chase_MovesTowardNorth() {
        Position toyPos = new Position(5, 8);
        dog.startChase(toyPos);
        dog.move();
        dog.move();
        dog.move();
        assertEquals(8, dog.getPosition().getY());
    }

    @Test
    void dog_ReachesToy_StartsWaiting() {
        Position toyPos = new Position(6, 5);
        dog.startChase(toyPos);
        dog.move();
        assertEquals(30, dog.waitCounter, 0.01f);
    }

    @Test
    void dog_Waiting_DecrementsCounter() {
        Position toyPos = new Position(6, 5);
        dog.startChase(toyPos);
        dog.move();
        float initialWait = dog.waitCounter;
        dog.move();
        assertEquals(initialWait - 1, dog.waitCounter, 0.01f);
    }

    @Test
    void dog_AfterWaiting_ReturnsHome() {
        Position toyPos = new Position(6, 5);
        dog.startChase(toyPos);
        dog.move();
        dog.waitCounter = 0;
        dog.move();
        dog.move();
        assertEquals(5, dog.getPosition().getX());
    }

    @Test
    void dog_GetRenderPosition_ReturnsValue() {
        float renderX = dog.getRenderX();
        assertEquals(5 * 32, renderX, 0.01f);
    }

    @Test
    void dog_UpdateSmooth_ChangesRenderPosition(){
        float initialRenderX = dog.getRenderX();

        dog.startChase(new Position(7, 5));
        dog.move();
        dog.updateSmooth(0.1f);

        assertTrue(dog.getRenderX() > initialRenderX);
    }

    @Test
    void dog_Chase_ToPositionBelow() {
        Dog testDog = new Dog(5, 3, 10);
        Position toyPos = new Position(5, 7);
        
        testDog.startChase(toyPos);
        testDog.move();
        testDog.move();
        
        assertNotNull(testDog.getPosition());
    }
}