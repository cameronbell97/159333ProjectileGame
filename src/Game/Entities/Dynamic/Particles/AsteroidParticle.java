package Game.Entities.Dynamic.Particles;

import Game.Display.Assets.AssetManager;
import Game.Entities.Dynamic.DynamicEntity;
import Game.Entities.Entity;
import Game.Handler;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 * Created by Cameron on 5/04/2018.
 */
public class AsteroidParticle extends Particle {
// VARIABLES //
    private double spriteDirection;
    private double spriteRotation;
    protected int level;

// CONSTRUCTORS //
    public AsteroidParticle(DynamicEntity parent, Double direction, int level) {
        super(parent.getXpos(), parent.getYpos(), DEF_PARTICLE_WIDTH, DEF_PARTICLE_HEIGHT, direction);
        handler.getTimerManager().newCodeTimer(100, this, "DIE");
        setNewPosition();
        this.spriteDirection = direction;
        this.level = level;
        moveSpeed = 0.55;
        img = getSprite();

        // Set Move Speed
        ymove = (float)(moveSpeed * -Math.sin(direction));
        xmove = (float)(moveSpeed * Math.cos(direction));

        // Set Rotation
        spriteRotation = Handler.getDoubleFromRange(-0.05*Math.PI, 0.02*Math.PI);
        rotateSprite();
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

    protected BufferedImage getSprite() {
        if(level < 3) return AssetManager.get().getSprite(11, Handler.getIntFromRange(0, 3), 3);
        else return AssetManager.get().getSprite(11, Handler.getIntFromRange(0, 3), 4);
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
        setXpos(Handler.getFloatFromRange(xpos-(width/2/2)+22, xpos+(width/2/2)+22));
        setYpos(Handler.getFloatFromRange(ypos-(height/2/2)+22, ypos+(height/2/2)+22));
    }
}
