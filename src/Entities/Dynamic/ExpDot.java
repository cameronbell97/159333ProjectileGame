package Entities.Dynamic;

import Assets.AssetManager;
import Entities.Collision.CollisionBox;
import Entities.Entity;
import Entities.EntityManager;
import Game.Launcher;
import Timer.CodeTimer;
import Timer.TimerManager;
import Timer.iCanHaveCodeTimer;

/**
 * Created by Cameron on 6/04/2018.
 */
public class ExpDot extends DynamicEntity implements iCanHaveCodeTimer {
// VARIABLES //
    // Statics
    private static final int OFFSCREEN_BOUNDARY = 0;
    private static final int DEF_HEIGHT_WIDTH = 10;
    private static final int DESPAWN_TIME = 10*60;

    private int value;
    private int yImg;
    private boolean merged = false;

// CONSTRUCTORS //
    public ExpDot(Entity parent, int value) {
        super(parent.getXpos()+(parent.getWidth()/2-5),
                parent.getYpos()+(parent.getHeight()/2-5),
                DEF_HEIGHT_WIDTH,
                DEF_HEIGHT_WIDTH,
                (Math.PI / 2))
        ;
        this.parent = parent;
        this.value = value;
        initialise();
    }

    public ExpDot(Entity parent, float xpos, float ypos, int value) {
        super(xpos,
                ypos,
                DEF_HEIGHT_WIDTH,
                DEF_HEIGHT_WIDTH,
                (Math.PI / 2))
        ;
        this.parent = parent;
        this.value = value;
        initialise();
    }

// METHODS //
    private void initialise() {
        // Collision Box
        collision = new CollisionBox(xpos, ypos, width, height, 0, 0, this);

        //Set Img
        if(this.value < 10) yImg = 1;
        else if(this.value < 25) yImg = 2;
        else yImg = 3;
        img = AssetManager
                .get()
                .getSprite(10, Game.Game.getIntFromRange(0, 3), yImg);

        // Set Despawn Timer
        TimerManager.get().newCodeTimer(DESPAWN_TIME + Game.Game.getIntFromRange(-30, 30), this, "DIE");
    }

    @Override
    public void update() {

        if(     xpos <= -OFFSCREEN_BOUNDARY ||
                ypos <= -OFFSCREEN_BOUNDARY ||
                xpos >= Launcher.DEF_GAME_WIDTH + width + OFFSCREEN_BOUNDARY ||
                ypos >= Launcher.DEF_GAME_HEIGHT + height + OFFSCREEN_BOUNDARY) {
            destroy();
        }

    }

    @Override
    public void collide(Entity ec) {
        if(ec instanceof Entities.Dynamic.PlayerEntity) {
            destroy();
        } else if (ec instanceof Entities.Dynamic.ExpDot && !merged) {
            EntityManager
                    .get()
                    .subscribe(new ExpDot(
                            this,
                            (this.getXpos() + ec.getXpos()) / 2,
                            (this.getYpos() + ec.getYpos()) / 2,
                            this.getValue() + ((ExpDot) ec).getValue()))
            ;
            ((ExpDot) ec).setMerged(true);
            ((ExpDot) ec).destroy();
            destroy();
        }
    }

    public void destroy() {
        EntityManager.get().unsubscribe(this);
        EntityManager.get().unsubscribe(collision);
    }

    @Override
    public void timerNotify(CodeTimer t) {
        // Get Code
        String code = t.getCode();

        // Do depending on code
        switch (code) {
            case "DIE":
                destroy();
                break;
        }
    }

// GETTERS & SETTERS //
    public int getValue() {
        return value;
    }

    public void setMerged(boolean merged) {
        this.merged = merged;
    }

}
