package Game;

import Game.Entities.Entity;

/**
 * Cameron Bell - 02/04/2018
 * iObserver Interface Class
 * Basic observer pattern
 */

public interface iObserver {
    public void subscribe(Entity e);
    public void unsubscribe(Entity e);
}
