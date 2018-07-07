package Game.Data.PlayerModules;

/* Cameron Bell - 18/09/18
 *
 */

import Game.Entities.Dynamic.Bullets.PlayerPiercingBullet;

public class PierceCannonModule extends BlasterModule {
// VARRIABLES //
    // Statics //
    private static final int PIERCE_RELOAD_SPEED = 12; // 60 = 1 second

// CONSTRUCTORS //
    public PierceCannonModule() {
        super(true);
        reload_speed = PIERCE_RELOAD_SPEED;
    }

// METHODS //
    // Method Override - Contains Shoot Mechanics //
    @Override
    protected void shoot() {
        handler.getEntityManager().subscribe(new PlayerPiercingBullet(parent));
    }
}
