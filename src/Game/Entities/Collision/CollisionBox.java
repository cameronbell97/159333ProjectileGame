package Game.Entities.Collision;

import Game.Data.Settings;
import Game.Display.Assets.AssetManager;
import Game.Entities.Dynamic.DynamicEntity;
import Game.Entities.Entity;
import Game.Entities.EntityManager;
import javafx.geometry.Point2D;

import java.awt.*;

public class CollisionBox extends DynamicEntity{
// VARIABLES //
    private float xoff, yoff;
    protected Entity parent;

// CONSTRUCTORS //
    public CollisionBox(float x, float y, int w, int h, float xo, float yo, Entity parent) {
        super(x, y, w, h, (Math.PI / 2));
        xoff = xo;
        yoff = yo;
        this.parent = parent;
        if(Settings.DEBUG_DRAW_COLLISIONS) img = AssetManager.get().getSprite("Coll");
        handler.getEntityManager().subscribe(this);
    }

// METHODS //
    public void update(int dt) {
        setXpos(parent.getXpos()+xoff);
        setYpos(parent.getYpos()+yoff);
        // TODO // Get min & max values to use for checking out of bounds / screen
//        corners = SAT.getCorners(this);
//        double min1 = corns1.stream().mapToDouble(p -> p.dotProduct(ax)).min().getAsDouble();
//        double min2 = corns1.stream().mapToDouble(p -> p.dotProduct(ax)).min().getAsDouble();
    }

    @Override
    public void setCollisionBox() {

    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.yellow);
        Rectangle rect = new Rectangle((int)xpos, (int)ypos, width-1, height-1);
        g2d.rotate(direction+Math.PI/2, xpos + width/2, ypos + height/2);
        g2d.draw(rect);
        g2d.rotate(-direction-Math.PI/2, xpos + width/2, ypos + height/2);
    }

    // Get the position of the centre of the entity
    public Point2D getCentre() {
        return new Point2D(xpos + width / 2, ypos + height / 2);
    }
    private void destroy() {
        handler.getEntityManager().unsubscribe(this);
    }

    @Override
    public void collide(Entity ec) {
        if(ec.getParent() != null && parent != null) parent.collide(ec.getParent());
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
    public Entity getParent() {
        return parent;
    }
    public void setAnchor(int x, int y) {
        anchorx = x;
        anchory = y;
    }
}
