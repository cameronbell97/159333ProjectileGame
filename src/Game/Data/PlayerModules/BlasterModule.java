package Game.Data.PlayerModules;

import Game.Data.KeyManager;
import Game.Timer.CodeTimer;
import Game.Timer.iCanHaveCodeTimer;

public abstract class BlasterModule extends WeaponModule implements iCanHaveCodeTimer {
// VARIABLES //
    // Statics //
    private static final String DEF_MODULE_NAME = "Blaster";
    public static final int DEF_RELOAD_SPEED = 1; // 60 = 1 second

    // Managers //
    KeyManager km;

    // Data //
    private boolean gun_lock;
    private boolean reloaded;
    private boolean released;
    protected int reload_speed;

// CONSTRUCTORS //
    public BlasterModule(boolean gun_lock) {
        super(DEF_MODULE_NAME);
        km = handler.getKeyManager();
        this.gun_lock = gun_lock;
        reloaded = true;
        released = true;
        reload_speed = DEF_RELOAD_SPEED;
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
            handler.getTimerManager().newCodeTimer(reload_speed, this, "REL");
            reloaded = false;

            /* The following command is placed here instead of in the update function so that if the player
             *  presses the spacebar a little too early, the gun will still fire at the next free interval,
             *  while still not firing at following free intervals unless the player has released the key.
             */
            if(gun_lock) released = false;
        }
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
