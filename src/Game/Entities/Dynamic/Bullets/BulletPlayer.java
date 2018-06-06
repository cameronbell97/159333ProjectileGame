package Game.Entities.Dynamic.Bullets;

import Game.Display.Assets.AssetManager;
import Game.Entities.Collision.CollisionBox;
import Game.Entities.Dynamic.DynamicEntity;
import Game.Entities.Dynamic.Enemies.GoblinFighter;
import Game.Entities.Dynamic.Particles.PlayerBulletParticle;
import Game.Entities.Entity;
import Game.Entities.EntityManager;

/**
 * Cameron Bell - 02/04/2018
 * Player Bullet Entity
 * The Player's Bullet - Damages Enemies
 */

public class BulletPlayer extends Bullet {
// VARIABLES //
    protected static final int IMG_X_OFFSET = 3;

// CONSTRUCTORS //
    public BulletPlayer(DynamicEntity parent) {
        super(10, 10, parent);
        img = AssetManager.get().getSprite("BulletPlayer");
        setCollisionBox();

        // Move bullet to nose of Player ship
        moveSpeed = 20;
        setMoveSpeeds();
        move(1);

        // Reset move speed
        moveSpeed = 12;
        setMoveSpeeds();

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
        }
    }

    // Method Override - Used for initial spacial setup for the Collision Box //
    @Override
    public void setCollisionBox() {
        collision = new CollisionBox(xpos+IMG_X_OFFSET, ypos, 4, 10, IMG_X_OFFSET, 0, this);
    }
}
