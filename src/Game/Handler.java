package Game;

import Game.Data.Settings;
import Game.Display.UserInterface.GameUIManager;
import Game.Entities.Dynamic.PlayerEntity;
import Game.Entities.EnemyDirector;
import Game.Entities.EntityManager;

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
    private EntityManager entityManager;
    private EnemyDirector enemyDirector;
    private GameUIManager gameUIManager;

    private boolean visibleUI;

// CONSTRUCTOR //
    private Handler() {
        visibleUI = true;
    }

// METHODS //
    public void update() {
        entityManager.update();
        enemyDirector.update();
        gameUIManager.update();
    }

    public void draw(Graphics g) {
        // Draw All Game Entities (and their collision boxes if enabled)
        entityManager.draw(g);
        if(Settings.DEBUG_DRAW_COLLISIONS) entityManager.drawCollisionBoxes(g);

        // Draw User Interface Last // So it appears over everything else
        if(visibleUI) gameUIManager.draw(g);
    }

    public void newGame() {
        entityManager = new EntityManager();
        entityManager.getPlayer().setCollisionBox();
        enemyDirector = new EnemyDirector(this);
        gameUIManager = new GameUIManager();
        setUIVisible();
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
    public void setUIHidden() {
        visibleUI = false;
    }
    public void setUIVisible() {
        visibleUI = true;
    }
}
