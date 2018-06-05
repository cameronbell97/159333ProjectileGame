package Game.Data;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * Cameron Bell - 13/04/2018
 * Scoreboard Class
 * Class Object to Keep Score Data
 */
public class ScoreBoard implements iXMLSerializable {
// VARIABLES //
    // Statics //
    public static final String DEF_SCORE_NAME_FORMAT = "---";
    public static final int DEF_SCORES_NUM = 10;
    private static final int DEVELOPER_HIGH_SCORE = 1361;

    // Data //
    private int scores[];
    private String scoreNames[];

// CONSTRUCTORS //
    public ScoreBoard() {
        scores = new int[DEF_SCORES_NUM];
        scoreNames = new String[DEF_SCORES_NUM];
    }

// METHODS //
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
            scoreNames[i] = DEF_SCORE_NAME_FORMAT;
        }
    }

    // Method - Load ScoreBoard Data from XML File into Class //
    @Override
    public boolean fromXML(String path) {
        // Load Data From File
        File file = new File(path);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder docb = null;
        Document doc = null;

        try {
            docb = dbf.newDocumentBuilder();
            doc = docb.parse(file);
        } catch (SAXException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return false;
        }
        doc.getDocumentElement().normalize();

        // Load Data Into ScoreBoard
        NodeList scoreList = doc.getElementsByTagName("score");

        String[] sNames = new String[scoreList.getLength()];
        String[] sValues = new String[scoreList.getLength()];

        for(int i = 0; i < scoreList.getLength(); i++) {
            Node score = scoreList.item(i);
            Element scoreElem = (Element)score;

            sNames[i] = scoreElem
                    .getElementsByTagName("name")
                    .item(0)
                    .getFirstChild()
                    .getNodeValue();

            sValues[i] = scoreElem
                    .getElementsByTagName("value")
                    .item(0)
                    .getFirstChild()
                    .getNodeValue();
        }

        if(scores.length != sValues.length || scoreNames.length != sNames.length)
            return false;

        for(int i = 0; i < scores.length; i++) {
            scoreNames[i] = sNames[i];
            scores[i] = Save.parseInt(sValues[i]);
        }

        return true;
    }

    // Method - Write ScoreBoard Data as XML & Return as String //
    @Override
    public String toXML() {
        String xml = "";
        String scoresxml = "";

        for(int i = 0; i < scoreNames.length; i++) {
            if(scoreNames[i] == null || scoreNames[i].equals(""))
                scoreNames[i] = DEF_SCORE_NAME_FORMAT;
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
