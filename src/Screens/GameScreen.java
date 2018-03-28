package Screens;

import Assets.AssetManager;
import Entities.PlayerEntity;
import Game.Game;

import java.awt.*;
import java.io.IOException;

/**
 * Cameron Bell - 26/03/2018
 * Game.Game Screens.Screen Class
 */

public class GameScreen extends Screen {
// VARIABLES //
    private AssetManager assMan;
    private PlayerEntity player;

// CONSTRUCTORS //
    public GameScreen(Game game) throws IOException {
        super(game);
        assMan = AssetManager.get();
        player = new PlayerEntity(game, 1280/2-32, 720/2-32);
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
