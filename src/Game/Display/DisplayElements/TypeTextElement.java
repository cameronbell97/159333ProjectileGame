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

public class TypeTextElement extends Element implements iCanHaveCodeTimer {
// VARIABLES //
    private static final int UNDERSCORE_FLASH_TIME = 30;

    TextManager textManager;
    int maxLength;
    int activeIndex;
    String letters[][];
    int letterCodes[][];
    boolean isTyped[][];
    boolean backTyped;
    String text;
    KeyManager km;
    boolean underscoreFlash;

// CONSTRUCTORS //
    public TypeTextElement(int maxLength) {
        super(0, 0);
        km = Handler.get().getKeyManager();
        textManager = new TextManager();
        setWidth(textManager.getWordWidth(maxLength));
        setHeight(textManager.getCharacterHeight());
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

        // Build Letter Codes
        letterCodes = new int[letters.length][letters[0].length];
        for(int i = 0; i < letters.length; i++) {
            for(int j = 0; j < letters[0].length; j++) {
                letterCodes[i][j] = KeyEvent.getExtendedKeyCodeForChar(letters[i][j].charAt(0));
            }
        }

        underscoreFlash = false;
        Handler.get().getTimerManager().newCodeTimer(UNDERSCORE_FLASH_TIME, this, "_");
    }

// METHODS //
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

    @Override
    public void draw(Graphics g, int xStart, int yStart) {
        if(underscoreFlash) {
            textManager.drawString(g, setUnderscore(text), "left", xStart, yStart);
        } else {
            textManager.drawString(g, text, "left", xStart, yStart);
        }
    }

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

    private String setUnderscore(String t) {
        if(t.length() == 0) {
            return "_";
        } else if(t.length() != maxLength) {
            return t + "_";
        } else return t;
    }
}
