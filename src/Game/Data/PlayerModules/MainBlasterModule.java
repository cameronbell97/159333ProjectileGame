package Game.Data.PlayerModules;

import Game.Entities.Dynamic.Bullets.PlayerBlasterBullet;

public class MainBlasterModule extends BlasterModule {
// VARIABLES //
    // Statics //
    public static final int BLASTER_RELOAD_SPEED = 8; // 60 = 1 second

// CONSTRUCTORS //
    public MainBlasterModule() {
        super(true);
        reload_speed = BLASTER_RELOAD_SPEED;
    }

// METHODS //
    // Method Override - Contains Shoot Mechanics //
    @Override
    protected void shoot() {
        handler.getEntityManager().subscribe(new PlayerBlasterBullet(parent));
    }
}
