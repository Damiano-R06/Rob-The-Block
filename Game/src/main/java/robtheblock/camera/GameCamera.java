package robtheblock.camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import robtheblock.characters.Burglar;
import robtheblock.level.Level;
 
/**
 * movement for the game camera
 */
public class GameCamera {
    private OrthographicCamera camera;
    private Viewport viewport;
    private int tileSize;

    /**
     * constuctor for the game camera 
     * 
     * @param worldWidth width of the world
     * @param worldHeight height of the world
     * @param tileSize sizes of the tiles
     */
    public GameCamera(int worldWidth, int worldHeight, int tileSize){
        this.tileSize = tileSize;
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(worldWidth, worldHeight, camera);

        camera.position.set(worldWidth/2f, worldHeight/2f,0);
        camera.update();
    }

    /**
     * updates the camera and its postion
     * 
     * @param burglar where teh burglar is
     * @param level what level it is
     */
    public void updateCamera(Burglar burglar, Level level){

        //sets teh camera postion on the burglar
        float camX = burglar.getRenderX() +tileSize / 2;
        float camY = burglar.getRenderY() +tileSize /2;

        //half of the visible camera area
        float halfW= viewport.getWorldWidth()/2;
        float halfH = viewport.getWorldHeight()/2;

        //size of the map
        float mapW= level.getWidth() * tileSize;
        float mapH = level.getHeight() * tileSize;

        //sets the area of movement to map limits width
        if(mapW <= viewport.getWorldWidth()){
            camX = mapW /2;
        }else{
            camX =Math.max(halfW, Math.min(camX, mapW-halfW));
        }

        //sets the area of movement to map limits height
        if(mapH <= viewport.getWorldHeight()){
            camY = mapH /2;
        }else{
            camY =Math.max(halfH, Math.min(camY, mapH-halfH));
        }

        //updates the the camera position
        camera.position.set(camX,camY, 0);
        camera.update();
    }

    /**
     * updates the size of the viewport
     * @param width new window 
     * @param height new window 
     */
    public void resize(int width, int height){
        viewport.update(width, height,true);
    }

    /**
     * getter for the camera
     * @return returns the camera
     */
    public OrthographicCamera getCamera(){
        return camera;
    }

    /**
     * getter for the viewport
     * @return returns the viewport
     */
    public Viewport getViewport(){
        return viewport;
    }

}
