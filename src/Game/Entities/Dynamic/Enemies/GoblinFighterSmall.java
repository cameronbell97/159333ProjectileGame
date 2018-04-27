package Game.Entities.Dynamic.Enemies;

import Game.Display.Assets.AssetManager;
import Game.Entities.Collision.CollisionBox;
import Game.Entities.Dynamic.Bullets.GoblinBulletSmall;
import Game.Entities.EntityManager;

public class GoblinFighterSmall extends GoblinFighter {
// VARIABLES //
    private static final int GOBLIN_FIGHTER_SMALL_DEF_HP = 4;
    private static final int GOBLIN_FIGHTER_SMALL_ROTATE_WAIT_TIME = 20;
    private static final int GOBLIN_FIGHTER_TIME_BEFORE_SHOOTING = 2*60;
    private static final double GOBLIN_FIGHTER_SMALL_ROTATE_SPEED = 0.005 * Math.PI;;

// CONSTRUCTOR //
    public GoblinFighterSmall(float x, float y, double direction) {
        super(x, y, direction);
        img = AssetManager.get().getSprite(1, 0, 3);
        this.hp = GOBLIN_FIGHTER_SMALL_DEF_HP;
        this.exp_value = 10;
        timeBeforeRotating = GOBLIN_FIGHTER_SMALL_ROTATE_WAIT_TIME;
        rotationSpeed = GOBLIN_FIGHTER_SMALL_ROTATE_SPEED;
        shootTimer = GOBLIN_FIGHTER_TIME_BEFORE_SHOOTING;
        timeBetweenPhases = GOBLIN_FIGHTER_TIME_BEFORE_SHOOTING;
    }

    public void shoot() {
        EntityManager.get().subscribe(new GoblinBulletSmall(this));
    }

    @Override
    public void setCollisionBox() {

        collision = new CollisionBox(xpos+21, ypos+17, 22, 30, 21, 17, this);
    }
}
