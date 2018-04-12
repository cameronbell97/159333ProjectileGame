package Entities;

import Entities.Collision.CollisionBox;
import Entities.Dynamic.Particles.Particle;
import Entities.Dynamic.PlayerEntity;
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
    PlayerEntity player;
    List<Entity> ents; // A list of Entities
    List<Entity> sub_queue; // A list of Entities
    List<Entity> unsub_queue; // A list of Entities
    List<CollisionBox> cols; // A list of CollisionBoxes
    List<CollisionBox> sub_cueue; // A list of CollisionBoxes
    List<CollisionBox> unsub_cueue; // A list of CollisionBoxes
    List<Particle> particles; // A list of CollisionBoxes
    List<Particle> p_sub_queue; // A list of CollisionBoxes
    List<Particle> p_unsub_queue; // A list of CollisionBoxes

// CONSTRUCTORS //
    private EntityManager() {
        player = null;

        ents = new ArrayList<Entity>();
        sub_queue = new ArrayList<Entity>();
        unsub_queue = new ArrayList<Entity>();

        cols = new ArrayList<CollisionBox>();
        sub_cueue = new ArrayList<CollisionBox>();
        unsub_cueue = new ArrayList<CollisionBox>();

        particles = new ArrayList<Particle>();
        p_sub_queue = new ArrayList<Particle>();
        p_unsub_queue = new ArrayList<Particle>();
    }

// METHODS //
    // Methods that subscribe Entities to this Entity Manager

    public void subscribe(Particle p) {
        p_sub_queue.add(p);
    }
    public void subscribe(Entity e) {
        sub_queue.add(e);
    }
    public void subscribe(CollisionBox e) {
        sub_cueue.add(e);
    }
    public void subPlayer(PlayerEntity p) {
        player = p;
        subscribe(p);
    }

    // Methods that unsubscribe Entities from this Entity Manager

    public void unsubscribe(Particle p) {
        p_unsub_queue.add(p);
    }
    @Override
    public void unsubscribe(Entity e) {
        unsub_queue.add(e);
    }
    public void unsubscribe(CollisionBox e) {
        unsub_cueue.add(e);
    }
    public void unsubPlayer(PlayerEntity p) {
        player = null;
        unsubscribe(p);
    }

    // Method that calls update() on every entity
    public void update() {
        // ENTITIES //
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

        // COLLISION BOXES //
        // Check for collisions
        checkCollisions();
        // Add Queued Collision Boxes
        for(CollisionBox e : sub_cueue) {
            cols.add(e);
        }
        sub_cueue.clear(); // Clear the sub_cueue
        // Remove Collision Boxes
        for(CollisionBox e : unsub_cueue) {
            cols.remove(e);
        }
        unsub_cueue.clear(); // Clear the unsub_cueue

        // PARTICLES //
        // Update Entities
        for(Particle p : particles) {
            p.update();
        }
        // Add Queued Entities
        for(Particle p : p_sub_queue) {
            particles.add(p);
        }
        p_sub_queue.clear(); // Clear the sub_queue
        // Remove Queued Entities
        for(Particle p : p_unsub_queue) {
            particles.remove(p);
        }
        p_unsub_queue.clear(); // Clear the unsub_queue
    }

    // Method that calls draw() on every entity
    public void draw(Graphics g) {
        for(Entity e : ents) {
            e.draw(g);
        }
        for(Particle p : particles) {
            p.draw(g);
        }
        // Draw Player on top of everything else
        if(player != null) player.draw(g);
    }

    private void checkCollisions() {
        for(CollisionBox e : cols) {
            for(CollisionBox f : cols) {
                if(
                    (
                        e != f || // The entities are not the same entity
                        ( // One of the entities's parent object is not the bullet of the other's (aka ignore player bullets colliding with player)
                          // And also check they're not both from the same entity (two bullets colliding both going in the same direction from the same entity)
                                f.getParent() != null && // The first entity has a parent and
                                e.getParent() != null && // The second entity has a parent and
                                f.getParent().getParent() != null && // The first entity's parent has a parent and
                                e.getParent().getParent() != null && // The second entity's parent has a parent and
                                f.getParent().getParent() != e.getParent() && // The first entity's parent's parent is not the second entity's parent
                                e.getParent().getParent() != f.getParent() &&// The second entity's parent's parent is not the first entity's parent
                                f.getParent().getParent() != e.getParent().getParent() // The first and second entities' parents' parents are not the same
                        )
                    ) &&
                        SAT.isColliding(e, f) // The entities are colliding
                    ) {
//                    System.out.println("C O L L I S I O N");
                    e.getParent().collide(f.getParent()); // Call the collide function on e
                    //f.getParent().collide(e.getParent()); // (CURRENTLY) no need to call collide() on f because f and e will switch places in a later check
                }
            }
        }
    }

    public void drawCollisionBoxes(Graphics g) {
        for(CollisionBox c : cols) {
            c.draw(g);
        }
    }

// GETTERS & SETTERS //
    public PlayerEntity getPlayer() {
        return player;
    }
}
