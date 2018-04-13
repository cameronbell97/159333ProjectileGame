package MainMenu;

import Game.MouseManager;
import Game.TextManager;

import java.awt.*;

/**
 * Created by Cameron on 13/04/2018.
 */
public class Button {
// VARIABLES //
    private static final int BORDER_WIDTH = 4;
    private static final int INNER_PADDING = 8;
    MouseManager mouseManager;
    TextManager textManager;
    private boolean left, right;
    private int xpos, ypos, height, width;
    private String text;


// CONSTRUCTORS //
    public Button(String text, int xpos, int ypos) {
        this.text = text;
        this.xpos = xpos;
        this.ypos = ypos;

        textManager = new TextManager();
        this.height = textManager.getCharacterHeight() + (INNER_PADDING * 2) + (BORDER_WIDTH * 2);
        this.width = textManager.getCharacterWidth() + (INNER_PADDING * 2) + (BORDER_WIDTH * 2);

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

    }

    private void onClick() {

    }
}
