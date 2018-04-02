package Entities;
import Entities.Collision.CollisionBox;
import Game.Handler;

import Assets.AssetManager;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

/**
 * Cameron Bell - 27/03/2018
 * Player DynamicEntity Class
 * The controllable player
 */

public class PlayerEntity extends VulnerableEntity {
// VARIABLES //
    public static final int DEF_PLAYER_WIDTH = 64;
    public static final int DEF_PLAYER_HEIGHT = 64;
    public static final double DEF_ROT_SPEED = 0.015*Math.PI;
    AssetManager assMan = AssetManager.get();
    private int speedMultiplier;
    private double rotationSpeed;
    private boolean reverseThrust; // If true, player can reverse
    private float decelerate;


// CONSTRUCTORS //
    public PlayerEntity(Handler handler, float x, float y) {
        super(handler, x, y, DEF_PLAYER_WIDTH, DEF_PLAYER_HEIGHT);
        initialise();
    }
    public PlayerEntity(Handler handler, float x, float y, int w, int h) {
        super(handler, x, y, w, h);
        initialise();
    }

// METHODS //
    public void initialise() {
        speedMultiplier = 1;
        setSpeed(4);
        rotationSpeed = DEF_ROT_SPEED;
        img = assMan.getSprite("player");
//        img = assMan.getSprite(1, 2, 0);
        aTrans = AffineTransform.getRotateInstance(0, width/2, height/2);
        aTransOp = new AffineTransformOp(aTrans, AffineTransformOp.TYPE_BILINEAR);
        reverseThrust = true;
        decelerate = (float)0.06;
        collision = new CollisionBox(xpos+18, ypos+18, 28, 28);
    }

    public void move() {
        moveX();
        moveY();
    }

    @Override
    protected void moveX() {
        // If moving right
        if(xmove > 0) {
            // If you would NOT move out of the screen
            if(collision.getXpos() + 28/*CollisionBox Width*/ + xmove <= handler.getWidth())
                xpos += xmove;
            else {
                xpos = handler.getWidth() - width + (collision.getXpos() - xpos);
                xmove = 0;
            }
        }
        // If moving left
        if(xmove < 0) {
            // If you would NOT move out of the screen
            if(collision.getXpos() + xmove >= 0)
                xpos += xmove;
            else {
                // else player's X position = zero - the difference between the collision's X position and the player's
                xpos = 0 - (collision.getXpos() - xpos);
                xmove = 0;
            }
        }
    }

    @Override
    protected void moveY() {
        // If moving down
        if(ymove > 0) {
            // If you would NOT move out of the screen
            if(collision.getYpos() + 28/*CollisionBox Width*/ + ymove <= handler.getHeight())
                ypos += ymove;
            else {
                ypos = handler.getHeight() - height + (collision.getYpos() - ypos);
                ymove = 0;
            }
        }
        // If moving up
        if(ymove < 0) {
            // If you would NOT move out of the screen
            if(collision.getYpos() + ymove >= 0)
                ypos += ymove;
            else {
                // else player's Y position = zero - the difference between the collision's Y position and the player's
                ypos = 0 - (collision.getYpos()-ypos);
                ymove = 0;
            }
        }
    }

    @Override
    public void update() {
        getInput();
        move();
        collision.update(this);
    }

    private void getInput() {
        // Deceleration mechanics
        if(xmove > 0) xmove = Math.max(0, xmove - xmove*((float)0.01 + decelerate));
        if(xmove < 0) xmove = Math.min(0, xmove - xmove*((float)0.01 + decelerate));
        if(ymove > 0) ymove = Math.max(0, ymove - ymove*((float)0.01 + decelerate));
        if(ymove < 0) ymove = Math.min(0, ymove - ymove*((float)0.01 + decelerate));

        speedMultiplier = 1;

        if(handler.getKeyManager().ctrl || handler.getKeyManager().shift) {
            speedMultiplier = 2;
        }
        if(handler.getKeyManager().forward) {
            ymove = (float)(moveSpeed * -Math.sin(direction)* speedMultiplier);
            xmove = (float)(moveSpeed * Math.cos(direction)* speedMultiplier);
        }
        if(handler.getKeyManager().back && reverseThrust) {
            ymove = (float)(moveSpeed * Math.sin(direction)* speedMultiplier);
            xmove = (float)(moveSpeed * -Math.cos(direction)* speedMultiplier);
        }
        if(handler.getKeyManager().left) {
            direction += rotationSpeed * speedMultiplier;
            rotate();
        }
        if(handler.getKeyManager().right) {
            direction -= rotationSpeed * speedMultiplier;
            rotate();
        }
    }

    @Override
    public void draw(Graphics g) {
        // Old Draw Code
//        g.drawImage( // draw image at position (xpos,ypos)
//                img, // with the 'player' sprite
//                (int)xpos,
//                (int)ypos,
//                width, // of the object's set width
//                height, // and of the object's set height
//                null);
//        g.drawImage(aTransOp.filter(assMan.getSprite("player"), null), (int)xpos, (int)ypos, null);

        // New Draw Code
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(aTransOp.filter(img, null), (int)xpos, (int)ypos, null);
        g2d.dispose();
    }

// GETTERS & SETTERS //
    public void setReverseThrust(boolean reverseThrust) {
        this.reverseThrust = reverseThrust;
    }
}
