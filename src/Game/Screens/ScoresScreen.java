package Game.Screens;

import Game.Data.SaveManager;
import Game.Data.ScoreBoard;
import Game.Data.Settings;
import Game.Display.UserInterface.GameUIManager;
import Game.Display.UserInterface.TextManager;

import java.awt.*;

public class ScoresScreen extends Screen {
// VARIABLES //
    // Statics //
    private static final int SPACE_BETWEEN_COLUMNS = 0;
    private static final int SPACE_BETWEEN_ROWS = 8;
    private static final int BORDER_WIDTH = 1;
    private static final int OUTER_PADDING = 29;

    private ScoreBoard scoreBoard;
    private Screen lastScreen;
    private int totalTextLength;
    private int totalTextHeight;
    private int windowWidth;
    private int windowHeight;
    private int frameWidth;
    private int frameHeight;
    private int biggestScorePixelLength;
    private TextManager textManager;

// CONSTRUCTORS //
    public ScoresScreen(Screen lastScreen) {
        this.lastScreen = lastScreen;
        textManager = new TextManager();
        windowWidth = Settings.game_width;
        windowHeight = Settings.game_height;
        scoreBoard =
                SaveManager
                .get()
                .getSave()
                .getScoreBoard();

        biggestScorePixelLength = (((Integer.toString(scoreBoard.getBiggestScore()).length()) * (Settings.character_width + Settings.character_size) - Settings.character_size) * Settings.character_size) ;

        // TEMPORARY // TODO // REMOVE
        totalTextLength =
                biggestScorePixelLength + // Length of number
                /* length of names column + */
                SPACE_BETWEEN_COLUMNS;
        totalTextHeight = (Settings.character_height * Settings.character_size + SPACE_BETWEEN_ROWS) * 10 - SPACE_BETWEEN_ROWS;

        // Get Frame Dimensions
        frameWidth = totalTextLength + (BORDER_WIDTH + OUTER_PADDING) * 2;
        frameHeight = totalTextHeight + (BORDER_WIDTH + OUTER_PADDING) * 2;
    }

// METHODS //
    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics g) {
        if(lastScreen instanceof GameScreen) lastScreen.draw(g);
        else {
            // Draw Background
            g.setColor(new Color(0, 0, 20));
            g.fillRect(0, 0, Settings.game_width, Settings.game_height);
        }
        drawPanel(g);
        drawNames(g);
        drawScores(g);
    }

    private void drawPanel(Graphics g) {
        g.setColor(new Color(78, 78, 122, 58));
        g.fillRect((windowWidth - frameWidth) / 2, (windowHeight - frameHeight) / 2, frameWidth, frameHeight);
        g.setColor(new Color(129,130,174));
        g.drawRect((windowWidth - frameWidth) / 2, (windowHeight - frameHeight) / 2, frameWidth, frameHeight);
    }

    private void drawNames(Graphics g) {

    }

    private void drawScores(Graphics g) {
        int xpencil = (windowWidth - totalTextLength) / 2;
        int ypencil = ((windowHeight - totalTextHeight) / 2);

        for(int i = 0; i < scoreBoard.getScores().length; i++) {
            textManager.drawString(g, Integer.toString(scoreBoard.getScores()[i]), "left", xpencil, ypencil);

            ypencil += (Settings.character_height * Settings.character_size) + SPACE_BETWEEN_ROWS;
        }
    }
}
