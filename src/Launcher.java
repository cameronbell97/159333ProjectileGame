/**
 * Cameron Bell - 20/03/2018
 * Launcher Class
 * Launches the game
 */
public class Launcher {
// CONSTRUCTORS //
    public static void main(String[] args) {
        // Temporary launch code
        Game game = new Game("Projectile Game", 800, 600);
        game.start();
    }
}
