package Game.Display.DisplayElements;

import Game.Data.KeyManager;
import Game.Display.UserInterface.TextManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;

public class TypeTextElement extends Element {
// VARIABLES //
    TextManager textManager;
    int maxLength;
    int activeIndex;
    String letters[][];
    int letterCodes[][];
    String text;
    KeyManager km;

// CONSTRUCTORS //
    public TypeTextElement(int maxLength) {
        super(0, 0);
        km = KeyManager.get();
        textManager = new TextManager();
        setWidth(textManager.getWordWidth(maxLength));
        setHeight(textManager.getCharacterHeight());
        this.maxLength = maxLength;
        activeIndex = 0;
        this.text = "";
        letters = new String[][] {
                {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"},
                {"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T"},
                {"U", "V", "W", "X", "Y", "Z", ",", ".", "!", "?"},
                {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"},
                {" ", "<", ">", "-", "_", "\"", "\'", "/", "\\", "*"}
        };

        // Build Letter Codes
        letterCodes = new int[letters.length][letters[0].length];
        for(int i = 0; i < letters.length; i++) {
            for(int j = 0; j < letters[0].length; j++) {
                letterCodes[i][j] = KeyEvent.getExtendedKeyCodeForChar(letters[i][j].charAt(0));
            }
        }
    }

// METHODS //
    @Override
    public void update() {
        // stream map

        letterCodes = new int[letters.length][letters[0].length];
        for(int i = 0; i < letters.length; i++) {
            for(int j = 0; j < letters[0].length; j++) {
                if(km.checkKey(letterCodes[i][j]))
                    keyTyped(letters[i][j]);
            }
        }
    }

    @Override
    public void draw(Graphics g, int xStart, int yStart) {
        textManager.drawString(g, text, "left", xStart, yStart);
    }

    private void keyTyped(String key) {
        text = text + key;
    }
}
