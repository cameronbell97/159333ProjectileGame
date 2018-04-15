package Game.Entities.Dynamic.Enemies;

import Game.Entities.Dynamic.DynamicEntity;

public abstract class Enemy extends DynamicEntity {
// VARIABLES //
    int score;

// CONSTRUCTORS //
    public Enemy(float x, float y, int w, int h, double direction) {
        super(x, y, w, h, direction);
    }
}
