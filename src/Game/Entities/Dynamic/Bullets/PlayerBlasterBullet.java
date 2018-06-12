package Game.Entities.Dynamic.Bullets;

import Game.Display.Assets.AssetManager;
import Game.Entities.Dynamic.DynamicEntity;

import java.awt.image.BufferedImage;

public class PlayerBlasterBullet extends PlayerBullet {
// VARIABLES //
    private static final int DEF_MOVESPEED = 12;
    private static final int DEF_DAMAGE_VALUE = 2;

// CONSTRUCTORS //
    public PlayerBlasterBullet(DynamicEntity parent) {
        super(parent);
        moveSpeed = DEF_MOVESPEED;
    }

// METHODS //
    @Override
    protected void setPosition() {
        // Move bullet to nose of Player ship
        moveSpeed = 20;
        setMoveSpeeds();
        move(1);

        // Reset move speed
        moveSpeed = DEF_MOVESPEED;
        setMoveSpeeds();
    }

    @Override
    protected BufferedImage setSprite() {
        return AssetManager.get().getSprite(20, 0, 0);
    }

    @Override
    protected int setDamageValue() {
        return DEF_DAMAGE_VALUE;
    }
}
