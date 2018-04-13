package Game;

import Assets.AssetManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class UIManager {
// VARIABLES //
    // Statics
    private static final int LEFT_BOUNDARY = 24;
    private static final int RIGHT_BOUNDARY = 24;
    private static final int UPPER_BOUNDARY = 24;
    private static final int LOWER_BOUNDARY = 24;

    GameDataManager gameDataManager;
    TextManager textManager;

// CONSTRUCTORS //
    public UIManager() {
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
        int xoffset = LEFT_BOUNDARY;
        int yoffset = UPPER_BOUNDARY;
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
        int xoffset = Launcher.DEF_GAME_WIDTH / 2;
        int yoffset = UPPER_BOUNDARY;
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
        int xoffset = Launcher.DEF_GAME_WIDTH - RIGHT_BOUNDARY - textManager.getCharacterWidth();
        int yoffset = UPPER_BOUNDARY;
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
        int xoffset = LEFT_BOUNDARY;
        int yoffset = (Launcher.DEF_GAME_HEIGHT - LOWER_BOUNDARY) - (textManager.getCharacterHeight()) * 2 - 4;
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
