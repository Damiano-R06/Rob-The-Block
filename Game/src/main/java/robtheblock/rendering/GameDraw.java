package robtheblock.rendering;

import java.util.List;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import robtheblock.characters.Burglar;
import robtheblock.characters.enemies.Dog;
import robtheblock.characters.enemies.Homeowner;

import robtheblock.level.*;
import robtheblock.objects.BonusReward;
import robtheblock.objects.GameObject;
import robtheblock.objects.RegularReward;

public class GameDraw {
    
    private static final int TILE_SIZE =32;
    private TextureRenderer textures;

    public GameDraw(TextureRenderer textures){
        this.textures = textures;
    }

    /**
     * draws the burglar
     * 
     * @param batch draws the burglar Texture
     * @param burglar gets the burglar
     */
    public void drawBurglar(SpriteBatch batch, Burglar burglar){
        batch.draw(textures.getBurglarTexture(), burglar.getRenderX(), burglar.getRenderY(), TILE_SIZE,TILE_SIZE);
    }

    /**
     * draws the dog
     * 
     * @param batch draws the dog texture
     * @param dogEnemy
     */
    public void drawDog(SpriteBatch batch, Dog dogEnemy) {
        dogEnemy.updateSmooth(Gdx.graphics.getDeltaTime());
        batch.draw(textures.getDogTexture(), dogEnemy.getRenderX(), dogEnemy.getRenderY(), TILE_SIZE, TILE_SIZE);
    }
    
    /**
     * draws the dog toy
     * 
     * @param batch gets the texture to draw the dog toy
     * @param level gets the level
     */
    public void drawDogToy(SpriteBatch batch, Level level) {
        if (level.geDogToy() != null) {
            int dogToyX = level.geDogToy().getPosition().getX() * TILE_SIZE;
            int dogToyY = (level.getHeight() - 1 - level.geDogToy().getPosition().getY()) * TILE_SIZE;
            batch.draw(textures.getDogToyTexture(), dogToyX, dogToyY, TILE_SIZE, TILE_SIZE);
        }
    }

    /**
     * draws all the homeowners
     * 
     * @param batch drawsn the homeowners textures
     * @param homeownerEnemies list of homeowners
     */
    public void drawHomeowners(SpriteBatch batch, List<Homeowner> homeownerEnemies) {
        for(Homeowner homeownerEnemy : homeownerEnemies){
        homeownerEnemy.updateSmooth(Gdx.graphics.getDeltaTime());
        batch.draw(textures.getHomeownerTexture(), homeownerEnemy.getRenderX(), homeownerEnemy.getRenderY(), TILE_SIZE, TILE_SIZE);
        }
    }

    /**
     * draws the Environment
     * 
     * @param batch draws the environment textures
     * @param level gets the level
     */
    public void drawEnvironment(SpriteBatch batch, Level level){
        for (int y = 0; y < level.getHeight(); y++) {
            for (int x = 0; x < level.getWidth(); x++) {
                robtheblock.level.Tile tile = level.getTile(x, y);
                int renderX = x * TILE_SIZE;
                int renderY = (level.getHeight() - 1 - y) * TILE_SIZE;
                int type = tile.getTileType();

                batch.setColor(com.badlogic.gdx.graphics.Color.WHITE);

                if (type == 1) {
                    batch.setColor(com.badlogic.gdx.graphics.Color.BROWN);
                    batch.draw(textures.getWallTexture(), renderX, renderY, TILE_SIZE, TILE_SIZE);
                } else {
                    batch.draw(textures.getFloorTexture(), renderX, renderY, TILE_SIZE, TILE_SIZE);
                }
            }
        }
    }

    /**
     * draws all the furniture
     * 
     * @param batch draws the furniture Texture
     * @param level gets the level
     */
    public void drawFurniture(SpriteBatch batch, Level level){
         for (int y = 0; y < level.getHeight(); y++) {
            for (int x = 0; x < level.getWidth(); x++) {
                robtheblock.level.Tile tile = level.getTile(x, y);
                int renderX = x * TILE_SIZE;
                int renderY = (level.getHeight() - 1 - y) * TILE_SIZE;
                int type = tile.getTileType();

                batch.setColor(com.badlogic.gdx.graphics.Color.WHITE);

                if (type == 2) {
                    batch.draw(textures.getBedTopDownTexture(), renderX, renderY, TILE_SIZE * 2, TILE_SIZE * 2);
                } else if (type == 3) {
                    // Draw 1x1 Drawer
                    batch.draw(textures.getDrawerFrontTexture(), renderX, renderY, TILE_SIZE, TILE_SIZE);
                }else if (type == 5) {
                    batch.draw(textures.getFullShelfTexture(), renderX, renderY, TILE_SIZE* 2, TILE_SIZE* 2);
                } else if (type == 6) {
                    batch.draw(textures.getCouchTopDownTexture(), renderX, renderY, TILE_SIZE, TILE_SIZE);
                } else if (type == 7) {
                    batch.draw(textures.getChairTopDownTexture(), renderX, renderY, TILE_SIZE, TILE_SIZE); 
                } else if (type == 8) {
                    batch.draw(textures.getTableTexture(), renderX, renderY, TILE_SIZE * 2, TILE_SIZE);
                } else if (type == 9) {
                    batch.draw(textures.getPlantTexture(), renderX, renderY, TILE_SIZE, TILE_SIZE);
                } else if (type == 10){
                     batch.draw(textures.getChairBackTexture(), renderX, renderY, TILE_SIZE, TILE_SIZE);
                } else if (type == 11){
                     batch.draw(textures.getChairFaceLeftTexture(), renderX, renderY, TILE_SIZE, TILE_SIZE);
                } else if (type == 12){
                     batch.draw(textures.getEmptyShelfTexture(), renderX, renderY, TILE_SIZE* 2, TILE_SIZE*2 );
                }
            }
        }
    }
    /**
     * draws the rewards
     * 
     * @param batch draws the rewards
     * @param level gets the rewards
     */
    public void drawRewards(SpriteBatch batch, Level level) {
        for (int y = 0; y < level.getHeight(); y++) {
            for (int x = 0; x < level.getWidth(); x++) {
                Tile tile = level.getTile(x, y);

                if (tile.hasItem()) {
                    GameObject item = tile.getItem();

                    int rewardX = x * TILE_SIZE;
                    int rewardY = (level.getHeight() - 1 - y) * TILE_SIZE;
                    
                    if(item instanceof BonusReward){
                        batch.draw(textures.getBonusRewardTexture(), rewardX, rewardY, TILE_SIZE, TILE_SIZE);
                    }else if( item instanceof RegularReward){
                        batch.draw(textures.getRewardTexture(), rewardX, rewardY, TILE_SIZE, TILE_SIZE);
                    }
                }
            }
        }
    }

    /**
     * draws the exit door when player has collected all the rewards
     * 
     * @param batch draws the door
     * @param level gets the level
     * @param burglar gets the burglar
     */
    public void drawExitDoor(SpriteBatch batch, Level level, Burglar burglar){
        if (level.checkComplete(burglar.getRewardsCollected())) {
            level.openExit();
            
            int exitPixelX = level.getExitX() * TILE_SIZE;
            int exitPixelY = (level.getHeight() - 1 - level.getExitY()) * TILE_SIZE;
            batch.draw(textures.getExitDoorTexture(), exitPixelX, exitPixelY, TILE_SIZE, TILE_SIZE);
        }
    }

    
}
