package Game.Data;

import java.io.*;

public class Save {
// VARIABLES //
    private static final String DEF_SAVE_PATH = "data/save.txt";

    private ScoreBoard scoreBoard;

    private Settings settings;

// CONSTRUCTORS //
    public Save() {
        scoreBoard = new ScoreBoard();
        settings = new Settings();
        //save();
    }

// METHODS //
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
        // TODO // Implement Saved Settings
        settings = new Settings();
    }

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

        if(scoreBoard.load(scores, scoreNames))
            return true;
        else return false;
    }

    // Method to turn a file to a string
    private String filepathToString(String path) {
        StringBuilder builder = new StringBuilder();

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

        return builder.toString();
    }

    // Method to turn a string of number characters into an integer
    private int parseInt(String n) {
        try{
            return Integer.parseInt(n);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // Create save file if it doesn't already exist
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

    public void clearScores() {
        scoreBoard.clearScores();
        save();
    }

// GETTERS & SETTERS //
    public ScoreBoard getScoreBoard() {
        return scoreBoard;
    }
}
