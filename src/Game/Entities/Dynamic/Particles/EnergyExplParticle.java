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
    private int ySprExpl;

// CONSTRUCTORS //
    public EnergyExplParticle(DynamicEntity parent) {
        super(  parent.getXpos() + (parent.getWidth() / 2) - (DEF_PARTICLE_WIDTH / 2),
                parent.getYpos() + (parent.getHeight() / 2) - (DEF_PARTICLE_HEIGHT / 2),
                DEF_PARTICLE_WIDTH,
                DEF_PARTICLE_HEIGHT,
                0
        );
        ySprExpl = 7;
        img = AssetManager.get().getSprite(11, 3, ySprExpl);
        ticksLeft = DEF_LINGER_TIME;
    }
    public EnergyExplParticle(DynamicEntity parent, int ticks, int type) { // TYPE 0 = yellow, 1 = red
        super(  parent.getXpos() + (parent.getWidth() / 2) - (DEF_PARTICLE_WIDTH / 2),
                parent.getYpos() + (parent.getHeight() / 2) - (DEF_PARTICLE_HEIGHT / 2),
                DEF_PARTICLE_WIDTH,
                DEF_PARTICLE_HEIGHT,
                0
        );

        switch (type) {
            case 1:
                ySprExpl = 12;
                break;
            default:
                ySprExpl = 7;
        }

        if (ticks <= (DEF_LINGER_TIME / 3)) {
                AssetManager.get().getSprite(11, 2, ySprExpl);
        } else {
            img = AssetManager.get().getSprite(11, 3, ySprExpl);
        }

        ticksLeft = ticks;
    }

// METHODS //
    // Method Override - Update Particle Entity State //
    @Override
    public void update(int dt) {
        if(ticksLeft <= 0) {
            destroy();
        } else {
            if(ySprExpl == 7) {
                if (ticksLeft <= 10 && ticksLeft > 5) {
                    img = AssetManager.get().getSprite(11, 2, ySprExpl);
                } else if (ticksLeft <= 5) {
                    img = AssetManager.get().getSprite(11, 1, ySprExpl);
                }
            } else if (ySprExpl == 12) {
                if (ticksLeft <= 10 && ticksLeft > 5) {
                    img = AssetManager.get().getSprite(11, 3, ySprExpl);
                } else if (ticksLeft <= 5) {
                    img = AssetManager.get().getSprite(11, 2, ySprExpl);
                }
            }

            ticksLeft-=dt;
        }
    }

    // Method Override - To Handle Collisions //
    @Override
    public void collide(Entity ec) {

    }

    // Method Override - Draw Particle Entity State //
    @Override
    public void draw(Graphics g) {
        g.drawImage(img, (int)xpos, (int)ypos, width, height, null);
    }
}
