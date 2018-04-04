package Entities.Dynamic.Enemies;

import Assets.AssetManager;
import Entities.Collision.CollisionBox;
import Entities.Dynamic.DynamicEntity;
import Entities.Entity;
import Entities.EntityManager;
import Entities.iVulnerable;
import Game.Handler;
import Game.Launcher;

import java.util.Random;

/**
 * Cameron Bell - 04/04/2018
 * Asteroid cEntity Class
 * An asteroid object
 */

public class Asteroid extends DynamicEntity implements iVulnerable {
// VARIABLES //
    private static final int OFFSCREEN_BOUNDARY = 96;

    int level;
    int hp;

// CONSTRUCTORS //
    public Asteroid(Handler handler, float x, float y, int level, double direction, double speed) {
        super(handler, x, y, 64, 64);
        this.level = level;
        this.direction = direction;
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

        // Set Move Speed
        ymove = (float)(moveSpeed * -Math.sin(direction));
        xmove = (float)(moveSpeed * Math.cos(direction));

        // Rotate the sprite
//        rotate();
    }

// METHODS //
    @Override
    public void update() {
        move();
        collision.update();
        collision.rotate(direction);

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
        if(this.hp <= 0) die();
    }
    @Override
    public void die() {
        if(level > 1) {
            for(int i = 0; i < 2; i++) {
                float newX = Game.Game.getFloatFromRange(xpos-width/4, xpos+width/4);
                float newY = Game.Game.getFloatFromRange(ypos-height/4, ypos+height/4);
                double newDir = Game.Game.getDoubleFromRange(0, 2 * Math.PI);

                EntityManager.get().subscribe(new Asteroid(handler, newX, newY, level-1, newDir, moveSpeed));
            }
        }
        EntityManager.get().unsubscribe(this);
        EntityManager.get().unsubscribe(collision);
    }
}
