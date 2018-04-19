package Game.Display.DisplayElements;

import java.awt.*;

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

    public PaddedElement(int width, int height, int borderWidth, Color borderColour, Color fillColour, int padding) {
        super(width + (padding * 2), height + (padding * 2), borderWidth, borderColour, fillColour);
        this.padding = padding;
        childElement = null;
    }

    public PaddedElement(int borderWidth, Color borderColour, Color fillColour, int padding) {
        super((padding * 2), (padding * 2), borderWidth, borderColour, fillColour);
        this.padding = padding;
        childElement = null;
    }

    @Override
    public void update() {

    }

    // METHODS //
    @Override
    public void draw(Graphics g, int xStart, int yStart) {
        // Draw Element Border & Fill
        super.draw(g, xStart, yStart);
        if(childElement != null) {
            childElement.draw(g, xStart + padding, yStart + padding);
        }
    }

// GETTERS & SETTERS //
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
