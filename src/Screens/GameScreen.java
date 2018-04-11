package Screens;

import Assets.AssetManager;
import Entities.EntityManager;
import Entities.Dynamic.PlayerEntity;
import Game.EnemyDirector;
import Game.Launcher;

import java.awt.*;
import java.io.IOException;

/**
 * Cameron Bell - 26/03/2018
 * Game Screen Class
 */

public class GameScreen extends Screen {
// VARIABLES //
    // Managers
    private PlayerEntity player;
    private EntityManager entityManager;
    private EnemyDirector enemyDirector;

// CONSTRUCTORS //
    public GameScreen() throws IOException {
        super();

        // Declarations
        entityManager = EntityManager.get();
        enemyDirector = EnemyDirector.get();
        player = new PlayerEntity(
                Launcher.DEF_GAME_WIDTH/2 - player.DEF_PLAYER_WIDTH/2,
                Launcher.DEF_GAME_HEIGHT/2 - player.DEF_PLAYER_HEIGHT/2)
        ;
    }

// METHODS //
    // Method - Update Managers
    @Override
    public void update() {
        entityManager.update();
        enemyDirector.update();
    }

    // Method - Draw Everything in the Screen
    @Override
    public void draw(Graphics g) {
        // Draw Background
        g.setColor(new Color(0, 0, 20));
        g.fillRect(0, 0, Launcher.DEF_GAME_WIDTH, Launcher.DEF_GAME_HEIGHT);

        // Draw All Entities
        entityManager.draw(g);

        // If DRAW_COLLISIONS = true, draw all subscribed collision boxes
        if(Game.Game.DRAW_COLLISIONS) entityManager.drawCollisionBoxes(g);
    }
}
