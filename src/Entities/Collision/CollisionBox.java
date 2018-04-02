package Entities.Collision;

import Entities.Dynamic.DynamicEntity;

import java.awt.*;

public class CollisionBox extends CollisionArea{
// VARIABLES //
    private int width, height;

// CONSTRUCTORS //
    public CollisionBox() {
        super();
        width = 0;
        height = 0;
    }
    public CollisionBox(float x, float y, int w, int h) {
        super(x, y);
        width = w;
        height = h;
    }

// METHODS //
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillRect((int)xpos, (int)ypos, width, height);
    }

    @Override
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
}
