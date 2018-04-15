package Game.Display.UserInterface;

import Game.Data.GameDataManager;
import Game.Data.Settings;

import java.awt.*;

public class GameUIManager {
// VARIABLES //
    GameDataManager gameDataManager;
    TextManager textManager;

// CONSTRUCTORS //
    public GameUIManager() {
        gameDataManager = GameDataManager.get();
        textManager = new TextManager();
    }

// METHODS //
    public void update() {
        gameDataManager.update();
    }

    public void draw(Graphics g) {
        drawPlayerHP(g);
        drawRemainingEnemies(g);
        drawGameLevel(g);
        drawScore(g);
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



// GETTERS & SETTERS //

}
