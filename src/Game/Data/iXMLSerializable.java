package Game.Data;

/**
 * Cameron Bell - 30/05/2018
 * iXMLSerializable Interface
 * Interface for Objects that need to save to XML
 */

public interface iXMLSerializable {
    public boolean fromXML(String path);
    public String toXML();
}
