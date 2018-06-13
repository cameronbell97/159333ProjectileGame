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
        width += borderWidth * 2;
        setHeight(childElement.getHeight());
        height += borderWidth * 2;
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

            // Draw Fill & Border
            if (isVisible()) {
                // Draw Element Fill Colour
                g.setColor(fillColour);
                g.fillRect(xStart, yStart, width, height);

                // Draw Element Border
                g.setColor(borderColour);
                /* For the following command,
                 * -1 to width & height because drawRect doesn't include the bottom and right lines in the rectangle (draws them outside)
                 * +2 to height to draw border outside the image border
                 */
                g.drawRect(xStart, yStart, width + (borderWidth * 2) - 1, height + (borderWidth * 2));
            }

            // Draw Image
            childElement.draw(g, xStart + borderWidth, yStart + borderWidth);
        }
    }
}
