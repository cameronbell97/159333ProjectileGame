package Entities;

import Entities.Collision.CollisionArea;
import Game.Handler;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Cameron Bell - 02/04/2018
 * Entity Abstract Class
 */

public abstract class Entity {
// VARIABLES //
    protected float xpos, ypos;
    protected int width, height;
    protected BufferedImage img;
    protected CollisionArea collision;
    protected Handler handler;

// CONSTRUCTORS //
    public Entity(Handler handler, float x, float y, int w, int h) {
        this.xpos = x;
        this.ypos = y;
        this.width = w;
        this.height = h;
        this.handler = handler;
    }

// METHODS //
    public abstract void update();
    public abstract void draw(Graphics g);

// GETTERS & SETTERS //
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
