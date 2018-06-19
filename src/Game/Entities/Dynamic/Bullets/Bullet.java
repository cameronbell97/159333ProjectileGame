package Game.Entities.Dynamic.Bullets;

import Game.Entities.Dynamic.DynamicEntity;
import Game.Entities.EntityManager;
import Game.Data.Settings;

/**
 * Cameron Bell - 02/04/2018
 * Bullet Abstract Entity
 * Encompasses Bullet Related Functionality
 */

public abstract class Bullet extends DynamicEntity {
// VARIABLES //
    // Statics //
    private static final int OFFSCREEN_BOUNDARY = 8;

    // Data //
    private int damageValue;

// CONSTRUCTORS //
    public Bullet(int w, int h, DynamicEntity parent) {
        super(
                parent.getXpos() + (parent.getWidth() / 2) - (w / 2),
                parent.getYpos() + (parent.getHeight() / 2) - (h / 2),
                w, h, (parent.getDirection() - (Math.PI/2)))
        ;
        this.parent = parent;

        damageValue = setDamageValue();

        setMoveSpeeds();
    }

// METHODS //
    // Method - Update the State of the Entity //
    @Override
    public void update(int dt) {
        move(dt);
        if(xpos <= -OFFSCREEN_BOUNDARY || ypos <= -OFFSCREEN_BOUNDARY || xpos >= Settings.game_width + OFFSCREEN_BOUNDARY || ypos >= Settings.game_height + OFFSCREEN_BOUNDARY) {
            destroy();
        }
        if(collision != null) {
            collision.update(dt);
            collision.rotateSprite(direction);
        }
    }

    // Method - Destroy the Entity //
    protected void destroy() {
        EntityManager em = handler.getEntityManager();
        em.unsubscribe(this.collision);
        em.unsubscribe(this);
        if(this.collision != null) this.collision.nullParent();
        this.collision = null;
    }


// GETTERS & SETTERS //
    public int getDamageValue() {
        return damageValue;
    }
    protected abstract int setDamageValue();
}
