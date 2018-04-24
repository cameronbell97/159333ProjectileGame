package Game.Screens;

import Game.Data.SaveManager;
import Game.Data.ScoreBoard;
import Game.Data.Settings;
import Game.Display.DisplayElements.*;

import java.awt.*;

public class ScoresScreen extends Screen {
// VARIABLES //
    // Statics //
    private static final int SPACE_BETWEEN_COLUMNS = 24;
    private static final int SPACE_BETWEEN_ROWS = 16;
    private static final int BORDER_WIDTH = 1;
    private static final int OUTER_PADDING = 24;

    private ScoreBoard scoreBoard;
    private Screen returnScreen;
    private Screen drawScreen;

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

    private void fillElements() {
        mainElement = new PaddedElement(1, borderColor, fillColour, OUTER_PADDING);
        titleWrapper = new VerticalListElement((int)(SPACE_BETWEEN_ROWS*1.5));
        columnsContainer = new HorizontalListElement(SPACE_BETWEEN_COLUMNS);

        nameColumn = new VerticalListElement(SPACE_BETWEEN_ROWS);
        scoreColumn = new VerticalListElement(SPACE_BETWEEN_ROWS);

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
        backButton = new ButtonElement("<", BORDER_WIDTH, borderColor, fillColour, Settings.button_padding) {
            @Override
            protected void onClick() {
                ScreenManager.setScreen(returnScreen);
            }
        };
//        backButton.setInactiveColour(fillColour);
    }
}
