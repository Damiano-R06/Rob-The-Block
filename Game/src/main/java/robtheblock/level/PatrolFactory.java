package robtheblock.level;

import java.util.ArrayList;
import java.util.List;
import robtheblock.characters.Position;

/**
 * Factory class to generate specific patrol paths for enemies.
 */
public class PatrolFactory {

    // Was Case 1: A small 3x3 square patrol
    public static List<Position> createSquarePath(int x, int y) {
        List<Position> path = new ArrayList<>();
        path.add(new Position(x, y));
        path.add(new Position(x+1, y));
        path.add(new Position(x+2, y));
        path.add(new Position(x+2, y+1));
        path.add(new Position(x+2, y+2));
        path.add(new Position(x+1, y+2));
        path.add(new Position(x, y+2));
        path.add(new Position(x, y+1));
        return path;
    }

    // Was Case 2: A large intricate loop
    public static List<Position> createLargeLoopPath(int x, int y) {
        List<Position> path = new ArrayList<>();
        path.add(new Position(x, y));
        path.add(new Position(x+1, y));
        path.add(new Position(x+2, y));
        path.add(new Position(x+3, y));
        path.add(new Position(x+4, y));
        path.add(new Position(x+4, y+1));
        path.add(new Position(x+4, y+2));
        path.add(new Position(x+4, y+3));
        path.add(new Position(x+3, y+3));
        path.add(new Position(x+2, y+3));
        path.add(new Position(x+1, y+3));
        path.add(new Position(x, y+3));
        path.add(new Position(x-1, y+3));
        path.add(new Position(x-2, y+3));
        path.add(new Position(x-3, y+3));
        path.add(new Position(x-3, y+2));
        path.add(new Position(x-3, y+1));
        path.add(new Position(x-3, y));
        path.add(new Position(x-2, y));
        path.add(new Position(x-1, y));
        path.add(new Position(x, y));
        return path;
    }

    // Was Case 3: A horizontal line patrol moving left
    public static List<Position> createLinePath(int x, int y) {
        List<Position> path = new ArrayList<>();
        path.add(new Position(x-1, y));
        path.add(new Position(x-2, y));
        path.add(new Position(x-3, y));
        path.add(new Position(x-4, y));
        path.add(new Position(x-5, y));
        path.add(new Position(x-6, y));
        path.add(new Position(x-5, y));
        path.add(new Position(x-4, y));
        path.add(new Position(x-3, y));
        path.add(new Position(x-2, y));
        path.add(new Position(x-1, y));
        path.add(new Position(x, y));
        return path;
    }
}
