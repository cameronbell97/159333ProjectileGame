package Game.Data;

import Game.Entities.EnemyDirector;
import Game.Entities.EntityManager;
import Game.Handler;

public class GameDataManager {
// VARIABLES //
    boolean alive = true;

    // Sources //
    Handler handler;
    private Save save;
    EntityManager em;
    EnemyDirector ed;

    // Shortcuts //
    private int player_hp;
    private int enemies_remaining;
    private int current_level;
    private int score;

// CONSTRUCTORS //
    public GameDataManager() {
        // Initialise Variables
        player_hp = 0;
        enemies_remaining = 0;
        current_level = 0;
        score = 0;
        handler = Handler.get();
        em = handler.getEntityManager();
        ed = handler.getEnemyDirector();
    }

// METHODS //
    // Method - Update Shortcuts //
    public void update() {
        if(em != null && ed != null) {
            if (em.getPlayer() != null) player_hp = em.getPlayer().getHP();
            else player_hp = 0;
            enemies_remaining = ed.getRemainingEnemies();
            current_level = ed.getGameLevel();
        }
    }

//    public void clearData() {
//        handler.getEntityManager().clear();
//        handler.getEnemyDirector().clear();
//        handler.getTimerManager().clear();
////        GameUIManager.get().clear();
//        alive = false;
//    }

// GETTERS & SETTERS //
    public void addScore(int score) {
        this.score += score;
    }
    public int getPlayer_hp() {
        return player_hp;
    }
    public int getEnemies_remaining() {
        return enemies_remaining;
    }
    public int getCurrent_level() {
        return current_level;
    }
    public int getScore() {
        return score;
    }
    public boolean isAlive() {
        return alive;
    }
}
