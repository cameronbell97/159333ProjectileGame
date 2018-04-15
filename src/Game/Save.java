package Game;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Save {
// VARIABLES //
    private static final String DEF_SAVE_PATH = "data/save.txt";
    private static final String DEF_SCORES_FORMAT = "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 ";

    private ScoreBoard scoreBoard;

    private Settings settings;

// CONSTRUCTORS //
    public Save() {
        scoreBoard = new ScoreBoard();
        settings = new Settings();
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

        int[] scores = new int[scoreBoard.DEF_SCORES_NUM];
        for(int elem = 0; elem < scoreBoard.DEF_SCORES_NUM * 2; elem += 2) {
            if(elements[elem] == null) break; // To avoid an out of bounds exception
            scores[parseInt(elements[elem])] = parseInt(elements[elem+1]);
        }
        scoreBoard.load(scores);

        return true;
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
            br.write(DEF_SCORES_FORMAT);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
