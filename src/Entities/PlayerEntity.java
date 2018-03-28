package Entities;
import Game.Game;

import Assets.AssetManager;

import java.awt.*;

/**
 * Cameron Bell - 27/03/2018
 * Player Entity Class
 * The controllable player
 */
public class PlayerEntity extends VulnerableEntity {
// VARIABLES //
    public static final int DEF_PLAYER_WIDTH = 64;
    public static final int DEF_PLAYER_HEIGHT = 64;
    public static final double DEF_ROT_SPEED = 0.008*Math.PI;
    AssetManager assMan = AssetManager.get();
    Game game;
    protected int speedMultiplier;
    protected double rotationSpeed;

    // Inherits From // Entity
    // public static final float DEF_SPEED = 1;
    // protected float xpos, ypos;
    // protected float xmove, ymove;
    // protected int width, height;
    // protected double direction;
    // protected double moveSpeed;

    // Inherits From // VulnerableEntity
    //public static final int DEF_HP = 1;
    //protected int hp;

// CONSTRUCTORS //
    public PlayerEntity(Game gm, float x, float y) {
        super(x, y, DEF_PLAYER_WIDTH, DEF_PLAYER_HEIGHT);
        initialise(gm);
    }
    public PlayerEntity(Game gm, float x, float y, int w, int h) {
        super(x, y, w, h);
        initialise(gm);
    }

// METHODS //
    public void initialise(Game gm) {
        game = gm;
        speedMultiplier = 1;
        setSpeed(4);
        rotationSpeed = DEF_ROT_SPEED;
    }

    @Override
    public void update() {
        getInput();
        move();
    }

    private void getInput() {
        // Resets the movement so that the player doesn't keep moving indefinitely
        xmove = 0;
        ymove = 0;
        speedMultiplier = 1;

        if(game.getKeyManager().ctrl) {
            speedMultiplier = 2;
        }
        if(game.getKeyManager().forward) {
            ymove = (float)(moveSpeed * -Math.sin(direction)* speedMultiplier);
            xmove = (float)(moveSpeed * Math.cos(direction)* speedMultiplier);
        }
        if(game.getKeyManager().left) {
            direction += rotationSpeed;
        }
        if(game.getKeyManager().right) {
            direction -= rotationSpeed;
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage( // draw image at position (xpos,ypos)
                assMan.getSprite("player"), // with the 'player' sprite
                (int)xpos,
                (int)ypos,
                width, // of the object's set width
                height, // and of the object's set height
                null);
    }
}
