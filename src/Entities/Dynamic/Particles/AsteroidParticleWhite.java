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
        return AssetManager.get().getSprite(10, Game.Game.getIntFromRange(0, 3), 1);
    }
}
