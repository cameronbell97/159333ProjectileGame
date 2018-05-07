package Game.Screens;

import Game.Handler;

import java.awt.*;

/**
 * Cameron Bell - 26/03/2018
 * Game.Screens.Screen Class
 * Abstract class defining the basics for a game screen
 */

public abstract class Screen {
// VARIABLES //
    protected Handler handler;

// CONSTRUCTORS //
    public Screen() {

    }

// METHODS //
    public abstract void update();
    public abstract void draw(Graphics g);
}
