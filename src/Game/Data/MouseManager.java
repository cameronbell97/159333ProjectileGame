package Game.Data;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by Cameron on 13/04/2018.
 */
public class MouseManager implements MouseListener, MouseMotionListener{
// VARIABLES //
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
    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1)
            leftMouse = true;
        else if(e.getButton() == MouseEvent.BUTTON3)
            rightMouse = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1)
            leftMouse = false;
        else if(e.getButton() == MouseEvent.BUTTON3)
            rightMouse = false;
    }

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

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();

    }

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
