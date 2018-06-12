package Game.Entities.Dynamic.Bullets;

import Game.Display.Assets.AssetManager;
import Game.Entities.Collision.CollisionBox;
import Game.Entities.Dynamic.DynamicEntity;

import java.awt.image.BufferedImage;

public abstract class PlayerSideBullet extends PlayerBullet {
// VARIABLES //
    private static final int IMG_X_OFFSET = 3;
    public static final int DEF_MOVESPEED = 12;
    private static final int DEF_DAMAGE_VALUE = 1;

// CONSTRUCTORS //
    public PlayerSideBullet(DynamicEntity parent) {
        super(parent);
        moveSpeed = DEF_MOVESPEED;
    }

// METHODS //
    @Override
    public void setCollisionBox() {
        collision = new CollisionBox(xpos+IMG_X_OFFSET, ypos, 4, 10, IMG_X_OFFSET, 0, this);
    }

    @Override
    protected BufferedImage setSprite() {
        return AssetManager.get().getSprite(20, 0, 1);
    }

    @Override
    protected int setDamageValue() {
        return DEF_DAMAGE_VALUE;
    }
}
