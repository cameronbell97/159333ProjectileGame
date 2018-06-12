package Game.Entities;

import Game.Data.PlayerModules.MainBlasterModule;
import Game.Data.PlayerModules.SideBlasterModule;
import Game.Data.Settings;
import Game.Entities.Collision.CollisionBox;
import Game.Entities.Dynamic.Particles.Particle;
import Game.Entities.Dynamic.PlayerEntity;
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
// VARIABLES //
    boolean alive = true;
    PlayerEntity player;

    // Lists //
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
    public EntityManager() {
        player = new PlayerEntity(
                Settings.game_width/2 - PlayerEntity.DEF_PLAYER_WIDTH/2,
                Settings.game_height/2 - PlayerEntity.DEF_PLAYER_HEIGHT/2,
//                new MainBlasterModule(Settings.player_gun_lock)
                new SideBlasterModule()
        );

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
    // Methods - That subscribe Entities to this Entity Manager
    public void subscribe(Particle p) {
        p_sub_queue.add(p);
    }
    public void subscribe(Entity e) {
        sub_queue.add(e);
    }
    public void subscribe(CollisionBox e) {
        sub_cueue.add(e);
    }

    // Methods - That unsubscribe Entities from this Entity Manager
    @Override
    public void unsubscribe(Entity e) {
        unsub_queue.add(e);
    }
    public void unsubscribe(Particle p) {
        p_unsub_queue.add(p);
    }
    public void unsubscribe(CollisionBox e) {
        unsub_cueue.add(e);
    }
    public void unsubPlayer(PlayerEntity p) {
        player = null;
        unsubscribe(p);
    }

    // Method - Update State //
    public void update(int dt) {
        if(player != null) player.update(dt);

    // ENTITIES //

        // Update Entities
        for(Entity e : ents) {
            e.update(dt);
        }

        // Add Queued Entities
        for(Entity e : sub_queue) {
            ents.add(e);
        }
        sub_queue.clear(); // Clear the sub_queue

        // Remove Queued Entities
        for(Entity e : unsub_queue) {
            if(!ents.remove(e)) {
//                System.out.println("ERROR: Could Not Remove: " + e.toString()); // DEBUG //
            }
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

        // Remove Unsubbed Collision Boxes
        for(CollisionBox e : unsub_cueue) {
            if(e == null) continue;
            if(!cols.remove(e)) {
            // DEBUG CODE //
//                try {
//                    System.out.println("ERROR: Could Not Remove: " + e.toString());
//                } catch (NullPointerException ex) {
//                    ex.printStackTrace();
//                }
            }
        }
        unsub_cueue.clear(); // Clear the unsub_cueue

    // PARTICLES //

        // Limit Particles to Maximum Allowed Particles
        while(particles.size() > Settings.max_particles) {
            Particle particleToRemove = particles.get(0);
            particles.remove(particleToRemove);
        }

        // Update Particles
        for(Particle p : particles) {
            p.update(dt);
        }

        // Add Queued Particles
        for(Particle p : p_sub_queue) {
            particles.add(p);
        }
        p_sub_queue.clear(); // Clear the sub_queue

        // Remove Queued Particles
        for(Particle p : p_unsub_queue) {
            particles.remove(p);
        }
        p_unsub_queue.clear(); // Clear the unsub_queue
    }

    // Method - Draw State of Entities & Particles //
    public void draw(Graphics g) {
        for(Entity e : ents) {
            if(!(e instanceof Game.Entities.Dynamic.PlayerEntity)) // Don't draw player yet because it will be drawn later
                e.draw(g);
        }
        for(Particle p : particles) {
            p.draw(g);
        }
        // Draw Player on top of everything else
        if(player != null) player.draw(g);
    }

    // Method - Check for collisions between collision boxes //
    private void checkCollisions() {
        int x1 = 0;
        int x2 = 0;
        for(CollisionBox e : cols) {
            for(CollisionBox f : cols) {
                if(
                    x2 > x1 && // Skip checking collisions twice
//                    (
//                        e != f || // The entities are not the same entity
//                        ( // One of the entities's parent object is not the bullet of the other's (aka ignore player bullets colliding with player)
//                          // And also check they're not both from the same entity (two bullets colliding both going in the same direction from the same entity)
//                                f.getParent() != null && // The first entity has a parent and
//                                e.getParent() != null && // The second entity has a parent and
//                                f.getParent().getParent() != null && // The first entity's parent has a parent and
//                                e.getParent().getParent() != null && // The second entity's parent has a parent and
//                                f.getParent().getParent() != e.getParent() && // The first entity's parent's parent is not the second entity's parent
//                                e.getParent().getParent() != f.getParent() &&// The second entity's parent's parent is not the first entity's parent
//                                f.getParent().getParent() != e.getParent().getParent() // The first and second entities' parents' parents are not the same
//                        )
//                    ) &&
                        SAT.isColliding(e, f) // The entities are colliding
                    ) { // Collide the Entities
                    Entity ep = e.getParent();
                    Entity fp = f.getParent();
                    ep.collide(fp);
                    fp.collide(ep);
                }
                x2++;
            }
            x1++;
            x2 = 0;
        }
    }

    // Method - Draws Collision Boxes //
    public void drawCollisionBoxes(Graphics g) {
        for(CollisionBox c : cols) {
            c.draw(g);
        }
    }

// GETTERS & SETTERS //
    public PlayerEntity getPlayer() {
        return player;
    }
    public boolean isAlive() {
        return alive;
    }
}
