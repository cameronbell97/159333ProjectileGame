package Game.Entities.Dynamic.Particles;

import Game.Display.Assets.AssetManager;
import Game.Entities.Entity;

import java.awt.*;

/**
 * Cameron Bell - 04/05/2018
 * Energy Explosion Particle
 */
public class EnergyExplParticle extends Particle {
// VARIABLES //
    // Statics //
    private static final int Y_SPR_YELLOW = 7;
    private static final int Y_SPR_RED = 13;

    // Data //
    private int ticksStart;
    private int ticksLeft;
    private int stages;
    private int ySprExpl;

// CONSTRUCTORS //
    public EnergyExplParticle(int xpos, int ypos, int ticks, int stages, int type) { // TYPE 0 = yellow, 1 = red
        super(  xpos - (DEF_PARTICLE_WIDTH / 2),
                ypos - (DEF_PARTICLE_HEIGHT / 2),
                DEF_PARTICLE_WIDTH,
                DEF_PARTICLE_HEIGHT,
                0
        );

        // Set Particle Spritesheet y Position
        switch (type) {
            case 1:
                ySprExpl = Y_SPR_RED;
                break;
            default:
                ySprExpl = Y_SPR_YELLOW;
        }

        ticksStart = ticks;
        ticksLeft = ticksStart;

        // Set Stages
        if(stages > 3 || stages < 1) this.stages = 3;
        else this.stages = stages;

        // Get initial sprite
        if (stages == 1) {
                AssetManager.get().getSprite(11, 1, ySprExpl);
        } else if (stages == 2) {
            img = AssetManager.get().getSprite(11, 2, ySprExpl);
        } else {
            img = AssetManager.get().getSprite(11, 3, ySprExpl);
        }
    }

// METHODS //
    // Method Override - Update Particle Entity State //
    @Override
    public void update(int dt) {
        if(ticksLeft <= 0) destroy();
        else { // Else set sprite
            if(ticksLeft <= ticksStart / 3) img = AssetManager.get().getSprite(11, 1, ySprExpl);
            else if(ticksLeft <= 2 * (ticksStart / 3)) img = AssetManager.get().getSprite(11, 2, ySprExpl);
            else img = AssetManager.get().getSprite(11, 3, ySprExpl);
        }

        ticksLeft-=dt;
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
