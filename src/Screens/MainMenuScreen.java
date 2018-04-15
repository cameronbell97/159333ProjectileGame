package Screens;
import Game.Launcher;
import Game.MouseManager;
import Game.Settings;
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
    MouseManager mouseManager;

// CONSTRUCTORS //
    public MainMenuScreen() throws IOException {
        super();
        menuManager = new MenuManager();
        mouseManager = MouseManager.get();
    }

// METHODS //
    @Override
    public void update() {
        menuManager.update();
        if (mouseManager.checkLeftMouse() && mouseManager.checkRightMouse()) try {
            ScreenManager.setScreen(new GameScreen());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics g) {
        // Draw Background
        g.setColor(new Color(0, 0, 20));
        g.fillRect(0, 0, Settings.game_width, Settings.game_height);

        // Draw Menu Buttons
        menuManager.draw(g);
    }
}
