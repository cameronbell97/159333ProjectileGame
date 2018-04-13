package Game;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Save {
// VARIABLES //
    private static final String DEF_SAVE_PATH = "data/save.txt";
    private static final int DEF_SCORES_NUM = 10;
    private static final String DEF_SCORES_FORMAT = "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 ";
    private int scores[];

// CONSTRUCTORS //
    public Save() {
        scores = new int[DEF_SCORES_NUM];

    }

// METHODS //
    public void save() {
        File file = new File(DEF_SAVE_PATH);
        if(!file.isFile()) create();

        scores[0] = 1000;
        scores[0] = 900;

        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(file));
            br.write(saveScoresAsString());
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean load() {
        if(!(new File(DEF_SAVE_PATH).isFile())) return false;
        String file = filepathToString(DEF_SAVE_PATH);
        String[] elements = file.split("\\s+"); // Regex expression '\s' means whitespace and '+' means 1 or more
        for(int elem = 0; elem < DEF_SCORES_NUM*2; elem+=2) {
            if(elements[elem] == null) break; // To avoid an out of bounds exception
            scores[parseInt(elements[elem])] = parseInt(elements[elem+1]);
        }

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

    // Method to turn an integer into a string of number characters
    private String parseString(int n) {
        try{
            return Integer.toString(n);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "0";
        }
    }

    private String saveScoresAsString() {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < DEF_SCORES_NUM; i++) {
            builder.append(parseString(i) + " " + parseString(scores[i]) + " ");
        }
        return builder.toString();
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
