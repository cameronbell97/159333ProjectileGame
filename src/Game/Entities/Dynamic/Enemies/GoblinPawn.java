package Game.Entities.Dynamic.Enemies;

import Game.Data.Settings;
import Game.Display.Assets.AssetManager;
import Game.Entities.Collision.CollisionBox;
import Game.Entities.Dynamic.Bullets.EnemyBulletSmall;
import Game.Entities.Dynamic.ExpDot;
import Game.Entities.Entity;
import Game.Entities.EntityManager;
import Game.Entities.iOutOfBounds;
import Game.Entities.iVulnerable;

import java.awt.*;

public class GoblinPawn extends TargetingEnemy implements iOutOfBounds, iVulnerable {
// VARIABLES //
    private static final int PLAYER_STOP_DISTANCE = 500;
    private static final int GOBLIN_PAWN_MOVE_SPEED = 2;
    private static final int OFFSCREEN_BOUNDARY = -32;
    private static final int INITIAL_TIME_BEFORE_SHOOTING = 3*60;
    private static final int TIME_BETWEEN_SHOTS = 25;
    private static final int TIME_BETWEEN_SHOOT_PHASES = 3*60;
    private static final int SHOOT_PHASE_BULLET_NUMBER = 4;
    private static final int DEF_HP = 4;
    private static final int DEF_EXP = 8;

    private int shootTimer;
    private int shootPhase;
    private int phaseBullet;
    private int hp;

// CONSTRUCTORS //
    public GoblinPawn(float x, float y, double direction) {
        super(x, y, 64, 64, direction);
        img = AssetManager.get().getSprite(1, 0, 3);
        moveSpeed = GOBLIN_PAWN_MOVE_SPEED;
        shootTimer = INITIAL_TIME_BEFORE_SHOOTING;
        shootPhase = 1;
        phaseBullet = 0;
        hp = DEF_HP;

        collision = new CollisionBox(x+21, y+17, 22, 30, 21, 17, this);
    }

// METHODS //
    @Override
    public void update() {
        super.update();
        if((distanceFromPlayer > PLAYER_STOP_DISTANCE || checkOOB()) && checkMovingIsWorth()) setMoveSpeeds();

        move();
        tryShoot();
        collision.update();
    }

    @Override
    public void collide(Entity ec) {
        if(ec instanceof Game.Entities.Dynamic.Bullets.BulletPlayer) {
            addHP(-2);
        }
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

    private void tryShoot() {
        if(phaseBullet < SHOOT_PHASE_BULLET_NUMBER) {
            if (shootTimer == 0 || shootTimer == TIME_BETWEEN_SHOTS) {
                if (shootTimer == 0) shootPhase = 0;
                else shootPhase = 1;

                if (directionToPlayer == this.direction) shoot();

                phaseBullet++;
            }
            if (shootPhase == 0 && shootTimer < TIME_BETWEEN_SHOTS) shootTimer++;
            else if (shootPhase == 1 && shootTimer > 0) shootTimer--;
        } else {
            phaseBullet = 0;
            shootTimer = TIME_BETWEEN_SHOOT_PHASES;
            shootPhase = 1;
        }
    }

    private void shoot() {
        EntityManager.get().subscribe(new EnemyBulletSmall(this));
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

    public int getShootPhase() {
        return shootPhase;
    }

    @Override
    public int getHP() {
        return hp;
    }

    @Override
    public void setHP(int hp) {
        this.hp = hp;
    }

    @Override
    public void addHP(int hp) {
        this.hp += hp;
        if(this.hp <= 0) {
            die();
        }
    }

    @Override
    public void die() {
        EntityManager.get().subscribe(new ExpDot(this, DEF_EXP));
        explode();
        EntityManager.get().unsubscribe(this);
        EntityManager.get().unsubscribe(collision);
    }

    // Death animation
    private void explode() {

    }
}
