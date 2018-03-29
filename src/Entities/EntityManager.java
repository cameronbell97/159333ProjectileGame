package Entities;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Cameron Bell - 30/03/2018
 * Entity Manager Class
 * A class object that will contain all the entities that need to be regularly updated/drawn
 */

public class EntityManager {
// VARIABLES //
    List<Entity> ents; // A list of Entities


// CONSTRUCTORS //
    public EntityManager() {
        ents = new ArrayList<Entity>();
    }

// METHODS //
    // Method that subscribes an entity to the entity manager
    public void subscribe(Entity e) {
        ents.add(e);
    }

    // Method that calls update() on every entity
    public void update() {
        for(Entity e : ents) {
            e.update();
        }
    }

    // Method that calls draw() on every entity
    public void draw(Graphics g) {
        for(Entity e : ents) {
            e.draw(g);
        }
    }

}
