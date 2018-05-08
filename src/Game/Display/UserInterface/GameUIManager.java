package Game.Display.UserInterface;

import Game.Data.GameDataManager;
import Game.Data.Settings;
import Game.Timer.CodeTimer;
import Game.Timer.TimerManager;
import Game.Timer.iCanHaveCodeTimer;

import java.awt.*;

public class GameUIManager implements iCanHaveCodeTimer {
// VARIABLES //
    private static final int DEF_FLASH_TIME = 110;

    GameDataManager gameDataManager;
    TextManager textManager;

    String flashAlert;
    float flashAlertAlpha;
    int flashAlertAlphaPhase;
    private static final int FLASH_ALERT_Y = (Settings.ui_upper_boundary * 2) + (Settings.character_height * Settings.character_size) * 2;

// CONSTRUCTORS //
    public GameUIManager() {
        gameDataManager = GameDataManager.get();
        textManager = new TextManager();
        flashAlertAlpha = 0;
        flashAlertAlphaPhase = 0;
    }

// METHODS //
    public void update() {
        gameDataManager.update();
        flashAlert();
    }

    public void draw(Graphics g) {
        drawPlayerHP(g);
        drawRemainingEnemies(g);
        drawGameLevel(g);
        drawScore(g);

        // Draw Flash Alert
        if(flashAlert != null)
            textManager.drawString(g, flashAlert, "center", Settings.game_width / 2, FLASH_ALERT_Y, flashAlertAlpha);
    }

    private void drawScore(Graphics g) {
        // Get Parameters
        int xoffset = Settings.ui_left_boundary;
        int yoffset = Settings.ui_upper_boundary;
        int score = gameDataManager.getScore();

        // Draw Score Word
        textManager.drawString(g, "SCORE", "left", xoffset, yoffset);

        // Reset offsets for next line
        yoffset += textManager.getCharacterHeight() + 4;

        // Draw Score Number
        textManager.drawString(g, Integer.toString(score), "left", xoffset, yoffset);
    }

    private void drawGameLevel(Graphics g) {
        // Get Parameters
        int xoffset = Settings.game_width / 2;
        int yoffset = Settings.ui_upper_boundary;
        int level = gameDataManager.getCurrent_level();

        // Draw Level Word
        textManager.drawString(g, "LEVEL", "center", xoffset, yoffset);

        // Reset offsets for next line
        yoffset += textManager.getCharacterHeight() + 4;

        // Draw Level Number
        textManager.drawString(g, Integer.toString(level), "center", xoffset, yoffset);
    }

    private void drawRemainingEnemies(Graphics g) {
        // Get Parameters
        int xoffset = Settings.game_width - Settings.ui_right_boundary - textManager.getCharacterWidth();
        int yoffset = Settings.ui_upper_boundary;
        int remaining = gameDataManager.getEnemies_remaining();

        // Draw Remaining Enemies Word
        textManager.drawString(g, "ENEMIES", "right", xoffset, yoffset);

        // Reset offsets for next line
        yoffset += textManager.getCharacterHeight() + 4;

        // Draw Level Number
        textManager.drawString(g, Integer.toString(remaining), "right", xoffset, yoffset);
    }

    private void drawPlayerHP(Graphics g) {
        // Get Parameters
        int xoffset = Settings.ui_left_boundary;
        int yoffset = (Settings.game_height - Settings.ui_lower_boundary) - (textManager.getCharacterHeight()) * 2 - 4;
        int hp = gameDataManager.getPlayer_hp();

        // Draw HP Word
        textManager.drawString(g, "HP", "left", xoffset, yoffset);

        // Reset offsets for next line
        yoffset += textManager.getCharacterHeight() + 4;

        // Draw HP Number
        textManager.drawString(g, Integer.toString(hp), "left", xoffset, yoffset);
    }

    public void flashAlert() {
        if(flashAlert == null) return;

        if(flashAlertAlpha == 0) flashAlertAlphaPhase = 0;
        else if(flashAlertAlpha == 1) flashAlertAlphaPhase = 1;

        if(flashAlertAlphaPhase == 0) {
            flashAlertAlpha = Math.min(1, flashAlertAlpha + ((float)1/((float)DEF_FLASH_TIME/(float)2)));
        } else {
            flashAlertAlpha = Math.max(0, flashAlertAlpha - ((float)1/((float)DEF_FLASH_TIME/(float)2)));
        }
    }

    private void killFlashAlert() {
        flashAlert = null;
        flashAlertAlpha = 0;
        flashAlertAlphaPhase = 0;
    }

    @Override
    public void timerNotify(CodeTimer t) {
        String code = t.getCode();
        TimerManager.get().unsubTimer(t);

        switch(code) {
            case "FLA":
                killFlashAlert();
        }
    }

    public void clear() {
        gameDataManager = null;
    }



// GETTERS & SETTERS //
    public void setFlashAlert(String alert, int numberOfFlashes) {
        flashAlert = alert;
        TimerManager.get().newCodeTimer(numberOfFlashes * DEF_FLASH_TIME, this, "FLA");
    }
}
