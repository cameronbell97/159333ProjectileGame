package Game.Data;

/**
 * Cameron Bell - 13/04/2018
 * Settings Class
 * Class to keep Settings Data
 */
public class Settings {
// DEFAULT VALUES //
    // Debug Tools //
    public static final boolean DEBUG_CHEATS = false;
    public static final boolean DEBUG_MODE = false;
    public static final boolean DEBUG_GOBLIN_DRAW_LINE_TO_PLAYER = DEBUG_MODE;
    public static final boolean DEBUG_GOBLIN_DRAW_FACING_DIRECTION_LINE = DEBUG_MODE;
    public static final boolean DEBUG_DRAW_COLLISIONS = DEBUG_MODE;

    // Game Settings //
    private static final int DEF_GAME_WIDTH = 875;
    private static final int DEF_GAME_HEIGHT = 700;

    // User Interface //
    private static final int DEF_CHARACTER_HEIGHT = 9;
    private static final int DEF_CHARACTER_WIDTH = 5;
    private static final int DEF_CHARACTER_SIZE = 2;
    private static final int DEF_UI_LEFT_BOUNDARY = 24;
    private static final int DEF_UI_RIGHT_BOUNDARY = 24;
    private static final int DEF_UI_UPPER_BOUNDARY = 24;
    private static final int DEF_UI_LOWER_BOUNDARY = 24;

    // Menus //
    private static final int DEF_BUTTON_BORDER_WIDTH = 1;
    private static final int DEF_BUTTON_INNER_PADDING = 11;
    private static final int DEF_MENU_BUTTON_SPACING = 8;

    // Graphics //
    private static final int DEF_MAX_PARTICLES = 50;

    // Gameplay //
    private static final boolean DEF_PLAYER_STRAFE = false;
    private static final boolean DEF_PLAYER_DECELERATION = true;
    private static final boolean DEF_PLAYER_ACCELERATION = false;
    private static final boolean DEF_PLAYER_SPEED_LIMIT = false;
    private static final boolean DEF_PLAYER_GUN_LOCK = true;
    private static final boolean DEF_PLAYER_GUN_MAIN = true;
    private static final int DEF_EXP_PICKUP_DISTANCE = 52;

// SETTINGS //
    // Game //
    public static int game_width;
    public static int game_height;

    // User Interface //
    public static int character_height;
    public static int character_width;
    public static int character_size;
    public static int ui_left_boundary;
    public static int ui_right_boundary;
    public static int ui_upper_boundary;
    public static int ui_lower_boundary;

    // Menus //
    public static int button_border_width;
    public static int button_padding;
    public static int menu_button_spacing;

    // Graphics //
    public static int max_particles;

    // Gameplay //
    public static int exp_pickup_distance;
    public static boolean player_strafe;
    public static boolean player_deceleration;
    public static boolean player_acceleration;
    public static boolean player_speed_limit;
    public static boolean player_gun_lock;
    public static boolean player_gun_main;

// CONSTRUCTORS //
    // Default Values Constructor
    public Settings() {
        // Game //
        game_width = DEF_GAME_WIDTH;
        game_height = DEF_GAME_HEIGHT;

        // User Interface //
        character_height = DEF_CHARACTER_HEIGHT;
        character_width = DEF_CHARACTER_WIDTH;
        character_size = DEF_CHARACTER_SIZE;
        ui_left_boundary = DEF_UI_LEFT_BOUNDARY;
        ui_right_boundary = DEF_UI_RIGHT_BOUNDARY;
        ui_upper_boundary = DEF_UI_UPPER_BOUNDARY;
        ui_lower_boundary = DEF_UI_LOWER_BOUNDARY;

        // Menus //
        button_border_width = DEF_BUTTON_BORDER_WIDTH;
        button_padding = DEF_BUTTON_INNER_PADDING;
        menu_button_spacing = DEF_MENU_BUTTON_SPACING;

        // Graphics //
        max_particles = DEF_MAX_PARTICLES;

        // Gameplay //
        exp_pickup_distance = DEF_EXP_PICKUP_DISTANCE;
        player_strafe = DEF_PLAYER_STRAFE;
        player_deceleration = DEF_PLAYER_DECELERATION;
        player_acceleration = DEF_PLAYER_ACCELERATION;
        player_speed_limit = DEF_PLAYER_SPEED_LIMIT;
        player_gun_lock = DEF_PLAYER_GUN_LOCK;
        player_gun_main = DEF_PLAYER_GUN_MAIN;
    }
}
