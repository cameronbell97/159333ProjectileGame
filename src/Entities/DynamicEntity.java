package Entities;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;

import Entities.Collision.CollisionArea;
import Game.Handler;
import Game.Game;

/**
 * Cameron Bell - 27/03/2018
 * DynamicEntity Abstract Class
 */

public abstract class DynamicEntity {
// VARIABLES //
    public static final float DEF_SPEED = 1;
    protected float xpos, ypos;
    protected float xmove, ymove;
    protected int width, height;
    protected double direction;
    protected double moveSpeed;
    protected BufferedImage img;
    protected CollisionArea collision;

    AffineTransform aTrans;
    AffineTransformOp aTransOp;
    Handler handler;

// CONSTRUCTORS //
    public DynamicEntity(Handler handler, float x, float y, int w, int h) {
        xpos = x;
        ypos = y;
        width = w;
        height = h;
        direction = 0.5*Math.PI; // direction = 90 degrees but in radians
        moveSpeed = DEF_SPEED;
        this.handler = handler;
    }

// METHODS //
    public abstract void update();
    public abstract void draw(Graphics g);

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

    public CollisionArea getCollision() {
        return collision;
    }

    public float getXpos() {
        return xpos;
    }

    public float getYpos() {
        return ypos;
    }
}
