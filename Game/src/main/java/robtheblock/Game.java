package robtheblock;

import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;

import robtheblock.camera.GameCamera;
import robtheblock.characters.Burglar;
import robtheblock.characters.enemies.Dog;
import robtheblock.characters.enemies.Homeowner;
import robtheblock.level.Level;
import robtheblock.level.levels.Level1;
import robtheblock.level.levels.Level2;
import robtheblock.level.levels.Level3;
import robtheblock.level.levels.Level4;
import robtheblock.objects.NormalPunishment;
import robtheblock.rendering.GameDraw;
import robtheblock.rendering.TextureRenderer;
import robtheblock.rendering.UI;

/**
 * Main game class that handles the game loop, rendering, and game state.
 * Extends libGDX's ApplicationAdapter for game framework integration.
 * 
 * NEW SCORING SYSTEM IMPLEMENTATION:
 * - Score resets to 0 at the start of each level
 * - Total score accumulates across all levels
 * - Exit door appears when ALL regular rewards collected (not based on score)
 * - No "out of coins" game over (score can be negative)
 * 
 * DISPLAY FORMAT:
 * Level: 2          Rewards: 7/12          Score: -5          Total: 195
 * 
 * Where:
 * - Level: Current level number (1-4)
 * - Rewards: Regular rewards collected / Total needed
 * - Score: Current level coins (resets each level, can be negative)
 * - Total: Cumulative score across all levels (updated in real-time)
 */
public class Game extends ApplicationAdapter {

    private enum GameState { START_SCREEN, GAME_OVER, WIN, PLAYING}

    private GameState gameState;

    //Character playability
    private boolean isPlaying;

    private SpriteBatch batch; 
    private TextureRenderer textures;

    private static final int TILE_SIZE = 32;  //Size of each grid tile in pixels
    
    //Scoring system constants
    private static final int STARTING_COINS = 0;

    //Score
    private int currentLevel;
    private int totalScore;
    
    
    //Game objects
    private UI ui;
    private Burglar burglar;
    private Level level;
    private List<Homeowner> homeownerEnemies;
    private InputKeys inputKeys;
    private Dog dogEnemy;
    private NormalPunishment dogPunishment;
    private boolean alreadyPunished;
    private float homeownerMoverTimer;
    private float homeownerMoveDelay;
    private float dogMoveTimer;
    private float dogMoveDelay;
    private boolean alert;
    private GameDraw gameDraw;
    

    

    //camera
    private GameCamera gameCamera;

    /**
     * Called once when game starts.
     * Initializes all textures, creates game objects, and starts the game.
     */
    @Override
    public void create() {
        // Initialize rendering batch
        batch = new SpriteBatch();
        textures = new TextureRenderer();
       
        // Initialize game state
        this.currentLevel = 1;
        this.isPlaying = true;
        this.totalScore = STARTING_COINS;  //Start with 0 total score
        this.ui = new UI();
        this.dogPunishment = new NormalPunishment(5);  // Dog proximity costs 5 score
        this.alreadyPunished = false;
        this.homeownerMoverTimer =0f;
        this.homeownerMoveDelay = 0.2f;
        this.dogMoveTimer= 0f;
        this.dogMoveDelay =0.05f;

        gameState = GameState.START_SCREEN;
        currentLevel = 1;
        level = createLevel(currentLevel);
        burglar = new Burglar(0, 1, level.getHeight(), 0);
        homeownerEnemies = level.getHomeowners();
        dogEnemy = level.getDog();
        inputKeys = new InputKeys(this);
        alert = false;
        gameDraw = new GameDraw(textures);      


        gameCamera = new GameCamera(640, 640, TILE_SIZE);
    }

    /**
     * Called every frame (60 times per second).
     * Handles game logic, input, and rendering.
     */
    @Override
    public void render() {
        // Clear screen to dark gray
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.R)) {
            gameState = GameState.START_SCREEN;  // Restart to start screen
        }

        if (gameState == GameState.START_SCREEN) {
            renderStartScreen();
            return;
        }

        if (gameState == GameState.GAME_OVER) {
            renderGameOverScreen();
            return;
        }

        if (gameState == GameState.WIN) {
            renderWinScreen();
            return;
        }

        //Handle input and check game over conditions
        if (gameState == GameState.PLAYING) {
            boolean alive = inputKeys.handleInput(level, burglar, homeownerEnemies);
            if (!alive) {
                loseGame();
            }

            //Check if ran out of coins (score <= 0 = game over)
            if (burglar.isOutOfCoins()) {
                loseGame();
            }
        }

        //updates the camera so it follows the character
        gameCamera.updateCamera(burglar, level);

        //renders using the cameras current view
        batch.setProjectionMatrix(gameCamera.getCamera().combined);

        batch.begin();

        //Draws the game
        gameDraw.drawEnvironment(batch, level);
        gameDraw.drawFurniture(batch, level);
        gameDraw.drawRewards(batch, level);
        gameDraw.drawDogToy(batch, level);
        gameDraw.drawBurglar(batch, burglar);
        gameDraw.drawHomeowners(batch, homeownerEnemies);
        gameDraw.drawDog(batch, dogEnemy);
        gameDraw.drawExitDoor(batch, level, burglar);

        dogMoveTimer += Gdx.graphics.getDeltaTime();
        if(dogMoveTimer >= dogMoveDelay){
            dogEnemy.move();
            dogMoveTimer = 0f;
        }

        updateHomeowners();
        
        

        //Update smooth movement
        burglar.updateSmooth(Gdx.graphics.getDeltaTime());


        //Check if player is near dog (proximity punishment)
        checkDogPunishment();


        int currentTotal = totalScore + burglar.getScore();

        //set the position for the text to the camera view to the top leftX
        float leftX = gameCamera.getCamera().position.x- gameCamera.getViewport().getWorldHeight()/2 +8;
        float topY = gameCamera.getCamera().position.y+ gameCamera.getViewport().getWorldHeight() / 2-8;
        float bottomY = gameCamera.getCamera().position.y- gameCamera.getViewport().getWorldHeight() / 2+25 ;

        //Play state UI
        ui.renderText(batch, "Level: " + currentLevel, leftX, topY);
        ui.renderText(batch, "Rewards: " + burglar.getRewardsCollected() + "/" + level.getTotalRegularRewards(), leftX, topY-25);
        ui.renderText(batch, "Score: " + burglar.getScore(), leftX, topY-50);
        ui.renderText(batch, "Total: " + currentTotal, leftX, topY-75);
        ui.renderText(batch, "UP: W  DOWN: S  LEFT: A  RIGHT: D  RESTART: R", leftX+100, topY);
        
        //checks if the player has collected enough rewards
        if(level.checkComplete(burglar.getRewardsCollected())){
            alert = true;
        }

        //Draws the sprite text to run if the homeowners wake up
        if(alert){
            ui.renderText(batch, "RUN THE HOMEOWNERS ARE AWAKE",leftX+200 , bottomY);
        }
        ui.render(batch);

        batch.end();

        if (isPlaying && checkWinCondition()) {
            nextLevel();
        }
    }

    /**
     * Called when game is closed.
     * Cleans up resources to prevent memory leaks.
     */
    @Override
    public void dispose() {
        batch.dispose();
        textures.dispose();
        ui.dispose();
    }

    /**
     * Starts a new game from Level 1.
     * Resets all game state and creates initial level.
     */
    public void startGame() {
        isPlaying = true;
        gameState =GameState.PLAYING;
        currentLevel = 1;
        totalScore = STARTING_COINS;
        alert = false;

        loadLevel();
    }

    /**
     * Called when all 4 levels are complete.
     * Shows win screen with final total score.
     */
    public void endGame() {
        isPlaying = false;
        gameState = GameState.WIN;
    }

    /**
     * Called when player hits a homeowner.
     * Instant game over - Game state is now game over.
     */
    public void loseGame() {
        isPlaying = false;
        gameState = GameState.GAME_OVER;
        alert = false;
    }

    /**
     * Advances to the next level.
     * SCORING LOGIC:
     * 1. Add current level score to totalScore (permanently saved)
     * 2. Start next level with 0 coins (fresh start)
     * 3. Total score continues to accumulate
     *
     * Example:
     * - Finish Level 1 with 195 coins
     * - totalScore becomes 195
     * - Level 2 starts: Score = 0, Total = 195
     * - Finish Level 2 with 150 coins
     * - totalScore becomes 345
     * - Level 3 starts: Score = 0, Total = 345
     */
    /**
     * Advances to the next level.
     * SCORING LOGIC:
     * 1. Add current level score to totalScore (permanently saved)
     * 2. Start next level with 0 coins (fresh start)
     * 3. Total score continues to accumulate
     *
     * Example:
     * - Finish Level 1 with 195 coins
     * - totalScore becomes 195
     * - Level 2 starts: Score = 0, Total = 195
     * - Finish Level 2 with 150 coins
     * - totalScore becomes 345
     * - Level 3 starts: Score = 0, Total = 345
     */
    public void nextLevel() {
        totalScore += burglar.getScore();
        alert = false;
        currentLevel++;

        if (currentLevel > 4) {
            gameState = GameState.WIN;
            return;
        }

        loadLevel();
    }

    /**
     * Restarts the current level after game over.
     * Score resets to 0, totalScore is NOT affected.
     */
    public void restartLevel() {
        loadLevel();
        isPlaying = true;
        alert = false;
    }

    /**
     * loads all objects into current level
     */
    private void loadLevel(){
        level = createLevel(currentLevel);
        burglar = new Burglar(0, 1, level.getHeight(), STARTING_COINS);
        homeownerEnemies= level.getHomeowners();
        dogEnemy = level.getDog();
    }

    /**
     * NEW: Checks if level is complete.
     * WIN CONDITIONS (both must be true):
     * 1. Burglar is at exit position
     * 2. ALL regular rewards collected (checked via rewardsCollected counter)
     * 
     * NOTE: Score is NOT checked! Only rewards matter.
     * 
     * @return true if level complete
     */
    public boolean checkWinCondition() {
        int bx = burglar.getPosition().getX();
        int by = burglar.getPosition().getY();
        
        boolean atExit = level.isExit(bx, by);
        boolean allRewardsCollected = level.checkComplete(burglar.getRewardsCollected());
        
        
        return atExit && allRewardsCollected;
    }
    
    /**
     * Checks if burglar is near dog and applies proximity punishment.
     * Punishment is applied once per proximity event (not every frame).
     * 
     * Dog proximity costs 5 coins.
     */
    private void checkDogPunishment() {
        //Calculate distance to dog
        int dx = Math.abs(burglar.getPosition().getX() - dogEnemy.getPosition().getX());
        int dy = Math.abs(burglar.getPosition().getY() - dogEnemy.getPosition().getY());

        //Check if within 1 tile radius
        boolean nearDog = (dx <= 0 && dy <= 0);

        if (nearDog && !alreadyPunished) {
            dogPunishment.onPlayerStep(burglar);
            alreadyPunished = true;
        }

        if (!nearDog) {
            alreadyPunished = false;
        }
    }
    /**
     * Updates and moves all homeowner enemies based on timer.
     * Homeowners move every 0.2 seconds for slower, predictable movement.
     */
    private void updateHomeowners() {
        homeownerMoverTimer += Gdx.graphics.getDeltaTime();
        if (homeownerMoverTimer >= homeownerMoveDelay) {
            for (Homeowner homeowner : homeownerEnemies) {
                if (!alert) {
                    homeowner.move();
                } else {
                    homeowner.chase(burglar.getPosition(), level);
                }
            }
            homeownerMoverTimer = 0f;
        }
    }

    //Getters for InputKeys class
    public Level getLevel() { return level; }
    public Burglar getBurglar() { return burglar; }


    /**
     * updates the size of the viewport
     * @param width new window 
     * @param height new window 
     */
    @Override
    public void resize(int width, int height){
        gameCamera.resize(width, height);
    }

   

    

    /**
     * Factory method to create the correct level subclass.
     */
    private Level createLevel(int levelNum) {
        switch (levelNum) {
            case 1:
                return new Level1();
            case 2:
                return new Level2();
            case 3:
                return new Level3();
            case 4:
                return new Level4();
            default:
                return new Level1();
        }
    }
    /**
     * Renders the start screen and handles start button clicks.
     */
    private void renderStartScreen() {
        batch.setProjectionMatrix(new Matrix4().setToOrtho2D(
            0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        batch.begin();
        boolean startClicked = ui.renderStartScreen(batch);
        batch.end();
        
        if (startClicked) {
            startGame();
        }
    }

    /**
     * Renders the game over screen and handles restart button clicks.
     */
    private void renderGameOverScreen() {
        batch.setProjectionMatrix(new Matrix4().setToOrtho2D(
            0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        batch.begin();
        boolean restartClicked = ui.renderGameOver(batch);
        batch.end();

        if (restartClicked) {
            startGame();
        }
    }

    /**
     * Renders the win screen with final score and handles restart button clicks.
     */
    private void renderWinScreen() {
        batch.setProjectionMatrix(new Matrix4().setToOrtho2D(
            0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        batch.begin();
        boolean restartClicked = ui.renderWin(batch, totalScore);
        batch.end();
        
        if (restartClicked) {
            startGame();
        }
    }

}