package Entities.Dynamic.Enemies;

import Entities.Dynamic.DynamicEntity;
import Game.Handler;

public abstract class Enemy extends DynamicEntity {
    public Enemy(Handler handler, float x, float y, int w, int h, double direction) {
        super(handler, x, y, w, h, direction);
    }
}
