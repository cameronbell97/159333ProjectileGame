package Entities.Collision;

import Entities.Dynamic.DynamicEntity;

import java.awt.*;

public class CollisionBox{
// VARIABLES //
    private float xpos, ypos;
    private int width, height;

// CONSTRUCTORS //
    public CollisionBox(float x, float y, int w, int h) {
        xpos = 0;
        ypos = 0;
        width = w;
        height = h;
    }

// METHODS //
    public void draw(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillRect((int)xpos, (int)ypos, width, height);
    }

    public void update(DynamicEntity e) {
        setXpos(e.getXpos()+18);
        setYpos(e.getYpos()+18);
    }

// GETTERS & SETTERS //
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

    public float getXpos() {
        return xpos;
    }

    public void setXpos(float xpos) {
        this.xpos = xpos;
    }

    public float getYpos() {
        return ypos;
    }

    public void setYpos(float ypos) {
        this.ypos = ypos;
    }
}
