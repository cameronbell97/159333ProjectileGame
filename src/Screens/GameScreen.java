package Screens;

import java.awt.*;
import java.io.IOException;

/**
 * Cameron Bell - 26/03/2018
 * Game Screens.Screen Class
 */

public class GameScreen extends Screen {
// VARIABLES //
    private AssetManager assMan;

// CONSTRUCTORS //
    public GameScreen() throws IOException {
        assMan = AssetManager.get();
    }

// METHODS //
    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g) {

        g.drawImage(assMan.getSprite("player"), 64, 64, null);
    }
}
