package Game;

/**
 * Cameron Bell - 20/03/2018
 * Game.Launcher Class
 * Launches the game
 */
public class Launcher {
// VARIABLES //
    public static final int DEF_GAME_HEIGHT = 720;
    public static final int DEF_GAME_WIDTH = 1280;

// CONSTRUCTORS //
    public static void main(String[] args) {
        // Temporary launch code
        Game game = new Game("Projectile Game.Game", DEF_GAME_HEIGHT, DEF_GAME_WIDTH);
        game.start();
    }
}
