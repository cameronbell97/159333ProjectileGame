package Entities.Collision;

import Entities.Dynamic.DynamicEntity;
import Entities.EntityManager;
import Game.SAT;
import javafx.geometry.Point2D;
import java.util.List;
import java.awt.Graphics;
import java.awt.Color;

public class CollisionBox{
// VARIABLES //
    private float xpos, ypos, xoff, yoff;
    private int width, height;
    private double direction;
    private DynamicEntity parent;

// CONSTRUCTORS //
    public CollisionBox(float x, float y, int w, int h, float xo, float yo, DynamicEntity p) {
        xpos = x;
        ypos = y;
        width = w;
        height = h;
        xoff = xo;
        yoff = yo;
        direction = 0;
        parent = p;
        EntityManager.get().subscribe(this);
    }

// METHODS //
    public void draw(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillRect((int)xpos, (int)ypos, width, height);
    }

    public void update() {
        setXpos(parent.getXpos()+xoff);
        setYpos(parent.getYpos()+yoff);
//        direction = parent.getDirection();
        // TODO // Get min & max values
//        corners = SAT.getCorners(this);
//        double min1 = corns1.stream().mapToDouble(p -> p.dotProduct(ax)).min().getAsDouble();
//        double min2 = corns1.stream().mapToDouble(p -> p.dotProduct(ax)).min().getAsDouble();
    }

    // Get the position of the centre of the entity
    public Point2D getCentre() {
        return new Point2D(xpos + width / 2, ypos = height / 2);
    }

    private void destroy() {
        EntityManager.get().unsubscribe(this);
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

    public float getXoff() {
        return xoff;
    }

    public float getYoff() {
        return yoff;
    }

    public double getDirection() {
        return direction;
    }
}
