package Entities;

import java.awt.*;
import Game.Handler;

/**
 * Cameron Bell - 27/03/2018
 * Entity Abstract Class
 */

public abstract class Entity {
// VARIABLES //
    public static final float DEF_SPEED = 1;
    protected float xpos, ypos;
    protected float xmove, ymove;
    protected int width, height;
    protected double direction;
    protected double moveSpeed;
    Handler handler;

// CONSTRUCTORS //
    public Entity (Handler handler, float x, float y, int w, int h) {
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

    public void move() {
        xpos += xmove;
        ypos += ymove;
    }
}
