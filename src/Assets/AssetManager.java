package Assets;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Cameron Bell - 27/03/2018
 * Asset Manager Class
 * Class that holds the game assets
 */

public class AssetManager {
// SINGLETON PATTERN //
    private static AssetManager self = new AssetManager();
    public static AssetManager get() { return self; }

// VARIABLES //
    private SpriteSheet sheet1 = null;
    private SpriteSheet sheet2 = null;
    private SpriteSheet wall_sheet = null;
    private SpriteSheet char_sheet = null;
    private BufferedImage walls[];

// CONSTRUCTORS //
    public AssetManager() {
        // Build Spritesheets //
        // Sheet01
        try {
            sheet1 = new SpriteSheet(ImageLoader.load("../tile01.png")); // load the spritesheet
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Sheet02
        try {
            sheet2 = new SpriteSheet(ImageLoader.load("../tile02.png"), 4, 4, 32, 32); // load the spritesheet
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Walls sheet
        try {
            wall_sheet = new SpriteSheet(ImageLoader.load("../walls.png"), 2, 2, 16, 16); // load the spritesheet
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Walls sheet
        try {
            char_sheet = new SpriteSheet(ImageLoader.load("../characters01.png"), 10, 3, 5, 9); // load the spritesheet
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Walls //
        walls = new BufferedImage[2];
        try {
            walls[0] = ImageLoader.load("../wall_h.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            walls[1] = ImageLoader.load("../wall_v.png");
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
                return wall_sheet.getSprite(0, 0);
            case "RTWall":
                return wall_sheet.getSprite(1, 0);
            case "RBWall":
                return wall_sheet.getSprite(1, 1);
            case "LBWall":
                return wall_sheet.getSprite(0, 1);
            case "HozWall":
                return walls[0];
            case "VerWall":
                return walls[1];
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
