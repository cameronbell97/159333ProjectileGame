package Game.Data.PlayerModules;

import Game.Entities.Dynamic.PlayerEntity;

public abstract class WeaponModule extends PlayerModule {
// VARIABLES //
    protected PlayerEntity parent;

// CONSTRUCTORS //
    public WeaponModule(String moduleName, PlayerEntity parent) {
        super(moduleName);
        this.parent = parent;
    }

// METHODS //
    public abstract void tryShoot();
    protected abstract void shoot();
    public abstract void update(int dt);
}
