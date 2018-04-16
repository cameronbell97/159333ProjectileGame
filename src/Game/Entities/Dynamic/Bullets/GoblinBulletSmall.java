package Game.Entities.Dynamic.Bullets;

import Game.Display.Assets.AssetManager;
import Game.Entities.Collision.CollisionBox;
import Game.Entities.Dynamic.DynamicEntity;
import Game.Entities.Dynamic.Enemies.GoblinFighter;
import Game.Entities.Entity;

/**
 * Created by Cameron on 16/04/2018.
 */
public class GoblinBulletSmall extends Bullet {
// VARIABLES //
    protected static final int IMG_X_OFFSET = 4;

// CONSTRUCTORS //
    public GoblinBulletSmall(GoblinFighter parent) {
        super(10, 10, (DynamicEntity) parent);
        img = AssetManager.get().getSprite("BulletEnemy");
        collision = new CollisionBox(xpos+IMG_X_OFFSET, ypos, 2, 10, IMG_X_OFFSET, 0, this);

        // Move to correct position
        this.moveSpeed = 18;
        setMoveSpeeds();
        move();

        if(parent.getShootPhase() == 0) strafeLeft(6);
        else strafeRight(6);

        // Reset to correct speeds
        this.moveSpeed = 2;
        setMoveSpeeds();
        rotateSprite();
    }

// METHODS //
    @Override
    public void collide(Entity ec) {
        if(ec instanceof Game.Entities.Dynamic.PlayerEntity) {
            destroy();
        }
    }
}
