import controller.ImageController;
import java.io.InputStreamReader;

/**
 * Main class for an Image editor.
 */
public class ImageMain {
  /**
   * Main method of an Image.
   *
   * @param args standard field in a main method
   */
  //demo main
  public static void main(String[] args) {
    ImageController c = new ImageController(new InputStreamReader(System.in));
    c.controlImage();
  }
}
