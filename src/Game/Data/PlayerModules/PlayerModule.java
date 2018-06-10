package Game.Data.PlayerModules;

/* Cameron Bell - 10/06/18
 * Player Module Abstract Class
 * A Module that defines certain player ship functionality
 */

import Game.Handler;

public abstract class PlayerModule {
// VARIABLES //
    protected Handler handler = Handler.get();

     // Data //
    protected String moduleName;

// CONSTRUCTORS //
    public PlayerModule(String moduleName) {
        this.moduleName = moduleName;
    }

// METHODS //


// GETTERS & SETTERS //
    public String getModuleName() {
        return moduleName;
    }
}
