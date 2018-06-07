package Game;

import Game.Data.GameDataManager;
import Game.Data.KeyManager;
import Game.Data.MouseManager;
import Game.Data.Save;
import Game.Data.Settings;
import Game.Display.UserInterface.GameUIManager;
import Game.Entities.EnemyDirector;
import Game.Entities.EntityManager;
import Game.Timer.TimerManager;

import java.awt.*;
import java.util.Random;

/**
 * Cameron Bell - 07/05/2018
 * Handler Class
 * Global Point of Access & Management for Game Data
 */
public class Handler {
// SINGLETON PATTERN //
    private static Handler self = new Handler();
    public static Handler get() { return self; }

// VARIABLES //
    // Input Managers //
    private KeyManager keyManager;
    private MouseManager mouseManager;

    // Game Managers //
    private TimerManager timerManager;
    private EntityManager entityManager;
    private EnemyDirector enemyDirector;
    private GameUIManager gameUIManager;
    private GameDataManager gameDataManager;

    Save save;

    // Data //
    private boolean visibleUI;
    private int gameHeight;
    private int gameWidth;

// CONSTRUCTOR //
    private Handler() {
        // Initialise Save Data
        save = new Save();
        if(!save.loadXML()) {
            //save.create(); // If load fails, create a blank save
            if(!save.loadXML()) {
                Game.end(); // If load fails a second time, kill the program
            }
        }

        // Initialise Input Managers //
        keyManager = new KeyManager();
        mouseManager = new MouseManager();

        visibleUI = true;
    }

// METHODS //
    // Method - Updates Keyboard Input //
    public void update() {
        keyManager.update();
    }

    // Method - Updates All Data Managers //
    public void updateGame(int dt) {
        timerManager.update(dt);
        entityManager.update(dt);
        enemyDirector.update(dt);
        gameUIManager.update(dt);
        gameDataManager.update();
    }

    // Method - Updates All Data Managers //
    public void updateTutorial(int dt) {
        timerManager.update(dt);
        entityManager.update(dt);
    }

    // Method - Draw Entire Game //
    public void draw(Graphics g) {
        // Draw All Game Entities (and their collision boxes if enabled)
        entityManager.draw(g);
        if(Settings.DEBUG_DRAW_COLLISIONS) entityManager.drawCollisionBoxes(g);

        // Draw User Interface Last // So it appears over everything else
        if(visibleUI) gameUIManager.draw(g);
    }

    // Method - Create a New Game //
    public void newGame() {
        timerManager = new TimerManager();
        entityManager = new EntityManager();
        entityManager.getPlayer().setCollisionBox();
        enemyDirector = new EnemyDirector(this);
        gameUIManager = new GameUIManager(this);
        gameDataManager = new GameDataManager();
        setUIVisible();
    }

    // Method - Create a New Tutorial //
    public void newTutorial() {
        timerManager = new TimerManager();
        entityManager = new EntityManager();
        entityManager.getPlayer().setCollisionBox();
    }

    // Method - Run on End of Game //
    public void endGame() {

    }

// STATIC METHODS //
    // Method - Generate a Random Integer in a Range //
    public static int getIntFromRange(int min, int max) {
        // Switch the values if needed
        if(max < min) {
            int temp = min;
            min = max;
            max = temp;
        }

        max += 1;

        Random generator = new Random();
        return min + generator.nextInt(max - min);
    }

    // Method - Generate a Random Float in a Range //
    public static float getFloatFromRange(float min, float max) {
        // Switch the values if needed
        if(max < min) {
            float temp = min;
            min = max;
            max = temp;
        }
        Random generator = new Random();
        return min + generator.nextFloat() * (max - min);
    }

    // Method - Generate a Random Double in a Range //
    public static double getDoubleFromRange(double min, double max) {
        // Switch the values if needed
        if(max < min) {
            double temp = min;
            min = max;
            max = temp;
        }
        Random generator = new Random();
        return min + generator.nextDouble() * (max - min);
    }

    // Method - Convert Seconds to Ticks / Frames //
    public static int secsToTicks(int seconds) {
        return seconds * 60;
    }

// GETTERS & SETTERS //
    // Width & Height //
    public void setGameDimensions(int width, int height) {
        this.gameWidth = width;
        this.gameHeight = height;
    }
    public int getHeight() {
        return gameHeight;
    }
    public int getWidth() {
        return gameWidth;
    }

    // Managers //
    public EntityManager getEntityManager() {
        return entityManager;
    }
    public EnemyDirector getEnemyDirector() {
        return enemyDirector;
    }
    public GameUIManager getGameUIManager() {
        return gameUIManager;
    }
    public KeyManager getKeyManager() {
        return keyManager;
    }
    public MouseManager getMouseManager() {
        return mouseManager;
    }
    public TimerManager getTimerManager() {
        return timerManager;
    }
    public GameDataManager getGameDataManager() {
        return gameDataManager;
    }
    public Save getSave() {
        return save;
    }

    // UI //
    public void setUIHidden() {
        visibleUI = false;
    }
    public void setUIVisible() {
        visibleUI = true;
    }
}
