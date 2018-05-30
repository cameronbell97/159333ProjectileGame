package Game.Data;

/**
 * Cameron Bell - 13/04/2018
 * Scoreboard Class
 * Class Object to Keep Score Data
 */
public class ScoreBoard implements iXMLSerializable{
// VARIABLES //
    // Statics //
    public static final String DEF_SCORES_FORMAT = "0 0 1 0 2 0 3 0 4 0 5 0 6 0 7 0 8 0 9 0 ";
    public static final String DEF_SCORE_NAMES_FORMAT = "--- --- --- --- --- --- --- --- --- --- ";
    public static final String DEF_SCORE_NAME_FORMAT = "---";
    public static final int DEF_SCORES_NUM = 10;

    // Data //
    private int scores[];
    private String scoreNames[];

// CONSTRUCTORS //
    public ScoreBoard() {
        scores = new int[DEF_SCORES_NUM];
        scoreNames = new String[DEF_SCORES_NUM];
    }

// METHODS //
    // Method - Load Passed Data //
    public boolean load(int[] scores, String[] scoreNames) {
        if(this.scores.length == scores.length) this.scores = scores;
        else return false;

        if(this.scoreNames.length == scoreNames.length) this.scoreNames = scoreNames;
        else return false;

        return true;
    }

    // Method - Build Scores into String for File Saving //
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

    // Method - Turn an Integer into a String of Number Characters //
    private String parseString(int n) {
        try{
            return Integer.toString(n);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "0";
        }
    }

    // Method - Check the Score is Higher Than the Lowest Recorded High Score //
    public boolean isHighScore(int score) {
        if(score > scores[DEF_SCORES_NUM-1]) return true;
        return false;
    }

    // Method - Add Score into Correct Place //
    public void addNewScore(int score, String name) {
        // Remove Whitespace From String & Check if it's blank (if it is and it is left alone, it will mess with the save
        name = name.replaceAll("\\s", "");
        if(name.length() == 0) name = DEF_SCORE_NAME_FORMAT;

        // Only Add Score if it is Higher Than the Lowest Recorded Score
        if(isHighScore(score)) {
            // Variables //
            int currentIndex = 0;

            int scoreHolder1 = 0;
            int scoreHolder2 = 0;

            String scoreNameHolder1 = DEF_SCORE_NAME_FORMAT;
            String scoreNameHolder2 = DEF_SCORE_NAME_FORMAT;

            // Iterate through scores higher than new score //
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
            // Iterate through scores lower than new score, moving them each down one //
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

    // Method - Clear High Scores //
    public void clearScores() {
        scores = new int[DEF_SCORES_NUM];
        scoreNames = new String[DEF_SCORES_NUM];
        // Write over all score names with '---'
        for(int i = 0; i < scoreNames.length; i++) {
            scoreNames[i] = "---";
        }
    }

    @Override
    public String fromXML() {
        return null;
    }

    @Override
    public String toXML() {
        String xml = "";
        String scoresxml = "";

        for(int i = 0; i < scoreNames.length; i++) {
            scoresxml += XMLSerializer.makeElement(
                    "score",
                    XMLSerializer.makeElement(
                            "name",
                            scoreNames[i]
                    ) + XMLSerializer.makeElement(
                            "value",
                            Integer.toString(scores[i])
                    )
            );
        }

        xml = XMLSerializer.makeElement("scoreboard", scoresxml);

        return xml;
    }

// GETTERS & SETTERS //
    public int[] getScores() {
        return scores;
    }
    public String[] getScoreNames() {
        return scoreNames;
    }
    public int getBiggestScore() {
        return scores[0];
    }
//    // Getter Method - Get the Largest Score // OLD //
//    public int getBiggestScore() {
//        int biggest = 0;
//
//        for(int i = 0; i < scores.length; i++) {
//            if(biggest < scores[i]) biggest = scores[i];
//        }
//
//        return biggest;
//    }
}
