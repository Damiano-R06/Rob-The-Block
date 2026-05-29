package robtheblock.level.levels;

import robtheblock.level.Level;
import robtheblock.level.PatrolFactory;
import robtheblock.level.data.Level4Data;

public class Level4 extends Level {
    @Override
    protected int[][] getWallData() {
        return Level4Data.WALL_MAP;
    }

    @Override
    protected void populateLevel() {
        this.totalRegularRewards = 20;
        setHomeowner(5, 6, PatrolFactory.createSquarePath(5, 6));
        setHomeowner(3, 6, PatrolFactory.createSquarePath(3, 6));
        setHomeowner(15, 6, PatrolFactory.createLargeLoopPath(15, 6));
        setHomeowner(25, 22, PatrolFactory.createLinePath(25, 22));
        setHomeowner(24, 20, PatrolFactory.createLargeLoopPath(24, 20));
        setHomeowner(25, 17, PatrolFactory.createLinePath(25, 17));
        setHomeowner(28, 8, PatrolFactory.createLinePath(28, 8));
        setHomeowner(14, 12, PatrolFactory.createLargeLoopPath(14, 12));
        setHomeowner(2, 18, PatrolFactory.createSquarePath(2, 18));
        setHomeowner(10, 20, PatrolFactory.createSquarePath(10, 20));
        setHomeowner(17, 2, PatrolFactory.createLinePath(17, 2));
        setDog(15, 16);
        setDogToy(3, 16);

        generateRandomRewards(20, 25);
        generateBonusRewards(2,25);
    }
}
