package Game.Entities.Dynamic.Enemies;

import Game.Display.Assets.AssetManager;
import Game.Entities.Collision.CollisionBox;
import Game.Entities.Dynamic.Bullets.GoblinBulletSmall;
import Game.Entities.EntityManager;

public class GoblinFighterSmall extends GoblinFighter {
// VARIABLES //
    private static final int GOBLIN_FIGHTER_SMALL_DEF_HP = 4;

// CONSTRUCTOR //
    public GoblinFighterSmall(float x, float y, double direction) {
        super(x, y, direction);
        img = AssetManager.get().getSprite(1, 0, 3);
        this.hp = GOBLIN_FIGHTER_SMALL_DEF_HP;
        this.exp_value = 10;

    }

    public void shoot() {
        EntityManager.get().subscribe(new GoblinBulletSmall(this));
    }

    @Override
    public void setCollisionBox() {

        collision = new CollisionBox(xpos+21, ypos+17, 22, 30, 21, 17, this);
    }
}
