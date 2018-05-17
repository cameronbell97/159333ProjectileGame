package Game.Display.DisplayElements;

import Game.Data.KeyManager;
import Game.Display.UserInterface.TextManager;
import Game.Handler;
import Game.Timer.CodeTimer;
import Game.Timer.TimerManager;
import Game.Timer.iCanHaveCodeTimer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;

/**
 * Cameron Bell - 19/04/2018
 * Type Text Element Class
 * Element Class for Managing and Tracking User Input for Typing
 */
public class TypeTextElement extends Element implements iCanHaveCodeTimer {
// VARIABLES //
    // Statics //
    private static final int UNDERSCORE_FLASH_TIME = 30;

    // Managers //
    TextManager textManager;
    KeyManager km;

    // Data
    int
            maxLength,
            activeIndex,
            letterCodes[][];
    String
            text,
            letters[][];
    boolean
            backTyped,
            underscoreFlash,
            isTyped[][];

// CONSTRUCTORS //
    public TypeTextElement(int maxLength) {
        super(0, 0);

        // Get Managers //
        km = Handler.get().getKeyManager();
        textManager = new TextManager();

        // Set Dimensions //
        setWidth(textManager.getWordWidth(maxLength));
        setHeight(textManager.getCharacterHeight());

        // Set Data //
        this.maxLength = maxLength;
        activeIndex = 0;
        this.text = "";
        letters = new String[][] {
                {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"},
                {"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T"},
                {"U", "V", "W", "X", "Y", "Z", ",", ".", "[", "?"}, //[2][8] = !
                {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"},
                {"]", "<", ">", "-", "=", "\"", "\'", "/", "\\", "*"} // [4][4] = _
        };
        isTyped = new boolean[5][10];
        for (boolean[] array : isTyped) {
            for(boolean element : array)
                element = false;
        }
        backTyped = false;
        underscoreFlash = false;

        // Build Letter Codes //
        letterCodes = new int[letters.length][letters[0].length];
        for(int i = 0; i < letters.length; i++) {
            for(int j = 0; j < letters[0].length; j++) {
                letterCodes[i][j] = KeyEvent.getExtendedKeyCodeForChar(letters[i][j].charAt(0));
            }
        }

        // Start Flashing Underscore //
        Handler.get().getTimerManager().newCodeTimer(UNDERSCORE_FLASH_TIME, this, "_");
    }

// METHODS //
    // Method - Update Text Box Data //
    @Override
    public void update() {
        for(int i = 0; i < letters.length; i++) {
            for(int j = 0; j < letters[0].length; j++) {
                if(km.checkKey(letterCodes[i][j])) {
                    if (!isTyped[i][j]) {
                        keyTyped(letters[i][j]);
                        isTyped[i][j] = true;
                    }
                } else {
                    isTyped[i][j] = false;
                }
            }
        }
        if(km.checkKey(KeyEvent.VK_BACK_SPACE)) {
            if(!backTyped) {
                keyTyped("\b");
                backTyped = true;
            }
        } else {
            backTyped = false;
        }
    }

    // Method - For Drawing Element //
    @Override
    public void draw(Graphics g, int xStart, int yStart) {
        if(underscoreFlash) {
            textManager.drawString(g, setUnderscore(text), "left", xStart, yStart);
        } else {
            textManager.drawString(g, text, "left", xStart, yStart);
        }
    }

    // Method - Append Typed Key Character to Text Box String Value, if Possible //
    private void keyTyped(String key) {
        // Handle Backspace
        if(key.equals("\b")) {
            if(text.length() > 1) {
                text = text.substring(0, text.length() - 1);
            } else if(text.length() == 1) {
                text = "";
            }
        }
        else if(text.length() < maxLength) text = text + key;
    }

    public String getText() {
        return text;
    }

    // Method - Recieve Timer Notification //
    @Override
    public void timerNotify(CodeTimer t) {
        String code = t.getCode();
        TimerManager tm = Handler.get().getTimerManager();

        if(code.equals("_")) {
            underscoreFlash = true;
            tm.newCodeTimer(UNDERSCORE_FLASH_TIME, this, " ");
        } else if(code.equals(" ")) {
            underscoreFlash = false;
            tm.newCodeTimer(UNDERSCORE_FLASH_TIME, this, "_");
        }

        tm.unsubTimer(t);
    }

    // Method - Append Underscore to String //
    private String setUnderscore(String t) {
        if(t.length() == 0) {
            return "_";
        } else if(t.length() != maxLength) {
            return t + "_";
        } else return t;
    }
}
