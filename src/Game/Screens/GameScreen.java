package Game.Screens;

import Game.Data.Settings;
import Game.Handler;
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
    private Screen lastScreen;
    private boolean gameIsRunning;

// CONSTRUCTORS //
    public GameScreen(Screen lastScreen) throws IOException {
        super();

        gameIsRunning = true;

        this.lastScreen = lastScreen;

        handler.newGame();
    }

// METHODS //
    // Method - Update Managers
    @Override
    public void update(int dt) {
        if(gameIsRunning) {
            handler.updateGame(dt);
        } else endGame();
    }

    // Method - Draw Everything in the Screen
    @Override
    public void draw(Graphics g) {
        // Draw Background
        g.setColor(new Color(0, 0, 20));
        g.fillRect(0, 0, Settings.game_width, Settings.game_height);

        // Draw Everything
        handler.draw(g);
    }

    public void end() {
        handler.getTimerManager().newCodeTimer(120, this, "END");
    }

    private void endGame() {
        int score = handler.getGameDataManager().getScore();

        if(handler.getSave().getScoreBoard().isHighScore(score)) {
            ScreenManager.setScreen(new AddScoreScreen(lastScreen, this, score));
        } else {
            ScreenManager.setScreen(new ScoresScreen(lastScreen, this));
        }
    }

    // Method - Recieve Timer Notification //
    @Override
    public void timerNotify(CodeTimer t) {
        handler.getTimerManager().unsubTimer(t);
        switch (t.getCode()) {
            case "END":
                gameIsRunning = false;
                break;
        }
    }
}
