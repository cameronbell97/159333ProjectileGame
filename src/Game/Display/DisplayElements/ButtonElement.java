package Game.Display.DisplayElements;

import Game.Data.KeyManager;
import Game.Data.MouseManager;
import Game.Data.Settings;
import Game.Handler;

import java.awt.*;

/**
 * Cameron Bell - 19/04/2018
 * Button Element Class
 * Abstract Element Class for Providing Button Functionality
 */

public abstract class ButtonElement extends PaddedElement {
// VARIABLES //
    // Managers //
    MouseManager mouseManager;
    protected KeyManager km;

    // Data //
    private int xpos, ypos; // x & y positions needed for mouse detection
    private boolean leftMouse, rightMouse;
    private boolean isHovered, wasJustClicked, isClicked;
    private String text;

    // Colours //
    private Color inactiveColour;
    private Color activeColour;

// CONSTRUCTORS //
    public ButtonElement(String text) {
        super(Settings.button_padding);
        this.text = text;
        initialise();
    }

    public ButtonElement(String text, int padding) {
        super(padding);
        this.text = text;
        initialise();
    }

    public ButtonElement(String text, int borderWidth, Color borderColour, Color fillColour, int padding) {
        super(borderWidth, borderColour, fillColour, padding);
        this.text = text;
        initialise();
    }

// METHODS //
    // Method - On Click to be Overridden for Button Click Functionality //
    protected abstract void onClick();

    // Method - For Drawing Element //
    @Override
    public void draw(Graphics g, int xStart, int yStart) {
        if (isVisible()) {
            this.xpos = xStart;
            this.ypos = yStart;

            if (isHovered || isClicked) {
                fillColour = activeColour;
            } else fillColour = inactiveColour;

            super.draw(g, xStart, yStart);

            // Draw Element Border
            g.setColor(borderColour);
            g.drawRect(xStart, yStart, width - 1, height - 1); // The -1 is because drawRect doesn't include  the bottom and right lines in the rectangle (draws them outside)
        }
    }

    // Method - For Checking Mouse Position and Updating Values Accordingly //
    @Override
    public void update() {
        additionalActions();

        leftMouse = mouseManager.checkLeftMouse();
        rightMouse = mouseManager.checkRightMouse();

        // Check if the mouse is hovering over the button
        if(
                mouseManager.getMouseX() > this.xpos &&
                        mouseManager.getMouseX() < this.xpos + width &&
                        mouseManager.getMouseY() > this.ypos &&
                        mouseManager.getMouseY() < this.ypos + height
                ) {
            isHovered = true;
        } else isHovered = false;

        // Check for click
        if(isHovered && leftMouse) {
            wasJustClicked = true;
            isClicked = true;
        }
        if(!isHovered && !leftMouse) {
            isClicked = false;
        }
        if(!isHovered && !isClicked && wasJustClicked) {
            wasJustClicked = false;
        }
        if(isHovered && !leftMouse) {
            isClicked = false;
        }
        if(isHovered && !isClicked && wasJustClicked) {
            onClick();
        }
    }

    // Method - Can be Overridden on Instantiations of Buttons for Extra Functionality //
    protected void additionalActions() {

    }

    // Method - Initialise Variables //
    private void initialise() {
        km = Handler.get().getKeyManager();
        mouseManager = Handler.get().getMouseManager();

        setButtonText();

        leftMouse = false;
        rightMouse = false;
        isHovered = false;
        wasJustClicked = false;
        isClicked = false;

        inactiveColour = fillColour;
        activeColour = new Color(46, 47, 120);
    }

// GETTERS & SETTERS //
    private void setButtonText() {
        setChildElement(new TextElement(text));
    }
    public void setInactiveColour(Color inactiveColour) {
        this.inactiveColour = inactiveColour;
    }
    public void setActiveColour(Color activeColour) {
        this.activeColour = activeColour;
    }
}
