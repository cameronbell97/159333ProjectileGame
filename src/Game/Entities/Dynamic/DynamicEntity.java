package Game.Entities.Dynamic;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import Game.Entities.Entity;
import Game.Entities.EntityManager;

/**
 * Cameron Bell - 27/03/2018
 * Dynamic Entity Abstract Class
 * Abstract Entity Class encompassing everything an entity that can move in all directions needs
 */

public abstract class DynamicEntity extends Entity{
// VARIABLES //
    public static final float DEF_SPEED = 1;
    protected float xmove, ymove;
    protected double direction;
    protected double moveSpeed;
    protected double anchorx;
    protected double anchory;

    protected AffineTransform aTrans;
    protected AffineTransformOp aTransOp;

// CONSTRUCTORS //
    public DynamicEntity(float x, float y, int w, int h, double direction) {
        super(x, y, w, h);
        this.direction = direction;
        moveSpeed = DEF_SPEED;
        setMoveSpeeds();
        aTrans = AffineTransform.getRotateInstance(0, width/2, height/2);
        aTransOp = new AffineTransformOp(aTrans, AffineTransformOp.TYPE_BILINEAR);
        setMoveSpeeds();
        anchorx = width/2;
        anchory = height/2;
    }

// METHODS //
    // Abstract Method - Used for initial spacial setup for the Collision Box //
    public abstract void setCollisionBox();

    // Method - Calculates xmove and ymove from direction and move speed //
    public void setMoveSpeeds() {
        ymove = (float)(moveSpeed * -Math.sin(direction));
        xmove = (float)(moveSpeed * Math.cos(direction));
    }

    // Methods - Moves on the X and Y axis according to xmove and ymove //
    protected void move(int dt) {
        moveX(dt);
        moveY(dt);
    }
    protected void moveX(int dt) {
        xpos += dt * xmove;
    }
    protected void moveY(int dt) {
        ypos += dt * ymove;
    }

    // Method - Deceleration Mechanics //
    protected void decelerate(int dt, float decelRate) {
        if (xmove > 0) xmove = Math.max(0, xmove - dt * xmove * ((float) 0.01 + decelRate));
        if (xmove < 0) xmove = Math.min(0, xmove - dt * xmove * ((float) 0.01 + decelRate));
        if (ymove > 0) ymove = Math.max(0, ymove - dt * ymove * ((float) 0.01 + decelRate));
        if (ymove < 0) ymove = Math.min(0, ymove - dt * ymove * ((float) 0.01 + decelRate));

    }

    // Method - Strafes the Entity Left //
    protected void strafeLeft(double speed) {
        float oldymove = ymove;
        float oldxmove = xmove;

        ymove = (float)(speed * -Math.sin(direction+(Math.PI/2)));
        xmove = (float)(speed * Math.cos(direction+(Math.PI/2)));
        move(1);

        ymove = oldymove;
        xmove = oldxmove;
    }

    // Method - Strafes the Entity Right //
    protected void strafeRight(double speed) {
        float oldymove = ymove;
        float oldxmove = xmove;

        ymove = (float)(speed * -Math.sin(direction-(Math.PI/2)));
        xmove = (float)(speed * Math.cos(direction-(Math.PI/2)));
        move(1);

        ymove = oldymove;
        xmove = oldxmove;

    }

    // Method - Rotate the Sprite according to the direction and anchor point //
    protected void rotateSprite() {
        // TODO // Rotate Sprite Without Cutoffs
        aTrans = AffineTransform.getRotateInstance(-direction+(Math.PI/2), anchorx, anchorx);
        aTransOp = new AffineTransformOp(aTrans, AffineTransformOp.TYPE_BILINEAR);

    }

    // Method - Rotate the Sprite according to the given direction and anchor point //
    public void rotateSprite(double dir) {
        // TODO // Rotate Sprite Without Cutoffs
        direction = -dir;
        aTrans = AffineTransform.getRotateInstance(direction+(Math.PI/2), anchorx, anchory);
        aTransOp = new AffineTransformOp(aTrans, AffineTransformOp.TYPE_BILINEAR);

    }

    // Method - Draw the Entity's Sprite //
    @Override
    public void draw(Graphics g) {
        if(img != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(aTransOp.filter(img, null), (int) xpos, (int) ypos, null);
        }
    }

// GETTERS & SETTERS //
    public double getMoveSpeed() { return moveSpeed; }
    public void setSpeed(double ms) { moveSpeed = ms; }
    public double getDirection() {
        return direction + (Math.PI / 2);
    }
    public double getRealDirection() {
        return direction;
    }
    public void setDirection(double direction) {
        this.direction = direction;
    }

}
