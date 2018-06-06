package Game.Screens;

import Game.Data.*;
import Game.Display.DisplayElements.*;

import java.awt.*;

/**
 * Cameron Bell - 18/04/2018
 * Add Score Screen Class
 * The Screen that appears at the end of the game to record your High Score
 */

public class AddScoreScreen extends Screen {
// VARIABLES //
    // Data
    private int space_between_columns = DEF_SPACE_BETWEEN_COLUMNS;
    private int space_between_rows = DEF_SPACE_BETWEEN_ROWS;
    private int border_width = DEF_BORDER_WIDTH;
    private int outer_padding = DEF_OUTER_PADDING;
    private int score;

    // Screens
    private Screen returnScreen;
    private Screen drawScreen;

    // Colours
    private Color backgroundColor;
    private Color borderColor;
    private Color fillColour;

    // Elements
    private PaddedElement mainElement;
    private VerticalListElement titleWrapper;
    private HorizontalListElement columnsContainer;
    private VerticalListElement nameColumn;
    private VerticalListElement scoreColumn;
    private ButtonElement submitButton;
    private TypeTextElement nameTyper;

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
    // Method - Update Buttons & TypeText Box //
    @Override
    public void update(int dt) {
        submitButton.update();
        nameTyper.update();
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
    }

    // Method - Generate All The Main Menu Elements //
    private void fillElements() {
        mainElement = new PaddedElement(1, borderColor, fillColour, outer_padding);
        titleWrapper = new VerticalListElement((int)(space_between_rows *1.5));
        columnsContainer = new HorizontalListElement(space_between_columns);

        nameColumn = new VerticalListElement(space_between_rows);
        scoreColumn = new VerticalListElement(space_between_rows);

        nameTyper = new TypeTextElement(3);

        // Add Text Elements
        nameColumn.addChild(new TextElement("NAME"));
        scoreColumn.addChild(new TextElement("SCORE"));
        scoreColumn.addChild(new TextElement(Integer.toString(score)));

        nameColumn.addChild(nameTyper);

        submitButton = new ButtonElement("SUBMIT") {
            @Override
            protected void onClick() {
                Save save = handler.getSave();
                save.getScoreBoard().addNewScore(score, nameTyper.getText());
                save.saveXML();
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
