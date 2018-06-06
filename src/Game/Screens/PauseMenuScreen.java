package Game.Screens;

import Game.Data.Settings;
import Game.Display.DisplayElements.*;

import java.awt.*;
import java.io.IOException;

public class PauseMenuScreen extends Screen {
// VARIABLES //
    // Statics
    private static final int SPACE_BETWEEN_ROWS = 8;
    private static final int BORDER_WIDTH = 1;
    private static final int OUTER_PADDING = 24;

    // Screens
    private Screen returnScreen;

    // Colours
    private Color borderColor;
    private Color fillColour;

    // Elements
    private PaddedElement mainElement;
    private VerticalListElement titleWrapper;
    private VerticalListElement menuList;

    // Buttons
    private ButtonElement resumeButton;
    private ButtonElement mainMenuButton;

// CONSTRUCTORS //
    public PauseMenuScreen(Screen returnScreen) {
        this.returnScreen = returnScreen;

        borderColor = new Color(129,130,174);
        fillColour = new Color(78, 78, 122, 58);

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
        mainElement = new PaddedElement(1, borderColor, fillColour, OUTER_PADDING);
        titleWrapper = new VerticalListElement((int)(OUTER_PADDING));
        menuList = new VerticalListElement(SPACE_BETWEEN_ROWS);

        // Create Buttons
        resumeButton = new ButtonElement("RESUME", BORDER_WIDTH, borderColor, fillColour, Settings.button_padding) {
            @Override
            protected void onClick() {
                ScreenManager.setScreen(returnScreen);
            }
        };
        mainMenuButton = new ButtonElement("EXIT", BORDER_WIDTH, borderColor, fillColour, Settings.button_padding) {
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
