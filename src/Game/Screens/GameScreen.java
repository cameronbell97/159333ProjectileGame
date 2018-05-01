package Game.Screens;

import Game.Data.GameDataManager;
import Game.Data.SaveManager;
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
    private GameDataManager GDataMananger;
    private Screen lastScreen;

    private boolean gameIsRunning;

// CONSTRUCTORS //
    public GameScreen(Screen lastScreen) throws IOException {
        super();

        gameIsRunning = true;

        this.lastScreen = lastScreen;

        // Declarations
        entityManager = EntityManager.get();
        enemyDirector = EnemyDirector.get();
        enemyDirector.startGame();
        UIManager = GameUIManager.get();
        GDataMananger = GameDataManager.get();
        player = new PlayerEntity(
                Settings.game_width/2 - player.DEF_PLAYER_WIDTH/2,
                Settings.game_height/2 - player.DEF_PLAYER_HEIGHT/2)
        ;
    }

// METHODS //
    // Method - Update Managers
    @Override
    public void update() {
        if(gameIsRunning) {
            entityManager.update();
            enemyDirector.update();
            UIManager.update();
        } else endGame();
    }

    // Method - Draw Everything in the Screen
    @Override
    public void draw(Graphics g) {
        // Draw Background
        g.setColor(new Color(0, 0, 20));
        g.fillRect(0, 0, Settings.game_width, Settings.game_height);

        // Draw All Game.Entities
        entityManager.draw(g);

        // If DEBUG_DRAW_COLLISIONS = true, draw all subscribed collision boxes
        if(Settings.DEBUG_DRAW_COLLISIONS) entityManager.drawCollisionBoxes(g);

        // Draw Game.Display.UserInterface Last // So it appears over everything else
        UIManager.draw(g);
    }

    public void end() {
        TimerManager.get().newCodeTimer(120, this, "END");
    }

    private void endGame() {
        int score = GDataMananger.getScore();

        if(SaveManager.get().getSave().getScoreBoard().isHighScore(score)) {
            ScreenManager.setScreen(new AddScoreScreen(lastScreen, this, score));
        } else {
            ScreenManager.setScreen(new ScoresScreen(lastScreen, this));
        }
    }

    public void clearUIManager() {
        GameUIManager.get().clear();
    }

    @Override
    public void timerNotify(CodeTimer t) {
        TimerManager.get().unsubTimer(t);
        switch (t.getCode()) {
            case "END":
                gameIsRunning = false;
                break;
        }
    }
}
