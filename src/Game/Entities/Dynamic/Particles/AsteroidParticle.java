package Game.Entities.Dynamic.Particles;

import Game.Display.Assets.AssetManager;
import Game.Entities.Dynamic.DynamicEntity;
import Game.Entities.Entity;
import Game.Handler;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 * Cameron Bell - 05/04/2018.
 * Asteroid Particle Entity
 * Asteroid Debris Particle Effect
 */

public class AsteroidParticle extends Particle {
// VARIABLES //
    private double spriteDirection, spriteRotation;
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
        rotateSprite(1);
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

    // Method - Get Sprite Correlating to Level //
    protected BufferedImage getSprite() {
        if(level < 3) return AssetManager.get().getSprite(11, Handler.getIntFromRange(0, 3), 3);
        else return AssetManager.get().getSprite(11, Handler.getIntFromRange(0, 3), 4);
    }

    // Method - Rotate Sprite to Given Direction //
    public void rotateSprite(int dt) {
        // TODO // Rotate Sprite Without Cutoffs
        spriteDirection += dt * spriteRotation;
        aTrans = AffineTransform.getRotateInstance(-spriteDirection + (Math.PI / 2), width / 2, height / 2);
        aTransOp = new AffineTransformOp(aTrans, AffineTransformOp.TYPE_BILINEAR);
    }

    // Method - Set new position at a random point up to 4 pixels away from the current position in any direction //
    private void setNewPosition() {
        setXpos(Handler.getFloatFromRange(xpos-(width/2/2)+22, xpos+(width/2/2)+22));
        setYpos(Handler.getFloatFromRange(ypos-(height/2/2)+22, ypos+(height/2/2)+22));
    }
}
