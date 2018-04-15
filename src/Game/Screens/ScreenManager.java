package Game.Screens;

/**
 * Cameron Bell - 26/03/2018
 * Screen Manager Class
 */

public class ScreenManager {
// VARIABLES //
    private static Screen currentScreen = null; // Holds Current/Active Screen

// GETTERS & SETTERS //
    public static void setScreen(Screen s) {
        currentScreen = s;
    }
    public static Screen getScreen() {
        return currentScreen;
    }
}
