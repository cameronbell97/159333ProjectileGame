package Game;

/**
 * Created by Cameron on 13/04/2018.
 */
public class ScoreBoard {
// VARIABLES //
    public static final int DEF_SCORES_NUM = 10;
    private int scores[];

// CONSTRUCTORS //
    public ScoreBoard() {
        scores = new int[DEF_SCORES_NUM];

    }

// METHODS //
    public void load(int[] scores) {
        if(this.scores.length == scores.length) this.scores = scores;
    }

    public String saveScoresAsString() {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < DEF_SCORES_NUM; i++) {
            builder.append(parseString(i) + " " + parseString(scores[i]) + " ");
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
        if(isHighScore(score)) {
            int currentIndex = 0;
            int scoreHolder1 = 0;
            int scoreHolder2 = 0;

            for(int i = currentIndex; i < DEF_SCORES_NUM; i++) {
                if(score > scores[i]) {
                    scoreHolder1 = scores[i];
                    scores[i] = score;
                    currentIndex++;
                    break;
                }

                currentIndex++;
            }
            for(int i = currentIndex; i < DEF_SCORES_NUM; i++) {
                scoreHolder2 = scores[i];
                scores[i] = scoreHolder1;
                scoreHolder1 = scoreHolder2;

                currentIndex++;
            }
        }
    }

// GETTERS & SETTERS //
    public int[] getScores() {
        return scores;
    }
}
