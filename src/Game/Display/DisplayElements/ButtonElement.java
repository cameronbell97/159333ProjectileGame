package Game.Display.DisplayElements;

import Game.Data.MouseManager;
import Game.Data.Settings;
import Game.Screens.Screen;
import Game.Screens.ScreenManager;

import java.awt.*;

public abstract class ButtonElement extends PaddedElement {
// VARIABLES //
    MouseManager mouseManager;
    private int xpos, ypos; // x & y positions needed for mouse detection
    private boolean leftMouse, rightMouse;
    private boolean isHovered, wasJustClicked, isClicked;
    private String text;
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
    protected abstract void onClick();

    @Override
    public void draw(Graphics g, int xStart, int yStart) {
        this.xpos = xStart;
        this.ypos = yStart;

        if(isHovered || isClicked) {
            fillColour = activeColour;
        } else fillColour = inactiveColour;

        super.draw(g, xStart, yStart);

        // Draw Element Border
        g.setColor(borderColour);
        g.drawRect(xStart, yStart, width-1, height-1); // The -1 is because drawRect doesn't include  the bottom and right lines in the rectangle (draws them outside)
    }

    @Override
    public void update() {
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

    private void initialise() {
        setButtonText();

        mouseManager = MouseManager.get();

        leftMouse = false;
        rightMouse = false;
        isHovered = false;
        wasJustClicked = false;
        isClicked = false;

        inactiveColour = fillColour;
        activeColour = new Color(46, 47, 120);
    }

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
