package Game.Data;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Cameron Bell - 27/03/2018
 * Key Manager Class
 * Class Object to Record Key Presses
 */

public class KeyManager implements KeyListener{
// VARIABLES //
    // All Keys //
    private boolean[] keys;

    // Key Shortcuts //
    public boolean
        left,
        right,
        forward,
        back,
        shift,
        spacebar,
        ctrl,
        alt,
        delete,
        enter,
        insert,
        esc;

// CONSTRUCTORS //
    public KeyManager() {
        keys = new boolean[256];
    }

// METHODS //
    // Method - Update Key Shortcuts //
    public void update() {
        // Update the Key Shortcut Booleans
        left = keys[KeyEvent.VK_A];
        right = keys[KeyEvent.VK_D];
        forward = keys[KeyEvent.VK_W];
        back = keys[KeyEvent.VK_S];
        ctrl = keys[KeyEvent.VK_CONTROL];
        shift = keys[KeyEvent.VK_SHIFT];
        spacebar = keys[KeyEvent.VK_SPACE];
        alt = keys[KeyEvent.VK_ALT];
        delete = keys[KeyEvent.VK_DELETE];
        enter = keys[KeyEvent.VK_ENTER];
        insert = keys[KeyEvent.VK_INSERT];
        esc = keys[KeyEvent.VK_ESCAPE];
    }

    // Method - KeyTyped Event Override //
    @Override
    public void keyTyped(KeyEvent e) {

    }

    // Method - Update Keys[] Entry on Key Press //
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        keys[key] = true;
    }

    // Method - Update Keys[] Entry on Key Release //
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        keys[key] = false;
    }

    // Return a Key's Value Based on Virtual Key Code //
    public boolean checkKey(int KeyEventCode) {
        return keys[KeyEventCode];
    }
}
