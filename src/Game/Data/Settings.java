package Game.Data;

public class Settings {
// DEFAULT VALUES //
    // Debug Tools
    public static final boolean DEBUG_MODE = false;
    public static final boolean DEBUG_GOBLIN_DRAW_LINE_TO_PLAYER = DEBUG_MODE;
    public static final boolean DEBUG_GOBLIN_DRAW_FACING_DIRECTION_LINE = DEBUG_MODE;
    public static final boolean DRAW_COLLISIONS = true;

    // Game
    private static final int DEF_GAME_WIDTH = 875;
    private static final int DEF_GAME_HEIGHT = 700;

    // User Interface
    private static final int DEF_CHARACTER_HEIGHT = 9;
    private static final int DEF_CHARACTER_WIDTH = 5;
    private static final int DEF_CHARACTER_SIZE = 2;
    private static final int DEF_UI_LEFT_BOUNDARY = 24;
    private static final int DEF_UI_RIGHT_BOUNDARY = 24;
    private static final int DEF_UI_UPPER_BOUNDARY = 24;
    private static final int DEF_UI_LOWER_BOUNDARY = 24;

    // Menus
    private static final int DEF_BUTTON_BORDER_WIDTH = 1;
    private static final int DEF_BUTTON_INNER_PADDING = 11;
    private static final int DEF_MENU_BUTTON_SPACING = 8;

    // Graphics
    private static final int DEF_MAX_PARTICLES = 50;

// SETTINGS //
    // Game
    public static int game_width;
    public static int game_height;

    // Game.Display.UserInterface
    public static int character_height;
    public static int character_width;
    public static int character_size;
    public static int ui_left_boundary;
    public static int ui_right_boundary;
    public static int ui_upper_boundary;
    public static int ui_lower_boundary;

    // Menus
    public static int button_border_width;
    public static int button_padding;
    public static int menu_button_spacing;

    // Graphics
    public static int max_particles;

// CONSTRUCTORS //
    // Default Values Constructor
    public Settings() {
        // Game
        game_width = DEF_GAME_WIDTH;
        game_height = DEF_GAME_HEIGHT;

        // Game.Display.UserInterface
        character_height = DEF_CHARACTER_HEIGHT;
        character_width = DEF_CHARACTER_WIDTH;
        character_size = DEF_CHARACTER_SIZE;
        ui_left_boundary = DEF_UI_LEFT_BOUNDARY;
        ui_right_boundary = DEF_UI_RIGHT_BOUNDARY;
        ui_upper_boundary = DEF_UI_UPPER_BOUNDARY;
        ui_lower_boundary = DEF_UI_LOWER_BOUNDARY;

        // Menus
        button_border_width = DEF_BUTTON_BORDER_WIDTH;
        button_padding = DEF_BUTTON_INNER_PADDING;
        menu_button_spacing = DEF_MENU_BUTTON_SPACING;

        // Graphics
        max_particles = DEF_MAX_PARTICLES;
    }
}
