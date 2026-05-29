package robtheblock;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import robtheblock.characters.Burglar;
import robtheblock.characters.Direction;
import robtheblock.characters.enemies.Homeowner;
import robtheblock.level.Level;
import robtheblock.objects.DogToy;
import robtheblock.objects.Reward;



/**
 * Handles keyboard input and translates it into game actions.
 * Manages player movement with a cooldown system to prevent
 * overly fast movement, and checks for interactions with
 * level objects and enemy collisions each input frame.
 *
 * Supported controls:
 * 
 * W / UP    - Move up
 * S / DOWN  - Move down
 * A / LEFT  - Move left
 * D / RIGHT - Move right
 * Diagonal movement supported via simultaneous key presses
 * R - Restart current level
 *
 */
public class InputKeys {

    private float moveCooldown = Burglar.MOVE_DURATION; //seconds between moves, tweak this
    private float timer = 0f;
    private Game game;


    public InputKeys(Game game){
        this.game = game;
    }
    
    /**
     * Processes keyboard input each frame and updates game state accordingly.
     *
     * Each call:
     * Decrements the movement cooldown timer
     * Checks for restart input (R key)
     * If cooldown has expired, reads directional keys and moves the burglar
     * On movement, checks if the burglar stepped on a reward or dog toy
     * Checks for collision with any homeowner enemy
     *
     *
     * @param level The current level, used for tile and object lookups
     * @param burglar The player character to move and interact with
     * @param homeownerEnemies List of homeowner enemies to check collision against
     * @return true if the player is still alive, false if caught by a homeowner
     */
    public boolean handleInput(Level level, Burglar burglar, List<Homeowner> homeownerEnemies) {
        float delta = Gdx.graphics.getDeltaTime();
        timer -= delta;

        if (Gdx.input.isKeyJustPressed(Keys.R)) {
            game.restartLevel();
            return true;
        }

        if (timer <=0 ){
            boolean moved = false;
            boolean up = Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W);
            boolean down = Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S);
            boolean left = Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A);
            boolean right = Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D);

            //diagonals
            if (up && left) { burglar.moveDiagonal(Direction.UP, Direction.LEFT, level);   moved = true; }
            else if (up && right) { burglar.moveDiagonal(Direction.UP, Direction.RIGHT, level);  moved = true; }
            else if (down && left) { burglar.moveDiagonal(Direction.DOWN, Direction.LEFT, level); moved = true; }
            else if (down && right) { burglar.moveDiagonal(Direction.DOWN, Direction.RIGHT, level);moved = true; }
            
            //single directions
            else if (up) { burglar.move(Direction.UP, level);    moved = true; }
            else if (down) { burglar.move(Direction.DOWN, level);  moved = true; }
            else if (left) { burglar.move(Direction.LEFT, level);  moved = true; }
            else if (right) { burglar.move(Direction.RIGHT, level); moved = true; }

            if (moved) timer = moveCooldown;
            else timer = 0;

            if(moved){
                int x = burglar.getPosition().getX();
                int y = burglar.getPosition().getY();

                if(level.getTile(x,y).getItem() instanceof Reward){
                    level.removeReward(x,y);
                }
            
                DogToy toy = level.geDogToy();
                if(x == toy.getPosition().getX() && y == toy.getPosition().getY()){
                    level.getDog().startChase(toy.getPosition());
                }
            }
        }

        for(Homeowner homeownerEnemy: homeownerEnemies){
            if (homeownerEnemy.checkCollision(burglar)){
                return false;
            }
        }

        return true;
    }
}