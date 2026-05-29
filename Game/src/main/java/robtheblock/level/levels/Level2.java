package robtheblock.level.levels;

import robtheblock.level.Level;
import robtheblock.level.PatrolFactory;
import robtheblock.level.data.Level2Data;

public class Level2 extends Level {
    @Override
    protected int[][] getWallData() {
        return Level2Data.WALL_MAP;
    }

    @Override
    protected void populateLevel() {
        this.totalRegularRewards = 12;
        setHomeowner(3, 3, PatrolFactory.createSquarePath(3, 3));
        setHomeowner(8, 12, PatrolFactory.createLinePath(8, 12));
        setHomeowner(13, 13, PatrolFactory.createLinePath(13, 13));
        setHomeowner(15, 8, PatrolFactory.createLinePath(15, 8));
        setHomeowner(22, 15, PatrolFactory.createSquarePath(22, 15));
        setHomeowner(27, 20, PatrolFactory.createLinePath(27, 20));
        setHomeowner(22, 3, PatrolFactory.createSquarePath(22, 3));
        setDog(23, 3);
        setDogToy(23, 23);


        generateRandomRewards(12, 15);
        generateBonusRewards(3, 15);
    }
}
