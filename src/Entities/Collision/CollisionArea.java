package Entities.Collision;

import Entities.DynamicEntity;

import java.awt.*;

public abstract class CollisionArea {
// VARIABLES //
    protected float xpos, ypos;

// CONSTRUCTORS //
    public CollisionArea() {
        xpos = 0;
        ypos = 0;
    }
    public CollisionArea(float x, float y) {
        xpos = x;
        ypos = y;
    }

// METHODS //
    public abstract void draw(Graphics g);
    public abstract void update(DynamicEntity e);

// GETTERS & SETTERS //
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
