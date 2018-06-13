package Game.Display.DisplayElements;

import java.awt.*;

/**
 * Cameron Bell - 19/04/2018
 * Element Class
 * Abstract Class for Display Elements
 */
public abstract class Element {
// VARIABLES //
    // Statics //
    protected static final Color DEF_BORDER_COLOUR = new Color(129,130,174);
    protected static final Color DEF_FILL_COLOUR = new Color(78, 78, 122, 58);
    protected static final Color TRANSPARENT = new Color(0, 0, 0, 0);

    // Data //
    protected int width; // Width of Element
    protected int height; // Height of Element
    protected int borderWidth; // Width of Element Border (Grows inwards into element)
    private boolean visible;

    // Colours //
    protected Color borderColour;
    protected Color fillColour;

// CONSTRUCTORS //
    // Default Constructor //
    public Element(int width, int height) {
        this.visible = true;

        this.width = width;
        this.height = height;

        this.borderWidth = 0;
        this.borderColour = DEF_BORDER_COLOUR;
        this.fillColour = DEF_FILL_COLOUR;
    }

    // Constructor Overload - For Exact Dimensions //
    public Element(int width, int height, int borderWidth) {
        this.visible = true;

        this.width = width;
        this.height = height;

        this.borderWidth = borderWidth;
        this.borderColour = DEF_BORDER_COLOUR;
        this.fillColour = DEF_FILL_COLOUR;
    }

    // Constructor Overload - For Exact Dimensions //
    public Element(int width, int height, int borderWidth, Color borderColour, Color fillColour) {
        this.visible = true;

        this.width = width;
        this.height = height;

        this.borderWidth = borderWidth;
        this.borderColour = borderColour;
        this.fillColour = fillColour;
    }

// METHODS //
    // Abstract Method - For Updating Data //
    public abstract void update();

    // Method - Basic Drawing of Border and Fill //
    public void draw(Graphics g, int xStart, int yStart) {
        if (visible) {
            // Draw Element Fill Colour
            g.setColor(fillColour);
            g.fillRect(xStart, yStart, width, height);

            // Draw Element Border
            g.setColor(borderColour);
            g.drawRect(xStart, yStart, width - 1, height - 1); // The -1 is because drawRect doesn't include  the bottom and right lines in the rectangle (draws them outside)
        }
    }

// GETTERS & SETTERS //
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    protected void setWidth(int width) {
        this.width = width;
    }
    protected void setHeight(int height) {
        this.height = height;
    }
    public boolean isVisible() {
        return visible;
    }
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
