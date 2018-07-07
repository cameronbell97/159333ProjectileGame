package Game.Display.UserInterface;

import Game.Display.Assets.AssetManager;
import Game.Data.Settings;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

/**
 * Cameron Bell - 13/04/2018
 * Text Manager Class
 * Prints Text on the Screen from Given Values
 */
public class TextManager {
// VARIABLES //
    // Statics //
    private static final float MAX_ALPHA = 1;
    public static final int LINE_INCREMENT = 4;

    // Managers //
    private AssetManager assMan;

    // Maps //
    private HashMap<String, BufferedImage> charset_1;
    private HashMap<String, BufferedImage> charset_2;

// CONSTRUCTORS //
    public TextManager() {
        assMan = AssetManager.get();

        charset_1 = assMan.getMap("charset_1");
        charset_2 = assMan.getMap("charset_2");
    }

// METHODS //
    // Method - Draw a String on the screen at 100% Alpha //
    public void drawString(Graphics g, String text, String alignment, int xpos, int ypos) {
        drawString(g, text, alignment, xpos, ypos, Settings.character_size);
    }

    // Method - Draw a String on the screen at 100% Alpha //
    public void drawString(Graphics g, String text, String alignment, int xpos, int ypos, int textSize) {
        drawString(g, text, alignment, xpos, ypos, textSize, MAX_ALPHA);
    }

    // Method Overload - Draw a String on the screen at custom Alpha //
    public void drawString(Graphics g, String text, String alignment, int xpos, int ypos, int textSize, float alpha) {
        if(text.length() == 0) return;

        text = text.toUpperCase(); // Self Explanatory

        // Get Parameters
        int character_width_final = getCharacterWidth(textSize);
        int character_height_final = getCharacterHeight(textSize);
        int xPencil = xpos;
        int yPencil = ypos;
        int xIncrement = character_width_final + (textSize); // +(textSize) is the space between characters
        String[] wordArray = text.split("");

        switch (alignment) {
            case "center":
                xPencil -= ((wordArray.length * xIncrement) - textSize) / 2;
                break;
            case "right":
                Collections.reverse(Arrays.asList(wordArray));
                break;
        }

        // Draw Text
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
    // Getter Method - Get Width of Word Depending on Word Length & Character Size //
    public static int getWordWidth(String word, int textSize) {
        return (word.length() * getCharacterWidth(textSize)) + ((word.length() - 1) * textSize);
    }
    public static int getWordWidth(int wordSize, int textSize) {
        return (wordSize * getCharacterWidth(textSize)) + ((wordSize - 1) * textSize);
    }
    public static int getCharacterHeight(int textSize) {
        return Settings.character_height * textSize;
    }
    public static int getCharacterWidth(int textSize) {
        return Settings.character_width * textSize;
    }
    public static int getCharacterHeight() {
        return Settings.character_height * Settings.character_size;
    }
    public static int getCharacterWidth() {
        return Settings.character_width * Settings.character_size;
    }
}
