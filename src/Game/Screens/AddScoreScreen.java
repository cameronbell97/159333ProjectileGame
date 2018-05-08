package Game.Screens;

import Game.Data.KeyManager;
import Game.Data.SaveManager;
import Game.Data.ScoreBoard;
import Game.Data.Settings;
import Game.Display.DisplayElements.*;

import java.awt.*;

public class AddScoreScreen extends Screen {
// VARIABLES //
    // Statics
    private static final int SPACE_BETWEEN_COLUMNS = 24;
    private static final int SPACE_BETWEEN_ROWS = 16;
    private static final int BORDER_WIDTH = 1;
    private static final int OUTER_PADDING = 24;

    // Screens
    private Screen returnScreen;
    private Screen drawScreen;

    // Colours
    Color backgroundColor;
    Color borderColor;
    Color fillColour;

    // Elements
    PaddedElement mainElement;
    VerticalListElement titleWrapper;
    HorizontalListElement columnsContainer;
    VerticalListElement nameColumn;
    VerticalListElement scoreColumn;
    ButtonElement submitButton;
    TypeTextElement nameTyper;

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
        nameTyper.update();
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
        titleWrapper = new VerticalListElement((int)(SPACE_BETWEEN_ROWS*1.5));
        columnsContainer = new HorizontalListElement(SPACE_BETWEEN_COLUMNS);

        nameColumn = new VerticalListElement(SPACE_BETWEEN_ROWS);
        scoreColumn = new VerticalListElement(SPACE_BETWEEN_ROWS);

        nameTyper = new TypeTextElement(3);

        // Add Text Elements
        nameColumn.addChild(new TextElement("NAME"));
        scoreColumn.addChild(new TextElement("SCORE"));
        scoreColumn.addChild(new TextElement(Integer.toString(score)));

        nameColumn.addChild(nameTyper);

        submitButton = new ButtonElement("SUBMIT") {
            @Override
            protected void onClick() {
                SaveManager sm = SaveManager.get();
                sm.getSave().getScoreBoard().addNewScore(score, nameTyper.getText());
                sm.getSave().save();
                ScreenManager.setScreen(new ScoresScreen(returnScreen, drawScreen));
            }

            @Override
            protected void additionalActions() {
                if(km.enter) onClick();
            }
        };

        // Compile Columns into Row
        columnsContainer.addChild(nameColumn);
        columnsContainer.addChild(scoreColumn);
        columnsContainer.addChild(submitButton);
        columnsContainer.setCenterAlign(true);

        titleWrapper.addChild(new TextElement("NEW HIGH SCORE"));
        titleWrapper.addChild(columnsContainer);
        titleWrapper.setCenterAlign(true);

        // Put Into Main
        mainElement.setChildElement(titleWrapper);
    }
}
