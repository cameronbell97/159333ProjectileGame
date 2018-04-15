package Game.Display;

import javax.swing.*;
import java.awt.*;

/**
 * Cameron Bell - 20/03/2018
 * Game.Display.DisplayWindow Class
 * Displays the window in which the game operates
 */

public class DisplayWindow {
// VARIABLES //
    private JFrame frame;
    private String fTitle;
    private int fWidth;
    private int fHeight;

    public Canvas canvas;

// CONSTRUCTORS //
    public DisplayWindow(String title, int width, int height) {
        fTitle = title;
        fWidth = width;
        fHeight = height;

        // Create JFrame
        frame = new JFrame(fTitle); // Set window title
        frame.setSize(fWidth, fHeight); // Set window dimensions
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // To ensure game closes upon hitting the red X button
        frame.setResizable(false); // Restricts resizing the window
        frame.setVisible(true);

        // Create Canvas
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(fWidth, fHeight));
        canvas.setMinimumSize(new Dimension(fWidth, fHeight));
        canvas.setMaximumSize(new Dimension(fWidth, fHeight));
        canvas.setFocusable(false);

        frame.add(canvas); // Puts the canvas in the JFrame
        frame.pack();

    }

// GETTERS & SETTERS //
    public Canvas getCanvas() {
        return canvas;
    }

    public JFrame getFrame() {
        return frame;
    }
}
