package Game.Entities.Dynamic.Particles;

import Game.Display.Assets.AssetManager;
import Game.Entities.Dynamic.DynamicEntity;
import Game.Handler;

import java.awt.image.BufferedImage;

/**
 * Created by Cameron on 15/04/2018.
 * White Asteroid Particle Entity
 * White Asteroid Debris Particle Effect
 */

public class AsteroidParticleWhite extends AsteroidParticle{
// CONSTRUCTORS //
    public AsteroidParticleWhite(DynamicEntity parent, Double direction, int level) {
        super(parent, direction, level);
    }

// METHODS //
    // Method Override - Get Sprite Correlating to Level //
    @Override
    protected BufferedImage getSprite() {
        if(level < 3) return AssetManager.get().getSprite(11, Handler.getIntFromRange(0, 3), 5);
        else  return AssetManager.get().getSprite(11, Handler.getIntFromRange(0, 3), 6);
    }
}
