package Game.Entities.Collision;

import Game.Entities.Dynamic.PlayerEntity;

// Code for Future Use //

public class PlayerCollisionBoxBody extends CollisionBox {
    public PlayerCollisionBoxBody(float xpos, float ypos, int width, int height, int xoff, int yoff, PlayerEntity playerEntity) {
        super(xpos, ypos, width, height, xoff, yoff, playerEntity);
    }

    @Override
    public void update(int dt) {
        setXpos(parent.getXpos() + (parent.getWidth() / 2));
        setYpos(parent.getYpos() + (parent.getHeight() / 2));
        direction = -((PlayerEntity)parent).getRealDirection();
        strafeRight(15);
        setSpeed(-6);
        setMoveSpeeds();
        move(dt);
        setSpeed(0);
        setMoveSpeeds();
    }
}
