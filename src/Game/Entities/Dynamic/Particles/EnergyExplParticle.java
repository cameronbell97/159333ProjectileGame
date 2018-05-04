package Game.Entities.Dynamic.Particles;

import Game.Display.Assets.AssetManager;
import Game.Entities.Dynamic.DynamicEntity;
import Game.Entities.Entity;

import java.awt.*;

/**
 * Created by Cameron on 4/05/2018.
 */
public class EnergyExplParticle extends Particle {
// VARIABLES //
    private static final int DEF_LINGER_TIME = 15;
    private int ticksLeft;

// CONSTRUCTORS //
    public EnergyExplParticle(DynamicEntity parent) {
        super(  parent.getXpos() + (parent.getWidth() / 2) - (DEF_PARTICLE_WIDTH / 2),
                parent.getYpos() + (parent.getHeight() / 2) - (DEF_PARTICLE_HEIGHT / 2),
                DEF_PARTICLE_WIDTH,
                DEF_PARTICLE_HEIGHT,
                0
        );
        img = AssetManager.get().getSprite(11, 3, 7);
        ticksLeft = DEF_LINGER_TIME;
    }

// METHODS //
    @Override
    public void update() {
        if(ticksLeft <= 0) {
            destroy();
        } else {
            if(ticksLeft == 10) {
                img = AssetManager.get().getSprite(11, 2, 7);
            } else if(ticksLeft == 5) {
                img = AssetManager.get().getSprite(11, 1, 7);
            }

            ticksLeft--;
        }
    }

    @Override
    public void collide(Entity ec) {

    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(img, (int)xpos, (int)ypos, width, height, null);
    }
}
