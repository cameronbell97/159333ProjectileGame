package Game.Entities.Dynamic.Enemies;

import Game.Data.Settings;
import Game.Entities.*;
import Game.Entities.Dynamic.Bullets.PlayerBullet;
import Game.Entities.Dynamic.ExpDot;

import java.awt.*;

/**
 * Cameron Bell - 16/04/2018
 * Goblin Fighter Abstract Class
 * Encompasses Goblin Fighter Mechanics
 */

public abstract class GoblinFighter extends TargetingEnemy implements iOutOfBounds, iVulnerable {
// VARIABLES //
    private static final double GOBLIN_FIGHTER_MOVE_SPEED = 1.4;
    private static final int
            DEF_PLAYER_STOP_DISTANCE = 500,
            OFFSCREEN_BOUNDARY = -32,
            INITIAL_TIME_BEFORE_SHOOTING = 3*60,
            TIME_BETWEEN_SHOOT_PHASES = 3*60,
            TIME_BETWEEN_SHOTS = 25,
            DEF_SHOOT_PHASE_BULLET_NUMBER = 4,
            DEF_HP = 1,
            DEF_EXP = 8;

    protected int
            shootTimer,
            shootPhase,
            phaseBullet,
            hp,
            exp_value,
            phaseBulletsNumber = 4,
            playerStopDistance,
            timeBetweenPhases;

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
    // Method Override - Update Entity State //
    @Override
    public void update(int dt) {
        super.update(dt);
        if((distanceFromPlayer > playerStopDistance || checkOOBX() || checkOOBY()) && checkMovingIsWorth()) {
            setMoveSpeeds();
        }
        if(directionToPlayer == this.direction) {
            currentRotateWaitTime = timeBeforeRotating;
        }

        move(dt);
        tryShoot(dt);
        if(collision != null) {
            collision.update(dt);
            collision.rotateSprite(direction);
        }
    }

    // Method Override - To Handle Collisions //
    @Override
    public void collide(Entity ec) {
        if(ec instanceof PlayerBullet) {
            addHP(-((PlayerBullet) ec).getDamageValue());
        }
        else if(ec instanceof Game.Entities.Dynamic.PlayerEntity) {
            die();
        }
    }

    // Methpd - Draw Goblin to Screen //
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
                    (int) (this.xpos + width / 2) + (int) (getDebugLineX() * 48),
                    (int) (this.ypos + height / 2) + (int) (getDebugLineY() * 48)
            );
        }
    }

    // Method - Will try to shoot bullets at the player if it can //
    private void tryShoot(int dt) {
        if(phaseBullet < phaseBulletsNumber) {
            if (shootTimer == 0 || shootTimer == TIME_BETWEEN_SHOTS) {
                if (shootTimer == 0) shootPhase = 0;
                else shootPhase = 1;

                double directionUpperBound = directionToPlayer + (Math.PI / (double)12);
                double directionLowerBound = directionToPlayer - (Math.PI / (double)12);

                if (this.direction <= directionUpperBound && this.direction >= directionLowerBound) {
                    shoot();
                }

                phaseBullet+=dt;
            }
            if (shootPhase == 0 && shootTimer < TIME_BETWEEN_SHOTS) shootTimer+=dt;
            else if (shootPhase == 1 && shootTimer > 0) shootTimer-=dt;
        } else {
            phaseBullet = 0;
            shootTimer = timeBetweenPhases;
            shootPhase = 1;
        }
    }

    // Abstract Method - Shoot Bullets //
    protected abstract void shoot();

    // Method Override - Check if the Goblin is Out Of Bounds //
    @Override
    public boolean checkOOBX() {
        if(xpos <= -OFFSCREEN_BOUNDARY || xpos >= Settings.game_width + OFFSCREEN_BOUNDARY){
            return true;
        }

        return false;
    }

    @Override
    public boolean checkOOBY() {
        if(ypos <= -OFFSCREEN_BOUNDARY || ypos >= Settings.game_height + OFFSCREEN_BOUNDARY){
            return true;
        }

        return false;
    }

    // Method Override - Do when the Goblin is Out Of Bounds //
    @Override
    public void doWhenOutOfBounds(int dt, boolean shiftX) {

    }

    // Method - Add HP (remove using negative integers) //
    @Override
    public void addHP(int hp) {
        this.hp += hp;
        if(this.hp <= 0) {
            die();
        }
    }

    // Method - Destroy Goblin //
    @Override
    public void die() {
        EntityManager em = handler.getEntityManager();
        em.subscribe(new ExpDot(this, exp_value));
        explode();
        em.unsubscribe(this);
        em.unsubscribe(collision);
        handler.getEnemyDirector().unsubscribe(this);
    }

    // Abstract Method - Death Animation //
    protected abstract void explode();

    // Methods - Gets information for drawing line for debug mode //
    private float getDebugLineX() {
        return (float)(moveSpeed * Math.cos(direction));
    }
    private float getDebugLineY() {
        return (float)(moveSpeed * -Math.sin(direction));
    }

// GETTERS & SETTERS //
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
}
