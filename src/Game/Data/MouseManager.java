package Game.Data;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Cameron Bell - 13/04/2018
 * Mouse Manager Class
 * Class Object to Mouse Input
 */
public class MouseManager implements MouseListener, MouseMotionListener{
// VARIABLES //
    // Shortcuts //
    private boolean leftMouse, rightMouse;
    private int mouseX, mouseY;

// CONSTRUCTORS //
    public MouseManager() {
        leftMouse = false;
        rightMouse = false;
        mouseX = 0;
        mouseY = 0;
    }

// METHODS //
    // Method - Update Shortcut on Mouse Press //
    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1)
            leftMouse = true;
        else if(e.getButton() == MouseEvent.BUTTON3)
            rightMouse = true;
    }

    // Method - Update Shortcut on Mouse Release //
    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1)
            leftMouse = false;
        else if(e.getButton() == MouseEvent.BUTTON3)
            rightMouse = false;
    }

    // Method - Update Mouse X,Y on Mouse Press //
    @Override
    public void mouseClicked(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    // Method - Update Shortcut on Mouse Drag //
    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    // Method - Update Shortcut on Mouse Moved //
    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

// GETTERS & SETTERS //
    public boolean checkLeftMouse() {
        return leftMouse;
    }
    public boolean checkRightMouse() {
        return rightMouse;
    }
    public int getMouseX() {
        return mouseX;
    }
    public int getMouseY() {
        return mouseY;
    }
}
