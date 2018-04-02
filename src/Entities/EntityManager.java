package Entities;

import Entities.Collision.CollisionBox;
import Game.SAT;
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
    List<CollisionBox> cols; // A list of CollisionBoxes
    List<CollisionBox> sub_cueue; // A list of CollisionBoxes
    List<CollisionBox> unsub_cueue; // A list of CollisionBoxes

// CONSTRUCTORS //
    public EntityManager() {
        ents = new ArrayList<Entity>();
        sub_queue = new ArrayList<Entity>();
        unsub_queue = new ArrayList<Entity>();
        cols = new ArrayList<CollisionBox>();
        sub_cueue = new ArrayList<CollisionBox>();
        unsub_cueue = new ArrayList<CollisionBox>();
    }

// METHODS //
    // Method that subscribes an entity to the entity manager
    public void subscribe(Entity e) {
        sub_queue.add(e);
    }
    // Method that subscribes an collision box to the entity manager
    public void subscribe(CollisionBox e) {
        sub_cueue.add(e);
    }

    @Override
    public void unsubscribe(Entity e) {
        unsub_queue.add(e);
    }
    public void unsubscribe(CollisionBox e) {
        unsub_cueue.add(e);
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

        // Remove Queued Entities
        for(Entity e : unsub_queue) {
            ents.remove(e);
        }
        unsub_queue.clear(); // Clear the unsub_queue


        // Check for collisions
        checkCollisions();

        // Add Queued Entities
        for(CollisionBox e : sub_cueue) {
            cols.add(e);
        }
        sub_cueue.clear(); // Clear the sub_cueue

        // Remove Queued Entities
        for(CollisionBox e : unsub_cueue) {
            cols.remove(e);
        }
        unsub_cueue.clear(); // Clear the unsub_cueue
    }

    // Method that calls draw() on every entity
    public void draw(Graphics g) {
        for(Entity e : ents) {
            e.draw(g);
        }
    }

    public void checkCollisions() {
        for(CollisionBox e : cols) {
            for(CollisionBox f : cols) {
                if(e != f && SAT.isColliding(e, f)) {
                    System.out.println("C O L L I S I O N");
                }
            }
        }
    }
}
