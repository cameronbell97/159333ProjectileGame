package Game.Timer;

import Game.Entities.Dynamic.Enemies.Enemy;

import java.util.ArrayList;
import java.util.List;

/**
 * Cameron Bell - 05/04/2018
 * Game.Timer Manager Class
 * Manages Game Timers
 */

public class TimerManager {
// VARIABLES //
    boolean alive = true;

    // Lists of Timers
    List<Timer> timers;
    List<Timer> sub_queue;
    List<Timer> unsub_queue;

// CONSTRUCTORS //
    public TimerManager() {
        // Instantiate Game.Timer Lists
        timers = new ArrayList();
        sub_queue = new ArrayList();
        unsub_queue = new ArrayList();
    }

// METHODS //
    // Method - Update Timers
    public void update(int dt) {
        if(timers == null) return; // Safeguard

        // Update Subscribed Timers
        for(Timer t : timers) {
            t.update(dt);
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

    // Method - Unsubscribe Game.Timer
    public void unsubTimer(Timer t) {
        unsub_queue.add(t);
    }

    public void clear() {
        for (Timer timer : timers) {
            unsubTimer(timer);
        }
        alive = false;
    }

    public boolean isAlive() {
        return alive;
    }
}
