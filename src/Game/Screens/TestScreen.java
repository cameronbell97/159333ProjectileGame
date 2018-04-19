package Game.Screens;

import Game.Data.Settings;
import Game.Display.DisplayElements.DummyElement;
import Game.Display.DisplayElements.TextElement;
import Game.Display.DisplayElements.VerticalListElement;
import Game.Display.UserInterface.TextManager;

import java.awt.*;

public class TestScreen extends Screen {
// VARIABLES //
    DummyElement testElement;

// CONSTRUCTORS //
    public TestScreen() {
        testElement = new DummyElement(1, Color.red, Color.black, 16);

        TextElement t1 = new TextElement("SCORE 1");
        TextElement t2 = new TextElement("SCORE 2");
        VerticalListElement v = new VerticalListElement(16);
        v.addChild(t1);
        v.addChild(t2);
        v.addChild(t1);
        v.addChild(t2);

        testElement.setChildElement(v);
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
