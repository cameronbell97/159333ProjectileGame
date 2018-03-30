package Game;

/**
 * Cameron Bell - 30/03/2018
 * Handler Class
 * The handler
 */

public class Handler {
// VARIABLES //
    private Game game;

// CONSTRUCTORS //
    public Handler(Game game) {
        this.game = game;
    }

// METHODS //

// GETTERS & SETTERS //
    public int getWidth() {
        return game.getGameWidth();
    }

    public int getHeight() {
        return game.getGameHeight();
    }

    public KeyManager getKeyManager() {
        return game.getKeyManager();
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
