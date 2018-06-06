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
    // Statics //
    protected static final int DEF_SPACE_BETWEEN_COLUMNS = 24;
    protected static final int DEF_SPACE_BETWEEN_ROWS = 16;
    protected static final int DEF_BORDER_WIDTH = 1;
    protected static final int DEF_OUTER_PADDING = 24;


    protected Handler handler;

// CONSTRUCTORS //
    public Screen() {
        handler = Handler.get();
    }

// METHODS //
    public abstract void update(int dt);
    public abstract void draw(Graphics g);
}
