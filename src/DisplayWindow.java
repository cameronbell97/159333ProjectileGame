import javax.swing.*;

/**
 * Cameron Bell - 20/03/2018
 * DisplayWindow Class
 * Displays the window in which the game operates
 */
public class DisplayWindow {
    // VARIABLES //
    private JFrame frame;
    private String fTitle;
    private int fWidth;
    private int fHeight;

    // CONSTRUCTOR //
    public DisplayWindow(String title, int width, int height) {
        fTitle = title;
        fWidth = width;
        fHeight = height;

        frame = new JFrame(fTitle); // Set window title
        frame.setSize(fWidth, fHeight); // Set window dimensions
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // To ensure game closes upon hitting the red X button
        frame.setResizable(false); // Restricts resizing the window
        frame.setVisible(true);
    }
}
