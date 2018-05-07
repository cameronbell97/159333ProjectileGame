package Game.Entities.Dynamic.Bullets;

import Game.Entities.Dynamic.DynamicEntity;
import Game.Entities.EntityManager;
import Game.Data.Settings;

public abstract class Bullet extends DynamicEntity {
// VARIABLES //
    private static final int OFFSCREEN_BOUNDARY = 8;

// CONSTRUCTORS //
    public Bullet(int w, int h, DynamicEntity parent) {
        super(
                parent.getXpos() + (parent.getWidth() / 2) - (w / 2),
                parent.getYpos() + (parent.getHeight() / 2) - (h / 2),
                w, h, (parent.getDirection() - (Math.PI/2)))
        ;
        this.parent = parent;
        setMoveSpeeds();
    }

// METHODS //
    @Override
    public void update() {
        move();
        if(xpos <= -OFFSCREEN_BOUNDARY || ypos <= -OFFSCREEN_BOUNDARY || xpos >= Settings.game_width + OFFSCREEN_BOUNDARY || ypos >= Settings.game_height + OFFSCREEN_BOUNDARY) {
            destroy();
        }
        if(collision != null) {
            collision.update();
            collision.rotateSprite(direction);
        }
    }

    protected void destroy() {
        EntityManager em = handler.getEntityManager();
        em.unsubscribe(this.collision);
        em.unsubscribe(this);
        if(this.collision != null) this.collision.nullParent();
        this.collision = null;
    }
}
