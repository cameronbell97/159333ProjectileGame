package Screens;

import Assets.AssetManager;
import Entities.PlayerEntity;
import Game.Game;
import Game.Launcher;

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
        player = new PlayerEntity(
                game,
                Launcher.DEF_GAME_WIDTH/2-player.DEF_PLAYER_WIDTH/2,
                Launcher.DEF_GAME_HEIGHT/2-player.DEF_PLAYER_HEIGHT/2);
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
