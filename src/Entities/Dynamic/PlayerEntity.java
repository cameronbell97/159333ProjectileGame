package Entities.Dynamic;
import Entities.Collision.CollisionBox;
import Entities.EntityManager;
import Entities.iVulnerable;
import Game.Handler;

import Assets.AssetManager;

/**
 * Cameron Bell - 27/03/2018
 * Player DynamicEntity Class
 * The controllable player
 */

public class PlayerEntity extends DynamicEntity implements iVulnerable {
// VARIABLES //
    public static final int DEF_PLAYER_WIDTH = 64;
    public static final int DEF_PLAYER_HEIGHT = 64;
    public static final double DEF_ROT_SPEED = 0.015*Math.PI;
    AssetManager assMan = AssetManager.get();
    private int speedMultiplier;
    private double rotationSpeed;
    private boolean reverseThrust; // If true, player can reverse
    private float decelerate;
    private int health;
    private boolean shoot_release;


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
        reverseThrust = true;
        decelerate = (float)0.06;
        collision = new CollisionBox(handler, xpos+22, ypos+17, 20, 35, 22, 17, this);
        health = 10;
        shoot_release = true;
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
            if(collision.getXpos() + collision.getWidth()/*CollisionBox Width*/ + xmove <= handler.getWidth())
                xpos += xmove;
            else {
                xpos = handler.getWidth() - collision.getWidth() - collision.getXoff();
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
            if(collision.getYpos() + collision.getHeight()/*CollisionBox Width*/ + ymove <= handler.getHeight())
                ypos += ymove;
            else {
//                ypos = handler.getHeight() - height + (collision.getYpos() - ypos);
                ypos = handler.getHeight() - collision.getHeight() - collision.getYoff();
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
        collision.update();
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
            collision.rotate(direction);
        }
        if(handler.getKeyManager().right) {
            direction -= rotationSpeed * speedMultiplier;
            rotate();
            collision.rotate(direction);
        }
        if(handler.getKeyManager().spacebar && shoot_release) {
            EntityManager.get().subscribe(new BulletPlayer(handler,this));
            shoot_release = false;
        }
        if(!handler.getKeyManager().spacebar) shoot_release = true;
    }

    @Override
    public void addHP(int hp) {
        health += hp;
    }

    @Override
    public void die() {

    }

// GETTERS & SETTERS //
    public void setReverseThrust(boolean reverseThrust) {
        this.reverseThrust = reverseThrust;
    }

    @Override
    public int getHP() {
        return health;
    }

    @Override
    public void setHP(int hp) {
        health = hp;
    }
}
