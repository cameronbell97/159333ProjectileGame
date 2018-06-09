package Game.Entities.Dynamic.Enemies;

import Game.Display.Assets.AssetManager;
import Game.Entities.Collision.CollisionBox;
import Game.Entities.Dynamic.Bullets.GoblinBulletLarge;
import Game.Entities.Dynamic.Particles.DebrisParticle;
import Game.Entities.Dynamic.Particles.EnergyExplParticle;
import Game.Entities.EntityManager;

/**
 * Cameron Bell - 15/04/2018
 * Large Goblin Fighter Class
 * Large Goblin Fighter Enemy
 */

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

    // Method - Shoot Bullet //
    public void shoot() {
        handler.getEntityManager().subscribe(new GoblinBulletLarge(this));
    }

    // Method - Explode out ship particles on death //
    @Override
    protected void explode() {
        EntityManager em = handler.getEntityManager();
        AssetManager am = AssetManager.get();

        // Explosion
        em.subscribe(new EnergyExplParticle(
                (int)xpos + width / 2,
                (int)ypos + height / 2,
                24,
                3,
                1
        ));

        // Head Guns of Ship
        em.subscribe(new DebrisParticle(this, am.getSprite(11, 0, 11)) {
            @Override
            protected void setPosAndDir() {
                double saveSpeed = moveSpeed;

                moveSpeed = 20;
                setMoveSpeeds();
                move(1);

                strafeLeft(9);

                direction += (Math.PI/10);

                moveSpeed = saveSpeed;
            }
        });
        em.subscribe(new DebrisParticle(this, am.getSprite(11, 1, 11)) {
            @Override
            protected void setPosAndDir() {
                double saveSpeed = moveSpeed;

                moveSpeed = 20;
                setMoveSpeeds();
                move(1);

                strafeRight(9);

                direction -= (Math.PI/10);

                moveSpeed = saveSpeed;
            }
        });

        // Sides of Ship
        em.subscribe(new DebrisParticle(this, am.getSprite(11, 0, 12)) {
            @Override
            protected void setPosAndDir() {
                double saveSpeed = moveSpeed;

                moveSpeed = 4;
                setMoveSpeeds();
                move(1);

                strafeLeft(11);

                direction += (Math.PI/2);

                moveSpeed = saveSpeed;
            }
        });
        em.subscribe(new DebrisParticle(this, am.getSprite(11, 1, 12)) {
            @Override
            protected void setPosAndDir() {
                double saveSpeed = moveSpeed;

                moveSpeed = 4;
                setMoveSpeeds();
                move(1);

                strafeRight(11);

                direction += 3*(Math.PI/2);

                moveSpeed = saveSpeed;
            }
        });

        // Thrusters of Ship
        em.subscribe(new DebrisParticle(this, am.getSprite(11, 2, 11)) {
            @Override
            protected void setPosAndDir() {
                double saveSpeed = moveSpeed;

                moveSpeed = -16;
                setMoveSpeeds();
                move(1);

                strafeLeft(11);

                direction += (Math.PI)-(Math.PI/8);

                moveSpeed = saveSpeed;
            }
        });
        em.subscribe(new DebrisParticle(this, am.getSprite(11, 3, 11)) {
            @Override
            protected void setPosAndDir() {
                double saveSpeed = moveSpeed;

                moveSpeed = -16;
                setMoveSpeeds();
                move(1);

                strafeRight(11);

                direction += (Math.PI)+(Math.PI/8);

                moveSpeed = saveSpeed;
            }
        });

    }

    // Method Override - Used for initial spacial setup for the Collision Box //
    @Override
    public void setCollisionBox() {
        collision = new CollisionBox(xpos+15, ypos+9, 34, 45, 15, 9, this);
    }
}
