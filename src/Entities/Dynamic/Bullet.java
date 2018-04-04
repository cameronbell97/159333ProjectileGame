package Entities.Dynamic;

import Entities.Entity;
import Entities.EntityManager;
import Game.Handler;
import Game.Launcher;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

public abstract class Bullet extends DynamicEntity{
// CONSTRUCTORS //
    public Bullet(Handler handler, int w, int h, DynamicEntity parent) {
        super(
                handler,
                parent.getXpos() + (parent.getWidth() / 2) - (w / 2),
                parent.getYpos() + (parent.getHeight() / 2) - (h / 2),
                w, h)
        ;
//        customAffines();
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
        if(xpos <= -32 || ypos <= -32 || xpos >= Launcher.DEF_GAME_WIDTH + 32 || ypos >= Launcher.DEF_GAME_HEIGHT + 32) {
            destroy();
        }
        collision.update();
        collision.rotate(direction);
    }

//    @Override
//    public void draw(Graphics g) {
//        Graphics2D g2d = (Graphics2D) g;
//        g2d.drawImage(aTransOp.filter(img, null), (int)xpos - IMG_X_OFFSET, (int)ypos,  null);
//    }
//
//    private void customAffines() {
//        aTrans = AffineTransform.getRotateInstance(0, (width/2)+(IMG_X_OFFSET*2), height/2);
//        aTransOp = new AffineTransformOp(aTrans, AffineTransformOp.TYPE_BILINEAR);
//    }

    private void destroy() {
        EntityManager.get().unsubscribe(this.collision);
        EntityManager.get().unsubscribe(this);
    }
}
