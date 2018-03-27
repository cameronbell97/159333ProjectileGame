package Screens;

import Assets.AssetManager;
import Entities.PlayerEntity;

import java.awt.*;
import java.io.IOException;

/**
 * Cameron Bell - 26/03/2018
 * Game Screens.Screen Class
 */

public class GameScreen extends Screen {
// VARIABLES //
    private AssetManager assMan;
    private PlayerEntity player;

// CONSTRUCTORS //
    public GameScreen() throws IOException {
        assMan = AssetManager.get();
        player = new PlayerEntity(64, 64);
    }

// METHODS //
    @Override
    public void update() {
        player.update();
    }

    @Override
    public void draw(Graphics g) {
        player.draw(g);
    }
}
