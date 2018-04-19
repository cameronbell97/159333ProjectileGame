package Game.Screens;

import Game.Data.Settings;
import Game.Display.DisplayElements.HorizontalListElement;
import Game.Display.DisplayElements.PaddedElement;
import Game.Display.DisplayElements.TextElement;
import Game.Display.DisplayElements.VerticalListElement;

import java.awt.*;

public class TestScreen extends Screen {
// VARIABLES //
    PaddedElement testElement;

// CONSTRUCTORS //
    public TestScreen() {
        testElement = new PaddedElement(1, Color.red, Color.black, 16);

        TextElement t1 = new TextElement("SCORE 1");
        TextElement t2 = new TextElement("SCORE 2");
        VerticalListElement v = new VerticalListElement(16);
        v.addChild(t1);
        v.addChild(t2);
        v.addChild(t1);
        v.addChild(t2);
        HorizontalListElement hz = new HorizontalListElement(16);
        hz.addChild(v);
        hz.addChild(v);

        testElement.setChildElement(hz);
    }

// METHODS //
    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g) {
        // Draw Background
        g.setColor(new Color(0, 0, 20));
        g.fillRect(0, 0, Settings.game_width, Settings.game_height);

        // Draw Menu
        int xStart = (Settings.game_width / 2) - (testElement.getWidth() / 2);
        int yStart = (Settings.game_height / 2) - (testElement.getHeight() / 2);

        testElement.draw(g, xStart, yStart);
    }
}
