package Timer;

import Entities.Dynamic.Enemies.Enemy;

import java.util.ArrayList;
import java.util.List;

/**
 * Cameron Bell - 05/04/2018
 * Timer Manager Class
 * Manages Game Timers
 */

public class TimerManager {
// SINGLETON PATTERN //
    private static TimerManager self = new TimerManager();
    public static TimerManager get() { return self; }

// VARIABLES //
    // Lists of Timers
    List<Timer> timers;
    List<Timer> sub_queue;
    List<Timer> unsub_queue;

// CONSTRUCTORS //
    private TimerManager() {
        // Instantiate Timer Lists
        timers = new ArrayList();
        sub_queue = new ArrayList();
        unsub_queue = new ArrayList();
    }

// METHODS //
    // Method - Update Timers
    public void update() {
        if(timers == null) return; // Safeguard

        // Update Subscribed Timers
        for(Timer t : timers) {
            t.update();
        }

        // Subscribe Queued Timers
        for(Timer t : sub_queue) {
            timers.add(t);
        }
        sub_queue.clear();

        // Unsubscribe Queued Timers
        for(Timer t : unsub_queue) {
            timers.remove(t);
        }
        unsub_queue.clear();
    }

    // Method - Subscribe New CodeTimer
    public CodeTimer newCodeTimer(int time, iCanHaveCodeTimer notifiee, String code) {
        CodeTimer t = new CodeTimer(time, notifiee, code);
        sub_queue.add(t);
        return t;
    }

    // Method - Subscribe New CodeTimer
    public CodeTimer newCodeTimer(int time, iCanHaveCodeTimer notifiee, String code, int codenum) {
        CodeTimer t = new CodeTimer(time, notifiee, code, codenum);
        sub_queue.add(t);
        return t;
    }

    // Method - Subscribe New EnemyTimer
    public EnemyTimer newEnemyTimer(int time, iCanHaveEnemyTimer notifiee, Enemy enemy) {
        EnemyTimer t = new EnemyTimer(time, notifiee, enemy);
        sub_queue.add(t);
        return t;
    }

    // Method - Unsubscribe Timer
    public void unsubTimer(Timer t) {
        unsub_queue.add(t);
    }
}
