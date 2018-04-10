package Game;

import Entities.Dynamic.DynamicEntity;
import Entities.Dynamic.Enemies.Asteroid;
import Entities.Entity;
import Entities.EntityManager;
import Timer.*;

/**
 * Created by Cameron on 7/04/2018.
 */
public class EnemyDirector implements iCanHaveTimer {
// VARIABLES //
    private static final int STAGE_0_DURATION = 4*60;
    private static final int STAGE_1_START = 10;
    private int gameLevel;
    private Timer currentTimer;
    private Handler handler;

// CONSTRUCTORS //
    public EnemyDirector() {
        this.gameLevel = 0;
        currentTimer = null;
        this.handler = handler;
    }

    public void update() {
        switch (gameLevel) {
            case 0:
                if(currentTimer == null)
                    currentTimer = TimerManager.get().newTimer(STAGE_0_DURATION, this, "LVL0", 0);
                break;
            case 1:
                if(currentTimer == null)
//                    EntityManager.get().subscribe(new Asteroid(handler, ));
//                    currentTimer = TimerManager.get().newTimer();
                break;
        }
    }

    // METHODS //
    @Override
    public void timerNotify(Timer t) {
        String code = t.getCode();
        int codenum = t.getCodeNum();

        TimerManager.get().ubsubTimer(currentTimer);
        currentTimer = null;

        // Enemy Spawning Mechanics
        switch (code) {
            case "LVL0":
                gameLevel++;
                break;
        }
    }

    public static DynamicEntity generateEnemyPosition(DynamicEntity e) {
        float newXpos = Game.getFloatFromRange(-64, Launcher.DEF_GAME_HEIGHT);
        float newYpos = Game.getFloatFromRange(-64, Launcher.DEF_GAME_WIDTH);

        switch (Game.getIntFromRange(0, 1)) {
            case 0:
                if(newXpos < (Launcher.DEF_GAME_HEIGHT+64)/2) newXpos = -64;
                else newXpos = Launcher.DEF_GAME_HEIGHT;
                break;
            case 1:
                if(newYpos < (Launcher.DEF_GAME_WIDTH+64)/2) newXpos = -64;
                else newYpos = Launcher.DEF_GAME_WIDTH;
                break;
        }

        e.setXpos(newXpos);
        e.setYpos(newYpos);

        // determine direction
        e.setDirection(calculateDir(e));

        e.setMoveSpeeds();

        return e;
    }

    private static double calculateDir(DynamicEntity e) {
        double newDir = 0;

        // Define point variables for easier function designing
        float P1x = e.getXpos();
        float P1y = e.getYpos();
        float P2x = Launcher.DEF_GAME_WIDTH/2;
        float P2y = Launcher.DEF_GAME_HEIGHT/2;

        // Determine right-angled-triangle opposite and adjacent lengths
        float triangleX = Math.abs(P2x - P1x);
        float triangleY = Math.abs(P2y - P1y);

        // In the event tx = 0, don't do calculations
        if(triangleX == 0) {
            if(P1y > P2y) return Math.PI / 2;
            else return (3*Math.PI) / 2;
        }

        //tan(theta) = (ty / tx)
        double theta = Math.atan(triangleY /triangleX);

        // Determine direction depending on P1's location
        if(P1x > P2x && P1y < P2y) {
            // Quadrant 1
            newDir = Math.PI + theta;
        }
        else if(P1x < P2x && P1y < P2y) {
            // Quadrant 2
            newDir = -theta;
        }
        else if(P1x < P2x && P1y > P2y) {
            // Quadrant 3
            newDir = theta;
        }
        else if(P1x > P2x && P1y > P2y) {
            // Quadrant 4
            newDir = Math.PI - theta;
        }

        // Maybe do some math here to vary the direction

        return newDir;
    }
}
