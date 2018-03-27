package Entities;

import java.awt.*;

/**
 * Cameron Bell - 27/03/2018
 * Entities.Entity Abstract Class
 */

public abstract class Entity {
    protected float xpos, ypos;

    public Entity (float x, float y) {
        xpos = x;
        ypos = y;
    }

    public abstract void update();
    public abstract void draw(Graphics g);

}
