package Game.Data;

import Game.Display.UserInterface.GameUIManager;
import Game.Entities.EnemyDirector;
import Game.Entities.EntityManager;
import Game.Handler;
import Game.Timer.TimerManager;

public class GameDataManager {
// VARIABLES //
    boolean alive = true;

    // Sources
    Handler handler;
    private Save save;

    // Shortcuts
    private int player_hp;
    private int enemies_remaining;
    private int current_level;
    private int score;

// CONSTRUCTORS //
    public GameDataManager() {
        player_hp = 0;
        enemies_remaining = 0;
        current_level = 0;
        score = 0;
        handler = Handler.get();
    }

// METHODS //
    public void update() {
        EntityManager em = handler.getEntityManager();
        EnemyDirector ed = handler.getEnemyDirector();

        if(em.getPlayer() != null) player_hp = em.getPlayer().getHP();
        else player_hp = 0;
        enemies_remaining = ed.getRemainingEnemies();
        current_level = ed.getGameLevel();
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
