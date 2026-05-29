package robtheblock;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
 
import robtheblock.characters.Direction;
 
/**
 * Unit tests for the Direction enum.
 */
public class DirectionTest {
 
    @Test
    void direction_UP_Exists() {
        assertNotNull(Direction.UP);
    }
 
    @Test
    void direction_DOWN_Exists() {
        assertNotNull(Direction.DOWN);
    }
 
    @Test
    void direction_LEFT_Exists() {
        assertNotNull(Direction.LEFT);
    }
 
    @Test
    void direction_RIGHT_Exists() {
        assertNotNull(Direction.RIGHT);
    }
 
    @Test
    void direction_Values_HasFourDirections() {
        Direction[] directions = Direction.values();
        assertEquals(4, directions.length);
    }
 
    @Test
    void direction_ValueOf_ReturnsCorrectDirection() {
        assertEquals(Direction.UP, Direction.valueOf("UP"));
        assertEquals(Direction.DOWN, Direction.valueOf("DOWN"));
        assertEquals(Direction.LEFT, Direction.valueOf("LEFT"));
        assertEquals(Direction.RIGHT, Direction.valueOf("RIGHT"));
    }
}
