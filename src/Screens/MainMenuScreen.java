package Screens;
import Game.MouseManager;
import Game.Settings;
import MainMenu.Button;
import MainMenu.MenuManager;

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

        // TODO // Add Buttons //
        ArrayList<String> buttonNames = new ArrayList<>();
        buttonNames.add("PLAY");
        buttonNames.add("OPTIONS");
        buttonNames.add("QUIT");

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

        for(int i = 0; i < buttonCount; i++) {
            menuManager.addButton(new Button(
                    buttonNames.get(i),
                    Settings.game_width / 2,
                    topButtonY + (buttonIncrement * i)
            ));
        }

//        ArrayList<Button> buttonList = new ArrayList<>();

//        buttonList.add(new Button(
//                "PLAY",
//                Settings.game_width/2,
//                topButtonY + (buttonIncrement * 0)
//        ));
//        buttonList.add(new Button(
//                "OPTIONS",
//                Settings.game_width/2,
//                topButtonY + (buttonIncrement * 1)
//        ));
//        buttonList.add(new Button(
//                "QUIT",
//                Settings.game_width/2,
//                topButtonY + (buttonIncrement * 2)
//        ));
//
//        menuManager.addButton(
//        );
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
