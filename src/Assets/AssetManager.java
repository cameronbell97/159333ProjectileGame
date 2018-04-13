package Assets;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
    // Spritesheets
    private SpriteSheet sheet1 = null;
    private SpriteSheet sheet2 = null;
    private SpriteSheet wall_sheet = null;
    private SpriteSheet char_sheet_01 = null;
    private SpriteSheet char_sheet_02 = null;
    private SpriteSheet bullet_sheet = null;
    private SpriteSheet particles_sheet = null;
    private BufferedImage walls[];
    private BufferedImage collColour;

    // Maps / Dictionaries
    private HashMap<String, BufferedImage> charset_1;
    private HashMap<String, BufferedImage> charset_2;

    // Animations
    private BufferedImage[] player_thrust_anim;

// CONSTRUCTORS //
    private AssetManager() {
        buildSpritesheets();
        buildMaps();

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

    // Animations //
        player_thrust_anim = new BufferedImage[4];
        for(int i = 0; i < 4; i++)
            player_thrust_anim[i] = getSprite(1, i, 0);
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
            case "AstLargeWhite":
                return sheet1.getSprite(0,2);
            case "AstMediumWhite":
                return sheet1.getSprite(1,2);
            case "AstSmallWhite":
                return sheet1.getSprite(2,2);
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

    private void buildSpritesheets() {
        // Sheet01
        try { sheet1 = new SpriteSheet(ImageLoader.load("../tile01.png")); // load the spritesheet
        } catch (IOException e) { e.printStackTrace(); }

        // Sheet02
        try { sheet2 = new SpriteSheet(ImageLoader.load("../tile02.png"), 4, 4, 32, 32); // load the spritesheet
        } catch (IOException e) { e.printStackTrace(); }

        // Walls sheet
        try { wall_sheet = new SpriteSheet(ImageLoader.load("../walls.png"), 2, 2, 16, 16); // load the spritesheet
        } catch (IOException e) { e.printStackTrace(); }

        // Characters sheet Roman
        try { char_sheet_01 = new SpriteSheet(ImageLoader.load("../characters01.png"), 10, 4, 5, 9); // load the spritesheet
        } catch (IOException e) { e.printStackTrace(); }

        // Characters sheet Alien
        try { char_sheet_02 = new SpriteSheet(ImageLoader.load("../characters02.png"), 10, 4, 5, 9); // load the spritesheet
        } catch (IOException e) { e.printStackTrace(); }

        // Bullet Sheet
        try { bullet_sheet = new SpriteSheet(ImageLoader.load("../bullets.png"), 2, 2, 10, 10); // load the spritesheet
        } catch (IOException e) { e.printStackTrace(); }

        // Particle Sheet
        try { particles_sheet = new SpriteSheet(ImageLoader.load("../particles8.png"), 4, 4, 8, 8); // load the spritesheet
        } catch (IOException e) { e.printStackTrace(); }
    }

    private void buildMaps() {
        // Character Sets
        String[][] letters = {
                {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"},
                {"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T"},
                {"U", "V", "W", "X", "Y", "Z", ",", ".", "!", "?"},
                {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"}
        };
        charset_1 = new HashMap<>();
        charset_2 = new HashMap<>();
        for(int y = 0; y < 4; y++) {
            for(int x = 0; x < 10; x++) {
                if (!charset_1.containsKey(letters[y][x])) {
                     charset_1.put(letters[y][x], char_sheet_01.getSprite(x, y));
                }
                if (!charset_2.containsKey(letters[y][x])) {
                     charset_2.put(letters[y][x], char_sheet_02.getSprite(x, y));
                }
            }
        }
    }

// GETTERS & SETTERS //
    public BufferedImage getAnimPThrust(int frame) {
        if (frame >= 4) frame = 0;
        return player_thrust_anim[frame];
    }

    public HashMap<String, BufferedImage> getMap(String mapName) {
        switch (mapName) {
            case "charset_1":
                if(charset_1 != null) return charset_1;
                break;
            case "charset_2":
                if(charset_2 != null) return charset_2;
                break;
        }
        return null;
    }
}
