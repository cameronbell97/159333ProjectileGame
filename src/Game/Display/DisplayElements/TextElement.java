package Game.Display.DisplayElements;

import Game.Display.UserInterface.TextManager;

import java.awt.*;

/**
 * Cameron Bell - 19/04/2018
 * Text Element Class
 * Element Class for Holding and Displaying Text
 */
public class TextElement extends Element {
// VARIABLES //
    TextManager textManager;
    String text;

// CONSTRUCTORS //
    public TextElement(String text) {
        super(0, 0);
        textManager = new TextManager();
        setWidth(textManager.getWordWidth(text));
        setHeight(textManager.getCharacterHeight());
        this.text = text;
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
            textManager.drawString(g, text, "left", xStart, yStart);
        }
    }
}
