package MainMenu.Buttons;

import Screens.GameScreen;
import Screens.ScreenManager;

import java.io.IOException;

public class PlayButton extends Button {
// CONSTRUCTORS //
    public PlayButton(String text, int middleXpos, int middleYpos) {
        super(text, middleXpos, middleYpos);
    }

// METHODS //
    @Override
    protected void onClick() {
        try {
            ScreenManager.setScreen(new GameScreen());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
