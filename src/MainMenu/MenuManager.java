package MainMenu;

import Game.MouseManager;
import Game.TextManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by Cameron on 13/04/2018.
 */
public class MenuManager {
// VARIABLES //
    ArrayList<Button> buttons;

// CONSTRUCTORS //
    public MenuManager() {
        buttons = new ArrayList<>();

        // TODO // Add Buttons
    }

// METHODS //
    public void update() {
        // Update Buttons
        for(Button b : buttons) {
            b.update();
        }
    }

    public void draw(Graphics g) {
        // Draw Buttons
        for(Button b : buttons) {
            b.draw(g);
        }
    }
}
