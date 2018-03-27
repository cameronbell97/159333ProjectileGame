import java.awt.*;
import java.io.IOException;

/**
 * Cameron Bell - 26/03/2018
 * Game Screen Class
 */

public class GameScreen extends Screen {
// VARIABLES //
    private SpriteSheet spritesheet;

// CONSTRUCTORS //
    public GameScreen() throws IOException {
        spritesheet = new SpriteSheet(ImageLoader.load("tile01.png")); // load the spritesheet
    }

// METHODS //
    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g) {

        g.drawImage(spritesheet.getSprite("player"), 64, 64, null);
    }
}
