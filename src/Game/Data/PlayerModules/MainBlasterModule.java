package Game.Data.PlayerModules;

import Game.Entities.Dynamic.Bullets.PlayerBlasterBullet;
import Game.Entities.Dynamic.Bullets.PlayerBullet;

public class MainBlasterModule extends BlasterModule {
// CONSTRUCTORS //
    public MainBlasterModule(boolean gun_lock) {
        super(gun_lock);
    }

// METHODS //
    // Method Override - Contains Shoot Mechanics //
    @Override
    protected void shoot() {
        handler.getEntityManager().subscribe(new PlayerBlasterBullet(parent));
    }
}
