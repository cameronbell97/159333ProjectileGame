import java.awt.image.BufferedImage;

/**
 * Cameron Bell - 25/03/2018
 * Sprite Sheet Class
 */
public class SpriteSheet {
// CONSTANT VARIABLES //
    private static final int
        SHEETX = 8,
        SHEETY = 8,
        SPRITEWIDTH = 64,
        SPRITEHEIGHT = 64;

// VARIABLES //
    private BufferedImage sheet; // Original uncut image
    private BufferedImage imgArray[][]; // Array of images, compiled upon object creation

// CONSTRUCTORS //
    public SpriteSheet(BufferedImage img){
        sheet = img;
        imgArray = new BufferedImage[SHEETX][SHEETY];
        compile();
    }

    // METHODS //
    // Method to extract a subimage from given co-ordinates and dimensions
    public BufferedImage extract(int x, int y, int w, int h) {
        return sheet.getSubimage(x, y, w, h);
    }

    // Method to get an extracted image from a preset name
    public BufferedImage getSprite(String key) {
        if(key == null) return null; // If key is null, return null

        switch (key) {
            case "player":
                return imgArray[1][0];
        }

        return null; // If key is unknown, return null
    }

    // Method to programmatically extract the entire spritesheet into the BufferedImage 2D array
    private void compile() {
        for(int x = 0; x < SHEETX; x++) {
            for(int y = 0; y < SHEETY; y++) {
                imgArray[x][y] = extract(x * SPRITEWIDTH, y * SPRITEHEIGHT, SPRITEWIDTH, SPRITEHEIGHT);
            }
        }
    }
}
