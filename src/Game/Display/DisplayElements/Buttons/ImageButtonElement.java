package Game.Display.DisplayElements.Buttons;

import Game.Display.DisplayElements.ButtonElement;
import Game.Display.DisplayElements.Element;
import Game.Display.DisplayElements.ImageElement;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class ImageButtonElement extends ButtonElement {
// CONSTRUCTORS //
    public ImageButtonElement(BufferedImage image) {
        super("");
        setChildElement(new ImageElement(image));
        setWidth(childElement.getWidth());
        setHeight(childElement.getHeight());
    }
    public ImageButtonElement(BufferedImage image, Color borderColour, Color fillColour) {
        super("");
        setChildElement(new ImageElement(image));
        setWidth(childElement.getWidth());
        setHeight(childElement.getHeight());
        this.borderColour = borderColour;

        inactiveColour = fillColour;
        activeColour = new Color(46, 47, 120);
    }

// METHODS //
    // Method - For Drawing Element //
    @Override
    public void draw(Graphics g, int xStart, int yStart) {
        if (isVisible()) {
            this.xpos = xStart;
            this.ypos = yStart;

            if (isHovered || isClicked) {
                fillColour = activeColour;
            } else fillColour = inactiveColour;

            // Draw Fill
            if (isVisible()) {
                // Draw Element Fill Colour
                g.setColor(fillColour);
                g.fillRect(xStart, yStart, width, height);

                // Draw Element Border
                g.setColor(borderColour);
                g.drawRect(xStart, yStart, width - 1, height - 1); // The -1 is because drawRect doesn't include  the bottom and right lines in the rectangle (draws them outside)
            }

            // Draw Image
            childElement.draw(g, xStart+1, yStart+1);

            // Draw Element Border
            g.setColor(borderColour);
            g.drawRect(xStart, yStart, width - 1, height - 1); // The -1 is because drawRect doesn't include  the bottom and right lines in the rectangle (draws them outside)
        }
    }
}
