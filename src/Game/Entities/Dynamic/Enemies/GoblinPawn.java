package Game.Entities.Dynamic.Enemies;

import Game.Data.Settings;
import Game.Display.Assets.AssetManager;
import Game.Entities.iOutOfBounds;

public class GoblinPawn extends TargetingEnemy implements iOutOfBounds {
// VARIABLES //
    private static final int PLAYER_STOP_DISTANCE = 200;
    private static final int GOBLIN_PAWN_MOVE_SPEED = 2;
    private static final int OFFSCREEN_BOUNDARY = -64;

// CONSTRUCTORS //
    public GoblinPawn(float x, float y, double direction) {
        super(x, y, 64, 64, direction);
        img = AssetManager.get().getSprite(1, 0, 3);
        moveSpeed = GOBLIN_PAWN_MOVE_SPEED;
    }

// METHODS //
    @Override
    public void update() {
        super.update();
        if(distanceFromPlayer > PLAYER_STOP_DISTANCE || checkOOB()) move();
    }

    @Override
    public boolean checkOOB() {
        if(     xpos <= -OFFSCREEN_BOUNDARY ||
                ypos <= -OFFSCREEN_BOUNDARY ||
                xpos >= Settings.game_width + OFFSCREEN_BOUNDARY ||
                ypos >= Settings.game_height + OFFSCREEN_BOUNDARY) {
            return true;
        }

        return false;
    }

    @Override
    public void doWhenOutOfBounds() {

    }
}
