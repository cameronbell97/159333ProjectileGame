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

/**
 * Created by Cameron on 7/05/2018.
 */
public class Handler {
// SINGLETON PATTERN //
    private static Handler self = new Handler();
    public static Handler get() { return self; }

// VARIABLES //
    // Managers //
    private KeyManager keyManager;
    private MouseManager mouseManager;

    // Game Managers //
    private TimerManager timerManager;
    private EntityManager entityManager;
    private EnemyDirector enemyDirector;
    private GameUIManager gameUIManager;
    private GameDataManager gameDataManager;

    Save save;

    private boolean visibleUI;

// CONSTRUCTOR //
    private Handler() {
        // Initialise Save Data
        save = new Save();
        if(!save.load()) {
            save.create(); // If load fails, create a blank save
            if(!save.load()) {
                Game.end(); // If load fails a second time, kill the program
            }
        }

        // Initialise Managers
        keyManager = new KeyManager();
        mouseManager = new MouseManager();
        visibleUI = true;
    }

// METHODS //
    public void update() {
        keyManager.update();
    }

    public void updateGame() {
        timerManager.update();
        entityManager.update();
        enemyDirector.update();
        gameUIManager.update();
        gameDataManager.update();
    }

    public void draw(Graphics g) {
        // Draw All Game Entities (and their collision boxes if enabled)
        entityManager.draw(g);
        if(Settings.DEBUG_DRAW_COLLISIONS) entityManager.drawCollisionBoxes(g);

        // Draw User Interface Last // So it appears over everything else
        if(visibleUI) gameUIManager.draw(g);
    }

    public void newGame() {
        timerManager = new TimerManager();
        entityManager = new EntityManager();
        entityManager.getPlayer().setCollisionBox();
        enemyDirector = new EnemyDirector(this);
        gameUIManager = new GameUIManager(this);
        gameDataManager = new GameDataManager();
        setUIVisible();
    }

    public void endGame() {

    }

// GETTERS & SETTERS //

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

    public void setUIHidden() {
        visibleUI = false;
    }
    public void setUIVisible() {
        visibleUI = true;
    }
}
