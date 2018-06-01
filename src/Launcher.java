package Game;

import Game.Data.Settings;

/**
 * Cameron Bell - 20/03/2018
 * Launcher Class
 * Launches the game
 */

public class Launcher {
// MAIN //
    public static void main(String[] args) {
        // Initialise Settings
        new Settings();

        // Launch Game
        Game game = new Game("YOZNOVA", Settings.game_height, Settings.game_width);
        game.start();
    }
}
