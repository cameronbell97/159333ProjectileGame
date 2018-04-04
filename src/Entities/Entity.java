package Entities;

import Entities.Collision.CollisionBox;
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
    protected CollisionBox collision;
    protected Handler handler;
    protected Entity parent;

// CONSTRUCTORS //
    public Entity(Handler handler, float x, float y, int w, int h) {
        this.xpos = x;
        this.ypos = y;
        this.width = w;
        this.height = h;
        this.handler = handler;
        this.parent = null;
    }

// METHODS //
    public abstract void update();
    public abstract void draw(Graphics g);

// GETTERS & SETTERS //
    public CollisionBox getCollision() {
        return collision;
    }
    public float getXpos() {
        return xpos;
    }
    public float getYpos() {
        return ypos;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public void setImg(BufferedImage img) {
        this.img = img;
    }
    public Entity getParent() {
        return parent;
    }
    public abstract void collide(Entity ec);
}
