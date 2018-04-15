package Game.Display.UserInterface.Buttons;


public class QuitButton extends Button {
// CONSTRUCTORS //
    public QuitButton(String text, int middleXpos, int middleYpos) {
        super(text, middleXpos, middleYpos);
    }

// METHODS //
    @Override
    protected void onClick() {
        Game.Game.end();
    }
}
