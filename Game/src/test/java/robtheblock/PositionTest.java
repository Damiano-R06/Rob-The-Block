package robtheblock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

import robtheblock.characters.Position;

/**
 * Unit tests for the Position class.
 */
public class PositionTest {

    @Test
    void position_GetX_ReturnsCorrectX() {
        Position pos = new Position(3, 5);
        assertEquals(3, pos.getX());
    }

    @Test
    void position_GetY_ReturnsCorrectY() {
        Position pos = new Position(3, 5);
        assertEquals(5, pos.getY());
    }

    @Test
    void position_SetX_UpdatesX() {
        Position pos = new Position(3, 5);
        pos.setX(10);
        assertEquals(10, pos.getX());
    }

    @Test
    void position_SetY_UpdatesY() {
        Position pos = new Position(3, 5);
        pos.setY(10);
        assertEquals(10, pos.getY());
    }

    @Test
    void position_Equals_SameCoordinates_ReturnsTrue() {
        Position a = new Position(4, 7);
        Position b = new Position(4, 7);
        assertEquals(a, b);
    }

    @Test
    void position_Equals_DifferentCoordinates_ReturnsFalse() {
        Position a = new Position(4, 7);
        Position b = new Position(1, 2);
        assertNotEquals(a, b);
    }

    @Test
    void position_ToString_ReturnsCorrectFormat() {
        Position pos = new Position(3, 5);
        assertEquals("(3, 5)", pos.toString());
    }
}