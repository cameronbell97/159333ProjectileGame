package Game.Entities.Dynamic.Enemies;

import Game.Entities.Dynamic.DynamicEntity;

/**
 * Cameron Bell - 10/04/2018
 * Enemy Abstract Class
 * Enemies Extend from this
 */

public abstract class Enemy extends DynamicEntity {
// VARIABLES //
    int score;

// CONSTRUCTORS //
    public Enemy(float x, float y, int w, int h, double direction) {
        super(x, y, w, h, direction);
    }
}
