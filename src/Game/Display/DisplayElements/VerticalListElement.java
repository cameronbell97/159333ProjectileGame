package Game.Display.DisplayElements;

import java.awt.*;
import java.util.ArrayList;

/**
 * Cameron Bell - 19/04/2018
 * Vertical List Element Class
 * Element Class for Holding and Managing a List of Elements
 */
public class VerticalListElement extends Element {
// VARIABLES //
    private ArrayList<Element> children;
    private int distanceBetweenElements;
    private boolean centerAlign;

// CONSTRUCTORS //
    public VerticalListElement(int distanceApart) {
        super(0, 0);
        children = new ArrayList<>();
        distanceBetweenElements = distanceApart;
        centerAlign = false;
    }

// METHODS //
    // Method - Update Child Elements //
    @Override
    public void update() {
        for(Element e : children) {
            e.update();
        }
    }

    // Method - For Drawing Element & Child Elements //
    @Override
    public void draw(Graphics g, int xStart, int yStart) {
        int xPencil = xStart;
        int yPencil = yStart;

        for(Element e : children) {
            if(centerAlign) xPencil = xStart + (width / 2 - e.getWidth() / 2);
            e.draw(g, xPencil, yPencil);
            yPencil += e.getHeight() + distanceBetweenElements;
        }

    }

    // Method - Add a Child Element //
    public void addChild(Element child) {
        if(child != null && children != null) {
            children.add(child);
        }

        width = calculateWidth();
        height = calculateHeight();
    }

    // Method - Calculate the Width in Pixels of the Element Based on the Maximum Width of the Child Elements //
    private int calculateWidth() {
        int newWidth = 0;

        for(Element e : children) {
            if(e.getWidth() > newWidth) newWidth = e.getWidth();
        }

        return newWidth;
    }

    // Method - Calculate the Height in Pixels of the Element, Based on //
    //          the Combined Heights (+ Spacing) of the Child Elements. //
    private int calculateHeight() {
        int newHeight = 0;

        for(Element e : children) {
            newHeight += e.getHeight();
            newHeight += distanceBetweenElements;
        }

        if(newHeight != 0) newHeight -= distanceBetweenElements;

        return newHeight;
    }

// GETTERS & SETTERS //
    public void setCenterAlign(boolean centerAlign) {
        this.centerAlign = centerAlign;
    }
}
