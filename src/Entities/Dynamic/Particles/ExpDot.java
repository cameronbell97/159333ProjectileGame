package Entities.Dynamic.Particles;

import Assets.AssetManager;
import Entities.Collision.CollisionBox;
import Entities.Entity;
import Entities.EntityManager;
import Game.Handler;
import Game.Launcher;

/**
 * Created by Cameron on 6/04/2018.
 */
public class ExpDot extends Particle {
// VARIABLES //
    private static final int OFFSCREEN_BOUNDARY = 16;

// CONSTRUCTORS //
    public ExpDot(Handler handler, Entity parent) {
        super(handler, parent.getXpos()+(parent.getWidth()/2-5), parent.getYpos()+(parent.getHeight()/2-5), 10, 10);
        this.parent = parent;
        collision = new CollisionBox(handler, xpos, ypos, width, height, 0, 0, this);
        img = AssetManager.get().getSprite(10, Game.Game.getIntFromRange(0, 3), 1);
    }

// METHODS //
    @Override
    public void update() {

        if(     xpos <= -OFFSCREEN_BOUNDARY ||
                ypos <= -OFFSCREEN_BOUNDARY ||
                xpos >= Launcher.DEF_GAME_WIDTH + OFFSCREEN_BOUNDARY ||
                ypos >= Launcher.DEF_GAME_HEIGHT + OFFSCREEN_BOUNDARY) {
            destroy();
        }

    }

    @Override
    public void collide(Entity ec) {
        if(ec instanceof Entities.Dynamic.PlayerEntity) {
            EntityManager.get().unsubscribe(collision);
            destroy();
        }
    }
}
