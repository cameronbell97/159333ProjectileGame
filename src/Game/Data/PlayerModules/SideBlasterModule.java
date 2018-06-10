package Game.Data.PlayerModules;

public class SideBlasterModule extends BlasterModule {
// VARIABLES //
    private boolean side; // true = left, false = right

// CONSTRUCTORS //
    public SideBlasterModule() {
        super(false);
    }

// METHODS //
    @Override
    protected void shoot() {
//        if(side) // Shoot from left
//        else // Shoot from right

        side = !side; // flip the side
    }
}
