package Game.Entities.Collision;

import Game.Entities.Dynamic.PlayerEntity;

public class PlayerCollisionBoxHead extends CollisionBox {
    public PlayerCollisionBoxHead(float xpos, float ypos, int width, int height, int xoff, int yoff, PlayerEntity playerEntity) {
        super(xpos, ypos, width, height, xoff, yoff, playerEntity);
    }

    @Override
    public void update() {
        xpos = parent.getXpos();
        xpos = parent.getYpos();
        direction = ((PlayerEntity)parent).getDirection();
        strafeLeft(5);
        setSpeed(25);
        setMoveSpeeds();
        move();
        setSpeed(0);
        setMoveSpeeds();
        super.update();
    }
}
