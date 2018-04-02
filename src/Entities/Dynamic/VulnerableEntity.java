package Entities.Dynamic;

import Game.Handler;

/**
 * Cameron Bell - 27/03/2018
 * VulnerableEntity DynamicEntity Class
 * To be used for entities that are not invulnerable (i.e player & enemies)
 */

public abstract class VulnerableEntity extends DynamicEntity {
// VARIABLES //
    public static final int DEF_HP = 1;
    protected int hp;

// CONSTRUCTORS //
    public VulnerableEntity(Handler handler, float x, float y, int w, int h) {
        super(handler, x, y, w, h);
        hp = DEF_HP;
    }

// GETTERS & SETTERS //
    public int getHP() { return hp; }
    public void setHP(int hp) { this.hp = hp; }
}
