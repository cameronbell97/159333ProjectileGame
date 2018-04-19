package Game.Display.UserInterface.Buttons;

import Game.Data.MouseManager;
import Game.Data.Settings;
import Game.Display.UserInterface.TextManager;

import java.awt.*;

/**
 * Created by Cameron on 13/04/2018.
 */
public abstract class Button {
// VARIABLES //
    MouseManager mouseManager;
    TextManager textManager;
    private boolean left, right;
    private int xpos, ypos, height, width;
    private String text;
    private boolean isHovered, wasJustClicked, isClicked;


// CONSTRUCTORS //
    public Button(String text, int middleXpos, int middleYpos) {
        this.text = text;

        this.height = textManager.getCharacterHeight() + (Settings.button_padding * 2) + (Settings.button_border_width * 2);
        this.width = (textManager.getCharacterWidth() * text.length() + Settings.character_size * (text.length() - 1)) + (Settings.button_padding * 2) + (Settings.button_border_width * 2);

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
        isHovered = false;
        wasJustClicked = false;
        isClicked = false;
    }

    public void update() {
        left = mouseManager.checkLeftMouse();
        right = mouseManager.checkRightMouse();

        // Check if the mouse is hovering over the button
        if(
                mouseManager.getMouseX() > xpos &&
                mouseManager.getMouseX() < xpos + width &&
                mouseManager.getMouseY() > ypos &&
                mouseManager.getMouseY() < ypos + height
            ) {
            isHovered = true;
        } else isHovered = false;

        // Check for click
        if(isHovered && left) {
            wasJustClicked = true;
            isClicked = true;
        }
        if(!isHovered && !left) {
            isClicked = false;
        }
        if(!isHovered && !isClicked && wasJustClicked) {
            wasJustClicked = false;
        }
        if(isHovered && !left) {
            isClicked = false;
        }
        if(isHovered && !isClicked && wasJustClicked) {
            onClick();
        }
    }

    public void draw(Graphics g) {
        if(isHovered || isClicked) {
            g.setColor(new Color(46, 47, 120));
            g.fillRect(xpos, ypos, width, height);
        }
        g.setColor(new Color(129,130,174));
        g.drawRect(xpos, ypos, width, height);

        textManager.drawString(g, text, "left", xpos + Settings.button_border_width + Settings.button_padding, ypos + Settings.button_border_width + Settings.button_padding);
    }

    protected abstract void onClick();

    public static int getButtonHeight() {
        return TextManager.getCharacterHeight() + (Settings.button_padding * 2) + (Settings.button_border_width * 2);
    }
}
