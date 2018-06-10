package Game.Data.PlayerModules;

import Game.Entities.Dynamic.Bullets.BulletPlayer;
import Game.Entities.Dynamic.PlayerEntity;
import Game.Timer.CodeTimer;
import Game.Timer.iCanHaveCodeTimer;

public class MainBlasterModule extends WeaponModule implements iCanHaveCodeTimer {
// VARIABLES //
    // Statics //
    private static final String DEF_MODULE_NAME = "Blaster";
    public static final int DEF_RELOAD_SPEED = 8; // 60 = 1 second

    // Data //
    private boolean reloaded;

// CONSTRUCTORS //
    public MainBlasterModule(PlayerEntity parent) {
        super(DEF_MODULE_NAME, parent);
        reloaded = true;
    }

// METHODS //
    // Method - Contains condition checking for shoot attempts
    @Override
    public void tryShoot() {
        if(reloaded) {
            shoot();
            handler.getTimerManager().newCodeTimer(DEF_RELOAD_SPEED, this, "REL");
            reloaded = false;
        }
    }

    // Method - Contains Shoot Mechanics
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
