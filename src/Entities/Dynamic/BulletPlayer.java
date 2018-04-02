package Entities.Dynamic;

import Assets.AssetManager;
import Game.Handler;

public class BulletPlayer extends Bullet {
    public BulletPlayer(Handler handler, DynamicEntity parent) {
        super(handler, 4, 10, parent);
        img = AssetManager.get().getSprite("BulletPlayer");
    }
}
