package Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Cameron Bell - 27/03/2018
 * Key Manager Class
 * A class that records key presses for gameplay
 */

public class KeyManager implements KeyListener{
// VARIABLES //
    private boolean[] keys;
    public boolean
            left,
            right,
            forward,
            back,
            shift,
            spacebar,
            ctrl;

// CONSTRUCTORS //
    public KeyManager() {
        keys = new boolean[256];
    }

// METHODS //
    // Updates variables
    public void update() {
        // Update the key shortcut booleans
        left = keys[KeyEvent.VK_A];
        right = keys[KeyEvent.VK_D];
        forward = keys[KeyEvent.VK_W];
        back = keys[KeyEvent.VK_S];
        ctrl = keys[KeyEvent.VK_CONTROL];
        shift = keys[KeyEvent.VK_SHIFT];
        spacebar = keys[KeyEvent.VK_SPACE];

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        keys[key] = true;
        System.out.println("Key " + key + " pressed"); // DEBUG // Prints what key was pressed
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        keys[key] = false;
        System.out.println("Key " + key + " released"); // DEBUG // Prints what key was pressed
    }
}
