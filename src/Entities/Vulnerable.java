package Entities;

/**
 * Cameron Bell - 27/03/2018
 * Vulnerable Entity Class
 * To be used for entities that are not invulnerable (i.e player & enemies)
 */

public abstract class Vulnerable extends Entity{
// VARIABLES //
    public static final int DEF_HP = 1;
    public static final double DEF_ROT_SPEED = 0.008*Math.PI;
    protected int hp;
    protected double moveSpeed;
    protected double rotationSpeed;
    protected int speedMultiplier;

    // Inherits //
    // public static final float DEF_SPEED = 1;
    // protected float xpos, ypos;
    // protected float xmove, ymove;
    // protected int width, height;
    // protected float direction;

// CONSTRUCTORS //
    public Vulnerable(float x, float y, int w, int h) {
        super(x, y, w, h);
        hp = DEF_HP;
        moveSpeed = DEF_SPEED;
        rotationSpeed = DEF_ROT_SPEED;
        speedMultiplier = 1;
    }

// GETTERS & SETTERS //
    public int getHP() { return hp; }
    public double getMoveSpeed() { return moveSpeed; }
    public void setHP(int hp) { this.hp = hp; }
    public void setSpeed() { this.moveSpeed = moveSpeed; }
}
