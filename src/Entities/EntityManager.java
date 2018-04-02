package Entities;

import Entities.Dynamic.DynamicEntity;
import Game.iObserver;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Cameron Bell - 30/03/2018
 * DynamicEntity Manager Class
 * A class object that will contain all the entities that need to be regularly updated/drawn
 */

public class EntityManager implements iObserver {
// VARIABLES //
    List<DynamicEntity> ents; // A list of Entities


// CONSTRUCTORS //
    public EntityManager() {
        ents = new ArrayList<DynamicEntity>();
    }

// METHODS //
    // Method that subscribes an entity to the entity manager
    public void subscribe(DynamicEntity e) {
        ents.add(e);
    }

    @Override
    public void unsubscribe(DynamicEntity e) {
        ents.remove(e);
    }

    // Method that calls update() on every entity
    public void update() {
        for(DynamicEntity e : ents) {
            e.update();
        }
    }

    // Method that calls draw() on every entity
    public void draw(Graphics g) {
        for(DynamicEntity e : ents) {
            e.draw(g);
        }
    }

}
