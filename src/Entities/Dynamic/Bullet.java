package Entities.Dynamic;

import Entities.EntityManager;
import Game.Handler;
import Game.Launcher;

import java.awt.*;

public abstract class Bullet extends DynamicEntity{
// CONSTRUCTORS //
    public Bullet(Handler handler, int w, int h, DynamicEntity parent) {
        super(
                handler,
                parent.getXpos() + (parent.getWidth() / 2) - (w / 2),
                parent.getYpos() + (parent.getHeight() / 2) - (h / 2),
                w, h)
        ;

        direction = parent.getDirection();

        // Move it to the nose of the ship
        ymove = (float)(20 * -Math.sin(direction));
        xmove = (float)(20 * Math.cos(direction));
        move();

        // Reset move speed
        moveSpeed = 12;
        ymove = (float)(moveSpeed * -Math.sin(direction));
        xmove = (float)(moveSpeed * Math.cos(direction));

        // Rotate the sprite
        rotate();
    }

// METHODS //
    @Override
    public void update() {
        move();
        if(xpos <= 32 || ypos <= 32 || xpos >= Launcher.DEF_GAME_WIDTH + 32 || ypos >= Launcher.DEF_GAME_HEIGHT + 32) {
            destroy();
        }
    }

    private void destroy() {
        EntityManager.get().unsubscribe(this);
    }
}
