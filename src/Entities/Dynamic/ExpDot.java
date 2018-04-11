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
    private static final int DEF_PICKUP_DISTANCE = 48;
    private static final int DEF_MOVE_SPEED = 2;

    private int value;
    private int yImg;
    private boolean merged = false;
    private float distanceFromPlayer;
    private int pickupDistance;
    private float deceleration;

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

        // Set Variables
        moveSpeed = DEF_MOVE_SPEED;
        pickupDistance = DEF_PICKUP_DISTANCE;
        deceleration = (float)0.04;
        xmove = 0;
        ymove = 0;
    }

    @Override
    public void update() {
        // Check for out-of-screen
        if(     xpos <= -OFFSCREEN_BOUNDARY ||
                ypos <= -OFFSCREEN_BOUNDARY ||
                xpos >= Launcher.DEF_GAME_WIDTH + width + OFFSCREEN_BOUNDARY ||
                ypos >= Launcher.DEF_GAME_HEIGHT + height + OFFSCREEN_BOUNDARY) {
            destroy();
        }

        // Check proximity to player
        calcDistanceFromPlayer();
        if(distanceFromPlayer <= pickupDistance) {
            setMoveSpeeds();
            collision.update();
        }

        // Deceleration mechanics
        if(xmove > 0) xmove = Math.max(0, xmove - xmove*((float)0.01 + deceleration));
        if(xmove < 0) xmove = Math.min(0, xmove - xmove*((float)0.01 + deceleration));
        if(ymove > 0) ymove = Math.max(0, ymove - ymove*((float)0.01 + deceleration));
        if(ymove < 0) ymove = Math.min(0, ymove - ymove*((float)0.01 + deceleration));

        move();
    }

    // Method - Determine the distance from the Player
    private void calcDistanceFromPlayer() {
        float distance;
        double newDir = 0;

        // Define point variables for easier function designing
        float P1x = this.getXpos();
        float P1y = this.getYpos();
        float P2x = EntityManager.get().getPlayer().getXpos() + EntityManager.get().getPlayer().getWidth() / 2;
        float P2y = EntityManager.get().getPlayer().getYpos() + EntityManager.get().getPlayer().getHeight() / 2;

        // Determine right-angled-triangle's opposite and adjacent lengths
        float triangleX = Math.abs(P2x - P1x);
        float triangleY = Math.abs(P2y - P1y);

        // Calculate Hypotenuse (Distance from Player)
        distance = (float) Math.sqrt((triangleX*triangleX)+(triangleY*triangleY));

        // Set Distance
        this.distanceFromPlayer = distance;

        // Calculate Direction // --------------------------------------------------- //

        // In the event tx = 0, don't do calculations
        if(triangleX == 0) {
            if(P1y > P2y) {
                this.direction = Math.PI / 2;
                return;
            }
            else {
                this.direction = (3*Math.PI) / 2;
                return;
            }
        }

        // tan(theta) = (ty / tx), so theta = inversetan(ty / tx)
        double theta = Math.atan(triangleY / triangleX);

        // Determine direction depending on P1's location
        if(P1x > P2x && P1y < P2y) { // Quadrant 1
            newDir = Math.PI + theta;
        }
        else if(P1x < P2x && P1y < P2y) { // Quadrant 2
            newDir = -theta;
        }
        else if(P1x < P2x && P1y > P2y) { // Quadrant 3
            newDir = theta;
        }
        else if(P1x > P2x && P1y > P2y) { // Quadrant 4
            newDir = Math.PI - theta;
        }

        // Set Direction
        this.direction = newDir;
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
