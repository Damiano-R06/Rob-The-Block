package robtheblock.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * User Interface handler for displaying text and messages.
 * Manages on-screen text rendering including:
 * - Game stats (Level, Rewards, Score, Total)
 * - Game over/win messages
 * - Control instructions
 * 
 * NEW: Supports displaying total score and rewards collected.
 */
public class UI {

    private BitmapFont font;  
    private String message;
    private Texture startScreenTexture;
    private Texture gameOverTexture;
    private Texture winTexture;


    private static final float BTN_LEFT = 0.40f;
    private static final float BTN_RIGHT = 0.60f;
    private static final float BTN_BOTTOM = 0.60f;
    private static final float BTN_TOP = 0.80f;

    /**
     * Creates the UI handler.
     * Initializes font with 1.5x scale for better readability.
     */
    public UI() {
        font = new BitmapFont();
        font.getData().setScale(1.0f);
        message = "";
        startScreenTexture = new Texture(Gdx.files.internal("assets/images/StartScreenFinal.png"));
        gameOverTexture = new Texture(Gdx.files.internal("assets/images/GameOver.png"));
        winTexture = new Texture(Gdx.files.internal("assets/images/GameWin.png"));
    }
    /**
     * Renders white text at specified position.
     * Used for game stats (Level, Rewards, Score, Total).
     * 
     * @param batch SpriteBatch to draw with (must be between begin/end)
     * @param text Text to display
     * @param x X coordinate (pixels from left)
     * @param y Y coordinate (pixels from bottom)
     */
    public void renderText(SpriteBatch batch, String text, float x, float y) {
        font.setColor(Color.WHITE);
        font.draw(batch, text, x, y);
    }

    

    /**
     * Renders the current message (if any) in yellow at center of screen.
     * Call this between batch.begin() and batch.end() in Game.render().
     * 
     * @param batch SpriteBatch to draw with
     */
    public void render(SpriteBatch batch) {
        if (!message.isEmpty()) {
            font.setColor(Color.YELLOW);
            // Center message approximately (480 is middle of 960px screen)
            font.draw(batch, message, 480 - (message.length() * 6), 480);
        }
    }

    /**
     * Renders the starting screen for the game and creates a interactable area
     * to click to start the game
     * 
     * @param batch SpriteBatch to draw with
     * @return boolean that checks if the button is pressed
     */
    public boolean renderStartScreen(SpriteBatch batch) {
        float screenW = Gdx.graphics.getWidth();
        float screenH = Gdx.graphics.getHeight();

        batch.draw(startScreenTexture, 0, 0, screenW, screenH);

        float mx = Gdx.input.getX();
        float my = screenH - Gdx.input.getY();
        boolean hovered = mx >= screenW * BTN_LEFT  && mx <= screenW * BTN_RIGHT
               && my >= screenH * BTN_BOTTOM && my <= screenH * BTN_TOP;

        return hovered && Gdx.input.justTouched();
    }

    /**
     * Renders the game over screen for the game and creates a interactable area
     * to click to restart the game
     * 
     * @param batch SpriteBatch to draw with
     * @return boolean that checks if the button is pressed
     */

    public boolean renderGameOver(SpriteBatch batch) {
        float screenW = Gdx.graphics.getWidth();
        float screenH = Gdx.graphics.getHeight();
        batch.draw(gameOverTexture, 0, 0, screenW, screenH);

        float mx = Gdx.input.getX();
        float my = screenH - Gdx.input.getY();
        boolean hovered = mx >= screenW * BTN_LEFT  && mx <= screenW * BTN_RIGHT
                && my >= screenH * BTN_BOTTOM && my <= screenH * BTN_TOP;

        return hovered && Gdx.input.justTouched();
    }

    /**
     * Renders the win screen for the game when you beat level 4 and creates a interactable area
     * to click to restart the game.
     * 
     * Also displays the total score accumulated throughout the game.
     * 
     * @param batch SpriteBatch to draw with
     * @return boolean that checks if the button is pressed
     */

    public boolean renderWin (SpriteBatch batch, int finalScore){
        float screenW = Gdx.graphics.getWidth();
        float screenH = Gdx.graphics.getHeight();

        batch.draw(winTexture, 0, 0, screenW, screenH);

        font.setColor(Color.BLACK);

        String scoreText = "Final Score: " + finalScore;
        font.draw(batch, scoreText, screenW / 2 - (scoreText.length() * 6) + 15, screenH * 0.65f);

        float mx = Gdx.input.getX();
        float my = screenH - Gdx.input.getY();
        boolean hovered = mx >= screenW * BTN_LEFT  && mx <= screenW * BTN_RIGHT
               && my >= screenH * BTN_BOTTOM && my <= screenH * BTN_TOP;

        return hovered && Gdx.input.justTouched();
    }

    /**
     * Cleans up font resources.
     * Called when game is closed.
     */
    public void dispose() {
        font.dispose();
        startScreenTexture.dispose();
        gameOverTexture.dispose();
        winTexture.dispose();
    }
}