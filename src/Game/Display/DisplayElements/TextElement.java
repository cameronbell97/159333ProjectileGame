package Game.Display.DisplayElements;

import Game.Data.Settings;
import Game.Display.UserInterface.TextManager;

import java.awt.*;

/**
 * Cameron Bell - 19/04/2018
 * Text Element Class
 * Element Class for Holding and Displaying Text
 */
public class TextElement extends Element {
// VARIABLES //
    private TextManager textManager;
    private String text;
    private int textSize;

// CONSTRUCTORS //
    public TextElement(String text) {
        this(text, Settings.character_size);
    }
    public TextElement(String text, int textSize) {
        super(0, 0);
        textManager = new TextManager();
        setWidth(textManager.getWordWidth(text, textSize));
        setHeight(textManager.getCharacterHeight(textSize));
        this.text = text;
        this.textSize = textSize;
    }

// METHODS //
    // Method - Empty Update Method as Nothing Needs Updating //
    @Override
    public void update() {

    }

    // Method - For Drawing Element //
    @Override
    public void draw(Graphics g, int xStart, int yStart) {
        if(isVisible()) {
            textManager.drawString(g, text, "left", xStart, yStart, textSize);
        }
    }
}
