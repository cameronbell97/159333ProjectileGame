package Entities.Dynamic.Enemies;

import Assets.AssetManager;
import Entities.Collision.CollisionBox;
import Entities.Dynamic.Particles.AsteroidParticle;
import Entities.Dynamic.ExpDot;
import Entities.Dynamic.Particles.AsteroidParticleWhite;
import Entities.Entity;
import Entities.EntityManager;
import Entities.iOutOfBounds;
import Entities.iVulnerable;
import Game.EnemyDirector;
import Game.Launcher;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

/**
 * Cameron Bell - 04/04/2018
 * Asteroid cEntity Class
 * An asteroid object
 */

public class Asteroid extends Enemy implements iVulnerable, iOutOfBounds {
// VARIABLES //
    private static final int OFFSCREEN_BOUNDARY = 96;
    public static final int DEFAULT_SIZE = 64;

    protected int level;
    private int hp;
    private double spriteDirection;
    private double spriteRotation;
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
            collision = new CollisionBox(xpos+11, ypos+11, 42, 42, 11, 11, this);
            if(!white) img = AssetManager.get().getSprite("AstLarge");
            else img = AssetManager.get().getSprite("AstLargeWhite");
        }
        else if(level == 2) {
            hp = 3;
            collision = new CollisionBox(xpos+18, ypos+18, 28, 28, 18, 18, this);
            if(!white) img = AssetManager.get().getSprite("AstMedium");
            else img = AssetManager.get().getSprite("AstMediumWhite");
        }
        else {
            hp = 1;
            collision = new CollisionBox(xpos+26, ypos+26, 12, 12, 26, 26, this);
            if(!white) img = AssetManager.get().getSprite("AstSmall");
            else img = AssetManager.get().getSprite("AstSmallWhite");
        }

        // Set Move Speeds
        setMoveSpeeds();

        // Set Rotation
        spriteRotation = Game.Game.getDoubleFromRange(-0.01*Math.PI, 0.01*Math.PI);

        // Rotate the sprite
        rotateSprite();
    }

    // METHODS //
    @Override
    public void update() {
        move();
        collision.update();
        rotateSprite();

        // If asteroid goes out of bounds
        if(checkOOB()) {
            doWhenOutOfBounds();
        }
    }

    @Override
    public void collide(Entity ec) {
        if(ec instanceof Entities.Dynamic.BulletPlayer) {
            addHP(-2);
        }
        if(ec instanceof Entities.Dynamic.PlayerEntity) {
            setHP(0);
        }

    }

    @Override
    public void rotateSprite() {
        // TODO // Rotate Sprite Without Cutoffs
        spriteDirection += spriteRotation;
        aTrans = AffineTransform.getRotateInstance(-spriteDirection+(Math.PI/2), width/2, height/2);
        aTransOp = new AffineTransformOp(aTrans, AffineTransformOp.TYPE_BILINEAR);

    }

    @Override
    public void die() {
        // Create 2 child asteroids if big enough to do so
        if(level > 1) {
            for(int i = -1; i < 2; i+=2) {
                float newX = Game.Game.getFloatFromRange(xpos-width/8, xpos+width/8);
                float newY = Game.Game.getFloatFromRange(ypos-height/8, ypos+height/8);
                double newDir = Game.Game.getDoubleFromRange(direction + (i*(Math.PI/4)), direction + (i*(Math.PI/8)));

                Asteroid ast = new Asteroid(newX, newY, level-1, newDir, moveSpeed*1.2, white);
                EntityManager.get().subscribe(ast);
                EnemyDirector.get().subscribe(ast);
            }
        }
        // 1 in 5 chance to generate a third asteroid child upon death of lvl 3 asteroid
        if(level == 3 && Game.Game.getIntFromRange(0, 4) == 4) {
            float newX = Game.Game.getFloatFromRange(xpos-width/8, xpos+width/8);
            float newY = Game.Game.getFloatFromRange(ypos-height/8, ypos+height/8);

            Asteroid ast = new Asteroid(newX, newY, level-1, direction, moveSpeed*1.2, white);
            EntityManager.get().subscribe(ast);
            EnemyDirector.get().subscribe(ast);
        }
        EntityManager.get().subscribe(new ExpDot(this, level+1));
        explode();
        EntityManager.get().unsubscribe(this);
        EntityManager.get().unsubscribe(collision);
        EnemyDirector.get().unsubscribe(this);
    }

    // Hard die, just kills the object

    public void kill() {
        EntityManager.get().unsubscribe(this);
        EntityManager.get().unsubscribe(collision);
        EnemyDirector.get().unsubscribe(this);
    }
    // Method to explode rock particles

    protected void explode() {
        int particNum = 3 + (2 * level);

        for(int i = 0; i < particNum; i++) {
            if(!white) EntityManager.get().subscribe(new AsteroidParticle(this, ((i * 2 * Math.PI) /(particNum))));
            else EntityManager.get().subscribe(new AsteroidParticleWhite(this, ((i * 2 * Math.PI) /(particNum))));
        }
    }
    @Override
    public boolean checkOOB() {
        if(     xpos <= -OFFSCREEN_BOUNDARY ||
                ypos <= -OFFSCREEN_BOUNDARY ||
                xpos >= Launcher.DEF_GAME_WIDTH + OFFSCREEN_BOUNDARY ||
                ypos >= Launcher.DEF_GAME_HEIGHT + OFFSCREEN_BOUNDARY) {
            return true;
        }

        return false;
    }

    @Override
    public void doWhenOutOfBounds() {

        // 1 in 3 chance to bounce back
        if(white) {
            xpos = getOverlapX();
            ypos = getOverlapY();
            move();
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
