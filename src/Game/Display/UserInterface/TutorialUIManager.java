package Game.Display.UserInterface;

import Game.Data.KeyManager;
import Game.Handler;

import java.awt.*;

/**
 * Cameron Bell - 06/06/2018
 * Tutorial UI Manager Class
 * Displays the Tutorial UI
 */

public class TutorialUIManager {
// VARIABLES //
    // Statics //
    private static final int SPACEBAR_PRESS_COUNT = 3;

    // Managers //
    private Handler handler;
    private TextManager textManager;
    private KeyManager keyManager;

    // Data //
    private int tutStage;
    private int xPencil;
    private int yPencil;

    // Keyboard Data //
    private boolean pressedA;
    private boolean pressedD;
    private boolean pressedW;
    private boolean pressedS;

    private boolean pressedSpace;
    private boolean justPressedSpace;
    private int pressedSpaceCount;

    private boolean pressedShift;
    private boolean pressedCtrl;

// CONSTRUCTORS //
    public TutorialUIManager() {
        handler = Handler.get();
        textManager = new TextManager();
        keyManager = handler.getKeyManager();

        xPencil = handler.getWidth()/2;
        yPencil = handler.getHeight()/8;

        tutStage = 0;
        pressedA = false;
        pressedD = false;
        pressedW = false;
        pressedS = false;

        pressedSpaceCount = 0;
        pressedSpace = false;
        justPressedSpace = false;

        pressedShift = false;
        pressedCtrl = false;
    }

// METHODS //
    // Method - Handles the Tutorial Progress //
    public void update() {
        switch(tutStage) {
            case 0:
                if(keyManager.left) pressedA = true; // [A] Key //
                if(keyManager.right) pressedD = true; // [D] Key //

                if(pressedA && pressedD) tutStage++;
                break;
            case 1:
                if(keyManager.forward) pressedW = true; // [W] Key //
                if(keyManager.back) pressedS = true; // [S] Key //

                if(pressedW && pressedS) tutStage++;
                break;
            case 2:
                pressedSpace = keyManager.spacebar;
                if(pressedSpace && !justPressedSpace) {  // [SPACE] Key //
                    pressedSpaceCount++;
                    justPressedSpace = true;
                }
                if(!pressedSpace && justPressedSpace) justPressedSpace = false;

                if(pressedSpaceCount >= SPACEBAR_PRESS_COUNT) tutStage++;
                break;
            case 3:
                pressedShift = keyManager.shift;
                pressedW = keyManager.forward;

                if(pressedShift && pressedW) tutStage++;
                break;
            case 4:
                pressedShift = keyManager.shift;

                if(!pressedShift) tutStage++;
                break;
            case 5:
                pressedCtrl = keyManager.ctrl;
                pressedA = keyManager.left;
                pressedD = keyManager.right;

                if((pressedCtrl && pressedA) || (pressedCtrl && pressedD)) tutStage++;
                break;
            case 6:
                pressedCtrl = keyManager.ctrl;

                if(!pressedCtrl) tutStage++;
                break;
            case 7:
                if(keyManager.esc) tutStage++;
                break;
        }
    }

    // Method - Draw's Text to the Screen //
    public void draw(Graphics g) {
        switch(tutStage) {
            case 0:
                textManager.drawString(g, "PRESS <A> OR <D> TO ROTATE", "center", xPencil, yPencil);
                break;
            case 1:
                textManager.drawString(g, "PRESS <W> OR <S> TO MOVE", "center", xPencil, yPencil);
                break;
            case 2:
                textManager.drawString(g, "PRESS <SPACEBAR> TO SHOOT", "center", xPencil, yPencil);

                if(pressedSpaceCount != SPACEBAR_PRESS_COUNT - 1) textManager.drawString(g, "PRESS <SPACEBAR> " + Integer.toString(SPACEBAR_PRESS_COUNT - pressedSpaceCount) + " TIMES", "center", xPencil, yPencil + TextManager.getCharacterHeight() + TextManager.LINE_INCREMENT);
                else textManager.drawString(g, "PRESS <SPACEBAR> " + Integer.toString(SPACEBAR_PRESS_COUNT - pressedSpaceCount) + " TIME", "center", xPencil, yPencil + TextManager.getCharacterHeight() + TextManager.LINE_INCREMENT);
                break;
            case 3:
                textManager.drawString(g, "PRESS <SHIFT> TO BOOST", "center", xPencil, yPencil);
                if(pressedShift) textManager.drawString(g, "NOW PRESS <W>", "center", xPencil, yPencil + TextManager.getCharacterHeight() + TextManager.LINE_INCREMENT);
                break;
            case 4:
                textManager.drawString(g, "PRESS <SHIFT> TO BOOST", "center", xPencil, yPencil);
                textManager.drawString(g, "YOU MOVE FASTER HOLDING <SHIFT>", "center", xPencil, yPencil + TextManager.getCharacterHeight() + TextManager.LINE_INCREMENT);
                break;
            case 5:
                textManager.drawString(g, "PRESS <CTRL> TO CONTROL SPEED", "center", xPencil, yPencil);
                if(pressedCtrl) textManager.drawString(g, "NOW PRESS <A> OR <D>", "center", xPencil, yPencil + TextManager.getCharacterHeight() + TextManager.LINE_INCREMENT);
                break;
            case 6:
                textManager.drawString(g, "PRESS <CTRL> TO CONTROL SPEED", "center", xPencil, yPencil);
                textManager.drawString(g, "YOU MOVE SLOWER HOLDING <CTRL>", "center", xPencil, yPencil + TextManager.getCharacterHeight() + TextManager.LINE_INCREMENT);
                break;
            case 7:
                textManager.drawString(g, "LASTLY, PRESS <ESC> FOR PAUSE MENU", "center", xPencil, yPencil);
                break;
            case 8:
                textManager.drawString(g, "TUTORIAL FINISHED, CONGRATS!", "center", xPencil, yPencil);
                textManager.drawString(g, "HIT 'EXIT' IN THE <ESC> SCREEN TO GO", "center", xPencil, yPencil + TextManager.getCharacterHeight() + TextManager.LINE_INCREMENT);
                textManager.drawString(g, "BACK TO THE MAIN MENU", "center", xPencil, yPencil + (TextManager.getCharacterHeight() + TextManager.LINE_INCREMENT) * 2);
        }
    }

// GETTERS & SETTERS //
    public int getStage() {
        return tutStage;
    }
}
