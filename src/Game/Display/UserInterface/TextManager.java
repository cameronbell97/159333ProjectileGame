package Game.Display.UserInterface;

import Game.Display.Assets.AssetManager;
import Game.Data.Settings;

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
    private static final float MAX_ALPHA = 1;
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
    public void drawString(Graphics g, String text, String alignment, int xpos, int ypos) {
        drawString(g, text, alignment, xpos, ypos, MAX_ALPHA);
    }
    public void drawString(Graphics g, String text, String alignment, int xpos, int ypos, float alpha) {
        if(text.length() == 0) return;

        // Get Parameters
        int character_width_final = getCharacterWidth();
        int character_height_final = getCharacterHeight();
        int xPencil = xpos;
        int yPencil = ypos;
        int xIncrement = character_width_final + Settings.character_size;
        String[] wordArray = text.split("");

        switch (alignment) {
            case "center":
                xPencil -= ((wordArray.length * xIncrement) - Settings.character_size) / 2;
                break;
            case "right":
                Collections.reverse(Arrays.asList(wordArray));
                break;
        }

        Graphics2D g2d = (Graphics2D) g;

        if(alignment.equals("left") || alignment.equals("right") || alignment.equals("center")) {
            for(int i = 0; i < wordArray.length; i++) {
                // Set Alpha
                AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                g2d.setComposite(ac);
                g2d.drawImage(
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
    public static int getWordWidth(String word) {
        return (word.length() * getCharacterWidth()) + ((word.length() - 1) * Settings.character_size);
    }

    public static int getWordWidth(int wordSize) {
        return (wordSize * getCharacterWidth()) + ((wordSize - 1) * Settings.character_size);
    }

    public static int getCharacterHeight() {
        return Settings.character_height * Settings.character_size;
    }

    public static int getCharacterWidth() {
        return Settings.character_width * Settings.character_size;
    }
}
