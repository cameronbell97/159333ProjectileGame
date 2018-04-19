package Game.Display.DisplayElements;

import java.awt.*;

public abstract class Element {
// VARIABLES //
    protected int width; // Width of Element
    protected int height; // Height of Element
    protected int borderWidth; // Width of Element Border (Grows inwards into element)
    protected Color borderColour;
    protected Color fillColour;

// CONSTRUCTORS //
    public Element(int width, int height) {
        this.width = width;
        this.height = height;

        this.borderWidth = 0;
        Color transparent = new Color(0, 0, 0, 0);
        this.borderColour = transparent;
        this.fillColour = transparent;
    }

    public Element(int width, int height, int borderWidth, Color borderColour, Color fillColour) {
        this.width = width;
        this.height = height;
        this.borderWidth = borderWidth;
        this.borderColour = borderColour;
        this.fillColour = fillColour;
    }

// METHODS //
    public abstract void update();
    public void draw(Graphics g, int xStart, int yStart) {
        // Draw Element Fill Colour
        g.setColor(fillColour);
        g.fillRect(xStart, yStart, width, height);

        // Draw Element Border
        g.setColor(borderColour);
        g.drawRect(xStart, yStart, width-1, height-1); // The -1 is because drawRect doesn't include  the bottom and right lines in the rectangle (draws them outside)
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
}
