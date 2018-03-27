package Screens;

import Screens.Screen;

/**
 * Cameron Bell - 26/03/2018
 * Screens.Screen Manager Class
 */
public class ScreenManager {
// VARIABLES //
    private static Screen currentScreen = null; // Holds the current screen

// METHODS //
    // Set the screen
    public static void setScreen(Screen s) {
        currentScreen = s;
    }

    // Get the screen
    public static Screen getScreen() {
        return currentScreen;
    }
}
