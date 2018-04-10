package Entities.Dynamic.Enemies;

import Assets.AssetManager;
import Entities.Collision.CollisionBox;
import Entities.Dynamic.DynamicEntity;
import Entities.Dynamic.Particles.AsteroidParticle;
import Entities.Dynamic.Particles.ExpDot;
import Entities.Entity;
import Entities.EntityManager;
import Entities.iVulnerable;
import Game.Handler;
import Game.Launcher;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

/**
 * Cameron Bell - 04/04/2018
 * Asteroid cEntity Class
 * An asteroid object
 */

public class Asteroid extends DynamicEntity implements iVulnerable {
// VARIABLES //
    private static final int OFFSCREEN_BOUNDARY = 96;

    private int level;
    private int hp;
    private double spriteDirection;
    private double spriteRotation;

// CONSTRUCTORS //
    public Asteroid(Handler handler, float x, float y, int level, double direction, double speed) {
        super(handler, x, y, 64, 64, direction);
        this.level = level;
        this.spriteDirection = direction;
        moveSpeed = speed;

        // Set depending on level
        if(level >=3) {
            hp = 5;
            collision = new CollisionBox(handler, xpos+11, ypos+11, 42, 42, 11, 11, this);
            img = AssetManager.get().getSprite("AstLarge");
        }
        else if(level == 2) {
            hp = 3;
            collision = new CollisionBox(handler, xpos+18, ypos+18, 28, 28, 18, 18, this);
            img = AssetManager.get().getSprite("AstMedium");
        }
        else {
            hp = 1;
            collision = new CollisionBox(handler, xpos+26, ypos+26, 12, 12, 26, 26, this);
            img = AssetManager.get().getSprite("AstSmall");
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

        if(     xpos <= -OFFSCREEN_BOUNDARY ||
                ypos <= -OFFSCREEN_BOUNDARY ||
                xpos >= Launcher.DEF_GAME_WIDTH + OFFSCREEN_BOUNDARY ||
                ypos >= Launcher.DEF_GAME_HEIGHT + OFFSCREEN_BOUNDARY) {
            die();
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
            // TODO // Drop Points
            die();
        }
    }
    @Override
    public void die() {
        if(level > 1) {
            for(int i = -1; i < 2; i+=2) {
                float newX = Game.Game.getFloatFromRange(xpos-width/8, xpos+width/8);
                float newY = Game.Game.getFloatFromRange(ypos-height/8, ypos+height/8);
                double newDir = Game.Game.getDoubleFromRange(direction + (i*(Math.PI/4)), direction + (i*(Math.PI/8)));

                EntityManager.get().subscribe(new Asteroid(handler, newX, newY, level-1, newDir, moveSpeed*1.2));
            }
            EntityManager.get().subscribe(new ExpDot(handler, this));
        }
        explode();
        EntityManager.get().unsubscribe(this);
        EntityManager.get().unsubscribe(collision);
    }

    // Method to explode rock particles
    private void explode() {
        int particNum = 3 + (2 * level);

        for(int i = 0; i < particNum; i++) {
            EntityManager.get().subscribe(new AsteroidParticle(handler, this, ((i * 2 * Math.PI) /(particNum))));
        }
    }
}
