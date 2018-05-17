package Game.Display.Assets;

import java.awt.image.BufferedImage;

/**
 * Cameron Bell - 25/03/2018
 * Sprite Sheet Class
 * Class Object for Holding A Matrix of Buffered Images
 */

public class SpriteSheet {
// VARIABLES //
    // Statics //
    private static final int
        DEF_SHEETX = 4,
        DEF_SHEETY = 4,
        DEF_SPRITE_WIDTH = 64,
        DEF_SPRITE_HEIGHT = 64;

    // Data //
    private BufferedImage
            sheet, // Original uncut image
            imgArray[][]; // Array of images, compiled upon object creation

    private int
            sheetx,
            sheety;

// CONSTRUCTORS //
    // Default Constructor //
    public SpriteSheet(BufferedImage img){
        sheet = img;
        imgArray = new BufferedImage[DEF_SHEETX][DEF_SHEETY];
        compile(); // Build the sheet
        this.sheetx = DEF_SHEETX;
        this.sheety = DEF_SHEETY;

    }

    // Constructor Overload - Overloaded for Custom Sheet Dimensions //
    public SpriteSheet(BufferedImage img, int sheetx, int sheety, int width, int height){
        sheet = img;
        imgArray = new BufferedImage[sheety][sheetx];
        compile(sheetx, sheety, width, height); // Build the sheet
        this.sheetx = sheetx;
        this.sheety = sheety;
    }

// METHODS //
    // Method - Extract a Subimage from Given Co-Ordinates & Dimensions //
    public BufferedImage extract(int x, int y, int w, int h) {
        return sheet.getSubimage(x, y, w, h);
    }

    // Method - Programmatically Extract the Entire Spritesheet into the BufferedImage 2D Array //
    private void compile() {
        for(int x = 0; x < DEF_SHEETX; x++) {
            for(int y = 0; y < DEF_SHEETY; y++) {
                imgArray[y][x] = extract(x * DEF_SPRITE_WIDTH, y * DEF_SPRITE_HEIGHT, DEF_SPRITE_WIDTH, DEF_SPRITE_HEIGHT);
            }
        }
    }

    // Method Overload - Compile Overloaded for custom Sheet Dimensions //
    private void compile(int sheetx, int sheety, int w, int h) {
        for(int x = 0; x < sheetx; x++) {
            for(int y = 0; y < sheety; y++) {
                imgArray[y][x] = extract(x * w, y * h, w, h);
            }
        }
    }

    // Method - Retrieve a Sprite of X,Y Co-Ordinates //
    public BufferedImage getSprite(int x, int y) {
        if(x >= 0 && y >= 0 && x < sheetx && y < sheety) {
            return imgArray[y][x];
        }
        return null;
    }
}
