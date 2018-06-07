package Game.Display.UserInterface;

import Game.Data.Settings;
import Game.Handler;
import Game.Timer.CodeTimer;
import Game.Timer.iCanHaveCodeTimer;

import java.awt.*;

/**
 * Cameron Bell - 12/04/2018
 * Game UI Manager Class
 * Displays the Game UI When Playing
 */

public class GameUIManager implements iCanHaveCodeTimer {
// VARIABLES //
    // Statics //
    private static final int DEF_FLASH_TIME = 110;
    private static final int FLASH_ALERT_Y = (Settings.ui_upper_boundary * 2) + (Settings.character_height * Settings.character_size) * 2;

    // Managers //
    private Handler handler;
    private TextManager textManager;

    // Data //
    private String flashAlert;
    private float flashAlertAlpha;
    private int flashAlertAlphaPhase;

// CONSTRUCTORS //
    public GameUIManager(Handler parentHandler) {
        handler = parentHandler;
        textManager = new TextManager();
        flashAlertAlpha = 0;
        flashAlertAlphaPhase = 0;
    }

// METHODS //
    // Method - Update Flashing Alert //
    public void update(int dt) {
        flashAlert(dt);
    }

    // Method - Draw UI To Screen //
    public void draw(Graphics g) {
        drawPlayerHP(g);
        drawRemainingEnemies(g);
        drawGameLevel(g);
        drawScore(g);

        // Draw Flash Alert
        if(flashAlert != null)
            textManager.drawString(g, flashAlert, "center", Settings.game_width / 2, FLASH_ALERT_Y, flashAlertAlpha);
    }

    // Method - Draw Score to Screen //
    private void drawScore(Graphics g) {
        // Get Parameters
        int xoffset = Settings.ui_left_boundary;
        int yoffset = Settings.ui_upper_boundary;
        int score = Handler.get().getGameDataManager().getScore();

        // Draw Score Word
        textManager.drawString(g, "SCORE", "left", xoffset, yoffset);

        // Reset offsets for next line
        yoffset += textManager.getCharacterHeight() + 4;

        // Draw Score Number
        textManager.drawString(g, Integer.toString(score), "left", xoffset, yoffset);
    }

    // Method - Draw Game Level to Screen //
    private void drawGameLevel(Graphics g) {
        // Get Parameters
        int xoffset = Settings.game_width / 2;
        int yoffset = Settings.ui_upper_boundary;
        int level = handler.getGameDataManager().getCurrent_level();

        // Draw Level Word
        textManager.drawString(g, "LEVEL", "center", xoffset, yoffset);

        // Reset offsets for next line
        yoffset += textManager.getCharacterHeight() + 4;

        // Draw Level Number
        textManager.drawString(g, Integer.toString(level), "center", xoffset, yoffset);
    }

    // Method - Draw Remaining Enemies to Screen //
    private void drawRemainingEnemies(Graphics g) {
        // Get Parameters
        int xoffset = Settings.game_width - Settings.ui_right_boundary - textManager.getCharacterWidth();
        int yoffset = Settings.ui_upper_boundary;
        int remaining = handler.getGameDataManager().getEnemies_remaining();

        // Draw Remaining Enemies Word
        textManager.drawString(g, "ENEMIES", "right", xoffset, yoffset);

        // Reset offsets for next line
        yoffset += textManager.getCharacterHeight() + 4;

        // Draw Level Number
        textManager.drawString(g, Integer.toString(remaining), "right", xoffset, yoffset);
    }

    // Method - Draw Player Health to Screen //
    private void drawPlayerHP(Graphics g) {
        // Get Parameters
        int xoffset = Settings.ui_left_boundary;
        int yoffset = (Settings.game_height - Settings.ui_lower_boundary) - (textManager.getCharacterHeight()) * 2 - 4;
        int hp = handler.getGameDataManager().getPlayer_hp();

        // Draw HP Word
        textManager.drawString(g, "HP", "left", xoffset, yoffset);

        // Reset offsets for next line
        yoffset += textManager.getCharacterHeight() + 4;

        // Draw HP Number
        textManager.drawString(g, Integer.toString(hp), "left", xoffset, yoffset);
    }

    // Method - Update Alpha Value of Flashing Alert //
    public void flashAlert(int dt) {
        if(flashAlert == null) return;

        if(flashAlertAlpha == 0) flashAlertAlphaPhase = 0;
        else if(flashAlertAlpha == 1) flashAlertAlphaPhase = 1;

        if(flashAlertAlphaPhase == 0) {
            flashAlertAlpha = Math.min(1, flashAlertAlpha + dt * ((float)1/((float)DEF_FLASH_TIME/(float)2)));
        } else {
            flashAlertAlpha = Math.max(0, flashAlertAlpha - dt * ((float)1/((float)DEF_FLASH_TIME/(float)2)));
        }
    }

    // Method - End Flashing Alert //
    private void killFlashAlert() {
        flashAlert = null;
        flashAlertAlpha = 0;
        flashAlertAlphaPhase = 0;
    }

    // Method - Recieve Timer Notification //
    @Override
    public void timerNotify(CodeTimer t) {
        String code = t.getCode();
        Handler.get().getTimerManager().unsubTimer(t);

        switch(code) {
            case "FLA":
                killFlashAlert();
        }
    }

// GETTERS & SETTERS //
    // Setter Method - Set a Flashing Alert //
    public void setFlashAlert(String alert, int numberOfFlashes) {
        flashAlert = alert;
        Handler.get().getTimerManager().newCodeTimer(numberOfFlashes * DEF_FLASH_TIME, this, "FLA");
    }
}
