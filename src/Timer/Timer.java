package Timer;

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
    private int codenum;

// CONSTRUCTORS //
    public Timer(int time, iCanHaveTimer notifiee, String code) {
        ticktime = time;
        this.notifiee = notifiee;
        this.code = code;
        this.codenum = codenum;
    }

    public Timer(int time, iCanHaveTimer notifiee, String code, int codenum) {
        ticktime = time;
        this.notifiee = notifiee;
        this.code = code;
        this.codenum = codenum;
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

    public int getCodeNum() {
        return codenum;
    }
}
