package MainMenu;

import Game.Settings;
import UserInterface.Buttons.Button;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Cameron on 13/04/2018.
 */
public class MenuManager {
// VARIABLES //
    private ArrayList<UserInterface.Buttons.Button> buttons;
    private int screenMiddleX, screenMiddleY;

// CONSTRUCTORS //
    public MenuManager() {
        buttons = new ArrayList<>();
        updateVariables();
    }

// METHODS //
    public void update() {
        updateVariables();

        // Update Buttons
        for(UserInterface.Buttons.Button b : buttons) {
            b.update();
        }
    }

    public void draw(Graphics g) {
        // Draw Buttons
        for(UserInterface.Buttons.Button b : buttons) {
            b.draw(g);
        }
    }

    private void updateVariables() {
        // Calculate Middle of the screen
        screenMiddleX = Settings.game_width / 2;
        screenMiddleY = Settings.game_height / 2;
    }

    public void addButton(Button b) {
        buttons.add(b);
    }
}
