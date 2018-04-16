package Game.Entities.Dynamic.Enemies;

import Game.Display.Assets.AssetManager;
import Game.Entities.Collision.CollisionBox;
import Game.Entities.Dynamic.Bullets.GoblinBulletLarge;
import Game.Entities.EntityManager;

public class s extends GoblinFighter {
// VARIABLES //
    private static final int GOBLIN_FIGHTER_LARGE_DEF_HP = 6;

// CONSTRUCTOR //
    public GoblinFighterLarge(float x, float y, double direction) {
        super(x, y, direction);
        img = AssetManager.get().getSprite(1, 1, 3);
        this.hp = GOBLIN_FIGHTER_LARGE_DEF_HP;
        this.exp_value = 8;

        collision = new CollisionBox(x+15, y+9, 34, 45, 15, 9, this);
    }

    public void shoot() {
        EntityManager.get().subscribe(new GoblinBulletLarge(this));
    }
}
