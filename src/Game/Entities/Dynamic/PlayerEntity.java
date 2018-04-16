package Game.Entities.Dynamic;
import Game.Entities.Collision.CollisionBox;
import Game.Entities.Dynamic.Bullets.BulletPlayer;
import Game.Entities.Dynamic.Bullets.GoblinBulletSmall;
import Game.Entities.Entity;
import Game.Entities.EntityManager;
import Game.Entities.iVulnerable;

import Game.Display.Assets.AssetManager;
import Game.Data.GameDataManager;
import Game.Data.KeyManager;
import Game.Data.Settings;
import Game.Timer.*;

/**
 * Cameron Bell - 27/03/2018
 * Player Entity Class
 * The controllable player
 */

public class PlayerEntity extends DynamicEntity implements iVulnerable, iCanHaveCodeTimer {
// VARIABLES //
    // Statics
    public static final int DEF_PLAYER_WIDTH = 64;
    public static final int DEF_PLAYER_HEIGHT = 64;
    public static final int DEF_RELOAD_SPEED = 1; // 60 = 1 second
    public static final double DEF_ROT_SPEED = 0.015*Math.PI;
    public static final int DEF_HEALTH = 20;
    private static final int THRUST_FRAME_TIME_1 = 5;
    private static final int THRUST_FRAME_TIME_2 = 25;
    private static final int THRUST_FRAME_TIME_3 = 40;
    private static final boolean PLAYER_STRAFE_ENABLED = false;
    private static final boolean PLAYER_DECELERATION_ENABLED = true;
    private static final boolean PLAYER_ACCELERATION_ENABLED = false;
    private static final boolean PLAYER_SPEED_LIMIT_ENABLED = false;

    AssetManager assMan = AssetManager.get();
    KeyManager km = KeyManager.get();
    GameDataManager gdm = GameDataManager.get();
    private double speedMultiplier;
    private double rotationSpeed;
    private boolean reverseThrust; // If true, player can reverse
    private float decelerate;
    private int health;
    private boolean shoot_release;
    private boolean shoot_reloaded;
    private int slowTimeStart;
    private int slowTimeCurrent;
    private int timeMoving;
    private int acceleration;


// CONSTRUCTORS //
    public PlayerEntity(float x, float y) {
        super(x, y, DEF_PLAYER_WIDTH, DEF_PLAYER_HEIGHT, (Math.PI / 2));
        initialise();
    }

// METHODS //
    public void initialise() {
        speedMultiplier = 1;
        setSpeed(3.5);
        rotationSpeed = DEF_ROT_SPEED;
        img = assMan.getSprite("player");
        reverseThrust = true;
        decelerate = (float)0.06;
        collision = new CollisionBox(xpos+22, ypos+17, 20, 35, 22, 17, this);
        health = DEF_HEALTH;
        shoot_release = true;
        shoot_reloaded = true;
        slowTimeStart = 0;
        slowTimeCurrent = 0;
        EntityManager.get().subPlayer(this);
        timeMoving = 0;
        xmove = 0;
        ymove = 0;
        acceleration = 0;
        acceleration = 60;
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
            if(collision.getXpos() + collision.getWidth() + xmove <= Settings.game_width)
                xpos += xmove;
            else {
                xpos = Settings.game_width - collision.getWidth() - collision.getXoff();
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
            if(collision.getYpos() + collision.getHeight()/*CollisionBox Width*/ + ymove <= Settings.game_height)
                ypos += ymove;
            else {
//                ypos = handler.getHeight() - height + (collision.getYpos() - ypos);
                ypos = Settings.game_height - collision.getHeight() - collision.getYoff();
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
        if(ec instanceof Game.Entities.Dynamic.Enemies.Asteroid) {
            addHP(-2);
            slow(50);
        }
        else if(ec instanceof Game.Entities.Dynamic.ExpDot) {
            gdm.addScore(((ExpDot) ec).getValue());
        }
        else if(ec instanceof GoblinBulletSmall) {
            addHP(-1);
        }
    }

    private void getInput() {
        // Deceleration mechanics
        if (PLAYER_DECELERATION_ENABLED) {
            decelerate(decelerate);
        }

        // Slow (if you hit an enemy) mechanics
        if(slowTimeStart <= 0) speedMultiplier = 1;
        else speedMultiplier = (double)slowTimeCurrent/slowTimeStart;

        if(km.shift && slowTimeStart <= 0) {
            if(slowTimeCurrent <= 0) speedMultiplier = 1.8;
            else speedMultiplier = (double)1 + ((double)((double)50 - slowTimeCurrent) / 50);

            // Thrust Animation Code
            if(km.forward) {
                setImg(assMan.getAnimPThrust(3));
                timeMoving = THRUST_FRAME_TIME_3;
            }
        }
        if(km.ctrl ) {
            speedMultiplier = (float)0.25;
        }
        if(km.forward) {
            if(!PLAYER_ACCELERATION_ENABLED) {
                ymove = (float) (moveSpeed * -Math.sin(direction) * speedMultiplier);
                xmove = (float) (moveSpeed * Math.cos(direction) * speedMultiplier);
            } else {
                float newMoveSpeed = ((float)1 / (float) acceleration) * (float)moveSpeed;

                ymove += (float) (newMoveSpeed * -Math.sin(direction) * speedMultiplier);
                xmove += (float) (newMoveSpeed * Math.cos(direction) * speedMultiplier);

//                acceleration++;
            }

        // Thrust Animation Code
            if (timeMoving < THRUST_FRAME_TIME_2) {
                timeMoving++;
                if(timeMoving == THRUST_FRAME_TIME_1) setImg(assMan.getAnimPThrust(1));
                else if(timeMoving == THRUST_FRAME_TIME_2) setImg(assMan.getAnimPThrust(2));
            }
        }
        else {
            if (timeMoving != 0) {
                timeMoving--;
                if(timeMoving == THRUST_FRAME_TIME_1) setImg(assMan.getAnimPThrust(0));
                else if(timeMoving == THRUST_FRAME_TIME_2 - 1) setImg(assMan.getAnimPThrust(1));
                else if(timeMoving == THRUST_FRAME_TIME_3 - 1) setImg(assMan.getAnimPThrust(2));
            }
        }

        if(km.back && reverseThrust) {
            ymove = (float)(moveSpeed * Math.sin(direction)* speedMultiplier);
            xmove = (float)(moveSpeed * -Math.cos(direction)* speedMultiplier);
        }
        if(km.left) {
            if (km.ctrl && PLAYER_STRAFE_ENABLED) {
                strafeLeft(moveSpeed / 2);
            } else {
                direction += rotationSpeed * speedMultiplier;
                rotateSprite();
                collision.rotateSprite(direction);
            }
        }
        if(km.right) {
            if (km.ctrl && PLAYER_STRAFE_ENABLED) {
                strafeRight(moveSpeed / 2);
            } else {
                direction -= rotationSpeed * speedMultiplier;
                rotateSprite();
                collision.rotateSprite(direction);
            }
        }
        if(km.spacebar && shoot_release && shoot_reloaded) {
            EntityManager.get().subscribe(new BulletPlayer(this));
            shoot_release = false;
            shoot_reloaded = false;
            TimerManager.get().newCodeTimer(DEF_RELOAD_SPEED, this, "REL");
        }
        if(!km.spacebar) shoot_release = true;
        // Slow/Speedup Mechanics for collision with asteroid
        if(slowTimeStart > 0) slowTimeCurrent++;
        if(slowTimeStart == slowTimeCurrent) slowTimeStart = 0;
        if(slowTimeStart == 0 && slowTimeCurrent > 0) slowTimeCurrent--;

        if(slowTimeCurrent == 1 && PLAYER_ACCELERATION_ENABLED) {
            ymove = ymove / 3;
            xmove = xmove / 3;
        }
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

    @Override
    public void timerNotify(CodeTimer t) {
        String timerCode = t.getCode(); // Get timer code

        switch (timerCode) {
            case "REL":
                shoot_reloaded = true;
                break;

        }
        TimerManager.get().unsubTimer(t); // Unsubscribe the timer
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
