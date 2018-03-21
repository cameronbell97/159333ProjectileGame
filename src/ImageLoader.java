import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Cameron on 21/03/2018.
 */
public class ImageLoader {
// METHODS //
    // Method to return a BufferedImage object from a given filepath
    public static BufferedImage load(String filepath) throws IOException {
        URL url = ImageLoader.class.getResource(filepath);
        try {
            return ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(5);
        }
        return null;
    }
}
