package Entities.Dynamic;

import Assets.AssetManager;
import Entities.Collision.CollisionBox;
import Entities.Entity;
import Game.Handler;

public class BulletPlayer extends Bullet {
// VARIABLES //
    protected static final int IMG_X_OFFSET = 3;

// CONSTRUCTORS //
    public BulletPlayer(Handler handler, DynamicEntity parent) {
        super(handler, 10, 10, parent);
        img = AssetManager.get().getSprite("BulletPlayer");
        collision = new CollisionBox(handler, xpos+IMG_X_OFFSET, ypos, width, height, IMG_X_OFFSET, 0, this);
    }

// METHODS //
    @Override
    public void collide(Entity ec) {

    }
}
