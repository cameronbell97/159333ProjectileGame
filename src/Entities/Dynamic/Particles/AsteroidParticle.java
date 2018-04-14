package Entities.Dynamic.Particles;

import Assets.AssetManager;
import Entities.Dynamic.DynamicEntity;
import Entities.Entity;
import Timer.TimerManager;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;

/**
 * Created by Cameron on 5/04/2018.
 */
public class AsteroidParticle extends Particle {
// VARIABLES //
    private double spriteDirection;
    private double spriteRotation;
    private int level;

// CONSTRUCTORS //
    public AsteroidParticle(DynamicEntity parent, Double direction, int level) {
        super(parent.getXpos(), parent.getYpos(), DEF_PARTICLE_WIDTH, DEF_PARTICLE_HEIGHT, direction);
        TimerManager.get().newCodeTimer(100, this, "DIE");
        setNewPosition();
        this.spriteDirection = direction;
        this.level = level;
        moveSpeed = 0.55;
        img = getSprite();

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

    protected BufferedImage getSprite() {
        if(level < 3) return AssetManager.get().getSprite(11, Game.Game.getIntFromRange(0, 3), 3);
        else return AssetManager.get().getSprite(11, Game.Game.getIntFromRange(0, 3), 4);
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
        setXpos(Game.Game.getFloatFromRange(xpos-(width/2/2)+22, xpos+(width/2/2)+22));
        setYpos(Game.Game.getFloatFromRange(ypos-(height/2/2)+22, ypos+(height/2/2)+22));
    }
}
