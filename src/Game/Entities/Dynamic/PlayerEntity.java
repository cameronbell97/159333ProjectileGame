package Game.Entities.Dynamic;
import Game.Entities.Collision.CollisionBox;
import Game.Entities.Dynamic.Bullets.GoblinBulletLarge;
import Game.Entities.Dynamic.Bullets.GoblinBulletSmall;
import Game.Entities.Dynamic.Enemies.GoblinFighter;
import Game.Entities.Dynamic.Particles.DebrisParticle;
import Game.Entities.Dynamic.Particles.EnergyExplParticle;
import Game.Entities.Entity;
import Game.Entities.EntityManager;
import Game.Data.PlayerModules.WeaponModule;
import Game.Entities.iVulnerable;

import Game.Display.Assets.AssetManager;
import Game.Data.KeyManager;
import Game.Data.Settings;
import Game.Screens.GameScreen;
import Game.Screens.Screen;
import Game.Screens.ScreenManager;

/**
 * Cameron Bell - 27/03/2018
 * Player Entity Class
 * The controllable player
 */

public class PlayerEntity extends DynamicEntity implements iVulnerable {
// VARIABLES //
    // Statics //
    public static final int DEF_PLAYER_WIDTH = 64;
    public static final int DEF_PLAYER_HEIGHT = 64;
    public static final double DEF_ROT_SPEED = 0.015*Math.PI;
    public static final int DEF_HEALTH = 20;
    private static final int THRUST_FRAME_TIME_1 = 5;
    private static final int THRUST_FRAME_TIME_2 = 25;
    private static final int THRUST_FRAME_TIME_3 = 40;

    // Managers //
    private AssetManager assMan = AssetManager.get();
    private KeyManager km;

    // Data //
    private double speedMultiplier, rotationSpeed;
    private boolean reverseThrust; // If true, player can reverse
    private float decelerate;
    private int
            health,
            slowTimeStart,
            slowTimeCurrent,
            timeMoving,
            acceleration;

//    private PlayerCollisionBoxHead headCollision; // EXPERIMENTAL //

    // Modules //
    WeaponModule weaponModule;


// CONSTRUCTORS //
    public PlayerEntity(float x, float y, WeaponModule weaponModule) {
        super(x, y, DEF_PLAYER_WIDTH, DEF_PLAYER_HEIGHT, (Math.PI / 2));

        this.weaponModule = weaponModule;
        weaponModule.setParent(this);

        initialise();
    }

// METHODS //
    // Method - Initialise the Entity //
    public void initialise() {
        km = handler.getKeyManager();
        speedMultiplier = 1;
        setSpeed(3.5);
        rotationSpeed = DEF_ROT_SPEED;
        img = assMan.getSprite("player");
        reverseThrust = true;
        decelerate = (float)0.06;
        health = DEF_HEALTH;
        slowTimeStart = 0;
        slowTimeCurrent = 0;
        timeMoving = 0;
        xmove = 0;
        ymove = 0;
        acceleration = 0;
        acceleration = 60;
    }

    // Method Override - Used for initial spacial setup for the Collision Box //
    @Override
    public void setCollisionBox() {
        collision = new CollisionBox(xpos+22, ypos+17, 20, 35, 22, 17, this);

        // EXPERIMENTAL CODE //
//        collision = new PlayerCollisionBoxBody(xpos+17, ypos+26, 30, 30, 17, 26, this);
//        collision.setAnchor(15, 6);
//        headCollision = new PlayerCollisionBoxHead(xpos+27, ypos+7, 10, 18, 27, 7, this);
//        headCollision.setAnchor(5, 25);
    }

    // Method Override - Move on the X axis unless the entity would move outside the screen //
    @Override
    protected void moveX(int dt) {
        // If moving right
        if(xmove > 0) {
            // If you would NOT move out of the screen
            if(collision.getXpos() + collision.getWidth() + xmove <= Settings.game_width)
                xpos += dt * xmove;
            else {
                xpos = Settings.game_width - collision.getWidth() - collision.getXoff();
                xmove = 0;
            }
        }
        // If moving left
        if(xmove < 0) {
            // If you would NOT move out of the screen
            if(collision.getXpos() + xmove >= 0)
                xpos += dt * xmove;
            else {
                // else player's X position = zero - the difference between the collision's X position and the player's
                xpos = 0 - (collision.getXpos() - xpos);
                xmove = 0;
            }
        }
    }

    // Method Override - Move on the Y axis unless the entity would move outside the screen //
    @Override
    protected void moveY(int dt) {
        // If moving down
        if(ymove > 0) {
            // If you would NOT move out of the screen
            if(collision.getYpos() + collision.getHeight()/*CollisionBox Width*/ + ymove <= Settings.game_height)
                ypos += dt * ymove;
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
                ypos += dt * ymove;
            else {
                // else player's Y position = zero - the difference between the collision's Y position and the player's
                ypos = 0 - (collision.getYpos()-ypos);
                ymove = 0;
            }
        }
    }

    // Method Override - Update Entity State //
    @Override
    public void update(int dt) {
        weaponModule.update(dt);
        getInput(dt);
        move(dt);
        collision.update(dt);
//        headCollision.updateGame(); // EXPERIMENTAL //
    }

    // Method Override - To Handle Collisions //
    @Override
    public void collide(Entity ec) {
        if(ec instanceof Game.Entities.Dynamic.Enemies.Asteroid) {
            addHP(-2);
            slow(50);
        }
        else if(ec instanceof ScoreDot) {
            handler.getGameDataManager().addScore(((ScoreDot) ec).getValue());
        }
        else if(ec instanceof GoblinBulletSmall) {
            addHP(-((GoblinBulletSmall) ec).getDamageValue());
        }
        else if(ec instanceof GoblinBulletLarge) {
            addHP(-((GoblinBulletLarge) ec).getDamageValue());
        }
        else if(ec instanceof GoblinFighter) {
            addHP(-((GoblinFighter) ec).getShipDamageValue());
            slow(80);
        }
    }

    // Method - Change variables depending on key presses //
    private void getInput(int dt) {
        // Deceleration mechanics
        if (Settings.player_deceleration) {
            decelerate(dt, decelerate);
        }

        // Slow (if you hit an enemy) mechanics
        if(slowTimeStart <= 0) speedMultiplier = 1;
        else speedMultiplier = (double)slowTimeCurrent/slowTimeStart;

        // Thrust Mechanics
        if(km.shift && slowTimeStart <= 0) {
            if(slowTimeCurrent <= 0) speedMultiplier = 1.8;
            else speedMultiplier = (double)1 + ((double)((double)50 - slowTimeCurrent) / 50);

            // Thrust Animation Code
            if(km.forward) {
                setImg(assMan.getAnimPThrust(3));
                timeMoving = THRUST_FRAME_TIME_3;
            }
        }
        // Precision Movement Mechanics
        if(km.ctrl ) {
            speedMultiplier = (float)0.25;
        }
        // Move Forward Mechanics
        if(km.forward) {
            if(!Settings.player_acceleration) {
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
                timeMoving+=dt;

                if(timeMoving == THRUST_FRAME_TIME_1) setImg(assMan.getAnimPThrust(1));
                else if(timeMoving == THRUST_FRAME_TIME_2) setImg(assMan.getAnimPThrust(2));
            }
        }
        else {
            // Thrust Animation Code
            if (timeMoving != 0) {
                timeMoving-=dt;

                if(timeMoving == THRUST_FRAME_TIME_1) setImg(assMan.getAnimPThrust(0));
                else if(timeMoving == THRUST_FRAME_TIME_2 - 1) setImg(assMan.getAnimPThrust(1));
                else if(timeMoving == THRUST_FRAME_TIME_3 - 1) setImg(assMan.getAnimPThrust(2));
            }
        }

        // Reverse Mechanics
        if(km.back && reverseThrust) {
            ymove = (float)(moveSpeed * Math.sin(direction)* speedMultiplier);
            xmove = (float)(moveSpeed * -Math.cos(direction)* speedMultiplier);
        }
        // Rotate Left Mechanics
        if(km.left) {
            if (km.ctrl && Settings.player_strafe) {
                strafeLeft(moveSpeed / 2);
            } else {
                direction += dt * rotationSpeed * speedMultiplier;
                rotateSprite();
                collision.rotateSprite(direction);
//                headCollision.rotateSprite(direction);
            }
        }
        // Rotate Right Mechanics
        if(km.right) {
            if (km.ctrl && Settings.player_strafe) {
                strafeRight(moveSpeed / 2);
            } else {
                direction -= dt * rotationSpeed * speedMultiplier;
                rotateSprite();
                collision.rotateSprite(direction);
//                headCollision.rotateSprite(direction);
            }
        }
        // Shooting Mechanics
        if(km.spacebar) {
            weaponModule.tryShoot();
        }

        // Slow/Speedup Mechanics for collision with asteroid
        if(slowTimeStart > 0) slowTimeCurrent+=dt;
        if(slowTimeStart == slowTimeCurrent) slowTimeStart = 0;
        if(slowTimeStart == 0 && slowTimeCurrent > 0) slowTimeCurrent-=dt;

        if(slowTimeCurrent == 1 && Settings.player_acceleration) {
            ymove = ymove / 3;
            xmove = xmove / 3;
        }

        // Debug Keys
        if(km.delete && Settings.DEBUG_CHEATS) addHP(-20);
        if(km.insert && Settings.DEBUG_CHEATS) handler.getGameDataManager().addScore(1);;
    }

    // Method - Add HP (remove using negative integers) //
    @Override
    public void addHP(int hp) {
        health += hp;
        if(health <= 0) die();
    }

    // Method - Destroy / Delete entity //
    @Override
    public void die() {
        EntityManager em = handler.getEntityManager();
        em.unsubPlayer(this);
        em.unsubscribe(this.collision);
//        EntityManager.get().unsubscribe(this.headCollision);
        explode();

        Screen screen = ScreenManager.getScreen();
        if(screen instanceof GameScreen) {
            ((GameScreen) screen).end();
        }
    }

    // Method - Explode Particles Away from Player After Death //
    private void explode() {
        EntityManager em = handler.getEntityManager();

        // Explosion
        em.subscribe(new EnergyExplParticle(
                (int)xpos + width / 2,
                (int)ypos + height / 2,
                21,
                3,
                0
        ));

        // Head of Ship
        em.subscribe(new DebrisParticle(this, assMan.getSprite(11, 0, 7)) {
            @Override
            protected void setPosAndDir() {
                double saveSpeed = moveSpeed;

                moveSpeed = 18;
                setMoveSpeeds();
                move(1);

                moveSpeed = saveSpeed;
            }
        });

        // Tails of Ship
        em.subscribe(new DebrisParticle(this, assMan.getSprite(11, 0, 8)) {
            @Override
            protected void setPosAndDir() {
                double saveSpeed = moveSpeed;

                moveSpeed = -18;
                setMoveSpeeds();
                move(1);

                strafeLeft(9);

                direction += Math.PI - (Math.PI / 8);

                moveSpeed = saveSpeed;
            }
        });
        em.subscribe(new DebrisParticle(this, assMan.getSprite(11, 1, 8)) {
            @Override
            protected void setPosAndDir() {
                double saveSpeed = moveSpeed;

                moveSpeed = -18;
                setMoveSpeeds();
                move(1);

                strafeRight(9);

                direction += Math.PI + (Math.PI / 8);

                moveSpeed = saveSpeed;
            }
        });

        // Wings of Ship
        em.subscribe(new DebrisParticle(this, assMan.getSprite(11, 2, 8)) {
            @Override
            protected void setPosAndDir() {
                double saveSpeed = moveSpeed;

                moveSpeed = 9;
                setMoveSpeeds();
                move(1);

                strafeLeft(7);

                direction += Math.PI/2 - (Math.PI / 8);

                moveSpeed = saveSpeed;
            }
        });
        em.subscribe(new DebrisParticle(this, assMan.getSprite(11, 2, 8)) {
            @Override
            protected void setPosAndDir() {
                double saveSpeed = moveSpeed;

                moveSpeed = -2;
                setMoveSpeeds();
                move(1);

                strafeLeft(13);

                direction += Math.PI/2;

                moveSpeed = saveSpeed;
            }
        });
        em.subscribe(new DebrisParticle(this, assMan.getSprite(11, 3, 8)) {
            @Override
            protected void setPosAndDir() {
                double saveSpeed = moveSpeed;

                moveSpeed = 9;
                setMoveSpeeds();
                move(1);

                strafeRight(7);

                direction -= ((3 * Math.PI)/8);

                moveSpeed = saveSpeed;
            }
        });
        em.subscribe(new DebrisParticle(this, assMan.getSprite(11, 3, 8)) {
            @Override
            protected void setPosAndDir() {
                double saveSpeed = moveSpeed;

                moveSpeed = -2;
                setMoveSpeeds();
                move(1);

                strafeRight(13);

                direction -= Math.PI/2;

                moveSpeed = saveSpeed;
            }
        });
    }

    // Method - Setup the Slow Mechanics //
    private void slow(int ticks) {
        slowTimeStart = slowTimeStart - slowTimeCurrent + ticks;
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
