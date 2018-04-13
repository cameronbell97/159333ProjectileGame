package Game;

import Assets.AssetManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class UIManager {
// VARIABLES //
    // Statics
    private static final int LEFT_BOUNDARY = 24;
    private static final int RIGHT_BOUNDARY = 24;
    private static final int UPPER_BOUNDARY = 24;
    private static final int LOWER_BOUNDARY = 24;
    private static final int CHARACTER_HEIGHT = 9;
    private static final int CHARACTER_WIDTH = 5;
    private static final int CHARACTER_SIZE = 3;

    GameDataManager gameDataManager;
    private AssetManager assMan;
    private HashMap<String, BufferedImage> charset_1;
    private HashMap<String, BufferedImage> charset_2;

// CONSTRUCTORS //
    public UIManager() {
        gameDataManager = GameDataManager.get();
        assMan = AssetManager.get();

        charset_1 = assMan.getMap("charset_1");
        charset_2 = assMan.getMap("charset_2");
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
        String[] scoreLetter = { "S", "C", "O", "R", "E" };
        drawString(g, scoreLetter, "left", xoffset, yoffset);

        // Reset offsets for next line
        xoffset = LEFT_BOUNDARY;
        yoffset += CHARACTER_HEIGHT * CHARACTER_SIZE + 4;

        // Draw Score Number
        drawString(g, Integer.toString(score).split(""), "left", xoffset, yoffset);
    }

    private void drawGameLevel(Graphics g) {
        int level = gameDataManager.getCurrent_level();

        // Draw Level Word

        // Draw Level Number

    }

    private void drawRemainingEnemies(Graphics g) {
        int remaining = gameDataManager.getEnemies_remaining();
    }

    private void drawPlayerHP(Graphics g) {
        int hp = gameDataManager.getPlayer_hp();
    }

    private void drawString(Graphics g, String[] word, String alignment, int xpos, int ypos) {
        // Get Parameters
        int character_width_final = CHARACTER_WIDTH * CHARACTER_SIZE;
        int character_height_final = CHARACTER_HEIGHT * CHARACTER_SIZE;
        int xPencil = xpos;
        int yPencil = ypos;
        int xIncrement = character_width_final + CHARACTER_SIZE;

        switch (alignment) {
            case "left":
                for(int i = 0; i < word.length; i++) {
                    g.drawImage(
                            charset_1.get(word[i]),
                            xPencil,
                            yPencil,
                            character_width_final,
                            character_height_final,
                            null
                    );
                    xPencil += xIncrement;
                }
                break;

            case "right":
                break;

            case "centre":
                break;
        }

    }

// GETTERS & SETTERS //

}
