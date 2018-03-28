package Entities;
import Game.Game;

import Assets.AssetManager;

import java.awt.*;

/**
 * Created by Cameron on 27/03/2018.
 */
public class PlayerEntity extends Vulnerable {
// VARIABLES //
    AssetManager assMan = AssetManager.get();
    Game game;

// CONSTRUCTORS //
    public PlayerEntity(Game gm, float x, float y) {
        super(x, y);
        game = gm;
    }

// METHODS //
    @Override
    public void update() {
        if(game.getKeyManager().forward) ypos -= 4;
        if(game.getKeyManager().left) xpos -= 4;
        if(game.getKeyManager().right) xpos += 4;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(assMan.getSprite("player"), (int)xpos, (int)ypos, null);
    }
}
