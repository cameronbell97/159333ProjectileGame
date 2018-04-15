package Game.Timer;

import Game.Entities.Dynamic.Enemies.Enemy;

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
        notifiee.timerNotify(this);
    }

// GETTERS & SETTERS //
    public Enemy getEnemy() {
        return enemy;
    }


}
