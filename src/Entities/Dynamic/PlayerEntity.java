package Entities.Dynamic;
import Entities.Collision.CollisionBox;
import Entities.Entity;
import Entities.EntityManager;
import Entities.iVulnerable;
import Game.Handler;

import Assets.AssetManager;

/**
 * Cameron Bell - 27/03/2018
 * Player Entity Class
 * The controllable player
 */

public class PlayerEntity extends DynamicEntity implements iVulnerable {
// VARIABLES //
    public static final int DEF_PLAYER_WIDTH = 64;
    public static final int DEF_PLAYER_HEIGHT = 64;
    public static final double DEF_ROT_SPEED = 0.015*Math.PI;
    AssetManager assMan = AssetManager.get();
    private double speedMultiplier;
    private double rotationSpeed;
    private boolean reverseThrust; // If true, player can reverse
    private float decelerate;
    private int health;
    private boolean shoot_release;
    private int slowTimeStart;
    private int slowTimeCurrent;


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
        reverseThrust = true;
        decelerate = (float)0.06;
        collision = new CollisionBox(handler, xpos+22, ypos+17, 20, 35, 22, 17, this);
        health = 10;
        shoot_release = true;
        slowTimeStart = 0;
        slowTimeCurrent = 0;
        EntityManager.get().subPlayer(this);
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

    @Override
    public void collide(Entity ec) {
        if(ec instanceof Entities.Dynamic.Enemies.Asteroid) {
            addHP(-1);
            slow(50);
        }
    }

    private void getInput() {
        // Deceleration mechanics
        if(xmove > 0) xmove = Math.max(0, xmove - xmove*((float)0.01 + decelerate));
        if(xmove < 0) xmove = Math.min(0, xmove - xmove*((float)0.01 + decelerate));
        if(ymove > 0) ymove = Math.max(0, ymove - ymove*((float)0.01 + decelerate));
        if(ymove < 0) ymove = Math.min(0, ymove - ymove*((float)0.01 + decelerate));

        // Slow (if you hit an enemy) mechanics
        if(slowTimeStart <= 0) speedMultiplier = 1;
        else speedMultiplier = (double)slowTimeCurrent/slowTimeStart;

        if(handler.getKeyManager().shift && slowTimeStart <= 0) {
            if(slowTimeCurrent <= 0) speedMultiplier = 2;
            else speedMultiplier = (double)1 + ((double)((double)50 - slowTimeCurrent) / 50);
        }
        if(handler.getKeyManager().ctrl ) {
            speedMultiplier = (float)0.1;
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
            rotateSprite();
            collision.rotateSprite(direction);
        }
        if(handler.getKeyManager().right) {
            direction -= rotationSpeed * speedMultiplier;
            rotateSprite();
            collision.rotateSprite(direction);
        }
        if(handler.getKeyManager().spacebar && shoot_release) {
            EntityManager.get().subscribe(new BulletPlayer(handler,this));
            shoot_release = false;
        }
        if(!handler.getKeyManager().spacebar) shoot_release = true;
        if(slowTimeStart > 0) slowTimeCurrent++;
        if(slowTimeStart == slowTimeCurrent) slowTimeStart = 0;
        if(slowTimeStart == 0 && slowTimeCurrent > 0) slowTimeCurrent--;
    }

    @Override
    public void addHP(int hp) {
        health += hp;
        if(health <= 0) die();
    }

    // Method to destroy / delete entity
    @Override
    public void die() {
        EntityManager.get().unsubPlayer(this);
        EntityManager.get().unsubscribe(this.collision);
    }

    // Method to setup the slow mechanics
    private void slow(int ticks) {
        slowTimeStart = ticks;
        slowTimeCurrent = 0;
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
