package robtheblock.characters;

import robtheblock.level.Level;
import robtheblock.level.Tile;
import robtheblock.objects.GameObject;

/**
 * Represents the player-controlled burglar character.
 * Handles movement, score tracking, and rewards collected.
 * 
 * NEW ADDITIONS FOR SCORING SYSTEM:
 * - rewardsCollected: Tracks how many regular rewards collected (separate from score)
 * - collectReward(): Increments reward counter (called by RegularReward only)
 * - isOutOfCoins(): Checks if score reached 0 (game over condition)
 */
public class Burglar {
    private Position currentPosition;  // Current grid position (x, y)
    private int score;  // Current level coins (can be negative)
    private int rewardsCollected;  // NEW: Counts regular rewards collected (NOT bonus rewards)

    // Smooth movement variables for rendering
    private float renderX, renderY;  // Current render position (pixels)
    private float targetX, targetY;  // Target render position (pixels)
    
    // Movement configuration constants
    private static final int TILE_SIZE = 32;  // Size of each grid tile in pixels
    private static final float TILES_PER_SECOND = 7.5f;  // How many tiles burglar moves per second
    private static final float SPEED = TILES_PER_SECOND * TILE_SIZE;  // Pixels per second (240)
    public static final float MOVE_DURATION = 1.0f / TILES_PER_SECOND;  // Time for one move (0.133s)

    /**
     * Creates a new Burglar at the specified starting position.
     * 
     * @param startX Initial X coordinate on grid
     * @param startY Initial Y coordinate on grid
     * @param levelHeight Height of level (needed for rendering calculations)
     * @param startingScore Starting score/coins (0 for fresh level, or carried from previous)
     */
    public Burglar(int startX, int startY, int levelHeight, int startingScore) {
        this.currentPosition = new Position(startX, startY);
        this.score = startingScore;  // NEW: Takes starting score as parameter
        this.rewardsCollected = 0;  // NEW: Always starts at 0 for each level

        // Initialize rendering positions
        this.targetX = startX * TILE_SIZE;
        this.targetY = (levelHeight - 1 - startY) * TILE_SIZE;
        this.renderX = this.targetX;
        this.renderY = this.targetY;
       
    }
    
    /**
     * Attempts to move the burglar in the specified direction.
     * Validates the move through the Level, then checks for items on the new tile.
     * 
     * @param d Direction to move (UP, DOWN, LEFT, RIGHT)
     * @param level The current level for collision detection
     * @return true if move succeeded, false if blocked by wall or boundary
     */
    public boolean move(Direction d, Level level) {
        int newX = currentPosition.getX();
        int newY = currentPosition.getY();
        
        switch (d) {
            case UP: newY -= 1; break;
            case DOWN: newY += 1; break;
            case LEFT: newX -= 1; break;
            case RIGHT: newX += 1; break;
        }
        
        if (level.isValidMove(newX, newY)) {
            updatePosition(newX, newY, level);
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Moves diagonally by combining two directions.
     * Allows smoother movement when holding two arrow keys.
     * 
     * @param d1 First direction (e.g., UP)
     * @param d2 Second direction (e.g., LEFT)
     * @param level Current level for collision detection
     * @return true if diagonal move succeeded
     */
    public boolean moveDiagonal(Direction d1, Direction d2, Level level) {
        int newX = currentPosition.getX();
        int newY = currentPosition.getY();

        // Apply first direction
        switch (d1) {
            case UP: newY -= 1; break;
            case DOWN: newY += 1; break;
            case LEFT: newX -= 1; break;
            case RIGHT: newX += 1; break;
        }
        
        // Apply second direction
        switch (d2) {
            case UP: newY -= 1; break;
            case DOWN: newY += 1; break;
            case LEFT: newX -= 1; break;
            case RIGHT: newX += 1; break;
        }

        if (level.isValidMove(newX, newY)) {
            updatePosition(newX, newY, level);
            return true;
        }
        return false;
    }
    /**
     * Updates the burglar's position and handles item collection.
     * Extracted to eliminate code duplication between move() and moveDiagonal().
     * 
     * @param newX New X coordinate
     * @param newY New Y coordinate
     * @param level Current level
     */
    private void updatePosition(int newX, int newY, Level level) {
        currentPosition.setX(newX);
        currentPosition.setY(newY);
        setTarget(newX, newY, TILE_SIZE, level.getHeight());
        collectItemAt(newX, newY, level);
    }

    /**
     * Checks for and collects any item at the specified position.
     * Extracted to reduce method complexity and improve readability.
     * 
     * @param x X coordinate to check
     * @param y Y coordinate to check
     * @param level Current level
     */
    private void collectItemAt(int x, int y, Level level) {
        Tile tile = level.getTile(x, y);
        if (tile.hasItem()) {
            GameObject item = tile.getItem();
            item.onPlayerStep(this);
            tile.removeItem();
        }
    }
    
    // Getters
    public Position getPosition() { return currentPosition; }
    public int getScore() { return score; }
    public int getRewardsCollected() { return rewardsCollected; }  // NEW: For checking level completion
    public float getRenderX() { return renderX; }
    public float getRenderY() { return renderY; }
    
    /**
     * Updates the burglar's score (adds or subtracts coins).
     * Used by both rewards (positive) and punishments (negative).
     * 
     * @param points Points to add (can be negative for punishments)
     */
    public void updateScore(int points) {
        score += points;
    }

    /**
     * Subtracts points from score (specifically for punishments).
     * Score CAN go below 0 (negative scores are allowed).
     * 
     * @param points Points to subtract
     */
    public void minusScore(int points) {
        score -= points;
    }
    
    /**
     * NEW: Increments the rewards collected counter.
     * Called ONLY by RegularReward (not BonusReward).
     * This determines when the exit door appears.
     */
    public void collectReward() {
        rewardsCollected++;
    }
    

    /**
     * NEW: Checks if score has gone below 0 (negative).
     * Used for game over condition.
     * 
     * @return true if score is negative (below 0)
     */
    public boolean isOutOfCoins() {
        return score < 0;  // Game over only when negative
    }

    /**
     * Updates smooth movement animation.
     * Gradually moves render position toward target position.
     * 
     * @param delta Time since last frame (seconds)
     */
    public void updateSmooth(float delta) {
        float dx = targetX - renderX;
        float dy = targetY - renderY;
        float step = SPEED * delta;

        // Move toward target
        if (Math.abs(dx) <= step) renderX = targetX;
        else renderX += Math.signum(dx) * step;

        if (Math.abs(dy) <= step) renderY = targetY;
        else renderY += Math.signum(dy) * step;
    }

    /**
     * Sets the target render position for smooth movement.
     * Called when burglar moves to a new grid position.
     * 
     * @param gridX New grid X coordinate
     * @param gridY New grid Y coordinate
     * @param tileSize Size of tiles in pixels
     * @param levelHeight Height of level (for Y coordinate conversion)
     */
    public void setTarget(int gridX, int gridY, int tileSize, int levelHeight) {
        targetX = gridX * tileSize;
        targetY = (levelHeight - 1 - gridY) * tileSize;  // Flip Y for screen coordinates
    }
}