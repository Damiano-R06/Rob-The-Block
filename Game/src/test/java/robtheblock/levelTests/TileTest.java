package robtheblock.levelTests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import robtheblock.level.Tile;
import robtheblock.objects.RegularReward;
import robtheblock.characters.Position;

/**
 * Unit tests for Tile.
 */
public class TileTest {

    private Tile walkableTile;
    private Tile wallTile;

    @BeforeEach
    void setUp() {
        walkableTile = new Tile(true, 0);
        wallTile = new Tile(false, 1);
    }

    @Test
    void newTileTest() {
        assertTrue(walkableTile.isWalkable());
        assertEquals(0, walkableTile.getTileType());
        assertFalse(wallTile.isWalkable());
        assertEquals(1, wallTile.getTileType());
    }

    @Test
    void emptyTileTest() {
        assertTrue(walkableTile.isEmpty());
        assertNull(walkableTile.getItem());
    }

    @Test
    void nonEmptyTileTest() {
        RegularReward reward = new RegularReward(new Position(0, 0), 10);
        walkableTile.setItem(reward);
        
        assertFalse(walkableTile.isEmpty());
        assertTrue(walkableTile.hasItem());
        assertEquals(reward, walkableTile.getItem());
    }

    @Test
    void makeTileEmptyTest() {
        RegularReward reward = new RegularReward(new Position(0, 0), 10);
        walkableTile.setItem(reward);
        walkableTile.removeItem();
        
        assertTrue(walkableTile.isEmpty());
        assertFalse(walkableTile.hasItem());
        assertNull(walkableTile.getItem());
    }
}