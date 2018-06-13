package Game.Display.DisplayElements;

import java.awt.*;

public abstract class TextButtonElement extends ButtonElement {
// VARIABLES //
    // Data //
    protected String text;
    private int padding;
    private int borderWidth;

// CONSTRUCTORS //
    public TextButtonElement(String text) {
        initialise(text);
        this.borderWidth = 0;
        this.padding = 0;
    }

    public TextButtonElement(String text, int padding) {
        super();
        this.borderWidth = 0;
        this.padding = padding;
        initialise(text);
    }

    public TextButtonElement(String text, int borderWidth, int padding) {
        super();
        this.borderWidth = borderWidth;
        this.padding = padding;
        initialise(text);
    }

// METHODS //
    private void initialise(String text) {
        this.text = text;
        setButtonText();
    }

    private void setButtonText() {
        PaddedElement paddedElement = new PaddedElement(borderWidth, padding);
        paddedElement.setChildElement(new TextElement(text));
        setChildElement(paddedElement);
    }
}
