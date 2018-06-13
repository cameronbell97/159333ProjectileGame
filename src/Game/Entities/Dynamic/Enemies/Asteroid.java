package Game.Entities.Dynamic.Enemies;

import Game.Display.Assets.AssetManager;
import Game.Entities.Collision.CollisionBox;
import Game.Entities.Dynamic.Bullets.PlayerBullet;
import Game.Entities.Dynamic.Particles.AsteroidParticle;
import Game.Entities.Dynamic.ScoreDot;
import Game.Entities.Dynamic.Particles.AsteroidParticleWhite;
import Game.Entities.Entity;
import Game.Entities.EntityManager;
import Game.Entities.iOutOfBounds;
import Game.Entities.iVulnerable;
import Game.Entities.EnemyDirector;
import Game.Data.Settings;
import Game.Handler;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

/**
 * Cameron Bell - 04/04/2018
 * Asteroid Entity Class
 * Asteroid Enemy
 */

public class Asteroid extends Enemy implements iVulnerable, iOutOfBounds {
// VARIABLES //
    private static final int OFFSCREEN_BOUNDARY = 64;
    public static final int DEFAULT_SIZE = 64;

    protected int level;
    private int hp;
    private double spriteDirection, spriteRotation;
    private boolean white;

// CONSTRUCTORS //
    public Asteroid(float x, float y, int level, double direction, double speed, boolean white) {
        super(x, y, DEFAULT_SIZE, DEFAULT_SIZE, direction);
        this.level = level;
        this.spriteDirection = direction;
        moveSpeed = speed;
        this.white = white;

        // Set depending on level
        if(level >=3) {
            hp = 5;
            if(!white) img = AssetManager.get().getSprite("AstLarge");
            else img = AssetManager.get().getSprite("AstLargeWhite");
        }
        else if(level == 2) {
            hp = 3;
            if(!white) img = AssetManager.get().getSprite("AstMedium");
            else img = AssetManager.get().getSprite("AstMediumWhite");
        }
        else {
            hp = 1;
            if(!white) img = AssetManager.get().getSprite("AstSmall");
            else img = AssetManager.get().getSprite("AstSmallWhite");
        }

        // Set Move Speeds
        setMoveSpeeds();

        // Set Rotation
        spriteRotation = Handler.getDoubleFromRange(-0.01*Math.PI, 0.01*Math.PI);

        // Rotate the sprite
        rotateSprite();
    }

// METHODS //
    // Method Override - Update Entity State //
    @Override
    public void update(int dt) {
        move(dt);
        if(collision != null) collision.update(dt);
        rotateSprite(dt);

        // If asteroid goes out of bounds
        if(checkOOBX()) {
            doWhenOutOfBounds(dt, true);
        }
        if(checkOOBY()) {
            doWhenOutOfBounds(dt, false);
        }
    }

    // Method Override - To Handle Collisions //
    @Override
    public void collide(Entity ec) {
        if(ec instanceof PlayerBullet) {
            addHP(-((PlayerBullet) ec).getDamageValue());
        }
        if(ec instanceof Game.Entities.Dynamic.PlayerEntity) {
            setHP(0);
        }

    }

    // Method Override - Used for initial spacial setup for the Collision Box //
    @Override
    public void setCollisionBox() {
        if(level >=3) {
            collision = new CollisionBox(xpos+11, ypos+11, 42, 42, 11, 11, this);
        }
        else if(level == 2) {
            collision = new CollisionBox(xpos+18, ypos+18, 28, 28, 18, 18, this);
        }
        else {
            collision = new CollisionBox(xpos+26, ypos+26, 12, 12, 26, 26, this);
        }
    }

    // Method - Rotates the Sprite //
    public void rotateSprite(int dt) {
        spriteDirection += dt * spriteRotation;
        aTrans = AffineTransform.getRotateInstance(-spriteDirection+(Math.PI/2), width/2, height/2);
        aTransOp = new AffineTransformOp(aTrans, AffineTransformOp.TYPE_BILINEAR);
    }

    // Method - Destroy Asteroid - Creates Additional Smaller Asteroids if Possible //
    @Override
    public void die() {
        // Get Managers
        EntityManager em = handler.getEntityManager();
        EnemyDirector ed = handler.getEnemyDirector();

        // Create 2 child asteroids if big enough to do so
        if(level > 1) {
            for(int i = -1; i < 2; i+=2) {
                float newX = Handler.getFloatFromRange(xpos-width/8, xpos+width/8);
                float newY = Handler.getFloatFromRange(ypos-height/8, ypos+height/8);
                double newDir = Handler.getDoubleFromRange(direction + (i*(Math.PI/4)), direction + (i*(Math.PI/8)));

                Asteroid ast = new Asteroid(newX, newY, level-1, newDir, moveSpeed*1.2, white);
                ast.setCollisionBox();
                em.subscribe(ast);
                ed.subscribe(ast);
            }
        }
        // 1 in 5 chance to generate a third asteroid child upon death of lvl 3 asteroid
        if(level == 3 && Handler.getIntFromRange(0, 4) == 4) {
            float newX = Handler.getFloatFromRange(xpos-width/8, xpos+width/8);
            float newY = Handler.getFloatFromRange(ypos-height/8, ypos+height/8);

            Asteroid ast = new Asteroid(newX, newY, level-1, direction, moveSpeed*1.2, white);
            ast.setCollisionBox();
            em.subscribe(ast);
            ed.subscribe(ast);
        }

        /* Drop Score Dot Code
         * f(x) = -x+4, where f = value and x = level
         * lvl 1 = 3
         * lvl 2 = 2
         * lvl 3 = 1
         */
        if(level <= 3 && level >= 1) em.subscribe(new ScoreDot(this, (-level)+4));

        explode();
        kill();
    }

    // Method - Hard die, just kills the object //
    public void kill() {
        EntityManager em = handler.getEntityManager();
        em.unsubscribe(this);
        em.unsubscribe(collision);
        handler.getEnemyDirector().unsubscribe(this);
    }

    // Method - Explode out rock particles on death //
    protected void explode() {
        EntityManager em = handler.getEntityManager();

        int particNum = 3 + (2 * level);

        for(int i = 0; i < particNum; i++) {
            if(!white) em.subscribe(new AsteroidParticle(this, ((i * 2 * Math.PI) /(particNum)), level));
            else em.subscribe(new AsteroidParticleWhite(this, ((i * 2 * Math.PI) /(particNum)), level));
        }
    }

    // Method Override - Check if the Asteroid is Out Of Bounds //
    @Override
    public boolean checkOOBX() {
        if(xpos <= -OFFSCREEN_BOUNDARY || xpos >= Settings.game_width + OFFSCREEN_BOUNDARY){
            return true;
        }

        return false;
    }

    @Override
    public boolean checkOOBY() {
        if(ypos <= -OFFSCREEN_BOUNDARY || ypos >= Settings.game_height + OFFSCREEN_BOUNDARY){
            return true;
        }

        return false;
    }

    // Method Override - Do when the Asteroid is Out Of Bounds //
    @Override
    public void doWhenOutOfBounds(int dt, boolean shiftX) {
        if(white) {
            if(shiftX) xpos = getOverlapX();
            else ypos = getOverlapY();
            move(dt);
        } else kill();
    }

// GETTERS & SETTERS //
    @Override
    public int getHP() {
        return hp;
    }
    @Override
    public void setHP(int hp) {
        this.hp = hp;
        if(this.hp <= 0) die();
    }
    @Override
    public void addHP(int hp) {
        this.hp += hp;
        if(this.hp <= 0) {
            die();
        }
    }
}
