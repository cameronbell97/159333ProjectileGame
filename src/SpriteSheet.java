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
        compile(); // Build the sheet
    }

    // TODO implement a constructor that defines sheetx and sheety,
    // TODO  in the event of multiple spritesheets with different dimensions

// METHODS //
    // Method to extract a subimage from given co-ordinates and dimensions
    public BufferedImage extract(int x, int y, int w, int h) {
        return sheet.getSubimage(x, y, w, h);
    }

    // Method to programmatically extract the entire spritesheet into the BufferedImage 2D array
    private void compile() {
        for(int x = 0; x < SHEETX; x++) {
            for(int y = 0; y < SHEETY; y++) {
                imgArray[x][y] = extract(x * SPRITEWIDTH, y * SPRITEHEIGHT, SPRITEWIDTH, SPRITEHEIGHT);
            }
        }
    }

    // Method to retrieve a sprite of x,y co-ords
    public BufferedImage getSprite(int x, int y) {
        if(x >= 0 && y >= 0 && x < SHEETX && y < SHEETY) {
            return imgArray[x][y];
        }
        return null;
    }
}
