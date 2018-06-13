package Game.Screens;

import Game.Data.Settings;
import Game.Display.DisplayElements.*;

import java.awt.*;
import java.io.IOException;

/**
 * Cameron Bell - 06/06/2018
 * Pause Menu Screen Class
 * The Pause Menu Screen
 */

public class PauseMenuScreen extends Screen {
// VARIABLES //
    // Statics //
    private static final int SPACE_BETWEEN_ROWS = 8;

    // Data //
    private int border_width = DEF_BORDER_WIDTH;
    private int outer_padding = DEF_OUTER_PADDING;

    // Screens //
    private Screen returnScreen;

    // Elements //
    private PaddedElement mainElement;
    private VerticalListElement titleWrapper;
    private VerticalListElement menuList;

    // Buttons //
    private ButtonElement resumeButton;
    private ButtonElement mainMenuButton;

// CONSTRUCTORS //
    public PauseMenuScreen(Screen returnScreen) {
        this.returnScreen = returnScreen;

        fillElements();
    }

// METHODS //
    // Method - Update The Buttons on the Screen //
    @Override
    public void update(int dt) {
        resumeButton.update();
        mainMenuButton.update();
    }

    // Method - Draw The Contents of the Screen //
    @Override
    public void draw(Graphics g) {
        // Draw Background
        if(returnScreen != null) returnScreen.draw(g);

        // Draw Menu
        int xStart = (Settings.game_width / 2) - (mainElement.getWidth() / 2);
        int yStart = (Settings.game_height / 2) - (mainElement.getHeight() / 2);

        mainElement.draw(g, xStart, yStart);
    }

    // Method - Generate All The Menu Elements //
    private void fillElements() {
        // Create Encompassing Elements
        mainElement = new PaddedElement(1, outer_padding);
        titleWrapper = new VerticalListElement((int)(outer_padding));
        menuList = new VerticalListElement(SPACE_BETWEEN_ROWS);

        // Create Buttons
        resumeButton = new TextButtonElement("RESUME", border_width, Settings.button_padding) {
            @Override
            protected void onClick() {
                ScreenManager.setScreen(returnScreen);
            }
        };
        mainMenuButton = new TextButtonElement("EXIT", border_width, Settings.button_padding) {
            @Override
            protected void onClick() {
                try {
                    ScreenManager.setScreen(new MainMenuScreen());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        // Build Element Hierarchy
        menuList.addChild(resumeButton);
        menuList.addChild(mainMenuButton);
        titleWrapper.addChild(new TextElement("PAUSE MENU"));
        titleWrapper.addChild(menuList);
        mainElement.setChildElement(titleWrapper);

        // Center Align Lists
        titleWrapper.setCenterAlign(true);
        menuList.setCenterAlign(true);
    }
}
