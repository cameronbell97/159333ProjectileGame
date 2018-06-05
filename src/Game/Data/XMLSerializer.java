package Game.Data;

/**
 * Cameron Bell - 30/05/2018
 * XMLSerializer Abstract Class
 * Class holding various XML Serializing Methods
 */

public abstract class XMLSerializer {
// METHODS //
    public static String makeElement(String eName, String eData) {
        return "<" + eName + ">" + eData + "</" + eName + ">";
    }
}
