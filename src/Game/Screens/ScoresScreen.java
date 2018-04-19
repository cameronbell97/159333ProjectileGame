package Game.Screens;

import Game.Data.SaveManager;
import Game.Data.ScoreBoard;
import Game.Data.Settings;
import Game.Display.DisplayElements.Buttons.BackButton;
import Game.Display.DisplayElements.HorizontalListElement;
import Game.Display.DisplayElements.PaddedElement;
import Game.Display.DisplayElements.TextElement;
import Game.Display.DisplayElements.VerticalListElement;
import Game.Display.UserInterface.GameUIManager;
import Game.Display.UserInterface.TextManager;

import java.awt.*;
import java.util.ArrayList;

public class ScoresScreen extends Screen {
// VARIABLES //
    // Statics //
    private static final int SPACE_BETWEEN_COLUMNS = 24;
    private static final int SPACE_BETWEEN_ROWS = 16;
    private static final int BORDER_WIDTH = 1;
    private static final int OUTER_PADDING = 32;

    private ScoreBoard scoreBoard;
    private Screen lastScreen;

    Color backgroundColor;
    Color borderColor;
    Color fillColour;

    // Elements
    PaddedElement mainElement;
    HorizontalListElement columnsContainer;
    VerticalListElement nameColumn;
    VerticalListElement scoreColumn;
    BackButton backButton;

// CONSTRUCTORS //
    public ScoresScreen(Screen lastScreen) {
        this.lastScreen = lastScreen;
        scoreBoard =
                SaveManager
                .get()
                .getSave()
                .getScoreBoard();

        borderColor = new Color(129,130,174);
        fillColour = new Color(78, 78, 122, 58);
        backgroundColor = new Color(0, 0, 20);

        fillElements();
    }

// METHODS //
    @Override
    public void update() {
        backButton.update();
    }

    @Override
    public void draw(Graphics g) {
        // Draw Background
        if(lastScreen instanceof GameScreen) lastScreen.draw(g);
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

    private void fillElements() {
        mainElement = new PaddedElement(1, borderColor, fillColour, OUTER_PADDING);
        columnsContainer = new HorizontalListElement(SPACE_BETWEEN_COLUMNS);

        nameColumn = new VerticalListElement(SPACE_BETWEEN_ROWS);
        scoreColumn = new VerticalListElement(SPACE_BETWEEN_ROWS);

        // Add Names
        for(int i : new int[]{0,1,2,3,4,5,6,7,8,9}) {
            nameColumn.addChild(new TextElement("AHH"));
        }

        // Add Scores
        for(int score : scoreBoard.getScores()) {
            scoreColumn.addChild(new TextElement(Integer.toString(score)));
        }

        // Fill Elements
        columnsContainer.addChild(nameColumn);
        columnsContainer.addChild(scoreColumn);
        mainElement.setChildElement(columnsContainer);

        // Back Button
        backButton = new BackButton("B", BORDER_WIDTH, borderColor, fillColour, Settings.button_padding, lastScreen);
        backButton.setInactiveColour(fillColour);
    }
}
