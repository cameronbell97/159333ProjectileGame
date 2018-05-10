package Game.Display.DisplayElements;

import Game.Display.UserInterface.TextManager;

import java.awt.*;

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
    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g, int xStart, int yStart) {
        if(isVisible()) {
            textManager.drawString(g, text, "left", xStart, yStart);
        }
    }
}
