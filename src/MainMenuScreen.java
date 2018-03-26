import java.awt.*;
import java.io.IOException;

/**
 * Created by Cameron on 26/03/2018.
 */
public class MainMenuScreen extends Screen{
// VARIABLES //
    private SpriteSheet spritesheet;

// CONSTRUCTORS //
    public MainMenuScreen() throws IOException {
        spritesheet = new SpriteSheet(ImageLoader.load("tile01.png")); // load the spritesheet
    }

// METHODS //
    @Override
    public void update() {
        // Sets game screen to a game screen immediately,
        // will change later when there's things to put on the main menu.
        try {
            ScreenManager.setScreen(new GameScreen());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void draw(Graphics g) {

    }
}
