package Game.Display.DisplayElements;

import java.awt.*;

public class DummyElement extends PaddedElement {
// CONSTRUCTORS //
    public DummyElement(int width, int height) {
        super(width, height, 0);
    }

    public DummyElement(int width, int height, int padding) {
        super(width, height, padding);
    }

    public DummyElement(int width, int height, int borderWidth, Color borderColour, Color fillColour, int padding) {
        super(width, height, borderWidth, borderColour, fillColour, padding);
    }

    public DummyElement(int borderWidth, Color borderColour, Color fillColour, int padding) {
        super(borderWidth, borderColour, fillColour, padding);
    }

// METHODS //
    @Override
    public void update() {

    }
}
