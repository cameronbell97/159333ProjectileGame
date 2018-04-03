package Entities;

import Game.iObserver;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Cameron Bell - 30/03/2018
 * Entity Manager Class
 * A class object that will contain all the entities that need to be regularly updated/drawn
 */

public class EntityManager implements iObserver {
// SINGLETON PATTERN //
    private static EntityManager self = new EntityManager();
    public static EntityManager get() { return self; }

// VARIABLES //
    List<Entity> ents; // A list of Entities
    List<Entity> sub_queue; // A list of Entities
    List<Entity> unsub_queue; // A list of Entities

// CONSTRUCTORS //
    public EntityManager() {
        ents = new ArrayList<Entity>();
        sub_queue = new ArrayList<Entity>();
        unsub_queue = new ArrayList<Entity>();
    }

// METHODS //
    // Method that subscribes an entity to the entity manager
    public void subscribe(Entity e) {
        sub_queue.add(e);
    }

    @Override
    public void unsubscribe(Entity e) {
        unsub_queue.add(e);
    }

    // Method that calls update() on every entity
    public void update() {
        // Update Entities
        for(Entity e : ents) {
            e.update();
        }

        // Add Queued Entities
        for(Entity e : sub_queue) {
            ents.add(e);
        }
        sub_queue.clear(); // Clear the sub_queue

        // Add Queued Entities
        for(Entity e : unsub_queue) {
            ents.remove(e);
        }
        unsub_queue.clear(); // Clear the sub_queue
    }

    // Method that calls draw() on every entity
    public void draw(Graphics g) {
        for(Entity e : ents) {
            e.draw(g);
        }
    }

}
