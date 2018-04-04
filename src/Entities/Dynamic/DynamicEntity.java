package Entities.Dynamic;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import Entities.Entity;
import Game.Handler;

/**
 * Cameron Bell - 27/03/2018
 * Dynamic Entity Abstract Class
 */

public abstract class DynamicEntity extends Entity{
// VARIABLES //
    public static final float DEF_SPEED = 1;
    protected float xmove, ymove;
    protected double direction;
    protected double moveSpeed;

    AffineTransform aTrans;
    AffineTransformOp aTransOp;

// CONSTRUCTORS //
    public DynamicEntity(Handler handler, float x, float y, int w, int h) {
        super(handler, x, y, w, h);
        direction = 0.5*Math.PI; // direction = 90 degrees but in radians
        moveSpeed = DEF_SPEED;
        aTrans = AffineTransform.getRotateInstance(0, width/2, height/2);
        aTransOp = new AffineTransformOp(aTrans, AffineTransformOp.TYPE_BILINEAR);
    }

// METHODS //
    protected void move() {
        moveX();
        moveY();
    }
    protected void moveX() {
        xpos += xmove;
    }
    protected void moveY() {
        ypos += ymove;
    }

    // Method to rotate the image
    protected void rotate() {
        // TODO // Rotate Sprite Without Cutoffs
        aTrans = AffineTransform.getRotateInstance(-direction+(Math.PI/2), width/2, height/2);
        aTransOp = new AffineTransformOp(aTrans, AffineTransformOp.TYPE_BILINEAR);

    }

    // Method to rotate the image
    public void rotate(double dir) {
        // TODO // Rotate Sprite Without Cutoffs
        direction = dir;
        aTrans = AffineTransform.getRotateInstance(-direction+(Math.PI/2), width/2, height/2);
        aTransOp = new AffineTransformOp(aTrans, AffineTransformOp.TYPE_BILINEAR);

    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(aTransOp.filter(img, null), (int)xpos, (int)ypos,  null);
    }

// GETTERS & SETTERS //
    public double getMoveSpeed() { return moveSpeed; }
    public void setSpeed(double ms) { moveSpeed = ms; }
    public double getDirection() {
        return direction + (Math.PI / 2);
    }
}
