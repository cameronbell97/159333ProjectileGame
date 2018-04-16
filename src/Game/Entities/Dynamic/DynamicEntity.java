package Game.Entities.Dynamic;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import Game.Entities.Entity;
import Game.Entities.EntityManager;

/**
 * Cameron Bell - 27/03/2018
 * Dynamic Entity Abstract Class
 */

public abstract class DynamicEntity extends Entity{
// VARIABLES //
    public static final float DEF_SPEED = 1;
    protected float xmove, ymove;
    protected double direction;
    protected double moveSpeed;

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
    }

// METHODS //
    public void setMoveSpeeds() {
        ymove = (float)(moveSpeed * -Math.sin(direction));
        xmove = (float)(moveSpeed * Math.cos(direction));
    }

    protected void move() {
        moveX();
        moveY();
    }
    protected void moveX() {
        xpos += xmove;
    }
    protected void moveY() {
        ypos += ymove;
    }

    // Deceleration mechanics
    protected void decelerate(float decelRate) {
        if (xmove > 0) xmove = Math.max(0, xmove - xmove * ((float) 0.01 + decelRate));
        if (xmove < 0) xmove = Math.min(0, xmove - xmove * ((float) 0.01 + decelRate));
        if (ymove > 0) ymove = Math.max(0, ymove - ymove * ((float) 0.01 + decelRate));
        if (ymove < 0) ymove = Math.min(0, ymove - ymove * ((float) 0.01 + decelRate));

    }

    protected void strafeLeft(double speed) {
        float oldymove = ymove;
        float oldxmove = xmove;

        ymove = (float)(speed * -Math.sin(direction+(Math.PI/2)));
        xmove = (float)(speed * Math.cos(direction+(Math.PI/2)));
        move();

        ymove = oldymove;
        xmove = oldxmove;
    }

    protected void strafeRight(double speed) {
        float oldymove = ymove;
        float oldxmove = xmove;

        ymove = (float)(speed * -Math.sin(direction-(Math.PI/2)));
        xmove = (float)(speed * Math.cos(direction-(Math.PI/2)));
        move();

        ymove = oldymove;
        xmove = oldxmove;

    }

    // Method to rotateSprite the image
    protected void rotateSprite() {
        // TODO // Rotate Sprite Without Cutoffs
        aTrans = AffineTransform.getRotateInstance(-direction+(Math.PI/2), width/2, height/2);
        aTransOp = new AffineTransformOp(aTrans, AffineTransformOp.TYPE_BILINEAR);

    }

    // Method to rotateSprite the image
    public void rotateSprite(double dir) {
        // TODO // Rotate Sprite Without Cutoffs
        direction = dir;
        aTrans = AffineTransform.getRotateInstance(-direction+(Math.PI/2), width/2, height/2);
        aTransOp = new AffineTransformOp(aTrans, AffineTransformOp.TYPE_BILINEAR);

    }

    @Override
    public void draw(Graphics g) {
//        if(img == null) return;
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(aTransOp.filter(img, null), (int)xpos, (int)ypos,  null);
    }

// GETTERS & SETTERS //
    public double getMoveSpeed() { return moveSpeed; }
    public void setSpeed(double ms) { moveSpeed = ms; }
    public double getDirection() {
        return direction + (Math.PI / 2);
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }
}
