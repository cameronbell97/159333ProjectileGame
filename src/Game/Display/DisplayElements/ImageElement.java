package Game.Display.DisplayElements;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageElement extends Element {
// VARIABLES //
    // Data //
    BufferedImage image;

// CONSTRUCTORS //
    public ImageElement(BufferedImage image) {
        super(image.getWidth(), image.getHeight());
        this.image = image;
    }

// METHODS //
    @Override
    public void update() {

    }

// GETTERS & SETTERS //
    // Method - Empty Update Method as Nothing Needs Updating //
    public void setImage(BufferedImage image) {
        this.image = image;
        setWidth(image.getWidth());
        setHeight(image.getHeight());
    }

    // Method - For Drawing Element //
    @Override
    public void draw(Graphics g, int xStart, int yStart) {
        if(isVisible()) {
            g.drawImage(image, xStart, yStart, null);
        }
    }
}
