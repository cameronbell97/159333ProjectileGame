package Game.Data;

import Game.Data.PlayerModules.PlayerModule;

import java.util.ArrayList;
import java.util.List;

public class PlayerData implements iXMLSerializable {
// VARIABLES //
    // Statics //
    private static final String DEF_PLAYER_NAME = "Player";

    // Data //
    private String playerName;
    private boolean tutorialCompleted;
    private ArrayList<PlayerModule> collectedModules;
    private int money;

// CONSTRUCTORS //
    public PlayerData() {
        playerName = DEF_PLAYER_NAME;
        initialise();
    }

    public PlayerData(String name) {
        playerName = name;
        initialise();
    }

// METHODS //
    // Method - Initialise data //
    private void initialise() {
        tutorialCompleted = false;
        collectedModules = new ArrayList<>();
    }

    // Method - Load ScoreBoard Data from XML File into Class //
    @Override
    public boolean fromXML(String path) {
        return false;
    }

    // Method - Write ScoreBoard Data as XML & Return as String //
    @Override
    public String toXML() {
        return null;
    }

    // Method - Gives the player a module //
    public boolean addModule(PlayerModule module) {
        return collectedModules.add(module);
    }

    // Method - Adds or substracts money from the player //
    public boolean addMoney(int m) {
        if(money + m >= 0) {
            money+=m;
            return true;
        }
        return false;
    }


// GETTERS & SETTERS //
    public String getPlayerName() {
        return playerName;
    }
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    public void setTutorialCompleted() {
        tutorialCompleted = true;
    }
    public boolean isTutorialCompleted() {
        return tutorialCompleted;
    }
    public ArrayList<PlayerModule> getCollectedModules() {
        return collectedModules;
    }
    public int getMoney() {
        return money;
    }
}
