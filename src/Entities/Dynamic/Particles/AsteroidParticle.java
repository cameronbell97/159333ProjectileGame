package Entities.Dynamic.Particles;

import Assets.AssetManager;
import Entities.Dynamic.DynamicEntity;
import Entities.Entity;
import Game.Handler;
import Timer.TimerManager;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

/**
 * Created by Cameron on 5/04/2018.
 */
public class AsteroidParticle extends Particle {
// VARIABLES //
    private double spriteDirection;
    private double spriteRotation;

// CONSTRUCTORS //
    public AsteroidParticle(Handler handler, DynamicEntity parent, Double direction) {
        super(handler, parent.getXpos(), parent.getYpos(), DEF_PARTICLE_WIDTH, DEF_PARTICLE_HEIGHT);
        TimerManager.get().newTimer(100, this, "DIE");
        setNewPosition();
        this.direction = direction;
        this.spriteDirection = direction;
        moveSpeed = 0.3;
        img = AssetManager.get().getSprite(10, Game.Game.getIntFromRange(0, 3), 0);

        // Set Move Speed
        ymove = (float)(moveSpeed * -Math.sin(direction));
        xmove = (float)(moveSpeed * Math.cos(direction));

        // Set Rotation
        spriteRotation = Game.Game.getDoubleFromRange(-0.05*Math.PI, 0.02*Math.PI);
        rotateSprite();
    }

// METHODS //
    @Override
    public void update() {
        move();
        rotateSprite();
    }

    @Override
    public void collide(Entity ec) {

    }

    @Override
    public void rotateSprite() {
        // TODO // Rotate Sprite Without Cutoffs
        spriteDirection += spriteRotation;
        aTrans = AffineTransform.getRotateInstance(-spriteDirection + (Math.PI / 2), width / 2, height / 2);
        aTransOp = new AffineTransformOp(aTrans, AffineTransformOp.TYPE_BILINEAR);
    }

    // Method that sets a new position at a random point up to
    // 4 pixels away from the current position in every direction
    private void setNewPosition() {
        setXpos(Game.Game.getFloatFromRange(xpos-4+22, xpos+4+22));
        setYpos(Game.Game.getFloatFromRange(ypos-4+22, ypos+4+22));
    }
}
