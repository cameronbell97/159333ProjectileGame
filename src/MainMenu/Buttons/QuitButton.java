package MainMenu.Buttons;

import Screens.GameScreen;
import Screens.ScreenManager;

import java.io.IOException;

public class QuitButton extends Button {
// CONSTRUCTORS //
    public QuitButton(String text, int middleXpos, int middleYpos) {
        super(text, middleXpos, middleYpos);
    }

// METHODS //
    @Override
    protected void onClick() {
        System.exit(0);
    }
}
