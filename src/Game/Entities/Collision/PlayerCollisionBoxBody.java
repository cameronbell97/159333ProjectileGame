package Game.Entities.Collision;

import Game.Entities.Dynamic.PlayerEntity;
import Game.Entities.Entity;

public class PlayerCollisionBoxBody extends CollisionBox {
    public PlayerCollisionBoxBody(float xpos, float ypos, int width, int height, int xoff, int yoff, PlayerEntity playerEntity) {
        super(xpos, ypos, width, height, xoff, yoff, playerEntity);
    }

    @Override
    public void update() {
        xpos = parent.getXpos();
        xpos = parent.getYpos();
        direction = ((PlayerEntity)parent).getDirection();
        strafeLeft(15);
        setSpeed(6);
        setMoveSpeeds();
        move();
        setSpeed(0);
        setMoveSpeeds();
        super.update();
    }
}
