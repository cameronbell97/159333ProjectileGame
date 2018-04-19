package Game.Display.DisplayElements;

import java.awt.*;
import java.util.ArrayList;

public class VerticalListElement extends Element {
// VARIABLES //
    private ArrayList<Element> children;
    private int distanceBetweenElements;

// CONSTRUCTORS //
    public VerticalListElement(int distanceApart) {
        super(0, 0);
        children = new ArrayList<>();
        distanceBetweenElements = distanceApart;
    }

// METHODS //
    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g, int xStart, int yStart) {
        int xPencil = xStart;
        int yPencil = yStart;

        for(Element e : children) {
            e.draw(g, xPencil, yPencil);
            yPencil += e.getHeight() + distanceBetweenElements;
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
            if(e.getWidth() > newWidth) newWidth = e.getWidth();
        }

        return newWidth;
    }

    private int calculateHeight() {
        int newHeight = 0;

        for(Element e : children) {
            newHeight += e.getHeight();
            newHeight += distanceBetweenElements;
        }

        if(newHeight != 0) newHeight -= distanceBetweenElements;

        return newHeight;
    }
}
