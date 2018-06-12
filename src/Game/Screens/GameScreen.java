package Game.Screens;

import Game.Data.KeyManager;
import Game.Data.PlayerModules.WeaponModule;
import Game.Data.Settings;
import Game.Timer.CodeTimer;
import Game.Timer.iCanHaveCodeTimer;

import java.awt.*;
import java.io.IOException;

/**
 * Cameron Bell - 26/03/2018
 * Game Screen Class
 * The Screen in which the actual Game is played
 */

public class GameScreen extends Screen implements iCanHaveCodeTimer {
// VARIABLES //
    private Screen lastScreen;
    private boolean gameIsRunning;
    private KeyManager keyManager;

// CONSTRUCTORS //
    public GameScreen(Screen lastScreen, WeaponModule playerWeapon) throws IOException {
        super();

        gameIsRunning = true;

        this.lastScreen = lastScreen;

        handler.newGame(playerWeapon);

        keyManager = handler.getKeyManager();
    }

// METHODS //
    // Method - Update Managers //
    @Override
    public void update(int dt) {
        if(gameIsRunning) {
            handler.updateGame(dt);
            if(keyManager.esc) ScreenManager.setScreen(new PauseMenuScreen(this));
        } else endGame();
    }

    // Method - Draw Everything in the Screen //
    @Override
    public void draw(Graphics g) {
        // Draw Background
        g.setColor(new Color(0, 0, 20));
        g.fillRect(0, 0, Settings.game_width, Settings.game_height);

        // Draw Everything
        handler.draw(g);
    }

    // Method - Start to End the Game //
    public void end() {
        handler.getTimerManager().newCodeTimer(120, this, "END");
    }

    // Method - Really End the Game //
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
