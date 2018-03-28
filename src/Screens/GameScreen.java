package Screens;

import Assets.AssetManager;
import Entities.PlayerEntity;
import Entities.Wall;
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
    Wall walls[][];

    // CONSTRUCTORS //
    public GameScreen(Game game) throws IOException {
        super(game);
        assMan = AssetManager.get();
        player = new PlayerEntity(
                game,
                Launcher.DEF_GAME_WIDTH/2-player.DEF_PLAYER_WIDTH/2,
                Launcher.DEF_GAME_HEIGHT/2-player.DEF_PLAYER_HEIGHT/2)
        ;
        // Make walls
        walls = new Wall[2][4];
        if(Launcher.DEF_GAME_WIDTH >= 32 && Launcher.DEF_GAME_HEIGHT >= 32) {
            for(int i = 0; i < 4; i++) {
                walls[0][i] = new Wall();
            }
            walls[0][0].setPos(0, 0);
            walls[0][1].setPos(Launcher.DEF_GAME_WIDTH - walls[0][1].getWidth(), 0);
            walls[0][2].setPos(0, Launcher.DEF_GAME_HEIGHT - walls[0][1].getHeight());
            walls[0][3].setPos(Launcher.DEF_GAME_WIDTH - walls[0][1].getWidth(), Launcher.DEF_GAME_HEIGHT - walls[0][1].getHeight());
            // TODO top/bot/left/right walls
        } else walls = null;
    }

// METHODS //
    @Override
    public void update() {
        player.update();
    }

    @Override
    public void draw(Graphics g) {
        // Draw Background
        g.setColor(Color.black);
        g.fillRect(0, 0, Launcher.DEF_GAME_WIDTH, Launcher.DEF_GAME_HEIGHT);

        // Draw Walls
        if(walls != null) {
            for(int i = 0; i < 4; i++) {
                walls[0][i].draw(g);
            }
            // TODO draw top/bot/left/right walls
        }

        // Draw Player
        player.draw(g);
    }
}
