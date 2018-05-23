package Game.Display.Assets;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * Cameron Bell - 21/03/2018
 * Image Loader Class
 * Static Class for Loading Images from Filepath
 */
public class ImageLoader {
// METHODS //
    // Method - Return a BufferedImage Object From a Given Filepath //
    public static BufferedImage load(String filepath) throws IOException {
        URL url = ImageLoader.class.getResource(filepath);
        try {
            return ImageIO.read(url);
        } catch (IOException e) {
            System.out.println("ERROR Loading File: " + filepath);
            e.printStackTrace();
            System.exit(5);
        } catch (IllegalArgumentException e) {
            System.out.println("ERROR Loading File: " + filepath);
            e.printStackTrace();
        }
        return null;
    }
}
