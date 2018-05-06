package Game.Entities.Dynamic.Enemies;

import Game.Display.Assets.AssetManager;
import Game.Entities.Collision.CollisionBox;
import Game.Entities.Dynamic.Bullets.GoblinBulletLarge;
import Game.Entities.EntityManager;

public class GoblinFighterLarge extends GoblinFighter {
// VARIABLES //
    private static final int LARGE_GOBLIN_STOP_DISTANCE = 650;
    private static final int GOBLIN_FIGHTER_LARGE_DEF_HP = 6;
    private static final int DEF_SHOOT_PHASE_BULLET_NUMBER = 3;

// CONSTRUCTOR //
    public GoblinFighterLarge(float x, float y, double direction) {
        super(x, y, direction);
        img = AssetManager.get().getSprite(1, 1, 3);
        this.hp = GOBLIN_FIGHTER_LARGE_DEF_HP;
        this.exp_value = 8;
        phaseBulletsNumber = DEF_SHOOT_PHASE_BULLET_NUMBER;
        playerStopDistance = LARGE_GOBLIN_STOP_DISTANCE;
    }

    public void shoot() {
        EntityManager.get().subscribe(new GoblinBulletLarge(this));
    }

    @Override
    protected void explode() {

    }

    @Override
    public void setCollisionBox() {
        collision = new CollisionBox(xpos+15, ypos+9, 34, 45, 15, 9, this);
    }
}
