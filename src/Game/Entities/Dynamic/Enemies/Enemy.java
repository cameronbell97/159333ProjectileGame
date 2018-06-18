package Game.Entities.Dynamic.Enemies;

import Game.Entities.Dynamic.Bullets.PlayerPiercingBullet;
import Game.Entities.Dynamic.DynamicEntity;

import java.util.ArrayList;

/**
 * Cameron Bell - 10/04/2018
 * Enemy Abstract Class
 * Enemies Extend from this
 */

public abstract class Enemy extends DynamicEntity {
// VARIABLES //
    int score;

    private ArrayList<PlayerPiercingBullet> pierceBullets;

// CONSTRUCTORS //
    public Enemy(float x, float y, int w, int h, double direction) {
        super(x, y, w, h, direction);
        pierceBullets = new ArrayList<>();
    }

// METHODS //
    protected boolean checkAlreadyPierced(PlayerPiercingBullet pb) {
        return pierceBullets.contains(pb);
    }

    protected boolean addPierceBullet(PlayerPiercingBullet pb) {
        if(pierceBullets.contains(pb)) return false;
        pierceBullets.add(pb);
        return true;
    }
}
