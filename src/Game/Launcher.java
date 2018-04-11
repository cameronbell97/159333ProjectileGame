package Game;

/**
 * Cameron Bell - 20/03/2018
 * Game.Launcher Class
 * Launches the game
 */

public class Launcher {
// VARIABLES //
    public static final int DEF_GAME_WIDTH = 875;
    public static final int DEF_GAME_HEIGHT = 700;

// CONSTRUCTORS //
    public static void main(String[] args) {
        // Launch Game
        Game game = new Game("PROJECTILE", DEF_GAME_HEIGHT, DEF_GAME_WIDTH);
        game.start();
    }
}
