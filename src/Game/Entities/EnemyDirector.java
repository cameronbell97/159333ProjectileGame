package Game.Entities;

import Game.Entities.Dynamic.DynamicEntity;
import Game.Entities.Dynamic.Enemies.*;
import Game.Handler;
import Game.Data.Settings;
import Game.Timer.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Cameron Bell - 7/04/2018
 * Enemy Director (Manager) Class
 * Generates and Manages all Enemies
 */

public class EnemyDirector implements iCanHaveCodeTimer, iCanHaveEnemyTimer {
// VARIABLES //
    boolean alive = true;

    // Statics
    private static final int LEVEL_WAIT_TIME = 4*60;

    // Managers
    Handler handler;

    // Enemy Director Variables
    private int gameLevel;
    private CodeTimer currentTimer;
    private int limboEntities;
    private int spawn_queue_minsec;
    private int spawn_queue_maxsec;

    // Enemy Lists
    List<Enemy> remaining_queue; // A list of Enemies
    List<Enemy> spawn_queue; // A list of Enemies
    List<Enemy> dead_queue; // A list of Enemies

// CONSTRUCTORS //
    public EnemyDirector(Handler parentHandler) {
        // Set Variables
        this.gameLevel = 0;
        this.limboEntities = 0;
        this.currentTimer = null;
        this.spawn_queue_minsec = 0;
        this.spawn_queue_maxsec = 0;

        // Instantiate Enemy Lists
        this.remaining_queue = new ArrayList();
        this.spawn_queue = new ArrayList();
        this.dead_queue = new ArrayList();

        handler = parentHandler;
    }

// METHODS //
    public void update() {
        // Generate time new enemy will wait to spawn
        int nextEnemyTime = Handler.getIntFromRange(spawn_queue_minsec, spawn_queue_maxsec);
        TimerManager tm = Handler.get().getTimerManager();

        // Create Enemy "to-be-spawned" Timers
        for(Enemy e : spawn_queue) {
            // Make new Enemy Game.Timer
            tm.newEnemyTimer(nextEnemyTime, this, e);

            // Increment Limbo Game.Entities - Enemy e is yet to spawn so can't be counted in "Remaining Enemies"
            limboEntities++;

            // Increment Time
            nextEnemyTime += Handler.getIntFromRange(1*60, 4*60);
        }
        spawn_queue.clear(); // Clear the spawn_queue

        for(Enemy e : dead_queue)
            remaining_queue.remove(e);
        dead_queue.clear(); // Clear the dead_queue


        // Go to next level if no more enemies remain, or are yet to spawn
        if(limboEntities == 0 && remaining_queue.isEmpty() && currentTimer == null)
            currentTimer = tm.newCodeTimer(LEVEL_WAIT_TIME, this, "LVL+", 0);
    }

    // Method - Recieve CodeTimer Notification //
    @Override
    public void timerNotify(CodeTimer t) {
        // Get Game.Timer Code
        String code = t.getCode();

        // Clear CurrentTimer so Game doesn't think we're still waiting for the next level
        Handler.get().getTimerManager().unsubTimer(currentTimer);
        currentTimer = null;

        // Do depending on the code
        switch (code) {
            // LVL+ is the code for Start Next Level
            case "LVL+":
                gameLevel++; // Increment Level
                populateEnemies(); // Spawn Enemies
                break;
        }
    }

    // Method - Recieve Timer Notification //
    @Override
    public void timerNotify(EnemyTimer t) {
        // Unsub Game.Timer so it can be deleted
        Handler.get().getTimerManager().unsubTimer(t);

        // Give new position and direction to enemy, ready for spawning
        Enemy enem = generateEnemyPosition(t.getEnemy(), t.getEnemy().getMaxSize());
        enem.setCollisionBox();

        limboEntities--; // Decrement Limbo Game.Entities Counter
        handler.getEntityManager().subscribe(enem); // // Subscribe Enemy to EntityManager for Managing (Updating and Drawing)
        remaining_queue.add(enem); // Count it as a remaining enemy
    }

    // Method - Add Enemies to Spawn Queue at Start of Level
    private void populateEnemies() {
        // Do depending on the Level
        switch (gameLevel) {
            case 1: // Level 1 //
                for(int i = 0; i < 2; i++)
                    spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 2, 0, 1, false), Asteroid.DEFAULT_SIZE));
                spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 3, 0, 1, false), Asteroid.DEFAULT_SIZE));

                spawn_queue_minsec = 60;
                spawn_queue_maxsec = 4*60;
                break;

            case 2: // Level 2 //
                for(int i = 0; i < 2; i++) {
                    spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 3, 0, 1, false), Asteroid.DEFAULT_SIZE));
                    spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 2, 0, 1.3, false), Asteroid.DEFAULT_SIZE));
                    spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 1, 0, 1.5, false), Asteroid.DEFAULT_SIZE));
                }
                spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 1, 0, 1, false), Asteroid.DEFAULT_SIZE));
                Collections.shuffle(spawn_queue);

                spawn_queue_minsec = 45;
                spawn_queue_maxsec = 3*60+30;
                break;

            case 3: // Level 3 //
                for(int i = 0; i < 2; i++) {
                    spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 2, 0, 1.2, false), Asteroid.DEFAULT_SIZE));
                    spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 1, 0, 1.3, true), Asteroid.DEFAULT_SIZE));
                }
                spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 3, 0, 1.4, false), Asteroid.DEFAULT_SIZE));
                Collections.shuffle(spawn_queue);

                spawn_queue_minsec = 45;
                spawn_queue_maxsec = 3*60+30;
                break;

            case 4: // Level 4 //
                spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 2, 0, 1.5, false), Asteroid.DEFAULT_SIZE));
                spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 3, 0, 1.2, false), Asteroid.DEFAULT_SIZE));
                for(int i = 0; i < 2; i++) {
                    spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 1, 0, 1, true), Asteroid.DEFAULT_SIZE));
                    spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 2, 0, 1, true), Asteroid.DEFAULT_SIZE));
                }
                Collections.shuffle(spawn_queue);
                spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 3, 0, 1.2, true), Asteroid.DEFAULT_SIZE));

                spawn_queue_minsec = 45;
                spawn_queue_maxsec = 3*60+30;
                break;

            case 5: // Level 5 //
                for(int i = 0; i < 2; i++) {
                    spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 1, 0, 2, true), Asteroid.DEFAULT_SIZE));
                    spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 2, 0, 2, true), Asteroid.DEFAULT_SIZE));
                    spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 3, 0, 2, true), Asteroid.DEFAULT_SIZE));
                }
                spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 1, 0, 2, true), Asteroid.DEFAULT_SIZE));
                spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 3, 0, 2, false), Asteroid.DEFAULT_SIZE));
                spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 2, 0, 2, false), Asteroid.DEFAULT_SIZE));
                Collections.shuffle(spawn_queue);

                spawn_queue_minsec = 35;
                spawn_queue_maxsec = 3*60;

                break;

            case 6: // Level 6 // Asteroid Belt
                handler.getGameUIManager().setFlashAlert("! ASTEROID BELT !", 2);
                for(int i = 0; i < 3; i++) {
                    spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 3, 0, 2, true), Asteroid.DEFAULT_SIZE));
                    spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 2, 0, 2, true), Asteroid.DEFAULT_SIZE));
                    spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 1, 0, 3, true), Asteroid.DEFAULT_SIZE));
                    spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 1, 0, 3, false), Asteroid.DEFAULT_SIZE));
                }
                spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 1, 0, 2, true), Asteroid.DEFAULT_SIZE));
                Collections.shuffle(spawn_queue);

                spawn_queue_minsec = 45;
                spawn_queue_maxsec = 3*60+30;
                break;

            case 7:
                for(int i = 0; i < 2; i++) {
                    spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 3, 0, 1, true), Asteroid.DEFAULT_SIZE));
                    spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 3, 0, 1.5, false), Asteroid.DEFAULT_SIZE));
                }
                Collections.shuffle(spawn_queue);
                spawn_queue.add(EnemyDirector.generateEnemyPosition(new GoblinFighterLarge(0, 0, 0), 64));


                spawn_queue_minsec = 45;
                spawn_queue_maxsec = 3*60;
                break;

            case 8:
                for(int i = 0; i < 2; i++) {
                    spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 2, 0, 2, true), Asteroid.DEFAULT_SIZE));
                    spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 1, 0, 2.5, true), Asteroid.DEFAULT_SIZE));
                    spawn_queue.add(EnemyDirector.generateEnemyPosition(new GoblinFighterLarge(0, 0, 0), 64));
                }
                spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 1, 0, 3, false), Asteroid.DEFAULT_SIZE));
                spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 2, 0, 2, false), Asteroid.DEFAULT_SIZE));
                Collections.shuffle(spawn_queue);

                spawn_queue_minsec = 35;
                spawn_queue_maxsec = 2*60+30;
                break;

            case 9:
                for(int i = 0; i < 2; i++) {
                    spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 3, 0, 2, true), Asteroid.DEFAULT_SIZE));
                    spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 2, 0, 2, false), Asteroid.DEFAULT_SIZE));
                    spawn_queue.add(EnemyDirector.generateEnemyPosition(new GoblinFighterSmall(0, 0, 0), 64));
                }
                spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 2, 0, 2, false), Asteroid.DEFAULT_SIZE));
                spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 3, 0, 2, true), Asteroid.DEFAULT_SIZE));
                spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 1, 0, 4, false), Asteroid.DEFAULT_SIZE));
                spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 1, 0, 4, true), Asteroid.DEFAULT_SIZE));
                spawn_queue.add(EnemyDirector.generateEnemyPosition(new GoblinFighterLarge(0, 0, 0), 64));
                Collections.shuffle(spawn_queue);

                spawn_queue_minsec = 35;
                spawn_queue_maxsec = 2*60+30;

                break;

            case 10:
                for(int i = 0; i < 2; i++) {
                    spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 2, 0, 2, false), Asteroid.DEFAULT_SIZE));
                    spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 2, 0, 2, true), Asteroid.DEFAULT_SIZE));
                    spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 1, 0, 3, false), Asteroid.DEFAULT_SIZE));
                    spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 1, 0, 3, true), Asteroid.DEFAULT_SIZE));
                    spawn_queue.add(EnemyDirector.generateEnemyPosition(new GoblinFighterSmall(0, 0, 0), 64));
                    spawn_queue.add(EnemyDirector.generateEnemyPosition(new GoblinFighterLarge(0, 0, 0), 64));
                }
                spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 3, 0, 2, false), Asteroid.DEFAULT_SIZE));
                spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 3, 0, 2, true), Asteroid.DEFAULT_SIZE));
                Collections.shuffle(spawn_queue);

                spawn_queue_minsec = 35;
                spawn_queue_maxsec = 2*60+30;

                break;

            default: // DYNAMIC LEVEL GENERATION
                if(gameLevel <= 10) break;

                int X = gameLevel - 10;
                handler.getGameUIManager().setFlashAlert(("X" + Integer.toString(X)), 1);

                // Grey Asteroid Small
                if(X%2==1)
                    for(int i = 0; i < (X+3)/3; i++)
                        spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 1, 0, 2.6, false), Asteroid.DEFAULT_SIZE));

                // Grey Asteroid Medium
                for(int i = 0; i < (X+4)/3; i++)
                    spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 2, 0, 2.3, false), Asteroid.DEFAULT_SIZE));

                // Grey Asteroid Large
                for(int i = 0; i < (X+1)/4; i++)
                    spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 3, 0, 2.1, false), Asteroid.DEFAULT_SIZE));

                // White Asteroid Small
                if(X%2==0)
                    for(int i = 0; i < (X+3)/3; i++)
                        spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 1, 0, 2.6, true), Asteroid.DEFAULT_SIZE));

                // White Asteroid Medium
                for(int i = 0; i < (X+2)/3; i++)
                    spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 2, 0, 2.3, true), Asteroid.DEFAULT_SIZE));

                // White Asteroid Large
                for(int i = 0; i < X/4; i++)
                    spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 3, 0, 2.1, true), Asteroid.DEFAULT_SIZE));

                // Goblin Fighter Large
                for(int i = 0; i < (X+1)/5; i++)
                    spawn_queue.add(EnemyDirector.generateEnemyPosition(new GoblinFighterLarge(0, 0, 0), 64));

                // Goblin Fighter Small
                for(int i = 0; i < X/6; i++)
                    spawn_queue.add(EnemyDirector.generateEnemyPosition(new GoblinFighterSmall(0, 0, 0), 64));

                
                Collections.shuffle(spawn_queue);
                break;
        }
    }

    // Method - Dynamically Generate A New Position & Direction for the Enemy
    private static Enemy generateEnemyPosition(Enemy e, int enemySize) {
        // Generate new (x,y) Co-ordinates
        float newXpos = Handler.getFloatFromRange(-enemySize, Settings.game_width);
        float newYpos = Handler.getFloatFromRange(-enemySize, Settings.game_height);

        // Randomly choose one wall to move behind (so the enemy spawns off-screen)
        switch (Handler.getIntFromRange(0, 1)) {
            case 0:
                if(newXpos < (Settings.game_width + enemySize) / 2) newXpos = -enemySize;
                else newXpos = Settings.game_width;
                break;
            case 1:
                if(newYpos < (Settings.game_height + enemySize) / 2) newXpos = -enemySize;
                else newYpos = Settings.game_height;
                break;
        }

        // Set the new (x,y) positions
        e.setXpos(newXpos);
        e.setYpos(newYpos);

        // Determine Direction based on Position
        e.setDirection(calculateDir(e));

        // Set Movement Speeds for new Direction
        e.setMoveSpeeds();

        return e;
    }

    // Method - Dynamically Generate a New Enemy Direction Based on Enemy Position
    private static double calculateDir(DynamicEntity e) {
        double newDir = 0;

        // Define point variables for easier function designing
        float P1x = e.getXpos();
        float P1y = e.getYpos();
        float P2x = Settings.game_width/2;
        float P2y = Settings.game_height/2;

        // Determine right-angled-triangle's opposite and adjacent lengths
        float triangleX = Math.abs(P2x - P1x);
        float triangleY = Math.abs(P2y - P1y);

        // In the event tx = 0, don't do calculations
        if(triangleX == 0) {
            if(P1y > P2y) return Math.PI / 2;
            else return (3*Math.PI) / 2;
        }

        // tan(theta) = (ty / tx), so theta = inversetan(ty / tx)
        double theta = Math.atan(triangleY / triangleX);

        // Determine direction depending on P1's location
        if(P1x > P2x && P1y < P2y) { // Quadrant 1
            newDir = Math.PI + theta;
        }
        else if(P1x < P2x && P1y < P2y) { // Quadrant 2
            newDir = -theta;
        }
        else if(P1x < P2x && P1y > P2y) { // Quadrant 3
            newDir = theta;
        }
        else if(P1x > P2x && P1y > P2y) { // Quadrant 4
            newDir = Math.PI - theta;
        }

        // Add small variation to direction
        newDir += Handler.getDoubleFromRange(-(Math.PI / 8), Math.PI / 8);

        return newDir;
    }

    // Method - Queue Enemy for Removal
    public void unsubscribe(Enemy e) {
        dead_queue.add(e);
    }

    // Method - Add Enemy to Remaining Queue for tracking
    public void subscribe(Enemy e) {
        remaining_queue.add(e);
    }

    public void clear() {
        for (Enemy enem : spawn_queue) {
            unsubscribe(enem);
        }
        for (Enemy enem : remaining_queue) {
            unsubscribe(enem);
        }

        this.update();
        alive = false;
    }

    public void startGame() {
        if(currentTimer != null) Handler.get().getTimerManager().unsubTimer(currentTimer);
        currentTimer = null;
        handler.getEnemyDirector().startGame();
    }

// GETTERS & SETTERS //
    public int getRemainingEnemies() {
        return remaining_queue.size() + limboEntities;
    }

    public int getGameLevel() {
        return gameLevel;
    }

    public boolean isAlive() {
        return alive;
    }
}
