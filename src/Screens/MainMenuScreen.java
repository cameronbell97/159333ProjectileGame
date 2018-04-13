package Screens;
import Game.Launcher;
import MainMenu.MenuManager;

import java.awt.*;
import java.io.IOException;

/**
 * Cameron Bell - 26/03/2018
 * Main Menu Screen Class
 */

public class MainMenuScreen extends Screen{
// VARIABLES //
    MenuManager menuManager;

// CONSTRUCTORS //
    public MainMenuScreen() throws IOException {
        super();
        menuManager = new MenuManager();
    }

// METHODS //
    @Override
    public void update() {
        menuManager.update();
    }

    @Override
    public void draw(Graphics g) {
        // Draw Background
        g.setColor(new Color(0, 0, 20));
        g.fillRect(0, 0, Launcher.DEF_GAME_WIDTH, Launcher.DEF_GAME_HEIGHT);

        // Draw Menu Buttons
        menuManager.draw(g);
    }
}
