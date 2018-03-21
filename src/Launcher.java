/**
 * Cameron Bell - 20/03/2018
 * Launcher Class
 * Launches the game
 */
public class Launcher {
// CONSTRUCTORS //
    public static void main(String[] args) {
        // Temporary launch code
        Game game = new Game("Projectile Game", 720, 1280);
        game.start();
    }
}
