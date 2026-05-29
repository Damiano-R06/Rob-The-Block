package robtheblock.level.levels;

import robtheblock.level.Level;
import robtheblock.level.PatrolFactory;
import robtheblock.level.data.Level1Data;

public class Level1 extends Level {
    @Override
    protected int[][] getWallData() {
        return Level1Data.WALL_MAP;
    }

    @Override
    protected void populateLevel() {
        this.totalRegularRewards = 10;
        setHomeowner(4, 5, PatrolFactory.createSquarePath(4, 5));
        setHomeowner(8, 12, PatrolFactory.createLargeLoopPath(8, 12));
        setHomeowner(13, 4, PatrolFactory.createSquarePath(13, 4));
        setHomeowner(22, 15, PatrolFactory.createLargeLoopPath(22, 15));
        setDog(15, 16);
        setDogToy(23, 16);

        generateRandomRewards(10, 10);
        generateBonusRewards(2, 15);
    }
}
