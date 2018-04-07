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
    private SpriteSheet char_sheet_01 = null;
    private SpriteSheet char_sheet_02 = null;
    private SpriteSheet bullet_sheet = null;
    private SpriteSheet particles_sheet = null;
    private BufferedImage walls[];
    private BufferedImage collColour;

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
        // Characters sheet Roman
        try {
            char_sheet_01 = new SpriteSheet(ImageLoader.load("../characters01.png"), 10, 4, 5, 9); // load the spritesheet
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Characters sheet Alien
        try {
            char_sheet_01 = new SpriteSheet(ImageLoader.load("../characters02.png"), 10, 4, 5, 9); // load the spritesheet
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Bullet Sheet
        try {
            bullet_sheet = new SpriteSheet(ImageLoader.load("../bullets.png"), 2, 2, 10, 10); // load the spritesheet
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Particle Sheet
        try {
            particles_sheet = new SpriteSheet(ImageLoader.load("../particles8.png"), 4, 4, 8, 8); // load the spritesheet
        } catch (IOException e) {
            e.printStackTrace();
        }

    // Individual Sprites //
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

        // Collision Colour
        try {
            collColour = ImageLoader.load("../collision.png");
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
                return sheet1.getSprite(0, 0);
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
            case "BulletPlayer":
                return bullet_sheet.getSprite(0, 0);
            case "AstLarge":
                return sheet1.getSprite(0,1);
            case "AstMedium":
                return sheet1.getSprite(1,1);
            case "AstSmall":
                return sheet1.getSprite(2,1);
            case "Coll":
                return collColour;
        }

        return null; // If key is unknown, return null
    }

    public BufferedImage getSprite(int s, int x, int y) {
        switch(s) {
            case 1:
                return sheet1.getSprite(x, y);
            case 2:
                return sheet2.getSprite(x, y);
            case 10:
                return particles_sheet.getSprite(x, y);
        }

        return null;
    }
}
