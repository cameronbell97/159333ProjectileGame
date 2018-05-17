package Game.Display.DisplayElements.Buttons;

import Game.Display.DisplayElements.ButtonElement;
import Game.Screens.Screen;
import Game.Screens.ScreenManager;

import java.awt.*;

/**
 * Cameron Bell - 19/04/2018
 * Back Button Element Class
 * Button Element Class for Returning to Previous Screen
 */
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
    // Method - On Button Click, Return to Previous Screen //
    @Override
    protected void onClick() {
        ScreenManager.setScreen(previousScreen);
    }
}
