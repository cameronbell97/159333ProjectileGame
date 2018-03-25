import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;

/**
 * Cameron Bell - 20/03/2018
 * Game Class
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
    private SpriteSheet spritesheet;

    int smello = 0;

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
        display = new DisplayWindow(gameTitle, gameWidth, gameHeight);
        displayCanvas = display.getCanvas();
        spritesheet = new SpriteSheet(ImageLoader.load("tile01.png")); // load the spritesheet
    }

    // Method to update the game state
    public void update() {
        smello++;
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

        g.drawImage(spritesheet.getSprite("player"), smello+64, 64, null);
        g.drawImage(spritesheet.extract(64, 0, 64, 64), smello*2+128, 64, null);
        g.drawImage(spritesheet.getSprite("player"), smello+96, smello+128, null);

        //test code
//        for(int i = 0; i < (gameHeight/4)-1; i++) {
//            if(g.getColor() == Color.magenta) g.setColor(Color.orange);
//            else g.setColor(Color.magenta);
//            g.drawRect(i*4, i*4, (gameWidth-(i*8)), (gameHeight-(i*8)));
//        }

//        g.setColor(Color.BLUE);
//        g.fillRect(0,0,16,16);
//        g.fillRect(16,16,32,32);
//        g.fillRect(32+16,32+16,64,64);
//        g.fillRect(64+32+16,64+32+16,128,128);
//        g.drawImage(ImageLoader.load("player_placeholder.png"), 128+64+32+16,128+64+32+16,null);
//        g.drawImage(ImageLoader.load("player_placeholder.png"), 128+64+32+16,128+64+32+16+16,null);
//        g.drawImage(ImageLoader.load("tile01.png"), 128+64+32+16+16,128+64+32+16+16+16,null);

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

        // The Game Loop // -- Start --
        while(isRunning) {
            now = System.nanoTime();
            delta += (now - lasttime) / timepertick; // Delta += time until next we run the game loop next
            lasttime = now;
            timer += now - lasttime;

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
                // Print FPS here to get one every second
                ticks = 0;
                timer = 0;
            }
        }
        // The Game Loop // -- End --
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
