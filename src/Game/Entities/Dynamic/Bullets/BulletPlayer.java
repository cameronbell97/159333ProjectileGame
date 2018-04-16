package Game.Entities.Dynamic.Bullets;

import Game.Display.Assets.AssetManager;
import Game.Entities.Collision.CollisionBox;
import Game.Entities.Dynamic.DynamicEntity;
import Game.Entities.Entity;

public class BulletPlayer extends Bullet {
// VARIABLES //
    protected static final int IMG_X_OFFSET = 3;

// CONSTRUCTORS //
    public BulletPlayer(DynamicEntity parent) {
        super(10, 10, parent);
        img = AssetManager.get().getSprite("BulletPlayer");
        collision = new CollisionBox(xpos+IMG_X_OFFSET, ypos, 4, 10, IMG_X_OFFSET, 0, this);
    }

// METHODS //
    @Override
    public void collide(Entity ec) {
        if(ec instanceof Game.Entities.Dynamic.Enemies.Asteroid) {
            destroy();
        }
    }
}