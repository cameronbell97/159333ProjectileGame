package Game.Entities.Dynamic.Particles;

import Game.Entities.Dynamic.DynamicEntity;
import Game.Entities.Entity;
import Game.Handler;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 * Cameron Bell - 04/05/2018.
 * Debris Particle Entity
 * Ship Debris Particle Effect
 */

public abstract class DebrisParticle extends Particle {
// VARIABLES //
    private static final double DEF_MOVE_SPEED = 0.55;

    private double spriteDirection, spriteRotSpeed;

// CONSTRUCTORS //
    public DebrisParticle(DynamicEntity parent, BufferedImage sprite) {
        super(  parent.getXpos() + (parent.getWidth() / 2) - (DEF_PARTICLE_WIDTH / 2),
                parent.getYpos() + (parent.getHeight() / 2) - (DEF_PARTICLE_HEIGHT / 2),
                DEF_PARTICLE_WIDTH,
                DEF_PARTICLE_HEIGHT,
                parent.getRealDirection()
        );
        img = sprite;
        this.spriteDirection = direction;

        setPosAndDir();

        moveSpeed = DEF_MOVE_SPEED;

        // Set Move Speed
        ymove = (float)(moveSpeed * -Math.sin(direction));
        xmove = (float)(moveSpeed * Math.cos(direction));

        // Set Rotation Speed
        spriteRotSpeed = Handler.getDoubleFromRange(-0.001*Math.PI, 0.001*Math.PI);;
        rotateSprite(1);

        handler.getTimerManager().newCodeTimer(200, this, "DIE");
    }

// METHODS //
    // Method Override - Update Particle Entity State //
    @Override
    public void update(int dt) {
        super.update(dt);
        move(dt);
        rotateSprite(dt);
    }

    // Method Override - To Handle Collisions //
    @Override
    public void collide(Entity ec) {

    }

    // Method - Rotate Sprite to Given Direction //
    public void rotateSprite(int dt) {
        // TODO // Rotate Sprite Without Cutoffs
        spriteDirection += dt * spriteRotSpeed;
        aTrans = AffineTransform.getRotateInstance(-spriteDirection + (Math.PI / 2), width / 2, height / 2);
        aTransOp = new AffineTransformOp(aTrans, AffineTransformOp.TYPE_BILINEAR);
    }

    // Abstract Method - Set Position and Direction of Particle //
    protected abstract void setPosAndDir();
}
