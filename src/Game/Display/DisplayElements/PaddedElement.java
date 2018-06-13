package Game.Display.DisplayElements;

import java.awt.*;

/**
 * Cameron Bell - 19/04/2018
 * Padded Element Class
 * Element Class for Providing Basic Padding around One Element
 */
public class PaddedElement extends Element {
// VARIABLES //
    protected int padding; // Inner Padding of Element
    protected Element childElement;

// CONSTRUCTORS //
    public PaddedElement(int padding) {
        super((padding * 2), (padding * 2));
        this.padding = padding;
        childElement = null;
    }

    public PaddedElement(int width, int height, int padding) {
        super(width + (padding * 2), height + (padding * 2));
        this.padding = padding;
        childElement = null;
    }

    public PaddedElement(int borderWidth, int padding) {
        super((padding * 2), (padding * 2), borderWidth);
        this.padding = padding;
        childElement = null;
    }

    @Override
    public void update() {

    }

    // METHODS //
    // Method - For Drawing Element & Child Element //
    @Override
    public void draw(Graphics g, int xStart, int yStart) {
        // Draw Element Border & Fill
        super.draw(g, xStart, yStart);

        if(childElement != null) {
            childElement.draw(g, xStart + padding, yStart + padding);
        }
    }

// GETTERS & SETTERS //
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
}
