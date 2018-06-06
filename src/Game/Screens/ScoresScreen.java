package Game.Screens;

import Game.Data.ScoreBoard;
import Game.Data.Settings;
import Game.Display.DisplayElements.*;
import Game.Handler;

import java.awt.*;

/**
 * Cameron Bell - 18/04/2018
 * Scores Screen Class
 * The Screen Displaying the High Scores
 */

public class ScoresScreen extends Screen {
// VARIABLES //
    // Screens
    private Screen returnScreen;
    private Screen drawScreen;

    // Data
    private ScoreBoard scoreBoard;
    private int space_between_columns = DEF_SPACE_BETWEEN_COLUMNS;
    private int space_between_rows = DEF_SPACE_BETWEEN_ROWS;
    private int border_width = DEF_BORDER_WIDTH;
    private int outer_padding = DEF_OUTER_PADDING;

    // Colors
    Color backgroundColor;
    Color borderColor;
    Color fillColour;

    // Elements
    PaddedElement mainElement;
    VerticalListElement titleWrapper;
    HorizontalListElement columnsContainer;
    VerticalListElement nameColumn;
    VerticalListElement scoreColumn;
    ButtonElement backButton;

// CONSTRUCTORS //
    public ScoresScreen(Screen returnScreen, Screen drawScreen) {
        this.returnScreen = returnScreen;
        this.drawScreen = drawScreen;
        scoreBoard =
                Handler
                .get()
                .getSave()
                .getScoreBoard();

        borderColor = new Color(129,130,174);
        fillColour = new Color(78, 78, 122, 58);
        backgroundColor = new Color(0, 0, 20);

        fillElements();
    }

// METHODS //
    // Method - Update Buttons //
    @Override
    public void update(int dt) {
        backButton.update();
    }

    // Method - Draw Elements of the Screen //
    @Override
    public void draw(Graphics g) {
        // Draw Background
        if(drawScreen instanceof GameScreen) drawScreen.draw(g);
        else {
            g.setColor(backgroundColor);
            g.fillRect(0, 0, Settings.game_width, Settings.game_height);
        }

        // Draw Menu
        int xStart = (Settings.game_width / 2) - (mainElement.getWidth() / 2);
        int yStart = (Settings.game_height / 2) - (mainElement.getHeight() / 2);

        mainElement.draw(g, xStart, yStart);

        // Draw Back Button
        backButton.draw(g, xStart - backButton.getWidth() + 1, yStart);
    }

    // Method - Generate All The Main Menu Elements //
    private void fillElements() {
        mainElement = new PaddedElement(1, borderColor, fillColour, outer_padding);
        titleWrapper = new VerticalListElement((int)(space_between_rows *1.5));
        columnsContainer = new HorizontalListElement(space_between_columns);

        nameColumn = new VerticalListElement(space_between_rows);
        scoreColumn = new VerticalListElement(space_between_rows);

        // Add Names
        for(String name : scoreBoard.getScoreNames()) {
            nameColumn.addChild(new TextElement(name));
        }

        // Add Scores
        for(int score : scoreBoard.getScores()) {
            scoreColumn.addChild(new TextElement(Integer.toString(score)));
        }

        // Fill Elements
        columnsContainer.addChild(nameColumn);
        columnsContainer.addChild(scoreColumn);

        titleWrapper.addChild(new TextElement("HIGH SCORES"));
        titleWrapper.addChild(columnsContainer);
        titleWrapper.setCenterAlign(true);

        mainElement.setChildElement(titleWrapper);

        // Back Button
        backButton = new ButtonElement("<", border_width, borderColor, fillColour, Settings.button_padding) {
            @Override
            protected void onClick() {

                ScreenManager.setScreen(returnScreen);
                if(drawScreen instanceof GameScreen) {
                    handler.setUIHidden();
                }
            }
        };
//        backButton.setInactiveColour(fillColour);
    }
}
