package Game.Data;

import java.io.*;

/**
 * Cameron Bell - 30/03/2018
 * Save Class
 * Class Object to Load, Save, & Store Save Data
 */
public class Save {
// VARIABLES //
    private static final String DEF_SAVE_PATH = "data/save.txt";
    private static final String DEF_XML_SAVE_PATH = "data/save.xml";

    // Data //
    private ScoreBoard scoreBoard;

// CONSTRUCTORS //
    public Save() {
        scoreBoard = new ScoreBoard();
    }

// METHODS //
    // Method - Save Data to File // TODO // Implement Saved Settings
    public void save() {
        File file = new File(DEF_SAVE_PATH);
        if(!file.isFile()) create();

        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(file));
            br.write(scoreBoard.saveScoresAsString());
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveXML() {
        File file = new File(DEF_XML_SAVE_PATH);
        //if(!file.isFile()) create();
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(file));
            br.write(scoreBoard.toXML());
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        scoreBoard.fromXML(DEF_XML_SAVE_PATH); // TODO // REMOVE
    }

    public boolean loadXML() {
        if(!(new File(DEF_XML_SAVE_PATH).isFile())) saveXML();

        return scoreBoard.fromXML(DEF_XML_SAVE_PATH);
    }

    // Method - Load Data from File // TODO // Implement Saved Settings
    public boolean load() {
        if(!(new File(DEF_SAVE_PATH).isFile())) return false;

        // Load Scores
        String file = filepathToString(DEF_SAVE_PATH);
        String[] elements = file.split("\\s+"); // Regex expression '\s' means whitespace and '+' means 1 or more

        // Create Data Structures
        int[] scores = new int[ScoreBoard.DEF_SCORES_NUM];
        String[] scoreNames = new String[ScoreBoard.DEF_SCORES_NUM];

        // Load Scores
        int elementCount = 0;
        int countStart = elementCount;

        for(; elementCount < ScoreBoard.DEF_SCORES_NUM * 2; elementCount += 2) {
            if(elements[elementCount] == null) {
                scores[parseInt(elements[elementCount])] = 0;
            } else {
                scores[parseInt(elements[elementCount])] = parseInt(elements[elementCount + 1]);
            }
        }

        countStart = elementCount;
        for(; elementCount < ScoreBoard.DEF_SCORES_NUM + countStart; elementCount++) {
            if(elements.length < elementCount+1) {
                scoreNames[elementCount - countStart] = "---";
            } else {
                scoreNames[elementCount - countStart] = elements[elementCount];
            }
        }

        // Attempt to load into scoreboard
        if(scoreBoard.load(scores, scoreNames))
            return true;
        else return false;
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

    // Method - Create Save File if it Doesn't Already Exist //
    public void create() {
        File file = new File(DEF_SAVE_PATH);
        try {
            file.getParentFile().mkdirs();
            file.createNewFile();
            BufferedWriter br = new BufferedWriter(new FileWriter(file));
            br.write(ScoreBoard.DEF_SCORES_FORMAT + ScoreBoard.DEF_SCORE_NAMES_FORMAT);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
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
