package Game.Entities;

/**
 * Cameron Bell - 13/04/2018
 * iOutOfBounds
 */

public interface iOutOfBounds {
    boolean checkOOBX();
    boolean checkOOBY();
    void doWhenOutOfBounds(int dt, boolean shiftX);
}
