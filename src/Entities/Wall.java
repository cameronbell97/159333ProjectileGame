package Entities;

import Assets.AssetManager;
import Assets.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

/**
 * Created by Cameron on 28/03/2018.
 */
public class Wall {
// VARIABLES //
    public static final int DEF_WIDTH = 16;
    public static final int DEF_HEIGHT = 16;
    private int width;
    private int height;
    private int xpos;
    private int ypos;
    private BufferedImage sprite;

// CONSTRUCTORS //
    public Wall() {
        width = DEF_WIDTH;
        height = DEF_HEIGHT;
        sprite = AssetManager.get().getSprite("LTWall");
    }

    public Wall(BufferedImage img, int w, int h) {
        width = w;
        height = h;
        sprite = img;
    }

// METHODS //
    public void draw(Graphics g) {
        g.drawImage(sprite, xpos, ypos, width, height, null);
    }

    public void setPos(int x, int y) {
        xpos = x;
        ypos = y;
    }


// GETTERS & SETTERS
    public int getHeight() {
        return height;
    }
    public void setHeight(int x) {
        height = x;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int x) {
        width = x;
    }
    public BufferedImage getSprite() {
        return sprite;
    }
    public void setSprite(BufferedImage x) {
        sprite = x;
    }
    public int getXpos() {
        return xpos;
    }
    public void setXpos(int x) {
        xpos = x;
    }
    public int getYpos() {
        return ypos;
    }
    public void setYpos(int y) {
        ypos = y;
    }
}
