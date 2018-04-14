package Entities.Dynamic.Particles;

import Assets.AssetManager;
import Entities.Dynamic.DynamicEntity;

import java.awt.image.BufferedImage;

public class AsteroidParticleWhite extends AsteroidParticle{
    public AsteroidParticleWhite(DynamicEntity parent, Double direction, int level) {
        super(parent, direction, level);
    }

    @Override
    protected BufferedImage getSprite() {
        if(level < 3) return AssetManager.get().getSprite(11, Game.Game.getIntFromRange(0, 3), 5);
        else  return AssetManager.get().getSprite(11, Game.Game.getIntFromRange(0, 3), 6);
    }
}
