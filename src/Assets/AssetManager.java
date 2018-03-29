package Assets;

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
    private BufferedImage walls[];

// CONSTRUCTORS //
    public AssetManager() {
        // Build Spritesheet //
        try {
            sheet1 = new SpriteSheet(ImageLoader.load("../tile01.png")); // load the spritesheet
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Walls //
        walls = new BufferedImage[6];
        try {
            walls[0] = ImageLoader.load("../wall_lt.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            walls[1] = ImageLoader.load("../wall_rt.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            walls[2] = ImageLoader.load("../wall_rb.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            walls[3] = ImageLoader.load("../wall_lb.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            walls[4] = ImageLoader.load("../wall_h.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            walls[5] = ImageLoader.load("../wall_v.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

// METHODS //
    // Method to get an extracted image from a preset name
    public BufferedImage getSprite(String key) {
        if(key == null) return null; // If key is null, return null

        switch (key) {
            case "LTWall":
                return walls[0];
            case "RTWall":
                return walls[1];
            case "RBWall":
                return walls[2];
            case "LBWall":
                return walls[3];
            case "HozWall":
                return walls[4];
            case "VerWall":
                return walls[5];
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
