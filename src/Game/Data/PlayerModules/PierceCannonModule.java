package Game.Data.PlayerModules;

/* Cameron Bell - 18/09/18
 *
 */

import Game.Entities.Dynamic.Bullets.PlayerPiercingBullet;

public class PierceCannonModule extends BlasterModule {
// CONSTRUCTORS //
    public PierceCannonModule() {
        super(true);
    }

// METHODS //
    // Method Override - Contains Shoot Mechanics //
    @Override
    protected void shoot() {
        handler.getEntityManager().subscribe(new PlayerPiercingBullet(parent));
    }
}
