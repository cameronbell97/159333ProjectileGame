package Entities.Dynamic;

import Assets.AssetManager;
import Entities.Collision.CollisionBox;
import Game.Handler;

public class BulletPlayer extends Bullet {
// CONSTRUCTORS //
    public BulletPlayer(Handler handler, DynamicEntity parent) {
        super(handler, 4, 10, parent);
        img = AssetManager.get().getSprite("BulletPlayer");
        collision = new CollisionBox(handler, xpos, ypos, width, height, 0, 0, this);
    }
}
