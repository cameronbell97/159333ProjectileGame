package Game.Timer;

import Game.Entities.Dynamic.Enemies.Enemy;
import Game.Handler;

public class EnemyTimer extends Timer {
// VARIABLES //
    private iCanHaveEnemyTimer notifiee;
    Enemy enemy;

// CONSTRUCTORS
    public EnemyTimer(int time, iCanHaveEnemyTimer notifiee, Enemy enemy) {
        super(time);
        this.notifiee = notifiee;
        this.enemy = enemy;
    }

// METHODS //
    @Override
    protected void notifyFinished() {
        if(notifiee != null) notifiee.timerNotify(this);
        else Handler.get().getTimerManager().unsubTimer(this);
    }

// GETTERS & SETTERS //
    public Enemy getEnemy() {
        return enemy;
    }


}
