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
        for(int i = 0; i < 5; i++) {
            g.drawImage(
                    charset_1.get(scoreLetter[i]),
                    xoffset,
                    yoffset,
                    CHARACTER_WIDTH * CHARACTER_SIZE,
                    CHARACTER_HEIGHT * CHARACTER_SIZE,
                    null
            );
            xoffset += CHARACTER_WIDTH * CHARACTER_SIZE + CHARACTER_SIZE;
        }
        xoffset = LEFT_BOUNDARY;
        yoffset += CHARACTER_HEIGHT * CHARACTER_SIZE + 4;

        // Draw Score Number
        if (score == 0) g.drawImage(
                charset_1.get("0"),
                xoffset,
                yoffset,
                CHARACTER_WIDTH * CHARACTER_SIZE,
                CHARACTER_HEIGHT * CHARACTER_SIZE,
                null
        );
        else {
            int numlength = 0;
            for (int i = 1; score / i != 0; i = i * 10) {
                numlength++;
            }
            xoffset = LEFT_BOUNDARY + (CHARACTER_WIDTH * CHARACTER_SIZE + 4) * (numlength - 1);
            for(int i = 0; i < numlength; i++) {
                int currentChar = score % (int)Math.pow((double)10, (double)i+1);
                if(i != 0) currentChar = currentChar / (int)Math.pow((double)10, (double)i);

                g.drawImage(
                        charset_1.get(Integer.toString(currentChar)),
                        xoffset,
                        yoffset,
                        CHARACTER_WIDTH * CHARACTER_SIZE,
                        CHARACTER_HEIGHT * CHARACTER_SIZE,
                        null
                );
                xoffset -= CHARACTER_WIDTH * CHARACTER_SIZE + 4;
            }
        }
    }

    private void drawGameLevel(Graphics g) {
        int level = gameDataManager.getCurrent_level();
    }

    private void drawRemainingEnemies(Graphics g) {
        int remaining = gameDataManager.getEnemies_remaining();
    }

    private void drawPlayerHP(Graphics g) {
        int hp = gameDataManager.getPlayer_hp();
    }

// GETTERS & SETTERS //

}
