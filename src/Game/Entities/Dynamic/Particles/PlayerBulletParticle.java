package Game.Entities.Dynamic.Particles;

import Game.Display.Assets.AssetManager;
import Game.Entities.Dynamic.Bullets.PlayerBullet;
import Game.Entities.Entity;

public class PlayerBulletParticle extends Particle {
// VARIABLES //
    private static final int DEF_LIVE_TIME = 10;
    private int timeAlive;

// CONSTRUCTORS //
    public PlayerBulletParticle(PlayerBullet parent) {
        super(  parent.getXpos() + (parent.getWidth() / 2) - (DEF_PARTICLE_WIDTH / 2),
                parent.getYpos() + (parent.getHeight() / 2) - (DEF_PARTICLE_HEIGHT / 2),
                DEF_PARTICLE_WIDTH,
                DEF_PARTICLE_HEIGHT,
                parent.getRealDirection()
        );
        timeAlive = DEF_LIVE_TIME;
        img = AssetManager.get().getSprite(11, 2, 10);
    }

// METHODS //
    // Method Override - Update Particle Entity State //
    @Override
    public void update(int dt) {
        if(timeAlive > 0) {
            if(timeAlive > (DEF_LIVE_TIME/2))
                img = AssetManager.get().getSprite(11, 2, 10);
            else
                img = AssetManager.get().getSprite(11, 3, 10);

            timeAlive-=dt;
        } else {
            destroy();
        }
    }

    // Method Override - To Handle Collisions //
    @Override
    public void collide(Entity ec) {

    }
}
