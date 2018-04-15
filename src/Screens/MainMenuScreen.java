package Screens;
import Game.MouseManager;
import Game.Settings;
import UserInterface.Buttons.Button;
import UserInterface.Buttons.DummyButton;
import UserInterface.Buttons.QuitButton;
import MainMenu.MenuManager;
import UserInterface.Buttons.PlayButton;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

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

        // MAKE BUTTONS //

        // Button Names
        ArrayList<String> buttonNames = new ArrayList<>();
        buttonNames.add("PLAY");
        buttonNames.add("OPTIONS");
        buttonNames.add("QUIT");

        // Button Positioning Variables
        int buttonCount = buttonNames.size();
        int buttonSpacing = Settings.menu_button_spacing;
        int buttonIncrement = Button.getButtonHeight() + buttonSpacing;
        int topButtonY =
                (Settings.game_height / 2) - (
                    (
                        (Button.getButtonHeight() * buttonCount) +
                        (buttonSpacing * (buttonCount - 1))
                    ) / 2
                );

        // Button Creation
        for(int i = 0; i < buttonCount; i++) {
            String n = buttonNames.get(i);
            int x = Settings.game_width / 2;
            int y = topButtonY + (buttonIncrement * i);
            switch(n) {
                case "PLAY":
                    menuManager.addButton(new PlayButton(n,x,y));
                    break;
                case "QUIT":
                    menuManager.addButton(new QuitButton(n,x,y));
                    break;
                default:
                    menuManager.addButton(new DummyButton(n,x,y));
                    break;
            }
        }
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
        g.fillRect(0, 0, Settings.game_width, Settings.game_height);

        // Draw Menu Buttons
        menuManager.draw(g);
    }
}
