package robtheblock.characters.enemies;

import robtheblock.characters.Burglar;
import robtheblock.characters.Position;

/**
 * represent the enimies in
 */
public abstract class Enemy {
    protected Position currentPosition;

    protected float renderX, renderY;
    protected float targetX, targetY;
    protected float SPEED; 
    protected static final int TILE_SIZE = 32;
    protected int levelHeight;
    
    /**
     * constructor for the enemies
     * @param x starting x coordiante
     * @param y startingy y coordinate
     * @param moveSpeed the moevement speed used 
     * @param levelHeight height of the game
     */
    public Enemy(int x, int y, float moveSpeed, int levelHeight) {
        this.currentPosition = new Position(x, y);
        this.SPEED = moveSpeed;
        this.levelHeight = levelHeight;

        this.targetX = x * TILE_SIZE;
        this.targetY = (levelHeight - 1 - y) * TILE_SIZE;
        this.renderX = this.targetX;
        this.renderY = this.targetY;
    }
    
    /**
     * Move the enemy (to be implemented by subclasses)
     */
    public abstract void move();
    
    /**
     * gets the Position for enemies
     * @return Position
     */
    public Position getPosition() {
        return currentPosition;
    }

    public float getRenderX(){
        return renderX;
    }

    public float getRenderY(){
        return renderY;
    }
    
    /**
     * checks collision with the Burglar
     */
    public boolean checkCollision(Burglar b) {
        Position burglarPos = b.getPosition();
        return currentPosition.getX() == burglarPos.getX() 
            && currentPosition.getY() == burglarPos.getY();
    }

    /**
     * enemy movement for up
     */
    protected void moveUP(){
        currentPosition.setY(currentPosition.getY()+1);
    }

    /**
     * enemy movement for down
     */
    protected void moveDown(){
        currentPosition.setY(currentPosition.getY()-1);
    }

    /**
     * enemy movement for left
     */
    protected void moveLeft(){
        currentPosition.setX(currentPosition.getX()-1);

    }

    /**
     * enemy movement for right
     */
    protected void moveRight(){
        currentPosition.setX(currentPosition.getX()+1);
    }

    public void updateSmooth(float delta) {
        float dx = targetX - renderX;
        float dy = targetY - renderY;
        float step = SPEED * delta;

        if (Math.abs(dx) <= step) renderX = targetX;
        else renderX += Math.signum(dx) * step;

        if (Math.abs(dy) <= step) renderY = targetY;
        else renderY += Math.signum(dy) * step;
    }

    protected void setTarget(int gridX, int gridY) {
        targetX = gridX * TILE_SIZE;
        targetY = (levelHeight - 1 - gridY) * TILE_SIZE;
    }
}