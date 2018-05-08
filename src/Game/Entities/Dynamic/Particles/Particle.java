package Game.Entities.Dynamic.Particles;

import Game.Entities.Dynamic.DynamicEntity;
import Game.Entities.EntityManager;
import Game.Handler;
import Game.Timer.*;

import java.awt.*;

/**
 * Cameron Bell - 05/04/2018
 * Particle Abstract Class
 */

public abstract class Particle extends DynamicEntity implements iCanHaveCodeTimer {
// VARIABLES //
    public static final int DEF_PARTICLE_WIDTH = 16;
    public static final int DEF_PARTICLE_HEIGHT = 16;
    protected static final double DEF_FADE_DECREMENT = 0.02;
    protected double alphaFade;
    protected boolean fade;

// CONSTRUCTORS //
    public Particle(float x, float y, int w, int h, double direction) {
        super(x, y, w, h, direction);
        alphaFade = 1;
        fade = false;
    }

// METHODS //
    @Override
    public void timerNotify(CodeTimer t) {
        String timerCode = t.getCode(); // Get timer code
        handler.getTimerManager().unsubTimer(t); // Unsubscribe the timer

        switch (timerCode) {
            case "DIE":
                fade = true;
                break;
        }
    }

    @Override
    public void update() {
        // Fading Mechanics
        if(fade && alphaFade > 0) alphaFade = Math.max(alphaFade - DEF_FADE_DECREMENT, 0);
        if(alphaFade <= 0) destroy();
    }

    @Override
    public void draw(Graphics g) {
//        if(img == null) return;
        Graphics2D g2d = (Graphics2D) g;
        if(fade) {
            AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)alphaFade);
            g2d.setComposite(ac);
            g2d.drawImage(aTransOp.filter(img, null), (int) xpos, (int) ypos, null);
            ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1);
            g2d.setComposite(ac);
        } else {
            g2d.drawImage(aTransOp.filter(img, null), (int) xpos, (int) ypos, null);
        }
    }

    protected void destroy() {
        handler.getEntityManager().unsubscribe(this);
    }

    @Override
    public void setCollisionBox() {

    }
}
