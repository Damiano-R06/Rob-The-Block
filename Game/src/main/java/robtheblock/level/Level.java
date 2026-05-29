package robtheblock.level;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import robtheblock.characters.Position;
import robtheblock.characters.enemies.Dog;
import robtheblock.characters.enemies.Homeowner;
import robtheblock.objects.BonusReward;
import robtheblock.objects.DogToy;
import robtheblock.objects.RegularReward;
import robtheblock.objects.Reward;

/**
 * Represents one level (house) in the game.
 * Manages the grid, enemies, rewards, and level completion conditions.
 * 
 * NEW ADDITIONS FOR SCORING SYSTEM:
 * - totalRegularRewards: How many regular rewards needed to complete level
 * - checkComplete(): Now checks rewards collected instead of score
 * - Progressive difficulty: Each level has more rewards with higher values
 */
public abstract class Level {
    protected int totalRegularRewards; 
    private List<Homeowner> homeowners;
    private Dog dog;
    private DogToy dogToy;
    private Tile[][] grid;
    private int width;
    private int height; 
    private int exitX;  // Exit door X coordinate
    private int exitY;  // Exit door Y coordinate
    private List<Reward> rewards; 

    /**
     * Creates a new level
     */
    public Level() {
        loadLevel();
    }

    protected abstract int[][] getWallData();
    protected abstract void populateLevel();


    /**
     * Configures the level based on level number.
     * Each level has progressively more rewards with higher coin values.
     * 
     * Level 1: 10 regular rewards (10 coins each) + 1 bonus (50 coins)
     * Level 2: 12 regular rewards (15 coins each) + 2 bonus (75 coins each)
     * Level 3: 15 regular rewards (20 coins each) + 2 bonus (100 coins each)
     * Level 4: 20 regular rewards (25 coins each) + 3 bonus (150 coins each)
     */
    public void loadLevel() {
        // Set grid dimensions
        this.width = 30;
        this.height = 26;
        this.exitX = width - 2;  // Exit in bottom-right corner
        this.exitY = height - 1;

        //initialize the list for rewards
        rewards = new ArrayList<>();

        //initialize the list for Homeowners
        homeowners = new ArrayList<>();

        // DYNAMICALLY LOAD THE CORRECT MAP DATA
        int[][] wallData = getWallData();
        grid = new Tile[height][width];


        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // If the array has a 1, it is a Wall (isWalkable = false)
                // Boundary check ensures we don't crash if the array is smaller than the grid
                boolean isWalkable = true;
                int type = 0;

                if (y < wallData.length && x < wallData[y].length) {
                    type = wallData[y][x];
                    isWalkable = (type == 0);
                }
                grid[y][x] = new Tile(isWalkable, type);
            }
        }

        populateLevel();
    }

    public void generateRandomRewards(int count, int rewardValue) {
        Random rand = new Random();
        int placed = 0;

        while (placed < count) {
            int rx = rand.nextInt(width);
            int ry = rand.nextInt(height);

            // Check: Must be walkable floor AND have no item on it yet
            if (isValidMove(rx, ry) && grid[ry][rx].isEmpty()) {
                setRegularRewardPosition(rx, ry, rewardValue);
                placed++;
            }
        }
    }

    public void generateBonusRewards(int count, int rewardValue) {
        Random rand = new Random();
        int placed = 0;

        while (placed < count) {
            int rx = rand.nextInt(width);
            int ry = rand.nextInt(height);

            // Check: Must be walkable floor AND have no item on it yet
            if (isValidMove(rx, ry) && grid[ry][rx].isEmpty()) {
                setBonusRewardPosition(rx, ry, rewardValue);
                placed++;
            }
        }
    }



    /**
     * Gets the tile at the specified coordinates.
     * 
     * @param x X coordinate
     * @param y Y coordinate
     * @return Tile at that position
     */
    public Tile getTile(int x, int y) {
        return grid[y][x];
    }

    /**
     * Checks if a move to the specified position is valid.
     * A move is valid if it's within bounds and the tile is walkable.
     * 
     * @param x Target X coordinate
     * @param y Target Y coordinate
     * @return true if move is valid
     */
    public boolean isValidMove(int x, int y) {
        // Check boundaries
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return false;
        }
        // Check if tile is walkable
        return grid[y][x].isWalkable();
    }

    /**
     * NEW: Checks if level is complete based on rewards collected (NOT score).
     * Level is complete when ALL regular rewards are collected.
     * 
     * @param rewardsCollected Number of regular rewards collected by burglar
     * @return true if all regular rewards collected
     */
    public boolean checkComplete(int rewardsCollected) {
        return rewardsCollected >= totalRegularRewards;
    }

    /**
     * Checks if the specified position is the exit.
     * 
     * @param x X coordinate to check
     * @param y Y coordinate to check
     * @return true if at exit position
     */
    public boolean isExit(int x, int y) {
        return x == exitX && y == exitY;
    }

    /**
     * NEW: Places a regular reward at the specified position.
     * Regular rewards COUNT toward level completion.
     * 
     * @param x X coordinate
     * @param y Y coordinate
     * @param points Coin value of reward
     */
    public void setRegularRewardPosition(int x, int y, int points) {
        RegularReward reward = new RegularReward(new Position(x, y), points);
        grid[y][x].setItem(reward);
        rewards.add(reward);
    }

    /**
     * NEW: Places a bonus reward at the specified position.
     * Bonus rewards do NOT count toward level completion (only give coins).
     * 
     * @param x X coordinate
     * @param y Y coordinate
     * @param points Coin value of bonus reward
     */
    public void setBonusRewardPosition(int x, int y, int points) {
        BonusReward reward = new BonusReward(new Position(x, y), points);
        grid[y][x].setItem(reward);
        rewards.add(reward);
    }

    /**
     * Gets the list of all rewards in the level.
     * 
     * @return List of rewards
     */
    public List<Reward> getRewards() { return rewards; }

    /**
     * Removes a reward from the tracking list.
     * Called when a reward is collected.
     * 
     * @param x X coordinate of reward
     * @param y Y coordinate of reward
     */
    public void removeReward(int x, int y) {
    for (int i = rewards.size() - 1; i >= 0; i--) {
        Reward reward = rewards.get(i);
        if (reward.getPosition().getX() == x && reward.getPosition().getY() == y) {
            rewards.remove(i);
        }
    }
        grid[y][x].removeItem();
    }

    /**
     * Places a homeowner enemy at the specified position.
     * 
     * @param x X coordinate
     * @param y Y coordinate
     */
    public void setHomeowner(int x, int y, List<Position> patrolPath) {
        Homeowner homeowner = new Homeowner(x, y, height);

        homeowner.setPatrolPath(patrolPath);
        homeowners.add(homeowner);
    }

    /**
     * Places the dog enemy at the specified position.
     * 
     * @param x X coordinate
     * @param y Y coordinate
     */
    public void setDog(int x, int y) {
        dog = new Dog(x, y, height);
        grid[y][x] = new Tile(true, 0);
    }

    /**
     * Places the dog toy at the specified position.
     * When burglar steps on toy, dog chases to that location.
     * 
     * @param x X coordinate
     * @param y Y coordinate
     */
    public void setDogToy(int x, int y) {
        dogToy = new DogToy(new Position(x, y));
        grid[y][x] = new Tile(true, 0);
    }

    //Getters
    public int getTotalRegularRewards() { return totalRegularRewards; }  // NEW: For UI display
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public List<Homeowner> getHomeowners(){return homeowners;}
    public Dog getDog() { return dog; }
    public DogToy geDogToy() { return dogToy; }
    public int getExitX() { return exitX; }
    public int getExitY() { return exitY; }

    /**
     * Converts the exit tile from a wall to a walkable floor.
     */
    public void openExit() {
        // If the tile isn't walkable yet, replace it with a walkable one
        if (!grid[exitY][exitX].isWalkable()) {
            grid[exitY][exitX] = new Tile(true,0);
        }
    }
}