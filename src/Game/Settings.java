package Game;

public class Settings {
// DEFAULT VALUES //
    // Game
    private static final int DEF_GAME_WIDTH = 875;
    private static final int DEF_GAME_HEIGHT = 700;

    // UI
    private static final int DEF_CHARACTER_HEIGHT = 9;
    private static final int DEF_CHARACTER_WIDTH = 5;
    private static final int DEF_CHARACTER_SIZE = 2;
    private static final int DEF_UI_LEFT_BOUNDARY = 24;
    private static final int DEF_UI_RIGHT_BOUNDARY = 24;
    private static final int DEF_UI_UPPER_BOUNDARY = 24;
    private static final int DEF_UI_LOWER_BOUNDARY = 24;

// SETTINGS //
    // Game
    public static int game_width;
    public static int game_height;

    // UI
    public static int character_height;
    public static int character_width;
    public static int character_size;
    public static int ui_left_boundary;
    public static int ui_right_boundary;
    public static int ui_upper_boundary;
    public static int ui_lower_boundary;

// CONSTRUCTORS //
    // Default Values Constructor
    public Settings() {
        // Game
        game_width = DEF_GAME_WIDTH;
        game_height = DEF_GAME_HEIGHT;

        // UI
        character_height = DEF_CHARACTER_HEIGHT;
        character_width = DEF_CHARACTER_WIDTH;
        character_size = DEF_CHARACTER_SIZE;
        ui_left_boundary = DEF_UI_LEFT_BOUNDARY;
        ui_right_boundary = DEF_UI_RIGHT_BOUNDARY;
        ui_upper_boundary = DEF_UI_UPPER_BOUNDARY;
        ui_lower_boundary = DEF_UI_LOWER_BOUNDARY;
    }
}
