package Game.Timer;

public abstract class Timer {
// VARIABLES //
    private int ticktime;

// CONSTRUCTORS //

    public Timer(int ticktime) {
        this.ticktime = ticktime;
    }


// METHODS //
    public void update(int dt) {
        if(ticktime > 0) ticktime-=dt;
        else {
            notifyFinished();
        }
    }

    protected abstract void notifyFinished();
}
