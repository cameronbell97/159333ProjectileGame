package Game.Data;

import java.io.*;

/**
 * Cameron Bell - 30/03/2018
 * Save Class
 * Class Object to Load, Save, & Store Save Data
 */
public class Save {
// VARIABLES //
    private static final String DEF_XML_SAVE_PATH = "data/save.xml";

    // Data //
    private ScoreBoard scoreBoard;

// CONSTRUCTORS //
    public Save() {
        scoreBoard = new ScoreBoard();
    }

// METHODS //
    // Method - Save Data to File // TODO // Implement Saved Settings
    public void saveXML() {
        File file = new File(DEF_XML_SAVE_PATH);
        file.getParentFile().mkdirs();
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(file));
            br.write(scoreBoard.toXML());
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        scoreBoard.fromXML(DEF_XML_SAVE_PATH); // TODO // REMOVE
    }

    // Method - Load Save Data from File // TODO // Implement Saved Settings
    public boolean loadXML() {
        if(!(new File(DEF_XML_SAVE_PATH).isFile())) saveXML();

        return scoreBoard.fromXML(DEF_XML_SAVE_PATH);
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
