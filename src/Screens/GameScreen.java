package Screens;

import Assets.AssetManager;
import Entities.EntityManager;
import Entities.PlayerEntity;
import Entities.Wall;
import Game.Handler;
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
    private EntityManager entityManager;

    // CONSTRUCTORS //
    public GameScreen(Handler handler) throws IOException {
        // Super Call
        super(handler);

        // Declarations
        entityManager = new EntityManager();
        assMan = AssetManager.get();
        player = new PlayerEntity(
                handler,
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
            walls[0][0].setSprite(AssetManager.get().getSprite("LTWall"));
            walls[0][1].setPos(Launcher.DEF_GAME_WIDTH - walls[0][1].getWidth(), 0);
            walls[0][1].setSprite(AssetManager.get().getSprite("RTWall"));
            walls[0][2].setPos(0, Launcher.DEF_GAME_HEIGHT - walls[0][1].getHeight());
            walls[0][2].setSprite(AssetManager.get().getSprite("LBWall"));
            walls[0][3].setPos(Launcher.DEF_GAME_WIDTH - walls[0][1].getWidth(), Launcher.DEF_GAME_HEIGHT - walls[0][1].getHeight());
            walls[0][3].setSprite(AssetManager.get().getSprite("RBWall"));

            if(Launcher.DEF_GAME_WIDTH > 32) {
                for(int i = 0; i < 2; i++) {
                    walls[1][i] = new Wall();
                }
                walls[1][0].setPos(16, 0);
                walls[1][1].setPos(16, Launcher.DEF_GAME_HEIGHT-16);
                walls[1][0].setSprite(AssetManager.get().getSprite("HozWall"));
                walls[1][1].setSprite(AssetManager.get().getSprite("HozWall"));
                walls[1][0].setWidth(Launcher.DEF_GAME_WIDTH-32);
                walls[1][1].setWidth(Launcher.DEF_GAME_WIDTH-32);
            }
            if(Launcher.DEF_GAME_HEIGHT > 32) {
                for(int i = 2; i < 4; i++) {
                    walls[1][i] = new Wall();
                }
                walls[1][2].setPos(0, 16);
                walls[1][3].setPos(Launcher.DEF_GAME_WIDTH-16, 16);
                walls[1][2].setSprite(AssetManager.get().getSprite("VerWall"));
                walls[1][3].setSprite(AssetManager.get().getSprite("VerWall"));
                walls[1][2].setHeight(Launcher.DEF_GAME_HEIGHT-32);
                walls[1][3].setHeight(Launcher.DEF_GAME_HEIGHT-32);
            }

        } else walls = null;

        // Entity Subscriptions
        entityManager.subscribe(player);
    }

// METHODS //
    @Override
    public void update() {
        entityManager.update();
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

            for(int i = 0; i < 4; i++) {
                if(walls[1][i] != null) walls[1][i].draw(g);
            }
        }

        // Draw Player
        entityManager.draw(g);
    }
}
