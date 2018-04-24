package Game.Data;

/**
 * Created by Cameron on 13/04/2018.
 */
public class ScoreBoard {
// VARIABLES //
    public static final String DEF_SCORES_FORMAT = "0 0 1 0 2 0 3 0 4 0 5 0 6 0 7 0 8 0 9 0 ";
    public static final String DEF_SCORE_NAMES_FORMAT = "--- --- --- --- --- --- --- --- --- --- ";
    public static final String DEF_SCORE_NAME_FORMAT = "---";

    public static final int DEF_SCORES_NUM = 10;
    private int scores[];
    private String scoreNames[];

// CONSTRUCTORS //
    public ScoreBoard() {
        scores = new int[DEF_SCORES_NUM];
        scoreNames = new String[DEF_SCORES_NUM];
    }

// METHODS //
    public boolean load(int[] scores, String[] scoreNames) {
        if(this.scores.length == scores.length) this.scores = scores;
        else return false;

        if(this.scoreNames.length == scoreNames.length) this.scoreNames = scoreNames;
        else return false;

        return true;
    }

    public String saveScoresAsString() {
        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < DEF_SCORES_NUM; i++) {
            builder.append(parseString(i) + " " + parseString(scores[i]) + " ");
        }

        for(int i = 0; i < DEF_SCORES_NUM; i++) {
            if(scoreNames[i] != null) builder.append(scoreNames[i] + " ");
            else builder.append(DEF_SCORE_NAME_FORMAT + " ");
        }

        return builder.toString();
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

    public boolean isHighScore(int score) {
        if(score > scores[DEF_SCORES_NUM-1]) return true;
        return false;
    }

    public void addNewScore(int score, String name) {
        // Remove Whitespace From String & Check if it's blank (if it is and it is left alone, it will mess with the save
        name = name.replaceAll("\\s", "");
        if(name.length() == 0) name = DEF_SCORE_NAME_FORMAT;

        if(isHighScore(score)) {
            int currentIndex = 0;

            int scoreHolder1 = 0;
            int scoreHolder2 = 0;

            String scoreNameHolder1 = DEF_SCORE_NAME_FORMAT;
            String scoreNameHolder2 = DEF_SCORE_NAME_FORMAT;

            for(int i = currentIndex; i < DEF_SCORES_NUM; i++) {
                if(score > scores[i]) {
                    scoreHolder1 = scores[i];
                    scores[i] = score;

                    scoreNameHolder1 = scoreNames[i];
                    scoreNames[i] = name;

                    currentIndex++;
                    break;
                }

                currentIndex++;
            }
            for(int i = currentIndex; i < DEF_SCORES_NUM; i++) {
                scoreHolder2 = scores[i];
                scores[i] = scoreHolder1;
                scoreHolder1 = scoreHolder2;

                scoreNameHolder2 = scoreNames[i];
                scoreNames[i] = scoreNameHolder1;
                scoreNameHolder1 = scoreNameHolder2;

                currentIndex++;
            }
        }
    }

// GETTERS & SETTERS //
    public int[] getScores() {
        return scores;
    }
    public String[] getScoreNames() {
        return scoreNames;
    }
    public int getBiggestScore() {
        int biggest = 0;

        for(int i = 0; i < scores.length; i++) {
            if(biggest < scores[i]) biggest = scores[i];
        }

        return biggest;
    }
}
