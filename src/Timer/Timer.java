package Timer;

import Game.iCanHaveTimer;

/**
 * Cameron Bell - 05/04/2018
 * Timer  Class
 * Ticks Down
 */

public class Timer {
// VARIABLES //
    private iCanHaveTimer notifiee;
    private int ticktime;
    private String code;

// CONSTRUCTORS //
    public Timer(int time, iCanHaveTimer notifiee, String code) {
        ticktime = time;
        this.notifiee = notifiee;
        this.code = code;
    }

// METHODS //
    public void update() {
        if(ticktime > 0) ticktime--;
        else {
            notifiee.timerNotify(this);
        }
    }

// GETTERS & SETTERS //

    public iCanHaveTimer getNotifiee() {
        return notifiee;
    }

    public String getCode() {
        return code;
    }
}
