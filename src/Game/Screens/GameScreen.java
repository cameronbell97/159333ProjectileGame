package Game.Screens;

import Game.Entities.EntityManager;
import Game.Entities.Dynamic.PlayerEntity;
import Game.Entities.EnemyDirector;
import Game.Data.Settings;
import Game.Display.UserInterface.GameUIManager;
import Game.Timer.CodeTimer;
import Game.Timer.TimerManager;
import Game.Timer.iCanHaveCodeTimer;

import java.awt.*;
import java.io.IOException;

/**
 * Cameron Bell - 26/03/2018
 * Game Screen Class
 */

public class GameScreen extends Screen implements iCanHaveCodeTimer {
// VARIABLES //
    // Managers
    private PlayerEntity player;
    private EntityManager entityManager;
    private EnemyDirector enemyDirector;
    private GameUIManager UIManager;
    private Screen lastScreen;

// CONSTRUCTORS //
    public GameScreen(Screen lastScreen) throws IOException {
        super();

        this.lastScreen = lastScreen;

        // Declarations
        entityManager = EntityManager.get();
        enemyDirector = EnemyDirector.get();
        UIManager = new GameUIManager();
        player = new PlayerEntity(
                Settings.game_width/2 - player.DEF_PLAYER_WIDTH/2,
                Settings.game_height/2 - player.DEF_PLAYER_HEIGHT/2)
        ;
    }

// METHODS //
    // Method - Update Managers
    @Override
    public void update() {
        entityManager.update();
        enemyDirector.update();
        UIManager.update();
    }

    // Method - Draw Everything in the Screen
    @Override
    public void draw(Graphics g) {
        // Draw Background
        g.setColor(new Color(0, 0, 20));
        g.fillRect(0, 0, Settings.game_width, Settings.game_height);

        // Draw All Game.Entities
        entityManager.draw(g);

        // If DRAW_COLLISIONS = true, draw all subscribed collision boxes
        if(Game.Game.DRAW_COLLISIONS) entityManager.drawCollisionBoxes(g);

        // Draw Game.Display.UserInterface Last // So it appears over everything else
        UIManager.draw(g);
    }

    public void end() {
        TimerManager.get().newCodeTimer(120, this, "END");
    }

    private void endGame() {
        ScreenManager.setScreen(new ScoresScreen(lastScreen, this));
    }

    @Override
    public void timerNotify(CodeTimer t) {
        switch (t.getCode()) {
            case "END":
                endGame();
                break;
        }

        TimerManager.get().unsubTimer(t);
    }
}
