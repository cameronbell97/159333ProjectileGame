package Entities.Static;

import Entities.Entity;
import Game.Handler;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Cameron Bell - 28/03/2018
 * Wall Class
 * A simple class to be expanded on later
 */

public class Wall extends Entity{
// VARIABLES //
    public static final int DEF_WIDTH = 16;
    public static final int DEF_HEIGHT = 16;
    private BufferedImage sprite;

// CONSTRUCTORS //
    public Wall(Handler handler, BufferedImage img, int x, int y, int w, int h) {
        super(handler, x, y, w, h);
        sprite = img;
    }

    @Override
    public void update() {

    }

    // METHODS //
    public void draw(Graphics g) {
        g.drawImage(sprite, (int)xpos, (int)ypos, width, height, null);
    }

    @Override
    public void collide(Entity ec) {
        
    }

    public void setPos(int x, int y) {
        xpos = x;
        ypos = y;
    }

// GETTERS & SETTERS
    public BufferedImage getSprite() {
        return sprite;
    }
    public void setSprite(BufferedImage x) {
        sprite = x;
    }
}
