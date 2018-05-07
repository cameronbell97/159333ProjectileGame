package Game;

import Game.Data.Settings;
import Game.Entities.Dynamic.PlayerEntity;
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

// CONSTRUCTOR //
    private Handler() {
        entityManager = new EntityManager();
    }

// METHODS //
    public void update() {
        entityManager.update();
    }

    public void draw(Graphics g) {
        entityManager.draw(g);
        if(Settings.DEBUG_DRAW_COLLISIONS) entityManager.drawCollisionBoxes(g);
    }

    public void newGame() {
        entityManager = new EntityManager();
        entityManager.getPlayer().setCollisionBox();
    }

// GETTERS & SETTERS //
    public EntityManager getEntityManager() {
        return entityManager;
    }
}
