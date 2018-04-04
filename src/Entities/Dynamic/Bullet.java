package Entities.Dynamic;

import Entities.Entity;
import Entities.EntityManager;
import Game.Handler;
import Game.Launcher;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

public abstract class Bullet extends DynamicEntity{
// VARIABLES //
    private static final int OFFSCREEN_BOUNDARY = 32;

// CONSTRUCTORS //
    public Bullet(Handler handler, int w, int h, DynamicEntity parent) {
        super(
                handler,
                parent.getXpos() + (parent.getWidth() / 2) - (w / 2),
                parent.getYpos() + (parent.getHeight() / 2) - (h / 2),
                w, h)
        ;
        this.parent = parent;
        direction = parent.getDirection() - (Math.PI/2); // Get the direction

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
        if(xpos <= -OFFSCREEN_BOUNDARY || ypos <= -OFFSCREEN_BOUNDARY || xpos >= Launcher.DEF_GAME_WIDTH + OFFSCREEN_BOUNDARY || ypos >= Launcher.DEF_GAME_HEIGHT + OFFSCREEN_BOUNDARY) {
            destroy();
        }
        collision.update();
        collision.rotate(direction);
    }

    protected void destroy() {
        EntityManager.get().unsubscribe(this.collision);
        EntityManager.get().unsubscribe(this);
    }
}
