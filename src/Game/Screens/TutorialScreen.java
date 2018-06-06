package Game.Screens;

import Game.Data.KeyManager;
import Game.Data.Settings;
import Game.Display.UserInterface.TutorialUIManager;
import Game.Entities.EntityManager;

import java.awt.*;

/**
 * Cameron Bell - 06/06/2018
 * Tutorial Screen Class
 * The Tutorial Screen
 */

public class TutorialScreen extends Screen {
// VARIABLES //
    private KeyManager keyManager;
    private EntityManager entityManager;
    private TutorialUIManager tutorialUIManager;

// METHODS //
    public TutorialScreen() {
        handler.newTutorial();
        keyManager = handler.getKeyManager();
        entityManager = handler.getEntityManager();
        tutorialUIManager = new TutorialUIManager();
    }

    @Override
    public void update(int dt) {
        handler.updateTutorial(dt);
        tutorialUIManager.update();
        if(keyManager.esc) ScreenManager.setScreen(new PauseMenuScreen(this));
    }

    @Override
    public void draw(Graphics g) {
        // Draw Background
        g.setColor(new Color(0, 0, 20));
        g.fillRect(0, 0, Settings.game_width, Settings.game_height);

        // Draw Entities
        entityManager.draw(g);

        // Draw UI
        tutorialUIManager.draw(g);
    }
}
