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

public abstract class ButtonElement extends Element {
// VARIABLES //
    // Statics //
    protected static final Color DEF_ACTIVE_COLOUR = new Color(46, 47, 120);

    // Managers //
    MouseManager mouseManager;

    // Data //
    protected int xpos, ypos; // x & y positions needed for mouse detection
    protected boolean leftMouse, rightMouse;
    protected boolean isHovered, wasJustClicked, isClicked;
    protected Element childElement;

    // Colours //
    protected Color inactiveColour;
    protected Color activeColour;

// CONSTRUCTORS //
    public ButtonElement() {
        super(0, 0);
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

            childElement.draw(g, xStart, yStart);
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
        mouseManager = Handler.get().getMouseManager();

        leftMouse = false;
        rightMouse = false;
        isHovered = false;
        wasJustClicked = false;
        isClicked = false;

        inactiveColour = fillColour;
        activeColour = DEF_ACTIVE_COLOUR;
    }

    // Setter Method - Set the Child Element //
    public void setChildElement(Element child) {
        // In case the child is set twice, we don't want the first child's width included
        if(childElement != null) {
            width -= childElement.getWidth();
            height -= childElement.getHeight();
        }

        childElement = child;
        width += child.getWidth();
        height += child.getHeight();
    }

// GETTERS & SETTERS //
    public void setInactiveColour(Color inactiveColour) {
        this.inactiveColour = inactiveColour;
    }
    public void setActiveColour(Color activeColour) {
        this.activeColour = activeColour;
    }
}
