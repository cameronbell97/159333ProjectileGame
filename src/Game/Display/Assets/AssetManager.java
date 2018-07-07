package Game.Display.Assets;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

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
    // Statics //
    private static final String imagePath = "/img/";

    // Spritesheets //
    private SpriteSheet sheet1 = null;
    private SpriteSheet char_sheet_01 = null;
    private SpriteSheet char_sheet_02 = null;
    private SpriteSheet bullet_sheet = null;
    private SpriteSheet particles_sheet_16 = null;
    private SpriteSheet weapon_thumbs_128 = null;

    // Maps / Dictionaries //
    private HashMap<String, BufferedImage> charset_1;
    private HashMap<String, BufferedImage> charset_2;

    // Animations //
    private BufferedImage[] player_thrust_anim;

// CONSTRUCTORS //
    private AssetManager() {
        // Build Sprites //
        buildSpritesheets();
        buildMaps();

        // Animations //
        player_thrust_anim = new BufferedImage[4];
        for(int i = 0; i < 4; i++)
            player_thrust_anim[i] = getSprite(1, i, 0);
    }

// METHODS //
    // Method - Get an Extracted Image Using a Preset Name //
    public BufferedImage getSprite(String key) {
        if(key == null) return null; // If key is null, return null

        switch (key) {
            case "player":
                return sheet1.getSprite(0, 0);
            case "BulletPlayer":
                return bullet_sheet.getSprite(0, 0);
            case "BulletEnemy":
                return bullet_sheet.getSprite(1, 0);
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
        }

        return null; // If key is unknown, return null
    }

    // Method - Get a Sprite from a Sprite Sheet //
    public BufferedImage getSprite(int s, int x, int y) {
        switch(s) {
            case 1:
                return sheet1.getSprite(x, y);
            case 11:
                return particles_sheet_16.getSprite(x, y);
            case 20:
                return bullet_sheet.getSprite(x, y);
            case 30:
                return weapon_thumbs_128.getSprite(x, y);
        }

        return null;
    }

    // Method - Build Spritesheets from Image Files //
    private void buildSpritesheets() {
        try {
            // Main 64x64 Sheet
            sheet1 = new SpriteSheet(ImageLoader.load(imagePath + "tile01.png")); // load the spritesheet

            // Characters sheet Roman/English
            char_sheet_01 = new SpriteSheet(ImageLoader.load(imagePath + "characters01.png"), 10, 5, 5, 9); // load the spritesheet

            // Characters sheet Alien
            char_sheet_02 = new SpriteSheet(ImageLoader.load(imagePath + "characters02.png"), 10, 5, 5, 9); // load the spritesheet

            // Bullet Sheet
            bullet_sheet = new SpriteSheet(ImageLoader.load(imagePath + "bullets.png"), 3, 2, 10, 10); // load the spritesheet

            // Particle Sheet 16x16
            particles_sheet_16 = new SpriteSheet(ImageLoader.load(imagePath + "particles16.png"), 4, 14, 16, 16); // load the spritesheet

            // Weapon Thumbnails Sheet 128x128
            weapon_thumbs_128 = new SpriteSheet(ImageLoader.load(imagePath + "weapon_thumbs_128.png"), 3, 1, 128, 128); // load the spritesheet

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method - Build Text Character Maps //
    private void buildMaps() {
        // Character Sets
        String[][] letters = {
                {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"},
                {"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T"},
                {"U", "V", "W", "X", "Y", "Z", ",", ".", "!", "?"},
                {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"},
                {" ", "<", ">", "-", "_", "\"", "\'", "/", "\\", "*"}
        };
        charset_1 = new HashMap<>();
        charset_2 = new HashMap<>();
        for(int y = 0; y < 5; y++) {
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
    // Getter Method - Get Animation Frame for Player Thrust //
    public BufferedImage getAnimPThrust(int frame) {
        if (frame >= 4) frame = 0;
        return player_thrust_anim[frame];
    }

    // Getter Method - Get Text Map Determined by Given Name //
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
