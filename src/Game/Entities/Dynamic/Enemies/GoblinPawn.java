package Game.Entities.Dynamic.Enemies;

import Game.Data.Settings;
import Game.Display.Assets.AssetManager;
import Game.Entities.EntityManager;
import Game.Entities.iOutOfBounds;

import java.awt.*;

public class GoblinPawn extends TargetingEnemy implements iOutOfBounds {
// VARIABLES //
    private static final int PLAYER_STOP_DISTANCE = 500;
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
        if((distanceFromPlayer > PLAYER_STOP_DISTANCE || checkOOB()) && checkMovingIsWorth()) move();
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);

        // Debug Tool // Draw Line to Player
        if(Settings.DEBUG_GOBLIN_DRAW_LINE_TO_PLAYER) {
            if(distanceFromPlayer > PLAYER_STOP_DISTANCE) {
                g.setColor(Color.ORANGE);
            } else g.setColor(Color.RED);
            g.drawLine(
                    (int) this.xpos + width / 2,
                    (int) this.ypos + height / 2,
                    (int) EntityManager.get().getPlayer().getXpos() + EntityManager.get().getPlayer().getWidth() / 2,
                    (int) EntityManager.get().getPlayer().getYpos() + EntityManager.get().getPlayer().getHeight() / 2
            );
        }
        // Debug Tool // Draw Line of Facing Direction
        if(Settings.DEBUG_GOBLIN_DRAW_FACING_DIRECTION_LINE) {
            g.setColor(Color.WHITE);
            g.drawLine(
                    (int) this.xpos + width / 2,
                    (int) this.ypos + height / 2,
                    (int) (this.xpos + width / 2) + (int) (xmove * 24),
                    (int) (this.ypos + height / 2) + (int) (ymove * 24)
            );
        }
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
