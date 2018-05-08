package Game.Entities.Dynamic.Enemies;

import Game.Data.Settings;
import Game.Entities.*;
import Game.Entities.Dynamic.ExpDot;
import Game.Handler;

import java.awt.*;

public abstract class GoblinFighter extends TargetingEnemy implements iOutOfBounds, iVulnerable {
// VARIABLES //
    private static final int DEF_PLAYER_STOP_DISTANCE = 500;
    private static final double GOBLIN_FIGHTER_MOVE_SPEED = 1.4;
    private static final int OFFSCREEN_BOUNDARY = -32;
    private static final int INITIAL_TIME_BEFORE_SHOOTING = 3*60;
    private static final int TIME_BETWEEN_SHOOT_PHASES = 3*60;
    private static final int TIME_BETWEEN_SHOTS = 25;
    private static final int DEF_SHOOT_PHASE_BULLET_NUMBER = 4;
    private static final int DEF_HP = 1;
    private static final int DEF_EXP = 8;

    protected int shootTimer;
    protected int shootPhase;
    protected int phaseBullet;
    protected int hp;
    protected int exp_value;
    protected int phaseBulletsNumber = 4;
    protected int playerStopDistance;
    protected int timeBetweenPhases;

// CONSTRUCTORS //
    public GoblinFighter(float x, float y, double direction) {
        super(x, y, 64, 64, direction);
        moveSpeed = GOBLIN_FIGHTER_MOVE_SPEED;
        shootTimer = INITIAL_TIME_BEFORE_SHOOTING;
        shootPhase = 1;
        phaseBullet = 0;
        hp = DEF_HP;
        exp_value = DEF_EXP;
        phaseBulletsNumber = DEF_SHOOT_PHASE_BULLET_NUMBER;
        playerStopDistance = DEF_PLAYER_STOP_DISTANCE;
        currentRotateWaitTime = timeBeforeRotating;
        timeBetweenPhases = TIME_BETWEEN_SHOOT_PHASES;
    }

// METHODS //
    @Override
    public void update() {
        super.update();
        if((distanceFromPlayer > playerStopDistance || checkOOB()) && checkMovingIsWorth()) {
            setMoveSpeeds();
        }
        if(directionToPlayer == this.direction) {
            currentRotateWaitTime = timeBeforeRotating;
        }

        move();
        tryShoot();
        if(collision != null) {
            collision.update();
            collision.rotateSprite(direction);
        }
    }

    @Override
    public void collide(Entity ec) {
        if(ec instanceof Game.Entities.Dynamic.Bullets.BulletPlayer) {
            addHP(-2);
        }
        else if(ec instanceof Game.Entities.Dynamic.PlayerEntity) {
            die();
        }
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);

        EntityManager em = handler.getEntityManager();

        // Debug Tool // Draw Line to Player
        if(Settings.DEBUG_GOBLIN_DRAW_LINE_TO_PLAYER) {
            if(distanceFromPlayer > playerStopDistance) {
                g.setColor(Color.ORANGE);
            } else g.setColor(Color.RED);
            g.drawLine(
                    (int) this.xpos + width / 2,
                    (int) this.ypos + height / 2,
                    (int) em.getPlayer().getXpos() + em.getPlayer().getWidth() / 2,
                    (int) em.getPlayer().getYpos() + em.getPlayer().getHeight() / 2
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
        if(phaseBullet < phaseBulletsNumber) {
            if (shootTimer == 0 || shootTimer == TIME_BETWEEN_SHOTS) {
                if (shootTimer == 0) shootPhase = 0;
                else shootPhase = 1;

                double directionUpperBound = directionToPlayer + (Math.PI / (double)12);
                double directionLowerBound = directionToPlayer - (Math.PI / (double)12);

                if (this.direction <= directionUpperBound && this.direction >= directionLowerBound) {
                    shoot();
                }

                phaseBullet++;
            }
            if (shootPhase == 0 && shootTimer < TIME_BETWEEN_SHOTS) shootTimer++;
            else if (shootPhase == 1 && shootTimer > 0) shootTimer--;
        } else {
            phaseBullet = 0;
            shootTimer = timeBetweenPhases;
            shootPhase = 1;
        }
    }

    protected abstract void shoot();

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
        EntityManager em = handler.getEntityManager();
        em.subscribe(new ExpDot(this, exp_value));
        explode();
        em.unsubscribe(this);
        em.unsubscribe(collision);
        handler.getEnemyDirector().unsubscribe(this);
    }

    // Death animation
    protected abstract void explode();
}
