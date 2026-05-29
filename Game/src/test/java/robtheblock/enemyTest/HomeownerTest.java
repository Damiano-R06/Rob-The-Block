package robtheblock.enemyTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import robtheblock.characters.Position;
import robtheblock.characters.enemies.Homeowner;


import java.util.ArrayList;
import java.util.List;

/**
 * Unit tests for the Homeowner enemy class.
 */
public class HomeownerTest {

    private Homeowner homeowner;

    @BeforeEach
    void setUp() {
        homeowner = new Homeowner(3, 3, 10);
    }

    @Test
    void homeowner_Constructor_InitializesPosition() {
        Position pos = homeowner.getPosition();
        assertNotNull(pos);
        assertEquals(3, pos.getX());
        assertEquals(3, pos.getY());
    }

    @Test
    void homeowner_NoPatrolPath_DoesNotMove() {
        Position initial = new Position(homeowner.getPosition().getX(), homeowner.getPosition().getY());
        homeowner.move();
        assertEquals(initial.getX(), homeowner.getPosition().getX());
    }

    @Test
    void homeowner_SetPatrolPath_UpdatesPosition() {
        List<Position> path = new ArrayList<>();
        path.add(new Position(5, 5));
        path.add(new Position(7, 5));
        homeowner.setPatrolPath(path);
        assertEquals(5, homeowner.getPosition().getX());
    }

    @Test
    void homeowner_Move_AdvancesToNextPoint() {
        List<Position> path = new ArrayList<>();
        path.add(new Position(5, 5));
        path.add(new Position(7, 5));
        homeowner.setPatrolPath(path);
        homeowner.move();
        assertEquals(7, homeowner.getPosition().getX());
    }

    @Test
    void homeowner_Move_LoopsBackToStart() {
        List<Position> path = new ArrayList<>();
        path.add(new Position(5, 5));
        path.add(new Position(7, 5));
        homeowner.setPatrolPath(path);
        homeowner.move();
        homeowner.move();
        assertEquals(5, homeowner.getPosition().getX());
    }

    @Test
    void homeowner_EmptyPath_DoesNotMove() {
        List<Position> path = new ArrayList<>();
        homeowner.setPatrolPath(path);
        Position initial = new Position(homeowner.getPosition().getX(), homeowner.getPosition().getY());
        homeowner.move();
        assertEquals(initial.getX(), homeowner.getPosition().getX());
    }

    @Test
    void homeowner_GetRenderPosition_ReturnsValue() {
        float renderX = homeowner.getRenderX();
        assertEquals(3 * 32, renderX, 0.01f);
    }

    
}