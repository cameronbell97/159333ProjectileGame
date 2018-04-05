package Timer;

import java.util.ArrayList;
import java.util.List;

/**
 * Cameron Bell - 05/04/2018
 * Timer Manager Class
 * Manages Game Timers player
 */

public class TimerManager {
// SINGLETON PATTERN //
    private static TimerManager self = new TimerManager();
    public static TimerManager get() { return self; }

    // VARIABLES //
    List<Timer> timers;
    List<Timer> sub_queue;
    List<Timer> unsub_queue;

// CONSTRUCTORS //
    public TimerManager() {
        timers = new ArrayList();
        sub_queue = new ArrayList();
        unsub_queue = new ArrayList();
    }

// METHODS //
    public void update() {
        if(timers == null) return;

        // Update Timers
        for(Timer t : timers) {
            t.update();
        }

        // Sub Timers
        for(Timer t : sub_queue) {
            timers.add(t);
        }
        sub_queue.clear();

        // Unsub Timers
        for(Timer t : unsub_queue) {
            timers.remove(t);
        }
        unsub_queue.clear();

    }

    public Timer newTimer(int time, iCanHaveTimer notifiee, String code) {
        Timer t = new Timer(time, notifiee, code);
        sub_queue.add(t);
        return t;
    }

    public void ubsubTimer(Timer t) {
        unsub_queue.add(t);
    }
}
