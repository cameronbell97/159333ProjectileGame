package Game.Screens;

import Game.Data.Settings;
import Game.Display.DisplayElements.*;
import Game.Display.UserInterface.TextManager;
import Game.Handler;

import java.awt.*;

public class OptionsScreen extends Screen {
// VARIABLES //
    // Statics //
    private static final int SPACE_BETWEEN_COLUMNS = 24;
    private static final int SPACE_BETWEEN_ROWS = 16;
    private static final int BORDER_WIDTH = 1;
    private static final int OUTER_PADDING = 24;

    private Screen returnScreen;

    Color backgroundColor;
    Color borderColor;
    Color fillColour;

    // Elements //
    PaddedElement mainElement;
    ButtonElement backButton;
    ButtonElement clearScoresButton;


// CONSTRUCTORS //
    public OptionsScreen(Screen returnScreen) {
        this.returnScreen = returnScreen;

        borderColor = new Color(129,130,174);
        fillColour = new Color(78, 78, 122, 58);
        backgroundColor = new Color(0, 0, 20);

        fillElements();
    }

    // METHODS //

    @Override
    public void update(int dt) {
        backButton.update();
        clearScoresButton.update();
    }
    @Override
    public void draw(Graphics g) {
        // Draw Background
        g.setColor(backgroundColor);
        g.fillRect(0, 0, Settings.game_width, Settings.game_height);

        // Draw Menu
        int xStart = (Settings.game_width / 2) - (mainElement.getWidth() / 2);
        int yStart = (Settings.game_height / 2) - (mainElement.getHeight() / 2);

        mainElement.draw(g, xStart, yStart);

        // Draw Back Button
        backButton.draw(g, xStart - backButton.getWidth() + 1, yStart);
    }

    private void fillElements() {
        mainElement = new PaddedElement(1, borderColor, fillColour, OUTER_PADDING);
        VerticalListElement titleWrapper = new VerticalListElement((int)(SPACE_BETWEEN_ROWS*1.5));
        VerticalListElement mainColumn = new VerticalListElement(SPACE_BETWEEN_ROWS);
        HorizontalListElement clearScoresRow = new HorizontalListElement(SPACE_BETWEEN_COLUMNS);

        // Add Elements
        TextElement cleared = new TextElement("CLEARED!");
        cleared.setVisible(false);
        clearScoresButton = new ButtonElement("CLEAR HIGH SCORES", BORDER_WIDTH, borderColor, fillColour, Settings.button_padding) {
            @Override
            protected void onClick() {
                Handler.get().getSave().clearScores();
                cleared.setVisible(true);
            }
        };
        clearScoresRow.addChild(clearScoresButton);
        clearScoresRow.addChild(cleared);
        clearScoresRow.setCenterAlign(true);
        mainColumn.addChild(clearScoresRow);

        titleWrapper.addChild(new TextElement("OPTIONS"));
        titleWrapper.addChild(mainColumn);
        titleWrapper.setCenterAlign(true);

        mainElement.setChildElement(titleWrapper);

        // Back Button
        backButton = new ButtonElement("<", BORDER_WIDTH, borderColor, fillColour, Settings.button_padding) {
            @Override
            protected void onClick() {
                ScreenManager.setScreen(returnScreen);
            }
        };
    }
}
