package Game.Data;

public class SaveManager {
// SINGLETON PATTERN //
    private static SaveManager self = new SaveManager();
    public static SaveManager get() { return self; }

// VARIABLES //
    Save save;

// CONSTRUCTORS //
    public SaveManager() {
        // Initialise Save Data
        save = new Save();
        if(!save.load()) {
            save.create(); // If load fails, create a blank save
            if(!save.load()) {
                Game.Game.end(); // If load fails a second time, kill the program
            }
        }

    }

// METHODS //
    public Save getSave() {
        return save;
    }
}
