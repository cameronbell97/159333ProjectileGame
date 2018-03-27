package Screens;

import Assets.ImageLoader;
import Assets.SpriteSheet;

import java.awt.*;
import java.io.IOException;

/**
 * Cameron Bell - 26/03/2018
 * Main Menu Screens.Screen Class
 */

public class MainMenuScreen extends Screen{
// VARIABLES //

// CONSTRUCTORS //
    public MainMenuScreen() throws IOException {
    }

// METHODS //
    @Override
    public void update() {
        // Sets game screen to a game screen immediately,
        // will change later when there's things to put on the main menu.
        try {
            ScreenManager.setScreen(new GameScreen());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void draw(Graphics g) {

    }
}
