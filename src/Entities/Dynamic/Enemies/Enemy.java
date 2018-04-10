package Entities.Dynamic.Enemies;

import Entities.Dynamic.DynamicEntity;

public abstract class Enemy extends DynamicEntity {
    public Enemy(float x, float y, int w, int h, double direction) {
        super(x, y, w, h, direction);
    }
}
