package Game.Data;

import java.io.*;

/**
 * Cameron Bell - 30/03/2018
 * Save Class
 * Class Object to Load, Save, & Store Save Data
 */
public class Save {
// VARIABLES //
    private static final String XML_TAG = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    private static final String DEF_XML_SAVE_DATA_PATH = "data/";
    private static final String DEF_XML_SCOREBOARD_FILENAME = "scores.xml";
    private static final String DEF_XML_PLAYERDATA_FILENAME = "pdata.xml";

    // Data //
    private ScoreBoard scoreBoard;
    private PlayerData playerData;

// CONSTRUCTORS //
    public Save() {
        scoreBoard = new ScoreBoard();
        playerData = new PlayerData();
    }

// METHODS //
    // Method - Save Data to File // TODO // Implement Saved Settings
    public void saveXML() {
        // Save ScoreBoard //
        File file = new File(DEF_XML_SAVE_DATA_PATH + DEF_XML_SCOREBOARD_FILENAME);
        file.getParentFile().mkdirs();
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(file));
            br.write(XML_TAG + scoreBoard.toXML());
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        scoreBoard.fromXML(DEF_XML_SAVE_DATA_PATH + DEF_XML_SCOREBOARD_FILENAME); // Load Newly Saved Data // TODO // Remove?

        // Save PlayerData //
        file = new File(DEF_XML_SAVE_DATA_PATH + DEF_XML_PLAYERDATA_FILENAME);
        file.getParentFile().mkdirs();
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(file));
            br.write(XML_TAG + playerData.toXML());
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        playerData.fromXML(DEF_XML_SAVE_DATA_PATH + DEF_XML_PLAYERDATA_FILENAME); // Load Newly Saved Data // TODO // Remove?
    }

    // Method - Load Save Data from File // TODO // Implement Saved Settings
    public boolean loadXML() {
        if(
                !(new File(DEF_XML_SAVE_DATA_PATH + DEF_XML_SCOREBOARD_FILENAME).isFile()) ||
                !(new File(DEF_XML_SAVE_DATA_PATH + DEF_XML_PLAYERDATA_FILENAME).isFile())
        )
            saveXML();

        if(
                scoreBoard.fromXML(DEF_XML_SAVE_DATA_PATH + DEF_XML_SCOREBOARD_FILENAME) &&
                playerData.fromXML(DEF_XML_SAVE_DATA_PATH + DEF_XML_PLAYERDATA_FILENAME)
        ) return true;

        return false;
    }

    // Method - Turn Save File into a String //
    private String filepathToString(String path) {
        StringBuilder builder = new StringBuilder();

        // Load Text Data
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            while (((line = br.readLine())) != null) {
                builder.append(line + "\n");
            }
            br.close();
        } catch(IOException e){
            e.printStackTrace();
        }

        // Return as String
        return builder.toString();
    }

    // Method - Turn a String of Number Characters into an Integer //
    public static int parseInt(String n) {
        try{
            return Integer.parseInt(n);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // Method - Clear High Scores //
    public void clearScores() {
        scoreBoard.clearScores();
        saveXML();
    }

// GETTERS & SETTERS //
    public ScoreBoard getScoreBoard() {
        return scoreBoard;
    }
}
