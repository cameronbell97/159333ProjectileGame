package Game.Screens;
import Game.Data.Settings;
import Game.Display.DisplayElements.ButtonElement;
import Game.Display.DisplayElements.PaddedElement;
import Game.Display.DisplayElements.VerticalListElement;

import java.awt.*;
import java.io.IOException;

/**
 * Cameron Bell - 26/03/2018
 * Main Menu Screen Class
 * The Main Menu Screen
 */

public class MainMenuScreen extends Screen{
// VARIABLES //
    private static final int SPACE_BETWEEN_ROWS = 8;
    private static final int BORDER_WIDTH = 1;
    private static final int OUTER_PADDING = 32;

    Color backgroundColor;
    Color borderColor;
    Color fillColour;

    // Elements
    PaddedElement mainElement;
    VerticalListElement buttonList;

// CONSTRUCTORS //
    public MainMenuScreen() throws IOException {
        super();

        borderColor = new Color(129,130,174);
        fillColour = new Color(78, 78, 122, 0);
        backgroundColor = new Color(0, 0, 20);

        fillElements();
    }

// METHODS //
    // Method - Update Buttons //
    @Override
    public void update(int dt) {
        buttonList.update();
    }

    // Method - Draw Elements of the Screen //
    @Override
    public void draw(Graphics g) {
        // Draw Background
        g.setColor(backgroundColor);
        g.fillRect(0, 0, Settings.game_width, Settings.game_height);

        // Draw Menu
        int xStart = (Settings.game_width / 2) - (mainElement.getWidth() / 2);
        int yStart = (Settings.game_height / 2) - (mainElement.getHeight() / 2);

        mainElement.draw(g, xStart, yStart);
    }

    // Method - Generate All The Main Menu Elements //
    private void fillElements() {
        mainElement = new PaddedElement(OUTER_PADDING);
        buttonList = new VerticalListElement(SPACE_BETWEEN_ROWS);

        buttonList.addChild(new ButtonElement("PLAY", BORDER_WIDTH, borderColor, fillColour, Settings.button_padding) {
            @Override
            protected void onClick() {
                try {
                    ScreenManager.setScreen(new GameScreen(ScreenManager.getScreen()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        buttonList.addChild(new ButtonElement("HIGH SCORES", BORDER_WIDTH, borderColor, fillColour, Settings.button_padding) {
            @Override
            protected void onClick() {
                ScreenManager.setScreen(new ScoresScreen(ScreenManager.getScreen(), ScreenManager.getScreen()));
            }
        });
        buttonList.addChild(new ButtonElement("OPTIONS", BORDER_WIDTH, borderColor, fillColour, Settings.button_padding) {
            @Override
            protected void onClick() {
                ScreenManager.setScreen(new OptionsScreen(ScreenManager.getScreen()));
            }
        });
        buttonList.addChild(new ButtonElement("QUIT", BORDER_WIDTH, borderColor, fillColour, Settings.button_padding) {
            @Override
            protected void onClick() {
                Game.Game.end();
            }
        });

        buttonList.setCenterAlign(true);

        mainElement.setChildElement(buttonList);
    }
}
