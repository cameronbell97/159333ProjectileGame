package Entities.Collision;

import Assets.AssetManager;
import Entities.Dynamic.DynamicEntity;
import Entities.Entity;
import Entities.EntityManager;
import Game.Handler;
import javafx.geometry.Point2D;
import Game.SAT;
import java.util.List;
import java.awt.Graphics;
import java.awt.Color;

public class CollisionBox extends DynamicEntity{
// VARIABLES //
    private float xoff, yoff;
    private Entity parent;

// CONSTRUCTORS //
    public CollisionBox(Handler handler, float x, float y, int w, int h, float xo, float yo, Entity parent) {
        super(handler, x, y, w, h);
        xoff = xo;
        yoff = yo;
        this.parent = parent;
        img = AssetManager.get().getSprite("Coll");
        EntityManager.get().subscribe(this);
    }

// METHODS //

    public void update() {
        setXpos(parent.getXpos()+xoff);
        setYpos(parent.getYpos()+yoff);
        // TODO // Get min & max values to use for checking out of bounds / screen
//        corners = SAT.getCorners(this);
//        double min1 = corns1.stream().mapToDouble(p -> p.dotProduct(ax)).min().getAsDouble();
//        double min2 = corns1.stream().mapToDouble(p -> p.dotProduct(ax)).min().getAsDouble();
    }

    // Get the position of the centre of the entity
    public Point2D getCentre() {
        return new Point2D(xpos + width / 2, ypos + height / 2);
    }

    private void destroy() {
        EntityManager.get().unsubscribe(this);
    }

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

    public float getXoff() {
        return xoff;
    }

    public float getYoff() {
        return yoff;
    }
}
