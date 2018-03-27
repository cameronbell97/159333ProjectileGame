package Entities;

import Assets.AssetManager;

import java.awt.*;

/**
 * Created by Cameron on 27/03/2018.
 */
public class PlayerEntity extends Vulnerable {
// VARIABLES //
    AssetManager assMan = AssetManager.get();

// CONSTRUCTORS //
    public PlayerEntity(float x, float y) {
        super(x, y);
    }

// METHODS //
    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(assMan.getSprite("player"), (int)xpos, (int)ypos, null);
    }
}
