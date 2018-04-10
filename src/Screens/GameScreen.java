package Screens;

import Assets.AssetManager;
import Entities.Dynamic.Enemies.Asteroid;
import Entities.EntityManager;
import Entities.Dynamic.PlayerEntity;
import Game.EnemyDirector;
import Game.Handler;
import Game.Launcher;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

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
    private EntityManager entityManager;
    private EnemyDirector enemyDirector;

// CONSTRUCTORS //
    public GameScreen(Handler handler) throws IOException {
        // Super Call
        super(handler);

        // Declarations
        entityManager = EntityManager.get();
        assMan = AssetManager.get();
        enemyDirector = EnemyDirector.get();
        player = new PlayerEntity(
                handler,
                Launcher.DEF_GAME_WIDTH/2-player.DEF_PLAYER_WIDTH/2,
                Launcher.DEF_GAME_HEIGHT/2-player.DEF_PLAYER_HEIGHT/2)
        ;

//        for(int i = 0; i < 8; i++) {
//            entityManager.subscribe(EnemyDirector.generateEnemyPosition(new Asteroid(handler, 0, 0, 3, 0, 1)));
//        }

    }

// METHODS //
    @Override
    public void update() {
        entityManager.update();
        enemyDirector.update();
    }

    @Override
    public void draw(Graphics g) {
        // Draw Background
        g.setColor(new Color(0, 0, 20));
        g.fillRect(0, 0, Launcher.DEF_GAME_WIDTH, Launcher.DEF_GAME_HEIGHT);

        // draw collision box for player // TODO // remove

        // Draw All Entities
        entityManager.draw(g);

        // If DRAW_COLLISIONS = true, draw all subscribed collision boxes
        if(Game.Game.DRAW_COLLISIONS) entityManager.drawCollisionBoxes(g);
    }
}
