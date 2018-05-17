package Game.Entities.Dynamic.Particles;

import Game.Display.Assets.AssetManager;
import Game.Entities.Dynamic.DynamicEntity;
import Game.Handler;

import java.awt.image.BufferedImage;

public class AsteroidParticleWhite extends AsteroidParticle{
    public AsteroidParticleWhite(DynamicEntity parent, Double direction, int level) {
        super(parent, direction, level);
    }

    @Override
    protected BufferedImage getSprite() {
        if(level < 3) return AssetManager.get().getSprite(11, Handler.getIntFromRange(0, 3), 5);
        else  return AssetManager.get().getSprite(11, Handler.getIntFromRange(0, 3), 6);
    }
}
