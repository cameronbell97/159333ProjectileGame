package Entities;

import Game.Handler;

/**
 * Cameron Bell - 27/03/2018
 * VulnerableEntity Entity Class
 * To be used for entities that are not invulnerable (i.e player & enemies)
 */

public abstract class VulnerableEntity extends Entity{
// VARIABLES //
    public static final int DEF_HP = 1;
    protected int hp;

    // Inherits From // Entity
    // public static final float DEF_SPEED = 1;
    // protected float xpos, ypos;
    // protected float xmove, ymove;
    // protected int width, height;
    // protected double direction;
    // protected double moveSpeed;

// CONSTRUCTORS //
    public VulnerableEntity(Handler handler, float x, float y, int w, int h) {
        super(handler, x, y, w, h);
        hp = DEF_HP;
    }

// GETTERS & SETTERS //
    public int getHP() { return hp; }
    public double getMoveSpeed() { return moveSpeed; }
    public void setHP(int hp) { this.hp = hp; }
    public void setSpeed(double ms) { moveSpeed = ms; }
}
