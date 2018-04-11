package Entities.Dynamic.Particles;

import Entities.Dynamic.DynamicEntity;
import Entities.EntityManager;
import Timer.*;

/**
 * Cameron Bell - 05/04/2018
 * Particle Abstract Class
 */

public abstract class Particle extends DynamicEntity implements iCanHaveCodeTimer {
// VARIABLES //
    public static final int DEF_PARTICLE_WIDTH = 8;
    public static final int DEF_PARTICLE_HEIGHT = 8;

// CONSTRUCTORS //
    public Particle(float x, float y, int w, int h, double direction) {
        super(x, y, w, h, direction);
    }

// METHODS //
    @Override
    public void timerNotify(CodeTimer t) {
        String timerCode = t.getCode(); // Get timer code
        TimerManager.get().unsubTimer(t); // Unsubscribe the timer

        switch (timerCode) {
            case "DIE":
                destroy();
                break;
        }
    }

    protected void destroy() {
        EntityManager.get().unsubscribe(this);
    }
}