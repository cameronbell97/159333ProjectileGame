package Game.Entities.Dynamic.Bullets;

import Game.Display.Assets.AssetManager;
import Game.Entities.Collision.CollisionBox;
import Game.Entities.Dynamic.DynamicEntity;
import Game.Entities.Dynamic.Enemies.GoblinFighter;
import Game.Entities.Entity;

/**
 * Created by Cameron on 16/04/2018.
 */
public class GoblinBulletLarge extends Bullet {
// VARIABLES //
    protected static final int IMG_X_OFFSET = 3;

// CONSTRUCTORS //
    public GoblinBulletLarge(GoblinFighter parent) {
        super(10, 10, (DynamicEntity) parent);
        img = AssetManager.get().getSprite(20, 1, 1);
        collision = new CollisionBox(xpos+IMG_X_OFFSET, ypos, 4, 10, IMG_X_OFFSET, 0, this);

        // Move to correct position
        this.moveSpeed = 22;
        setMoveSpeeds();
        move();

        if(parent.getShootPhase() == 0) strafeLeft(11);
        else strafeRight(11);

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
