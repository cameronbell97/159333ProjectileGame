package Game.Data.PlayerModules;

import Game.Data.KeyManager;
import Game.Data.Settings;
import Game.Entities.Dynamic.Bullets.BulletPlayer;
import Game.Entities.Dynamic.PlayerEntity;
import Game.Timer.CodeTimer;
import Game.Timer.iCanHaveCodeTimer;

public class MainBlasterModule extends WeaponModule implements iCanHaveCodeTimer {
// VARIABLES //
    // Statics //
    private static final String DEF_MODULE_NAME = "Blaster";
    public static final int DEF_RELOAD_SPEED = 8; // 60 = 1 second

    // Managers //
    KeyManager km;

    // Data //
    private boolean gun_lock;
    private boolean reloaded;
    private boolean released;

// CONSTRUCTORS //
    public MainBlasterModule(PlayerEntity parent) {
        super(DEF_MODULE_NAME, parent);
        km = handler.getKeyManager();
        gun_lock = Settings.player_gun_lock;
        reloaded = true;
        released = true;
    }

// METHODS //
    // Method Override - Update the module state //
    @Override
    public void update(int dt) {
        if(!km.spacebar) released = true;
    }

    // Method Override - Contains condition checking for shoot attempts //
    @Override
    public void tryShoot() {
        if(reloaded && released) {
            shoot();
            handler.getTimerManager().newCodeTimer(DEF_RELOAD_SPEED, this, "REL");
            reloaded = false;

            /* The following command is placed here instead of in the update function so that if the player
             *  presses the spacebar a little too early, the gun will still fire at the next free interval,
             *  while still not firing at following free intervals unless the player has released the key.
             */
            if(gun_lock) released = false;
        }
    }

    // Method Override - Contains Shoot Mechanics //
    @Override
    protected void shoot() {
        handler.getEntityManager().subscribe(new BulletPlayer(parent));
    }

    // Method Override - Recieve Timer Notification //
    @Override
    public void timerNotify(CodeTimer t) {
        String timerCode = t.getCode(); // Get timer code

        switch (timerCode) {
            case "REL":
                reloaded = true;
                break;

        }
        handler.getTimerManager().unsubTimer(t); // Unsubscribe the timer
    }
}
