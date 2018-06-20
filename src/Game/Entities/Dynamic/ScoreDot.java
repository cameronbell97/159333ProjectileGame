package Game.Entities.Dynamic;

import Game.Display.Assets.AssetManager;
import Game.Entities.Entity;
import Game.Entities.EntityManager;
import Game.Data.Settings;
import Game.Handler;
import Game.Timer.CodeTimer;
import Game.Timer.iCanHaveCodeTimer;

import java.awt.*;

/**
 * Cameron Bell - 06/04/2018
 * ScoreDot Entity
 * Currently used to gain score for the player
 */

public class ScoreDot extends DynamicEntity implements iCanHaveCodeTimer {
// VARIABLES //
    // Statics
    private static final int OFFSCREEN_BOUNDARY = 0;
    private static final int DEF_HEIGHT_WIDTH = 16;
    private static final int DESPAWN_TIME = 16*60;
    private static final int DEF_MOVE_SPEED = 2;
    private static final int DEF_COLLIDE_DISTANCE = 10;

    private static final int SPRITE_MEDIUM_THRESHHOLD = 8;
    private static final int SPRITE_LARGE_THRESHHOLD = 20;

    protected static final double DEF_FADE_DECREMENT = 0.02;
    protected double alphaFade;
    protected boolean fade;

    private int value;
    private int yImg;
    private boolean merged = false;
    private float distanceFromPlayer;
    private int magnetDistance;
    private int collideDistance;
    private float deceleration;
    private boolean active;

    private PlayerEntity player;

// CONSTRUCTORS //
    public ScoreDot(Entity parent, int value) {
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

    public ScoreDot(Entity parent, float xpos, float ypos, int value) {
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
    // Method - Initialise the Entity //
    private void initialise() {
        active = true;

        //Set Img
        if(this.value < SPRITE_MEDIUM_THRESHHOLD) yImg = 0;
        else if(this.value < SPRITE_LARGE_THRESHHOLD) yImg = 1;
        else yImg = 2;

        img = AssetManager
                .get()
                .getSprite(11, Handler.getIntFromRange(0, 3), yImg);

        setCollisionBox();

        // Set Despawn Timer
        Handler.get().getTimerManager().newCodeTimer(DESPAWN_TIME + Handler.getIntFromRange(-30, 30), this, "DIE");

        // Set Variables
        moveSpeed = DEF_MOVE_SPEED;
        magnetDistance = Settings.scoredot_magnet_distance;
        collideDistance = DEF_COLLIDE_DISTANCE;
        deceleration = (float)0.04;
        xmove = 0;
        ymove = 0;

        fade = false;
        alphaFade = 1;
    }

    // Method - Draw The Entity //
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

    // Method - Update the State of the Entity //
    @Override
    public void update(int dt) {
        // Fading Mechanics
        if(fade && alphaFade > 0) alphaFade = Math.max(alphaFade - dt * DEF_FADE_DECREMENT, 0);
        if(alphaFade <= 0) destroy();

        // Check for out-of-screen
        if(     xpos <= -OFFSCREEN_BOUNDARY ||
                ypos <= -OFFSCREEN_BOUNDARY ||
                xpos >= Settings.game_width + width + OFFSCREEN_BOUNDARY ||
                ypos >= Settings.game_height + height + OFFSCREEN_BOUNDARY) {
            destroy();
        }

        // Check proximity to player
        player = handler.getEntityManager().getPlayer();
        calcDistanceFromPlayer();

        // Set speeds depending on distance
        if(distanceFromPlayer <= magnetDistance)
            setMoveSpeeds();

        // Check collision with player
        if(distanceFromPlayer <= collideDistance) {
            player.collide(this);
            this.collide(player);
        }

        // Deceleration mechanics
        decelerate(dt, deceleration);

        if(collision != null) collision.update(dt);
        move(dt);
    }

    // Method - Determine the distance from the Player //
    private void calcDistanceFromPlayer() {
        float distance;
        double newDir = 0;

        // Define point variables for easier function designing
        float P1x = this.getXpos() + width / 2;
        float P1y = this.getYpos() + height / 2;
        float P2x = 0;
        float P2y = 0;

        if(player != null) {
            P2x = player.getXpos() + player.getWidth() / 2;
            P2y = player.getYpos() + player.getHeight() / 2;
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

    // Method Override - Collide with another Entity //
    @Override
    public void collide(Entity ec) {
        if(ec instanceof Game.Entities.Dynamic.PlayerEntity) {
            destroy();
        } else if (ec instanceof ScoreDot && !merged) {
            handler.getEntityManager()
                    .subscribe(new ScoreDot(
                            this,
                            (this.getXpos() + ec.getXpos()) / 2,
                            (this.getYpos() + ec.getYpos()) / 2,
                            this.getValue() + ((ScoreDot) ec).getValue()))
            ;
            ((ScoreDot) ec).setMerged(true);
            ((ScoreDot) ec).destroy();
            destroy();
        }
    }

    // Method - Destroy the Entity //
    public void destroy() {
        EntityManager em = handler.getEntityManager();
        em.unsubscribe(this);
        em.unsubscribe(collision);
    }

    // Method - Recieve Timer Notification //
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

    // Method Override - Used for initial spacial setup for the Collision Box //
    @Override
    public void setCollisionBox() {
//        //Set Collision Box
//        if(this.value < 10) {
//            collision = new CollisionBox(xpos+5, ypos+5, 6, 6, 5, 5, this);
//        }
//        else if(this.value < 25) {
//            collision = new CollisionBox(xpos+4, ypos+4, 8, 8, 4, 4, this);
//        }
//        else {
//            collision = new CollisionBox(xpos+1, ypos+1, 14, 14, 1, 1, this);
//        }
        this.collision = null;
    }

// GETTERS & SETTERS //
    public int getValue() {
        return value;
    }
    public void setMerged(boolean merged) {
        this.merged = merged;
    }
}
