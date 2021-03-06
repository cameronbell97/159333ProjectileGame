package Game.Entities.Dynamic.Enemies;

import Game.Display.Assets.AssetManager;
import Game.Entities.Collision.CollisionBox;
import Game.Entities.Dynamic.Bullets.GoblinBulletSmall;
import Game.Entities.Dynamic.Particles.DebrisParticle;
import Game.Entities.Dynamic.Particles.EnergyExplParticle;
import Game.Entities.EntityManager;

/**
 * Cameron Bell - 16/04/2018
 * Small Goblin Fighter Class
 * Small Goblin Fighter Enemy
 */

public class GoblinFighterSmall extends GoblinFighter {
// VARIABLES //
    private static final int GOBLIN_FIGHTER_SMALL_DEF_HP = 10;
    private static final int GOBLIN_FIGHTER_SMALL_ROTATE_WAIT_TIME = 20;
    private static final int GOBLIN_FIGHTER_TIME_BEFORE_SHOOTING = 2*60;
    private static final double GOBLIN_FIGHTER_SMALL_ROTATE_SPEED = 0.005 * Math.PI;;

// CONSTRUCTOR //
    public GoblinFighterSmall(float x, float y, double direction) {
        super(x, y, direction);
        img = AssetManager.get().getSprite(1, 0, 3);
        this.hp = GOBLIN_FIGHTER_SMALL_DEF_HP;
        this.exp_value = 12;
        timeBeforeRotating = GOBLIN_FIGHTER_SMALL_ROTATE_WAIT_TIME;
        rotationSpeed = GOBLIN_FIGHTER_SMALL_ROTATE_SPEED;
        shootTimer = GOBLIN_FIGHTER_TIME_BEFORE_SHOOTING;
        timeBetweenPhases = GOBLIN_FIGHTER_TIME_BEFORE_SHOOTING;
    }

    // Method - Shoot Bullet //
    public void shoot() {
        handler.getEntityManager().subscribe(new GoblinBulletSmall(this));
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
                16,
                2,
                1
        ));

        // Head Guns of Ship
        em.subscribe(new DebrisParticle(this, am.getSprite(11, 0, 9)) {
            @Override
            protected void setPosAndDir() {
                double saveSpeed = moveSpeed;

                moveSpeed = 15;
                setMoveSpeeds();
                move(1);

                strafeLeft(5);

                direction += (Math.PI/10);

                moveSpeed = saveSpeed;
            }
        });
        em.subscribe(new DebrisParticle(this, am.getSprite(11, 1, 9)) {
            @Override
            protected void setPosAndDir() {
                double saveSpeed = moveSpeed;

                moveSpeed = 15;
                setMoveSpeeds();
                move(1);

                strafeRight(5);

                direction -= (Math.PI/10);

                moveSpeed = saveSpeed;
            }
        });

        // Thrusters of Ship
        em.subscribe(new DebrisParticle(this, am.getSprite(11, 2, 9)) {
            @Override
            protected void setPosAndDir() {
                double saveSpeed = moveSpeed;

                moveSpeed = -9;
                setMoveSpeeds();
                move(1);

                strafeLeft(6);

                direction += (Math.PI)-(Math.PI/8);

                moveSpeed = saveSpeed;
            }
        });
        em.subscribe(new DebrisParticle(this, am.getSprite(11, 3, 9)) {
            @Override
            protected void setPosAndDir() {
                double saveSpeed = moveSpeed;

                moveSpeed = -9;
                setMoveSpeeds();
                move(1);

                strafeRight(6);

                direction += (Math.PI)+(Math.PI/8);

                moveSpeed = saveSpeed;
            }
        });

        // Sides of Ship
        em.subscribe(new DebrisParticle(this, am.getSprite(11, 0, 10)) {
            @Override
            protected void setPosAndDir() {
                double saveSpeed = moveSpeed;

                moveSpeed = 2;
                setMoveSpeeds();
                move(1);

                strafeLeft(8);

                direction += (Math.PI/2);

                moveSpeed = saveSpeed;
            }
        });
        em.subscribe(new DebrisParticle(this, am.getSprite(11, 1, 10)) {
            @Override
            protected void setPosAndDir() {
                double saveSpeed = moveSpeed;

                moveSpeed = 2;
                setMoveSpeeds();
                move(1);

                strafeRight(8);

                direction += 3*(Math.PI/2);

                moveSpeed = saveSpeed;
            }
        });
    }

    // Method Override - Used for initial spacial setup for the Collision Box //
    @Override
    public void setCollisionBox() {
        collision = new CollisionBox(xpos+21, ypos+17, 22, 30, 21, 17, this);
    }


}
