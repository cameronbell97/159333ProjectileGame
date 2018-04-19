package Game.Display.DisplayElements.Buttons;

import Game.Display.DisplayElements.ButtonElement;
import Game.Screens.Screen;
import Game.Screens.ScreenManager;

import java.awt.*;

public class BackButton extends ButtonElement {
// VARIABLES //
    private Screen previousScreen;

// CONSTRUCTORS //
    public BackButton(String text, Screen previousScreen) {
        super(text);
        this.previousScreen = previousScreen;
    }

    public BackButton(String text, int padding, Screen previousScreen) {
        super(text, padding);
        this.previousScreen = previousScreen;
    }

    public BackButton(String text, int borderWidth, Color borderColour, Color fillColour, int padding, Screen previousScreen) {
        super(text, borderWidth, borderColour, fillColour, padding);
        this.previousScreen = previousScreen;
    }

// METHODS //
    @Override
    protected void onClick() {
        ScreenManager.setScreen(previousScreen);
    }
}
