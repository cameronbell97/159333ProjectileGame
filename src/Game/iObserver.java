package Game;

import Entities.DynamicEntity;

/**
 * Cameron Bell - 02/04/2018
 * iObserver Interface Class
 * Basic observer pattern
 */

public interface iObserver {
    public void subscribe(DynamicEntity e);
    public void unsubscribe(DynamicEntity e);
}
