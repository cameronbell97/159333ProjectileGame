package Game.Display.UserInterface.Buttons;

import Game.Screens.ScoresScreen;
import Game.Screens.Screen;
import Game.Screens.ScreenManager;

public class ScoresButton extends Button {
    Screen parentScreen;

    public ScoresButton(String text, int middleXpos, int middleYpos, Screen parentScreen) {
        super(text, middleXpos, middleYpos);
        this.parentScreen = parentScreen;
    }

    @Override
    protected void onClick() {
        ScreenManager.setScreen(new ScoresScreen(parentScreen));
    }
}
