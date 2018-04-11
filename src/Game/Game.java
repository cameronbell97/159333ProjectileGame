package Game;

import Screens.GameScreen;
import Screens.MainMenuScreen;
import Screens.Screen;
import Screens.ScreenManager;
import Timer.TimerManager;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.Random;

/**
 * Cameron Bell - 20/03/2018
 * Game Class
 * High-Level Class Used to Instantiate Game and Encompass Many Entities
 */

public class Game implements Runnable{
// VARIABLES //
    // Statics
    private static final int FPS = 60;
    public static final boolean DRAW_COLLISIONS = false;

    // Basic Game Parameters / Settings
    private String gameTitle;
    private int gameHeight;
    private int gameWidth;

    // Window / Display variables
    private DisplayWindow display;
    private Canvas displayCanvas;
    private Graphics g;
    private BufferStrategy bufferStrategy;

    // Background Program Mechanics
    private Thread thread;
    private boolean isRunning;

    // Managers
    private KeyManager km;
    private TimerManager timerMan;

    // Screens
    private Screen mainMenuScreen;
    private Screen gameScreen;

    // Miscellaneous
    private Save save;

// CONSTRUCTORS //
    public Game(String title, int height, int width) {
        // Set variables
        gameHeight = height;
        gameWidth = width;
        gameTitle = title;
    }

// METHODS //
    // Method - Initialise Game
    public void initialise() throws IOException {
        // Create Managers
        km = KeyManager.get();
        timerMan = TimerManager.get();

        // Create Window
        display = new DisplayWindow(gameTitle, gameWidth, gameHeight);
        display.getFrame().addKeyListener(km);
        displayCanvas = display.getCanvas();

        // Initialise Screens
        gameScreen = new GameScreen();
        mainMenuScreen = new MainMenuScreen();
        ScreenManager.setScreen(gameScreen);

        // Initialise Save Data
        save = new Save();
        if(!save.load()) save.create(); // If load fails, create a blank save
    }

    // Method - Updates Everything in the Game
    public void update() {
        // Update Managers
        km.update();
        timerMan.update();

        // Update Current Screen
        if (ScreenManager.getScreen() != null) {
            ScreenManager.getScreen().update();
        }
    }

    // Method - Render the Graphics on the Screen
    public void draw() throws IOException {
        // Set-Up
        bufferStrategy = displayCanvas.getBufferStrategy();
        if(bufferStrategy == null) {
            displayCanvas.createBufferStrategy(3);
            return;
        }
        g = bufferStrategy.getDrawGraphics();

        // Draw to the Screen
        g.clearRect(0, 0, gameWidth, gameHeight); // Clear the screen before drawing
        if (ScreenManager.getScreen() != null) {
            ScreenManager.getScreen().draw(g);
        }

        // Display the new drawn-to Screen
        bufferStrategy.show();
        g.dispose();
    }

    // Method - Generate a Random Integer in a Range
    public static int getIntFromRange(int min, int max) {
        // Switch the values if needed
        if(max < min) {
            int temp = min;
            min = max;
            max = temp;
        }

        max += 1;

        Random generator = new Random();
        return min + generator.nextInt(max - min);
    }

    // Method - Generate a Random Float in a Range
    public static float getFloatFromRange(float min, float max) {
        // Switch the values if needed
        if(max < min) {
            float temp = min;
            min = max;
            max = temp;
        }
        Random generator = new Random();
        return min + generator.nextFloat() * (max - min);
    }

    // Method - Generate a Random Double in a Range
    public static double getDoubleFromRange(double min, double max) {
        // Switch the values if needed
        if(max < min) {
            double temp = min;
            min = max;
            max = temp;
        }
        Random generator = new Random();
        return min + generator.nextDouble() * (max - min);
    }

    // Method - Convert Seconds to Ticks / Frames
    public static int secsToTicks(int seconds) {
        return seconds * 60;
    }

    // -------------------------------------------- // Threading Methods // --------------------------------------------

    // Method - Start the Game
    public synchronized void start() {
        if(isRunning) return; // Safeguard in case start() is called a second time
        isRunning = true;
        thread = new Thread(this); // Start new thread of this class
        thread.start(); // Calls run() on Game object in the thread
    }

    // Method - Main Game Thread Method
    public void run() {
        // Initialise Game
        try { initialise(); }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(5);
        }

        // FPS / Gamestate Limiting Code
        double timepertick = 1000000000 / FPS; // Max time (in nanoseconds) that the game loop needs to be run by
        double delta = 0; // Time (nanoseconds) till next game loop cycle
        long now; // Current computer time
        long lasttime = System.nanoTime(); // Previous computer time
        long timer = 0;
        long ticks = 0;

        // The Main Game Loop // ----- Start -----
        while(isRunning) {
            // FPS / Gamestate Limiting Code
            now = System.nanoTime();
            delta += (now - lasttime) / timepertick; // Delta += time until next we run the game loop next
            timer += now - lasttime;
            lasttime = now;

            // Time for another frame
            if (delta >= 1) {
                // Update Game State
                update();

                // Draw Game State to Screen
                try { draw(); }
                catch (IOException e) { e.printStackTrace(); }

                // FPS / Gamestate Limiting Code
                delta--;
                ticks++;
            }

            // FPS Counter
            if (timer >= 1000000000) {
            System.out.println("FPS = " + ticks);   // DEBUG // Print FPS here to get one every second
                ticks = 0;
                timer = 0;
            }
        }
        // The Main Game Loop // ----- End -----
    }

    // Method - Executed on Game Stop
    public synchronized void stop() {
        if (!isRunning) return; // Safeguard in case stop() is called a second time

        isRunning = false;

        // Kill Thread
        try { thread.join(); }
        catch (java.lang.InterruptedException e) { e.printStackTrace(); }
    }

// GETTERS & SETTERS //
    public int getGameHeight() {
        return gameHeight;
    }
    public int getGameWidth() {
        return gameWidth;
    }
}
