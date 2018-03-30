package Game;

import Screens.GameScreen;
import Screens.MainMenuScreen;
import Screens.Screen;
import Screens.ScreenManager;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;

/**
 * Cameron Bell - 20/03/2018
 * Game.Game Class
 * High level class used to instantiate game and encompass a lot of classes and entities
 */

public class Game implements Runnable{
// VARIABLES //
    private static final int FPS = 60;

    private String gameTitle;
    private int gameHeight;
    private int gameWidth;
    private boolean isRunning;

    private DisplayWindow display;
    private Canvas displayCanvas;
    private Thread thread;

    private BufferStrategy bufferStrategy;
    private Graphics g;

    private KeyManager km;

    private Save save;

    // Screens
    private Screen mainMenuScreen;
    private Screen gameScreen;

// CONSTRUCTORS //
    public Game(String title, int height, int width) {
        // Set variables
        gameHeight = height;
        gameWidth = width;
        gameTitle = title;
    }

// METHODS //
    // Method to initialise game window
    public void initialise() throws IOException {
        // Initialise game function utilities
        display = new DisplayWindow(gameTitle, gameWidth, gameHeight);
        km = new KeyManager();
        display.getFrame().addKeyListener(km);
        displayCanvas = display.getCanvas();

        // Initialise screens
        gameScreen = new GameScreen(this);
        mainMenuScreen = new MainMenuScreen(this);
        ScreenManager.setScreen(gameScreen);

        // Initialise save data
        save = new Save();
        if(!save.load()) save.create(); // If load fails, create a blank save
    }

    // Method to update the game state
    public void update() {
        if (ScreenManager.getScreen() != null) {
            ScreenManager.getScreen().update();
        }
        km.update();
    }

    // Method to render the graphics on the screen
    public void draw() throws IOException {
        bufferStrategy = displayCanvas.getBufferStrategy();
        if(bufferStrategy == null) {
            displayCanvas.createBufferStrategy(3);
            return;
        }
        g = bufferStrategy.getDrawGraphics();

        // Draw to the screen // ---------
        g.clearRect(0, 0, gameWidth, gameHeight); // Clear the screen before drawing
        if (ScreenManager.getScreen() != null) {
            ScreenManager.getScreen().draw(g);
        }


        // Finish

        bufferStrategy.show();
        g.dispose();
    }

    // Threading Methods //

    public void run() {
        try {
            initialise();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(5);
        }

        // FPS/Gamestate Limiting code
        double timepertick = 1000000000 / FPS; // Max time (in nanoseconds) that the game loop needs to be run by
        double delta = 0; // Time (nanoseconds) till next game loop cycle
        long now; // Current computer time
        long lasttime = System.nanoTime(); // Previous computer time
        long timer = 0;
        long ticks = 0;

        // The Game.Game Loop // -- Start --
        while(isRunning) {
            now = System.nanoTime();
            delta += (now - lasttime) / timepertick; // Delta += time until next we run the game loop next
            timer += now - lasttime;
            lasttime = now;

            if (delta >= 1) {
                update(); // Update game state
                try {
                    draw(); // Render the graphics on the screen
                } catch (IOException e) {
                    e.printStackTrace();
                }
                delta--;
                ticks++;
            }

            // FPS counter
            if (timer >= 1000000000) {
            System.out.println("FPS = " + ticks);   // DEBUG // Print FPS here to get one every second
                ticks = 0;
                timer = 0;
            }
        }
        // The Game.Game Loop // -- End --
    }

    // Method executed on game start
    public synchronized void start() {
        if(isRunning) return; // Safeguard in case start() is called a second time
        isRunning = true;
        thread = new Thread(this); // Start new thread of this class
        thread.start(); // Calls run() on Game.Game object in thread
    }

    // Method executed on game stop
    public synchronized void stop() {
        if (!isRunning) return; // Safeguard in case stop() is called a second time
        isRunning = false;
        try {
            thread.join(); // Kills thread
        }
        catch (java.lang.InterruptedException e) {
            //!// Do something
        }
    }

    public KeyManager getKeyManager() {
        return km;
    }
}
