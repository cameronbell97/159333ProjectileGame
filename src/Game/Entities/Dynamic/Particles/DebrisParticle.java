package Game.Entities.Dynamic.Particles;

import Game.Entities.Dynamic.DynamicEntity;
import Game.Entities.Entity;
import Game.Timer.TimerManager;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 * Created by Cameron on 4/05/2018.
 */
public abstract class DebrisParticle extends Particle {
// VARIABLES //
    private static final double DEF_MOVE_SPEED = 0.55;

    private double spriteDirection;
    private double spriteRotSpeed;

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
        spriteRotSpeed = Game.Game.getDoubleFromRange(-0.001*Math.PI, 0.001*Math.PI);;
        rotateSprite();

        TimerManager.get().newCodeTimer(200, this, "DIE");
    }

// METHODS //
    @Override
    public void update() {
        super.update();
        move();
        rotateSprite();
    }

    @Override
    public void collide(Entity ec) {

    }

    @Override
    public void rotateSprite() {
        // TODO // Rotate Sprite Without Cutoffs
        spriteDirection += spriteRotSpeed;
        aTrans = AffineTransform.getRotateInstance(-spriteDirection + (Math.PI / 2), width / 2, height / 2);
        aTransOp = new AffineTransformOp(aTrans, AffineTransformOp.TYPE_BILINEAR);
    }

    protected abstract void setPosAndDir();
}
