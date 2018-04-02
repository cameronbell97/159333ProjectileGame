package Entities;

import Entities.Collision.CollisionBox;
import Game.Handler;
import javafx.geometry.Point2D;

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

    // Get the position of the centre of the entity
    public Point2D getCentre() {
        return new Point2D(xpos + width / 2, ypos = height / 2);
    }

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
    public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
}
