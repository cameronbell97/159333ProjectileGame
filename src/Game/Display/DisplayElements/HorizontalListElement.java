package Game.Display.DisplayElements;

import java.awt.*;
import java.util.ArrayList;

public class HorizontalListElement extends Element {
// VARIABLES //
    private ArrayList<Element> children;
    private int distanceBetweenElements;
    private boolean centerAlign;

// CONSTRUCTORS //
    public HorizontalListElement(int distanceApart) {
        super(0, 0);
        children = new ArrayList<>();
        distanceBetweenElements = distanceApart;
        centerAlign = false;
    }

// METHODS //
    @Override
    public void update() {
        for(Element e : children) {
            e.update();
        }
    }

    @Override
    public void draw(Graphics g, int xStart, int yStart) {
        int xPencil = xStart;
        int yPencil = yStart;

        for(Element e : children) {
            if(centerAlign) yPencil = yStart + (height / 2 - e.getHeight() / 2);
            e.draw(g, xPencil, yPencil);
            xPencil += e.getWidth() + distanceBetweenElements;
        }

    }

    public void addChild(Element child) {
        if(child != null && children != null) {
            children.add(child);
        }

        width = calculateWidth();
        height = calculateHeight();
    }

    private int calculateWidth() {
        int newWidth = 0;

        for(Element e : children) {
            newWidth += e.getWidth();
            newWidth += distanceBetweenElements;
        }

        if(newWidth != 0) newWidth -= distanceBetweenElements;

        return newWidth;
    }

    private int calculateHeight() {
        int newHeight = 0;

        for(Element e : children) {
            if(e.getHeight() > newHeight) newHeight = e.getHeight();
        }

        return newHeight;
    }

// GETTERS & SETTERS //
    public void setCenterAlign(boolean centerAlign) {
        this.centerAlign = centerAlign;
    }
}
