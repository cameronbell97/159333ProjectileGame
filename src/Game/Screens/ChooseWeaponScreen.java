package Game.Screens;

import Game.Data.PlayerModules.MainBlasterModule;
import Game.Data.PlayerModules.PierceCannonModule;
import Game.Data.PlayerModules.SideBlasterModule;
import Game.Data.Settings;
import Game.Display.Assets.AssetManager;
import Game.Display.DisplayElements.*;
import Game.Display.DisplayElements.ImageButtonElement;

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

    // Elements //
    private PaddedElement mainElement;
    private VerticalListElement titleWrapper;
    private HorizontalListElement buttonsContainer;

    private ButtonElement mainBlasterButton;
    private ButtonElement sideBlastersButton;
    ButtonElement backButton;

// CONSTRUCTORS //
    public ChooseWeaponScreen(Screen returnScreen) {
        super();

        this.returnScreen = returnScreen;
        backgroundColor = new Color(0, 0, 20);

        fillElements();
    }

// METHODS //
    @Override
    public void update(int dt) {
        mainBlasterButton.update();
        sideBlastersButton.update();
        backButton.update();
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
        mainElement = new PaddedElement(border_width, outer_padding);
        titleWrapper = new VerticalListElement((int)(space_between_rows *1.5));
        buttonsContainer = new HorizontalListElement(space_between_columns);

        titleWrapper.addChild(new TextElement("CHOOSE YOUR WEAPON"));

        mainBlasterButton = new ImageButtonElement(AssetManager.get().getSprite(30, 0, 0)) {
                @Override
                protected void onClick() {
                try {
                    ScreenManager.setScreen(new GameScreen(new MainBlasterModule()));
                    } catch (IOException e) {
                    e.printStackTrace();
                    }
                }
        };
        buttonsContainer.addChild(mainBlasterButton);
        sideBlastersButton = new ImageButtonElement(AssetManager.get().getSprite(30, 1, 0)) {
                @Override
                protected void onClick() {
                try {
                    ScreenManager.setScreen(new GameScreen(new SideBlasterModule()));
                    } catch (IOException e) {
                    e.printStackTrace();
                    }
                }
        };
        buttonsContainer.addChild(sideBlastersButton);

        titleWrapper.addChild(buttonsContainer);
        titleWrapper.setCenterAlign(true);

        // Back Button
        backButton = new TextButtonElement("<", border_width, Settings.button_padding) {
            @Override
            protected void onClick() {
                ScreenManager.setScreen(returnScreen);
            }
        };

        mainElement.setChildElement(titleWrapper);
    }
}
