package Entities;

/**
 * Cameron Bell - 27/03/2018
 * Entities.Vulnerable Entities.Entity Class
 * To be used for entities that are not invulnerable (i.e player & enemies)
 */

public abstract class Vulnerable extends Entity{

    protected int hp;

    public Vulnerable(float x, float y) {
        super(x, y);
    }
}
