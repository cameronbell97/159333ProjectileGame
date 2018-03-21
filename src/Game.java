import java.awt.*;
import java.awt.image.BufferStrategy;

/**
 * Cameron Bell - 20/03/2018
 * Game Class
 * High level class used to instantiate game and encompass a lot of classes and entities
 */
public class Game implements Runnable{
// VARIABLES //
    private String gameTitle;
    private int gameHeight;
    private int gameWidth;
    private boolean isRunning;

    private DisplayWindow display;
    private Canvas displayCanvas;
    private Thread thread;

    private BufferStrategy bufferStrategy;
    private Graphics g;

// CONSTRUCTORS //
    public Game(String title, int height, int width) {
        // Set variables
        gameHeight = height;
        gameWidth = width;
        gameTitle = title;
    }

// METHODS //
    // Method to initialise game window
    public void initialise() {
        display = new DisplayWindow(gameTitle, gameWidth, gameHeight);
        displayCanvas = display.getCanvas();
    }

    // Method to update the game state
    public void update() {

    }

    // Method to render the graphics on the screen
    public void draw() {
        bufferStrategy = displayCanvas.getBufferStrategy();
        if(bufferStrategy == null) {
            displayCanvas.createBufferStrategy(3);
            return;
        }
        g = bufferStrategy.getDrawGraphics();
        // Draw

        g.fillRect(0, 0, gameWidth/2, gameHeight/2);

        // Finish

        bufferStrategy.show();
        g.dispose();
    }

    // Threading Methods //

    public void run() {
        initialise();
        while(isRunning) { // The Game Loop
            update(); // Update game state
            draw(); // Render the graphics on the screen
        }
    }

    // Method executed on game start
    public synchronized void start() {
        if(isRunning) return; // Safeguard in case start() is called a second time
        isRunning = true;
        thread = new Thread(this); // Start new thread of this class
        thread.start(); // Calls run() on Game object in thread
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
}
