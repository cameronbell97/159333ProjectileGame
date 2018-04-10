package Game;

import Entities.Dynamic.DynamicEntity;
import Entities.Dynamic.Enemies.Asteroid;
import Entities.Dynamic.Enemies.Enemy;
import Entities.EntityManager;
import Timer.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cameron on 7/04/2018.
 */
public class EnemyDirector implements iCanHaveTimer, iCanHaveEnemyTimer {
// VARIABLES //
    private static final int LEVEL_WAIT_TIME = 4*60;
    private static final int LEVEL_DURATION = 90*60;
    private static final int STAGE_1_START = 10;
    private int gameLevel;
    private CodeTimer currentTimer;
    private Handler handler;
    private EntityManager entityManager;
    private TimerManager timerManager;
    private int limboEntities;

    // Enemies
    List<Enemy> remaining_queue; // A list of Enemies
    List<Enemy> unsub_queue; // A list of Enemies
    List<Enemy> spawn_queue; // A list of Enemies
    List<Enemy> dead_queue; // A list of Enemies

// CONSTRUCTORS //
    public EnemyDirector(Handler handler) {
        this.gameLevel = 0;
        limboEntities = 0;
        currentTimer = null;
        this.handler = handler;
        entityManager = EntityManager.get();
        timerManager = TimerManager.get();

        remaining_queue = new ArrayList();
        unsub_queue = new ArrayList();
        spawn_queue = new ArrayList();
        dead_queue = new ArrayList();
    }

    public void update() {

        // Queues
        int nextEnemyTime = Game.getIntFromRange(1*60, 4*60);
        for(Enemy e : spawn_queue) {
            timerManager.newEnemyTimer(nextEnemyTime, this, e);
            limboEntities++;
            nextEnemyTime += Game.getIntFromRange(1*60, 4*60);
        }
        spawn_queue.clear(); // Clear the spawn_queue

        for(Enemy e : dead_queue)
            remaining_queue.remove(e);
        dead_queue.clear(); // Clear the dead_queue


        // Next Level
        if(limboEntities == 0 && remaining_queue.isEmpty() && currentTimer == null)
            currentTimer = timerManager.newTimer(LEVEL_WAIT_TIME, this, "LVL+", 0);
    }

    // METHODS //
    @Override
    public void timerNotify(CodeTimer t) {
        String code = t.getCode();

        TimerManager.get().ubsubTimer(currentTimer);
        currentTimer = null;

        switch (code) {
            case "LVL+":
                gameLevel++;
                populateEnemies();
                break;
        }
    }

    private void populateEnemies() {
        switch (gameLevel) {
            case 1:
                for(int i = 0; i < 2; i++)
                    spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(handler, 0, 0, 2, 0, 1)));
                spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(handler, 0, 0, 3, 0, 1)));
                break;
        }
    }

    private static Enemy generateEnemyPosition(Enemy e) {
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

    public void unsubscribe(Enemy e) {
        unsub_queue.add(e);
    }

    @Override
    public void timerNotify(EnemyTimer t) {
        TimerManager.get().ubsubTimer(t);

        Enemy enem = generateEnemyPosition(t.getEnemy());

        limboEntities--;
        entityManager.subscribe(enem);
        remaining_queue.add(enem);
    }
}
