package Game.Entities.Dynamic.Enemies;

import Game.Entities.Entity;
import Game.Entities.EntityManager;

public abstract class TargetingEnemy extends Enemy {
// VARIABLES //
    private static final double DEF_ROTATE_SPEED = 0.005 * Math.PI;

    protected double rotationSpeed;
    protected float distanceFromPlayer;
    protected double directionToPlayer;


// CONSTRUCTORS //
    public TargetingEnemy(float x, float y, int w, int h, double direction) {
        super(x, y, w, h, direction);
        calcPlayerDistanceAndDirection();
        rotationSpeed = DEF_ROTATE_SPEED;
    }

    public TargetingEnemy(float x, float y, int w, int h, double direction, double rotateSpeed) {
        super(x, y, w, h, direction);
        calcPlayerDistanceAndDirection();
        rotationSpeed = rotateSpeed;
    }

// METHODS //
    @Override
    public void update() {
        calcPlayerDistanceAndDirection();
        rotateToPlayer();
        setMoveSpeeds();
        rotateSprite();
    }

    @Override
    public void collide(Entity ec) {

    }

    private void rotateToPlayer() {
        // Keep the entity's direction in the range 0 < x < 2(PI)
        if(this.direction > (2 * Math.PI)) this.direction -= (2 * Math.PI);
        if(this.direction < 0) this.direction += (2 * Math.PI);

        // Don't need to do calculations if neither entities have moved
        if(directionToPlayer == this.direction) return;

        if((directionToPlayer > this.direction && directionToPlayer - this.direction < Math.PI) || ((this.direction - directionToPlayer) > Math.PI)) {
            if(this.direction + rotationSpeed > directionToPlayer && !((this.direction - directionToPlayer) > Math.PI)) {
                this.direction = directionToPlayer;
            }
            else this.direction += rotationSpeed;
        } else {
            if (this.direction - rotationSpeed < directionToPlayer && directionToPlayer - this.direction < (Math.PI / 2)) {
                this.direction = directionToPlayer;
            }
            else this.direction -= rotationSpeed;
        }
    }

    protected float getFutureDistanceFromPlayer(float futureX, float futureY) {
        float distance;

        // Define point variables for easier function designing
        float P1x = futureX + width / 2;
        float P1y = futureY + height / 2;
        float P2x = 0;
        float P2y = 0;

        if (EntityManager.get().getPlayer() != null) {
            P2x = EntityManager.get().getPlayer().getXpos() + EntityManager.get().getPlayer().getWidth() / 2;
            P2y = EntityManager.get().getPlayer().getYpos() + EntityManager.get().getPlayer().getHeight() / 2;
        } else {
            return 1000;
        }

        // Determine right-angled-triangle's opposite and adjacent lengths
        float triangleX = Math.abs(P2x - P1x);
        float triangleY = Math.abs(P2y - P1y);

        // Calculate Hypotenuse (Distance from Player)
        distance = (float) Math.sqrt((triangleX * triangleX) + (triangleY * triangleY));

        // Set Distance
        return distance;
    }

    protected boolean checkMovingIsWorth() {
        if(getFutureDistanceFromPlayer(xpos + xmove, ypos + ymove) < distanceFromPlayer) return true;
        return false;
    }

    protected void calcPlayerDistanceAndDirection() {
        float distance;
        double newDir = 0;

        // Define point variables for easier function designing
        float P1x = this.getXpos() + width / 2;
        float P1y = this.getYpos() + height / 2;
        float P2x = 0;
        float P2y = 0;

        if (EntityManager.get().getPlayer() != null) {
            P2x = EntityManager.get().getPlayer().getXpos() + EntityManager.get().getPlayer().getWidth() / 2;
            P2y = EntityManager.get().getPlayer().getYpos() + EntityManager.get().getPlayer().getHeight() / 2;
        } else {
            this.distanceFromPlayer = 1000;
            return;
        }

        // Determine right-angled-triangle's opposite and adjacent lengths
        float triangleX = Math.abs(P2x - P1x);
        float triangleY = Math.abs(P2y - P1y);

        // Calculate Hypotenuse (Distance from Player)
        distance = (float) Math.sqrt((triangleX * triangleX) + (triangleY * triangleY));

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
            newDir = (2 * Math.PI) - theta;
        }
        else if(P1x < P2x && P1y > P2y) { // Quadrant 3
            newDir = theta;
        }
        else if(P1x > P2x && P1y > P2y) { // Quadrant 4
            newDir = Math.PI - theta;
        }

        // Set Direction
        directionToPlayer = newDir;
    }
}
