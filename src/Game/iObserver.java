package Game;

import Entities.Entity;

public interface iObserver {
    public void subscribe(Entity e);
    public void unsubscribe(Entity e);
}
