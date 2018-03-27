import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Cameron on 27/03/2018.
 */
public class AssetManager {
// SINGLETON PATTERN //
    private static AssetManager self = new AssetManager();
    public static AssetManager get() { return self; }

// VARIABLES //
    private SpriteSheet sheet1 = null;

    public AssetManager() {
        // Build Spritesheet //
        try {
            sheet1 = new SpriteSheet(ImageLoader.load("tile01.png")); // load the spritesheet
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

// METHODS //
    // Method to get an extracted image from a preset name
    public BufferedImage getSprite(String key) {
        if(key == null) return null; // If key is null, return null

        switch (key) {
            case "player":
                return sheet1.getSprite(1, 0);
        }

        return null; // If key is unknown, return null
    }

    public BufferedImage getSprite(int s, int x, int y) {
        switch(s) {
            case 1:
                return sheet1.getSprite(x, y);
        }

        return null;
    }
}
