package Game.Data;

import Game.Display.UserInterface.GameUIManager;
import Game.Entities.EnemyDirector;
import Game.Entities.EntityManager;
import Game.Timer.TimerManager;

public class GameDataManager {
// SINGLETON PATTERN //
    private static GameDataManager self = new GameDataManager();
    public static GameDataManager get() { return self; }

// VARIABLES //
    boolean alive = true;

    // Sources
    private EntityManager em;
    private EnemyDirector ed;
    private Save save;

    // Shortcuts
    private int player_hp;
    private int enemies_remaining;
    private int current_level;
    private int score;

// CONSTRUCTORS //
    public GameDataManager() {
        em = EntityManager.get();
        ed = EnemyDirector.get();

        player_hp = 0;
        enemies_remaining = 0;
        current_level = 0;
        score = 0;
    }

// METHODS //
    public void update() {
        if(em.getPlayer() != null) player_hp = em.getPlayer().getHP();
        else player_hp = 0;
        enemies_remaining = ed.getRemainingEnemies();
        current_level = ed.getGameLevel();
    }

    public void clearData() {
        em.clear();
        ed.clear();
        TimerManager.get().clear();
//        GameUIManager.get().clear();
        alive = false;
        em = null;
        ed = null;

        self = new GameDataManager();
    }

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
