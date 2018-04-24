package Game.Entities;

import Game.Display.UserInterface.GameUIManager;
import Game.Entities.Dynamic.DynamicEntity;
import Game.Entities.Dynamic.Enemies.*;
import Game.Game;
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
// SINGLETON PATTERN //
    private static EnemyDirector self = new EnemyDirector();
    public static EnemyDirector get() { return self; }

// VARIABLES //
    // Statics
    private static final int LEVEL_WAIT_TIME = 4*60;

    // Managers
    private EntityManager entityManager;
    private TimerManager timerManager;

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
    private EnemyDirector() {
        // Set Variables
        this.gameLevel = 0;
        this.limboEntities = 0;
        this.currentTimer = null;
        this.entityManager = EntityManager.get();
        this.timerManager = TimerManager.get();
        this.spawn_queue_minsec = 0;
        this.spawn_queue_maxsec = 0;

        // Instantiate Enemy Lists
        this.remaining_queue = new ArrayList();
        this.spawn_queue = new ArrayList();
        this.dead_queue = new ArrayList();
    }

// METHODS //
    public void update() {
        // Generate time new enemy will wait to spawn
        int nextEnemyTime = Game.getIntFromRange(spawn_queue_minsec, spawn_queue_maxsec);

        // Create Enemy "to-be-spawned" Timers
        for(Enemy e : spawn_queue) {
            // Make new Enemy Game.Timer
            timerManager.newEnemyTimer(nextEnemyTime, this, e);

            // Increment Limbo Game.Entities - Enemy e is yet to spawn so can't be counted in "Remaining Enemies"
            limboEntities++;

            // Increment Time
            nextEnemyTime += Game.getIntFromRange(1*60, 4*60);
        }
        spawn_queue.clear(); // Clear the spawn_queue

        for(Enemy e : dead_queue)
            remaining_queue.remove(e);
        dead_queue.clear(); // Clear the dead_queue


        // Go to next level if no more enemies remain, or are yet to spawn
        if(limboEntities == 0 && remaining_queue.isEmpty() && currentTimer == null)
            currentTimer = timerManager.newCodeTimer(LEVEL_WAIT_TIME, this, "LVL+", 0);
    }

    // Method - Recieve CodeTimer
    @Override
    public void timerNotify(CodeTimer t) {
        // Get Game.Timer Code
        String code = t.getCode();

        // Clear CurrentTimer so Game doesn't think we're still waiting for the next level
        timerManager.unsubTimer(currentTimer);
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

    // Method - Recieve EnemyTimer
    @Override
    public void timerNotify(EnemyTimer t) {
        // Unsub Game.Timer so it can be deleted
        timerManager.unsubTimer(t);

        // Give new position and direction to enemy, ready for spawning
        Enemy enem = generateEnemyPosition(t.getEnemy(), t.getEnemy().getMaxSize());
        enem.setCollisionBox();

        limboEntities--; // Decrement Limbo Game.Entities Counter
        entityManager.subscribe(enem); // // Subscribe Enemy to EntityManager for Managing (Updating and Drawing)
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
                    spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 2, 0, 1, false), Asteroid.DEFAULT_SIZE));
                    spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 1, 0, 2, false), Asteroid.DEFAULT_SIZE));
                }
                spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 1, 0, 1, false), Asteroid.DEFAULT_SIZE));
                Collections.shuffle(spawn_queue);

                spawn_queue_minsec = 45;
                spawn_queue_maxsec = 3*60+30;
                break;

            case 3: // Level 3 //
                spawn_queue.add(EnemyDirector.generateEnemyPosition(new GoblinFighterLarge(0, 0, 2), 64));

                for(int i = 0; i < 2; i++) {
                    spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 2, 0, 1, false), Asteroid.DEFAULT_SIZE));
                }

                spawn_queue_minsec = 45;
                spawn_queue_maxsec = 3*60+30;
                break;

            case 4: // Level 4 //
                spawn_queue.add(EnemyDirector.generateEnemyPosition(new GoblinFighterLarge(0, 0, 2), 64));
                for(int i = 0; i < 2; i++) {
                    spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 3, 0, 1, false), Asteroid.DEFAULT_SIZE));
                    spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 2, 0, 1, false), Asteroid.DEFAULT_SIZE));
                }
                spawn_queue.add(EnemyDirector.generateEnemyPosition(new GoblinFighterLarge(0, 0, 2), 64));
                spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 1, 0, 1, true), Asteroid.DEFAULT_SIZE));

                spawn_queue_minsec = 45;
                spawn_queue_maxsec = 3*60+30;
                break;

            case 5: // Level 5 //
                for(int i = 0; i < 2; i++) {
                    spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 3, 0, 2, false), Asteroid.DEFAULT_SIZE));
                    spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 2, 0, 2, false), Asteroid.DEFAULT_SIZE));
                }
            spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 2, 0, 2, true), Asteroid.DEFAULT_SIZE));
            spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 2, 0, 2, true), Asteroid.DEFAULT_SIZE));
            spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 3, 0, 2, true), Asteroid.DEFAULT_SIZE));
            Collections.shuffle(spawn_queue);

            spawn_queue_minsec = 35;
            spawn_queue_maxsec = 3*60;

            break;

            case 6: // Level 6 // Asteroid Belt
                GameUIManager.get().setFlashAlert("! ASTEROID BELT !", 2);
                for(int i = 0; i < 3; i++) {
                    spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 3, 0, 2, true), Asteroid.DEFAULT_SIZE));
                    spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 2, 0, 2, true), Asteroid.DEFAULT_SIZE));
                    spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 1, 0, 3, true), Asteroid.DEFAULT_SIZE));
                }
                spawn_queue.add(EnemyDirector.generateEnemyPosition(new Asteroid(0, 0, 3, 0, 2, true), Asteroid.DEFAULT_SIZE));
                Collections.shuffle(spawn_queue);

            spawn_queue_minsec = 45;
            spawn_queue_maxsec = 3*60+30;
            break;

            default: // TODO // DYNAMIC LEVEL GENERATION
                break;
        }
    }

    // Method - Dynamically Generate A New Position & Direction for the Enemy
    private static Enemy generateEnemyPosition(Enemy e, int enemySize) {
        // Generate new (x,y) Co-ordinates
        float newXpos = Game.getFloatFromRange(-enemySize, Settings.game_width);
        float newYpos = Game.getFloatFromRange(-enemySize, Settings.game_height);

        // Randomly choose one wall to move behind (so the enemy spawns off-screen)
        switch (Game.getIntFromRange(0, 1)) {
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
        newDir += Game.getDoubleFromRange(-(Math.PI / 8), Math.PI / 8);

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

// GETTERS & SETTERS //
    public int getRemainingEnemies() {
        return remaining_queue.size() + limboEntities;
    }

    public int getGameLevel() {
        return gameLevel;
    }
}
