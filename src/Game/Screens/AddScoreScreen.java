package Game.Screens;

import Game.Data.SaveManager;
import Game.Data.Settings;
import Game.Display.DisplayElements.*;

import java.awt.*;

public class AddScoreScreen extends Screen {
// VARIABLES //
    // Statics
    private static final int SPACE_BETWEEN_COLUMNS = 24;
    private static final int SPACE_BETWEEN_ROWS = 16;
    private static final int BORDER_WIDTH = 1;
    private static final int OUTER_PADDING = 32;

    // Screens
    private Screen returnScreen;
    private Screen drawScreen;

    // Colours
    Color backgroundColor;
    Color borderColor;
    Color fillColour;

    // Elements
    PaddedElement mainElement;
    HorizontalListElement columnsContainer;
    VerticalListElement nameColumn;
    VerticalListElement scoreColumn;
    ButtonElement submitButton;

    private int score;

// CONSTRUCTORS //
    public AddScoreScreen(Screen returnScreen, Screen drawScreen, int score) {
        this.returnScreen = returnScreen;
        this.drawScreen = drawScreen;
        this.score = score;

        borderColor = new Color(129,130,174);
        fillColour = new Color(78, 78, 122, 58);
        backgroundColor = new Color(0, 0, 20);

        fillElements();

    }

// METHODS //
    @Override
    public void update() {
        submitButton.update();
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
    }

    private void fillElements() {
        mainElement = new PaddedElement(1, borderColor, fillColour, OUTER_PADDING);
        columnsContainer = new HorizontalListElement(SPACE_BETWEEN_COLUMNS);

        nameColumn = new VerticalListElement(SPACE_BETWEEN_ROWS);
        scoreColumn = new VerticalListElement(SPACE_BETWEEN_ROWS);

        // Add Text Elements
        nameColumn.addChild(new TextElement("NAME"));
        scoreColumn.addChild(new TextElement("SCORE"));
        scoreColumn.addChild(new TextElement(Integer.toString(score)));

        nameColumn.addChild(new TextElement("---"));

        submitButton = new ButtonElement("SUBMIT") {
            @Override
            protected void onClick() {
                SaveManager sm = SaveManager.get();
                sm.getSave().getScoreBoard().addNewScore(score, "");
                sm.getSave().save();
                ScreenManager.setScreen(new ScoresScreen(returnScreen, drawScreen));
            }
        };

        // Compile Columns into Row
        columnsContainer.addChild(nameColumn);
        columnsContainer.addChild(scoreColumn);
        columnsContainer.addChild(submitButton);

        // Put Into Main
        mainElement.setChildElement(columnsContainer);
    }
}
