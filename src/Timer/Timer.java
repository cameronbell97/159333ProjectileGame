package Timer;

public abstract class Timer {
// VARIABLES //
    private int ticktime;

// CONSTRUCTORS //

    public Timer(int ticktime) {
        this.ticktime = ticktime;
    }


// METHODS //
    public void update() {
        if(ticktime > 0) ticktime--;
        else {
            notifyFinished();
        }
    }

    protected abstract void notifyFinished();
}
