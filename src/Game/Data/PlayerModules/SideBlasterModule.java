package Game.Data.PlayerModules;

import Game.Entities.Dynamic.Bullets.PlayerSideBullet;

public class SideBlasterModule extends BlasterModule {
// VARIABLES //
    public static final int SIDE_BLASTER_RELOAD_SPEED = 10; // 60 = 1 second
    private boolean side; // true = left, false = right

// CONSTRUCTORS //
    public SideBlasterModule() {
        super(false);
        reload_speed = SIDE_BLASTER_RELOAD_SPEED;
    }

// METHODS //
    @Override
    protected void shoot() {
        if(side) // Shoot from left
            handler.getEntityManager().subscribe(new PlayerSideBullet(parent) {
                @Override
                protected void setPosition() {
                    // Move bullet to left wing of Player ship
                    direction += Math.PI / 2;
                    moveSpeed = 13;
                    setMoveSpeeds();
                    move(1);

                    // Reset move speed
                    direction -= Math.PI / 2;
                    moveSpeed = PlayerSideBullet.DEF_MOVESPEED;
                    setMoveSpeeds();
                }
            });
        else // Shoot from right
            handler.getEntityManager().subscribe(new PlayerSideBullet(parent) {
                @Override
                protected void setPosition() {
                    // Move bullet to left wing of Player ship
                    direction -= Math.PI / 2;
                    moveSpeed = 13;
                    setMoveSpeeds();
                    move(1);

                    // Reset move speed
                    direction += Math.PI / 2;
                    moveSpeed = PlayerSideBullet.DEF_MOVESPEED;
                    setMoveSpeeds();
                }
            });

        side = !side; // flip the side
    }
}
