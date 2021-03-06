package Game.Entities;

import Game.Entities.Collision.CollisionBox;
import Game.Data.Settings;
import Game.Handler;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Cameron Bell - 02/04/2018
 * Entity Abstract Class
 * Class to define basic methods all entities should have
 */

public abstract class Entity {
// VARIABLES //
    protected Handler handler = Handler.get();

    protected float xpos, ypos;
    protected int width, height;

    protected BufferedImage img;
    protected CollisionBox collision;
    protected Entity parent;

// CONSTRUCTORS //
    public Entity(float x, float y, int w, int h) {
        this.xpos = x;
        this.ypos = y;
        this.width = w;
        this.height = h;
        this.parent = null;
    }

// METHODS //
    // Methods - Abstract Update & Draw methods, to be implemented & then called at the Update & Draw phases //
    public abstract void update(int dt);
    public abstract void draw(Graphics g);

    // Methods - For getting Overlapping (x,y) for wrapping when out of bounds //
    public float getOverlapX() {
        int w = Settings.game_width / 2;
        if(xpos > w) return -width;
        else return Settings.game_width;
    }
    public float getOverlapY() {
        int h = Settings.game_height / 2;
        if(ypos > h) return -height;
        else return Settings.game_height;
    }

    // Method - Clearing values //
    public void clearData() {
        parent = null;
        collision = null;
    }

    // Method - Nulls Parent Variable //
    public void nullParent() {
        if(parent != null) parent = null;
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
    public void setXpos(float xpos) {
        this.xpos = xpos;
    }
    public void setYpos(float ypos) {
        this.ypos = ypos;
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
    public int getMaxSize() {
        return Math.max(width, height);
    }
}
