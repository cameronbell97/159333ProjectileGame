package Game.Display.UserInterface;

import Game.Handler;

import java.awt.*;

/**
 * Cameron Bell - 06/06/2018
 * Tutorial UI Manager Class
 * Displays the Tutorial UI
 */

public class TutorialUIManager {
// VARIABLES //
    // Managers //
    Handler handler;
    TextManager textManager;

// CONSTRUCTORS //
    public TutorialUIManager() {
        handler = Handler.get();
        textManager = new TextManager();
    }

// METHODS //
    public void update() {

    }

    public void draw(Graphics g) {

    }
}
