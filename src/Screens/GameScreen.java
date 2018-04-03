package Screens;

import Assets.AssetManager;
import Entities.EntityManager;
import Entities.Dynamic.PlayerEntity;
import Entities.Static.Wall;
import Entities.Static.WallTest;
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
    private WallTest walt;

// CONSTRUCTORS //
    public GameScreen(Handler handler) throws IOException {
        // Super Call
        super(handler);

        // Declarations
        entityManager = EntityManager.get();
        assMan = AssetManager.get();
        player = new PlayerEntity(
                handler,
                Launcher.DEF_GAME_WIDTH/2-player.DEF_PLAYER_WIDTH/2,
                Launcher.DEF_GAME_HEIGHT/2-player.DEF_PLAYER_HEIGHT/2)
        ;

        walt = new WallTest(handler, AssetManager.get().getSprite("LTWall"), 100, 100, 16, 16);

        // DynamicEntity Subscriptions
        entityManager.subscribe(player);
//        entityManager.subscribe(walt);

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

        // draw collision box for player // TODO // remove

        // Draw All Entities
        entityManager.draw(g);

        // If DRAW_COLLISIONS = true, draw all subscribed collision boxes
        if(Game.Game.DRAW_COLLISIONS) entityManager.drawCollisionBoxes(g);
    }
}
