package robtheblock.level.levels;

import robtheblock.level.Level;
import robtheblock.level.PatrolFactory;
import robtheblock.level.data.Level3Data;

public class Level3 extends Level {
    @Override
    protected int[][] getWallData() {
        return Level3Data.WALL_MAP;
    }

    @Override
    protected void populateLevel() {
        this.totalRegularRewards = 15;
        setHomeowner(3, 3, PatrolFactory.createSquarePath(3, 3));
        setHomeowner(6, 8, PatrolFactory.createSquarePath(6, 8));
        setHomeowner(15, 8, PatrolFactory.createSquarePath(15, 8));
        setHomeowner(23, 23, PatrolFactory.createLinePath(23, 23));
        setHomeowner(25, 14, PatrolFactory.createLinePath(25, 14));
        setHomeowner(28, 10, PatrolFactory.createLinePath(28, 10));
        setHomeowner(28, 10, PatrolFactory.createLinePath(28, 10));
        setHomeowner(5, 12, PatrolFactory.createLargeLoopPath(5, 12));
        setHomeowner(2, 18, PatrolFactory.createSquarePath(2, 18));
        setHomeowner(10, 17, PatrolFactory.createSquarePath(10, 17));
        setDog(11, 20);
        setDogToy(11, 14);

        generateRandomRewards(15, 20);
        generateBonusRewards(4, 15);
    }
}
