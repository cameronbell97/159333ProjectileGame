package Screens;
import Game.Game;

import java.awt.*;

/**
 * Cameron Bell - 26/03/2018
 * Screens.Screen Class
 * Abstract class defining the basics for a game screen
 */
public abstract class Screen {
// VARIABLES //
    protected Game game;

// CONSTRUCTORS //
    public Screen(Game game) {
        this.game = game;
    }

// METHODS //
    public abstract void update();
    public abstract void draw(Graphics g);
}
