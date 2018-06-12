package Game.Entities.Dynamic.Bullets;

import Game.Entities.Collision.CollisionBox;
import Game.Entities.Dynamic.DynamicEntity;
import Game.Entities.Dynamic.Enemies.GoblinFighter;
import Game.Entities.Dynamic.Particles.PlayerBulletParticle;
import Game.Entities.Entity;

import java.awt.image.BufferedImage;

/**
 * Cameron Bell - 02/04/2018
 * Player Bullet Entity
 * The Player's Bullet - Damages Enemies
 */

public abstract class PlayerBullet extends Bullet {
// VARIABLES //
    // Statics //
    private static final int DEF_IMG_X_OFFSET = 3;
    private static final int DEF_IMG_Y_OFFSET = 1;
    private static final int DEF_DIMENSIONS = 10; // Width & Height

    // Data //
    private int damageValue;

// CONSTRUCTORS //
    public PlayerBullet(DynamicEntity parent) {
        super(DEF_DIMENSIONS, DEF_DIMENSIONS, parent);
        img = setSprite();
        setCollisionBox();

        setPosition();

        damageValue = setDamageValue();

        // Rotate the sprite
        rotateSprite();
    }

// METHODS //
    // Method Override - To Handle Collisions //
    @Override
    public void collide(Entity ec) {
        if(ec instanceof Game.Entities.Dynamic.Enemies.Asteroid) {
            destroy();
            handler.getEntityManager().subscribe(new PlayerBulletParticle(this));
        } else if(ec instanceof GoblinFighter) {
            destroy();
            handler.getEntityManager().subscribe(new PlayerBulletParticle(this));
        }
    }

    // Method Override - Used for initial spacial setup for the Collision Box //
    @Override
    public void setCollisionBox() {
        collision = new CollisionBox(xpos+ DEF_IMG_X_OFFSET, ypos+DEF_IMG_Y_OFFSET, 4, 10, DEF_IMG_X_OFFSET, DEF_IMG_Y_OFFSET, this);
    }

    protected abstract void setPosition();

    protected abstract BufferedImage setSprite();
    protected abstract int setDamageValue();

// GETTERS & SETTERS //
    public int getDamageValue() {
        return damageValue;
    }
}
