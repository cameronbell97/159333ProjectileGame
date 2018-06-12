package Game.Screens;

import Game.Data.PlayerModules.MainBlasterModule;
import Game.Data.PlayerModules.SideBlasterModule;
import Game.Data.Settings;
import Game.Display.DisplayElements.*;

import java.awt.*;
import java.io.IOException;

public class ChooseWeaponScreen extends Screen {
// VARIABLES //
    // Data //
    private int space_between_columns = DEF_SPACE_BETWEEN_COLUMNS;
    private int space_between_rows = DEF_SPACE_BETWEEN_ROWS;
    private int border_width = DEF_BORDER_WIDTH;
    private int outer_padding = DEF_OUTER_PADDING;

    Screen returnScreen;

    // Colours //
    private Color backgroundColor;
    private Color borderColor;
    private Color fillColour;

    // Elements //
    private PaddedElement mainElement;
    private VerticalListElement titleWrapper;
    private HorizontalListElement buttonsContainer;

    private ButtonElement mainBlasterButton;
    private ButtonElement sideBlastersButton;

// CONSTRUCTORS //
    public ChooseWeaponScreen(Screen returnScreen) {
        super();

        this.returnScreen = returnScreen;

        borderColor = new Color(129,130,174);
        fillColour = new Color(78, 78, 122, 58);
        backgroundColor = new Color(0, 0, 20);

        fillElements();
    }

// METHODS //
    @Override
    public void update(int dt) {
        mainBlasterButton.update();
        sideBlastersButton.update();
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
    }

    private void fillElements() {
        mainElement = new PaddedElement(border_width, borderColor, fillColour, outer_padding);
        titleWrapper = new VerticalListElement((int)(space_between_rows *1.5));
        buttonsContainer = new HorizontalListElement(space_between_columns);

        titleWrapper.addChild(new TextElement("CHOOSE YOUR WEAPON"));

        mainBlasterButton = new ButtonElement("MAIN") {
                @Override
                protected void onClick() {
                try {
                    ScreenManager.setScreen(new GameScreen(new MainMenuScreen(), new MainBlasterModule(Settings.player_gun_lock)));
                    } catch (IOException e) {
                    e.printStackTrace();
                    }
                }
        };
        buttonsContainer.addChild(mainBlasterButton);
        sideBlastersButton = new ButtonElement("SIDE") {
                @Override
                protected void onClick() {
                try {
                    ScreenManager.setScreen(new GameScreen(new MainMenuScreen(), new SideBlasterModule()));
                    } catch (IOException e) {
                    e.printStackTrace();
                    }
                }
        };
        buttonsContainer.addChild(sideBlastersButton);

        titleWrapper.addChild(buttonsContainer);
        titleWrapper.setCenterAlign(true);

        mainElement.setChildElement(titleWrapper);
    }
}
