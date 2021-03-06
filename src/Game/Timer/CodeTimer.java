package Game.Timer;

import Game.Handler;

/**
 * Cameron Bell - 05/04/2018
 * CodeTimer  Class
 * Ticks Down
 */

public class CodeTimer extends Timer {
// VARIABLES //
    private iCanHaveCodeTimer notifiee;
    private String code;
    private int codenum;

// CONSTRUCTORS //
    public CodeTimer(int time, iCanHaveCodeTimer notifiee, String code) {
        super(time);
        this.notifiee = notifiee;
        this.code = code;
        this.codenum = codenum;
    }

    public CodeTimer(int time, iCanHaveCodeTimer notifiee, String code, int codenum) {
        super(time);
        this.notifiee = notifiee;
        this.code = code;
        this.codenum = codenum;
    }

// METHODS //
    @Override
    protected void notifyFinished() {
        if(notifiee != null) notifiee.timerNotify(this);
        else Handler.get().getTimerManager().unsubTimer(this);
    }

// GETTERS & SETTERS //

    public iCanHaveCodeTimer getNotifiee() {
        return notifiee;
    }

    public String getCode() {
        return code;
    }

    public int getCodeNum() {
        return codenum;
    }
}
