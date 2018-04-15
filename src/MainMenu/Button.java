package MainMenu;

import Game.MouseManager;
import Game.Settings;
import Game.TextManager;

import java.awt.*;

/**
 * Created by Cameron on 13/04/2018.
 */
public class Button {
// VARIABLES //
    MouseManager mouseManager;
    TextManager textManager;
    private boolean left, right;
    private int xpos, ypos, height, width;
    private String text;


// CONSTRUCTORS //
    public Button(String text, int middleXpos, int middleYpos) {
        this.text = text;

        this.height = textManager.getCharacterHeight() + (Settings.button_inner_padding * 2) + (Settings.button_border_width * 2);
        this.width = (textManager.getCharacterWidth() * text.length() + Settings.character_size * (text.length() - 1)) + (Settings.button_inner_padding * 2) + (Settings.button_border_width * 2);

        this.xpos = middleXpos-width/2;
        this.ypos = middleYpos-height/2;

        textManager = new TextManager();

        initialise();
    }

    public Button(String text, int xpos, int ypos, int width, int height) {
        this.text = text;
        this.xpos = xpos;
        this.ypos = ypos;
        this.width = width;
        this.height = height;
        textManager = new TextManager();

        initialise();
    }

// METHODS //
    private void initialise() {
        mouseManager = MouseManager.get();
        left = false;
        right = false;
    }

    public void update() {
        left = mouseManager.checkLeftMouse();
        right = mouseManager.checkRightMouse();
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.drawRect(xpos, ypos, width, height);
        textManager.drawString(g, text, "left", xpos + Settings.button_border_width + Settings.button_inner_padding, ypos + Settings.button_border_width + Settings.button_inner_padding);
    }

    private void onClick() {

    }

    public static int getButtonHeight() {
        return TextManager.getCharacterHeight() + (Settings.button_inner_padding * 2) + (Settings.button_border_width * 2);
    }
}
