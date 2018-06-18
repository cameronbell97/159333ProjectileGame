package Game.Entities.Dynamic.Bullets;

// 18/06/18

import Game.Display.Assets.AssetManager;
import Game.Entities.Dynamic.DynamicEntity;
import Game.Entities.Dynamic.Particles.PlayerBulletParticle;
import Game.Entities.Entity;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PlayerPiercingBullet extends PlayerBullet {
// VARIABLES //
    private static final int DEF_MOVESPEED = 14;
    private static final int DEF_DAMAGE_VALUE = 1;
    private static final int DEF_HITS = 3;

    private ArrayList<Entity> collidedEntities;
    private int hitsRemaining;

// CONSTRUCTORS //
    public PlayerPiercingBullet(DynamicEntity parent) {
        super(parent);
        moveSpeed = DEF_MOVESPEED;
        collidedEntities = new ArrayList<>();
        hitsRemaining = DEF_HITS;
    }

// METHODS //
    // Method Override - To Handle Collisions //
    @Override
    public void collide(Entity ec) {
        if(collidedEntities.contains(ec)) return; // If already collided, stop

        if(ec instanceof Game.Entities.Dynamic.Enemies.Enemy) {
            collidedEntities.add(ec);
            deductHit();
            handler.getEntityManager().subscribe(new PlayerBulletParticle(this));
        }
    }

    @Override
    protected void setPosition() {
        // Move bullet to nose of Player ship
        moveSpeed = 19;
        setMoveSpeeds();
        move(1);

        // Reset move speed
        moveSpeed = DEF_MOVESPEED;
        setMoveSpeeds();
    }

    @Override
    protected BufferedImage setSprite() {
        return AssetManager.get().getSprite(20, 2, 0);
    }

    @Override
    protected int setDamageValue() {
        return DEF_DAMAGE_VALUE;
    }

    private void deductHit() {
        hitsRemaining--;
        if(hitsRemaining <= 0) {
            destroy();
        }
    }
}
