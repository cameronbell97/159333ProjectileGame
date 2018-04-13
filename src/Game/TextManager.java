package Game;

import Assets.AssetManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by Cameron on 13/04/2018.
 */
public class TextManager {
// VARIABLES //
    private static final int CHARACTER_HEIGHT = 9;
    private static final int CHARACTER_WIDTH = 5;
    private static final int CHARACTER_SIZE = 3;

    private AssetManager assMan;
    private HashMap<String, BufferedImage> charset_1;
    private HashMap<String, BufferedImage> charset_2;

// CONSTRUCTORS //
    public TextManager() {
        assMan = AssetManager.get();

        charset_1 = assMan.getMap("charset_1");
        charset_2 = assMan.getMap("charset_2");
    }

// METHODS //
    public void drawString(Graphics g, String word, String alignment, int xpos, int ypos) {
        // Get Parameters
        int character_width_final = getCharacterWidth();
        int character_height_final = getCharacterHeight();
        int xPencil = xpos;
        int yPencil = ypos;
        int xIncrement = character_width_final + CHARACTER_SIZE;
        String[] wordArray = word.split("");

        switch (alignment) {
            case "center":
                xPencil -= ((wordArray.length * xIncrement) - CHARACTER_SIZE) / 2;
                break;
            case "right":
                Collections.reverse(Arrays.asList(wordArray));
                break;
        }

        if(alignment.equals("left") || alignment.equals("right") || alignment.equals("center")) {
            for(int i = 0; i < wordArray.length; i++) {
                g.drawImage(
                        charset_1.get(wordArray[i]),
                        xPencil,
                        yPencil,
                        character_width_final,
                        character_height_final,
                        null
                );
                switch (alignment) {
                    case "right":
                        xPencil -= xIncrement;
                        break;
                    default:
                        xPencil += xIncrement;
                        break;
                }
            }
        }
    }


// GETTERS & SETTERS //

    public static int getCharacterHeight() {
        return CHARACTER_HEIGHT * CHARACTER_SIZE;
    }

    public static int getCharacterWidth() {
        return CHARACTER_WIDTH * CHARACTER_SIZE;
    }
}
