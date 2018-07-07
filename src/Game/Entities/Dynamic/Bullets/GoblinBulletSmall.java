package Game.Entities.Dynamic.Bullets;

import Game.Display.Assets.AssetManager;
import Game.Entities.Collision.CollisionBox;
import Game.Entities.Dynamic.DynamicEntity;
import Game.Entities.Dynamic.Enemies.GoblinFighter;
import Game.Entities.Entity;

/**
 * Cameron Bell - 16/04/2018
 * Small Goblin Fighter Bullet Entity
 * Bullet from Small Goblin Fighter - Damages the Player
 */

public class GoblinBulletSmall extends Bullet {
// VARIABLES //
    protected static final int IMG_X_OFFSET = 4;
    protected static final int DEF_DAMAGE_VALUE = 1;

// CONSTRUCTORS //
    public GoblinBulletSmall(GoblinFighter parent) {
        super(10, 10, (DynamicEntity) parent);
        img = AssetManager.get().getSprite("BulletEnemy");
        setCollisionBox();

        // Move to correct position
        this.moveSpeed = 18;
        setMoveSpeeds();
        move(1);

        if(parent.getShootPhase() == 0) strafeLeft(6);
        else strafeRight(6);

        // Reset to correct speeds
        this.moveSpeed = 2;
        setMoveSpeeds();
        rotateSprite();
    }

// METHODS //
    // Method Override - To Handle Collisions //
    @Override
    public void collide(Entity ec) {
        if(ec instanceof Game.Entities.Dynamic.PlayerEntity) {
            destroy();
        }
    }

    // Method Override - Used for initial spacial setup for the Collision Box //
    @Override
    public void setCollisionBox() {
        collision = new CollisionBox(xpos+IMG_X_OFFSET, ypos, 2, 10, IMG_X_OFFSET, 0, this);
    }

    @Override
    protected int setDamageValue() {
        return DEF_DAMAGE_VALUE;
    }
}
