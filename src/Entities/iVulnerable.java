package Entities;

/**
 * Cameron Bell - 02/04/2018
 * iVulnerable Entity Class
 * To be used for entities that are not invulnerable (i.e player & enemies)
 */

public interface iVulnerable {
// GETTERS & SETTERS //
    public int getHP();
    public void setHP(int hp);
    public void addHP(int hp);
    public void die();
}
