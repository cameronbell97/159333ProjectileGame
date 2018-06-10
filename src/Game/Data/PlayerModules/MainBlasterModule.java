package Game.Data.PlayerModules;

import Game.Entities.Dynamic.Bullets.BulletPlayer;

public class MainBlasterModule extends BlasterModule {
// CONSTRUCTORS //
    public MainBlasterModule(boolean gun_lock) {
        super(gun_lock);
    }

// METHODS //
    // Method Override - Contains Shoot Mechanics //
    @Override
    protected void shoot() {
        handler.getEntityManager().subscribe(new BulletPlayer(parent));
    }
}
