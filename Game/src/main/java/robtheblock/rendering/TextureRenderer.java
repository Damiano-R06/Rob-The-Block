package robtheblock.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Loads and owns all game textures, including sprite sheet regions for furniture.
 */
public class TextureRenderer {

    private final Texture burglarTexture;
    private final Texture regularRewardTexture;
    private final Texture bonusRewardTexture;
    private final Texture exitDoorTexture;
    private final Texture homeownerTexture;
    private final Texture dogTexture;
    private final Texture dogToyTexture;
    private final Texture wallTexture;
    private final Texture floorTexture;
    private final Texture bedTopDownTexture;
    private final Texture drawerFrontTexture;

    private final Texture fullShelfTexture;
    private final Texture emptyShelfTexture;

    private final Texture couchTopDownTexture;

    private final Texture chairBackTexture;
    private final Texture chairTopDownTexture;
    private final Texture chairFaceLeftTexture;

    private final Texture tableTexture;
    private final Texture plantTexture;

    public TextureRenderer() {
        burglarTexture = new Texture(Gdx.files.internal("assets/images/Test.png"));
        regularRewardTexture = new Texture(Gdx.files.internal("assets/images/RegularReward.png"));
        bonusRewardTexture = new Texture(Gdx.files.internal("assets/images/BonusReward.png"));
        exitDoorTexture = new Texture(Gdx.files.internal("assets/images/door.png"));
        homeownerTexture = new Texture(Gdx.files.internal("assets/images/Homeowner.png"));
        dogTexture = new Texture(Gdx.files.internal("assets/images/Dog.png"));
        dogToyTexture = new Texture(Gdx.files.internal("assets/images/dogToy.png"));
        wallTexture = new Texture(Gdx.files.internal("assets/images/wall.png"));
        floorTexture = new Texture(Gdx.files.internal("assets/images/floor.png"));
        bedTopDownTexture = new Texture(Gdx.files.internal("assets/images/bedTopDown.png"));
        drawerFrontTexture = new Texture(Gdx.files.internal("assets/images/drawerFront.png"));
        plantTexture =  new Texture(Gdx.files.internal("assets/images/plant.png"));

        fullShelfTexture = new Texture(Gdx.files.internal("assets/images/fullShelf.png"));
        emptyShelfTexture = new Texture(Gdx.files.internal("assets/images/emptyShelf.png"));

        couchTopDownTexture = new Texture(Gdx.files.internal("assets/images/couchTopDown.png"));

        chairTopDownTexture = new Texture(Gdx.files.internal("assets/images/chairTopDown.png"));
        chairFaceLeftTexture = new Texture(Gdx.files.internal("assets/images/chairFaceLeft.png"));
        chairBackTexture = new Texture(Gdx.files.internal("assets/images/chairBack.png"));

        tableTexture = new Texture(Gdx.files.internal("assets/images/table.png"));
    }

    // Getters
    public Texture getBurglarTexture() { return burglarTexture;}
    public Texture getBonusRewardTexture(){return bonusRewardTexture;}
    public Texture getRewardTexture() { return regularRewardTexture; }
    public Texture getExitDoorTexture() { return exitDoorTexture; }
    public Texture getHomeownerTexture() { return homeownerTexture; }
    public Texture getDogTexture() { return dogTexture; }
    public Texture getDogToyTexture() { return dogToyTexture; }
    public Texture getWallTexture() { return wallTexture; }
    public Texture getFloorTexture() { return floorTexture; }

    public Texture getBedTopDownTexture() { return bedTopDownTexture;  }
    public Texture getDrawerFrontTexture() { return drawerFrontTexture;  }

    public Texture getFullShelfTexture() { return fullShelfTexture; }
    public Texture getEmptyShelfTexture() { return emptyShelfTexture; }

    public Texture getCouchTopDownTexture() { return couchTopDownTexture;}

    public Texture getChairTopDownTexture() { return chairTopDownTexture;}
    public Texture getChairFaceLeftTexture() { return chairFaceLeftTexture;}
    public Texture getChairBackTexture() { return chairBackTexture;}


    public Texture getTableTexture() { return tableTexture;}
    public Texture getPlantTexture() { return plantTexture; }

    public void dispose() {
        burglarTexture.dispose();
        regularRewardTexture.dispose();
        bonusRewardTexture.dispose();
        exitDoorTexture.dispose();
        homeownerTexture.dispose();
        dogTexture.dispose();
        dogToyTexture.dispose();
        wallTexture.dispose();
        floorTexture.dispose();
        plantTexture.dispose();
        bedTopDownTexture.dispose();
        drawerFrontTexture.dispose();
        fullShelfTexture.dispose();
        couchTopDownTexture.dispose();
        chairTopDownTexture.dispose();
        chairBackTexture.dispose();
        chairFaceLeftTexture.dispose();
        emptyShelfTexture.dispose();
        tableTexture.dispose();
    }
}