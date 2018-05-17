package Game.Entities.Dynamic;

import Game.Display.Assets.AssetManager;
import Game.Entities.Collision.CollisionBox;
import Game.Entities.Entity;
import Game.Entities.EntityManager;
import Game.Data.Settings;
import Game.Handler;
import Game.Timer.CodeTimer;
import Game.Timer.iCanHaveCodeTimer;

import java.awt.*;

/**
 * Created by Cameron on 6/04/2018.
 */
public class ExpDot extends DynamicEntity implements iCanHaveCodeTimer {
// VARIABLES //
    // Statics
    private static final int OFFSCREEN_BOUNDARY = 0;
    private static final int DEF_HEIGHT_WIDTH = 16;
    private static final int DESPAWN_TIME = 16*60;
    private static final int DEF_MOVE_SPEED = 2;

    protected static final double DEF_FADE_DECREMENT = 0.02;
    protected double alphaFade;
    protected boolean fade;

    private int value;
    private int yImg;
    private boolean merged = false;
    private float distanceFromPlayer;
    private int pickupDistance;
    private float deceleration;
    private boolean active;

// CONSTRUCTORS //
    public ExpDot(Entity parent, int value) {
        super(parent.getXpos()+(parent.getWidth() / 2) - DEF_HEIGHT_WIDTH,
                parent.getYpos()+(parent.getHeight() / 2) - DEF_HEIGHT_WIDTH,
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
        active = true;

        //Set Img
        if(this.value < 10) {
            yImg = 0;
        }
        else if(this.value < 25) {
            yImg = 1;
        }
        else {
            yImg = 2;
        }
        img = AssetManager
                .get()
                .getSprite(11, Handler.getIntFromRange(0, 3), yImg);

        setCollisionBox();

        // Set Despawn Timer
        Handler.get().getTimerManager().newCodeTimer(DESPAWN_TIME + Handler.getIntFromRange(-30, 30), this, "DIE");

        // Set Variables
        moveSpeed = DEF_MOVE_SPEED;
        pickupDistance = Settings.exp_pickup_distance;
        deceleration = (float)0.04;
        xmove = 0;
        ymove = 0;

        fade = false;
        alphaFade = 1;
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

    @Override
    public void update() {
        // Fading Mechanics
        if(fade && alphaFade > 0) alphaFade = Math.max(alphaFade - DEF_FADE_DECREMENT, 0);
        if(alphaFade <= 0) destroy();

        // Check for out-of-screen
        if(     xpos <= -OFFSCREEN_BOUNDARY ||
                ypos <= -OFFSCREEN_BOUNDARY ||
                xpos >= Settings.game_width + width + OFFSCREEN_BOUNDARY ||
                ypos >= Settings.game_height + height + OFFSCREEN_BOUNDARY) {
            destroy();
        }

        // Check proximity to player
        calcDistanceFromPlayer();
        if(distanceFromPlayer <= pickupDistance) {
            setMoveSpeeds();
        }

        // Deceleration mechanics
        decelerate(deceleration);

        if(collision != null) collision.update();
        move();
    }

    // Method - Determine the distance from the Player
    private void calcDistanceFromPlayer() {
        EntityManager em = handler.getEntityManager();

        float distance;
        double newDir = 0;

        // Define point variables for easier function designing
        float P1x = this.getXpos() + width / 2;
        float P1y = this.getYpos() + height / 2;
        float P2x = 0;
        float P2y = 0;

        if(em.getPlayer() != null) {
            P2x = em.getPlayer().getXpos() + em.getPlayer().getWidth() / 2;
            P2y = em.getPlayer().getYpos() + em.getPlayer().getHeight() / 2;
        } else {
            this.distanceFromPlayer = 1000;
            return;
        }

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
        if(ec instanceof Game.Entities.Dynamic.PlayerEntity) {
            destroy();
        } else if (ec instanceof Game.Entities.Dynamic.ExpDot && !merged) {
            handler.getEntityManager()
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
        EntityManager em = handler.getEntityManager();
        em.unsubscribe(this);
        em.unsubscribe(collision);
    }

    @Override
    public void timerNotify(CodeTimer t) {
        // Get Code
        String code = t.getCode();

        // Do depending on code
        switch (code) {
            case "DIE":
                if(active) {
                    fade = true;
                    active = false;
                }
                break;
        }

        Handler.get().getTimerManager().unsubTimer(t);
    }

// GETTERS & SETTERS //
    public int getValue() {
        return value;
    }

    public void setMerged(boolean merged) {
        this.merged = merged;
    }

    @Override
    public void setCollisionBox() {

        //Set Collision Box
        if(this.value < 10) {
            collision = new CollisionBox(xpos+5, ypos+5, 6, 6, 5, 5, this);
        }
        else if(this.value < 25) {
            collision = new CollisionBox(xpos+4, ypos+4, 8, 8, 4, 4, this);
        }
        else {
            collision = new CollisionBox(xpos+1, ypos+1, 14, 14, 1, 1, this);
        }
    }
}
