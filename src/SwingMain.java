import controller.ISwingController;
import controller.ImageUtil;
import controller.SwingController;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Scanner;
import view.IView;
import view.JFrameView;

/**
 * main class for a swing view of an image processing application.
 */
public class SwingMain {

  String[] args;

  public SwingMain(String[] args) {
    this.args = args;
  }

  /**
   * main method operating an image processing application.
   *
   * @param args default arguments for a main method
   */
  public static void main(String[] args) {
    Scanner sc = new Scanner(new InputStreamReader(System.in));

    String input = sc.next();

    if (input == null) {
      throw new IllegalArgumentException(
          "Input has to be either a file path, \"-text\", or \"-interactive\"");
    }

    switch (input) {
      case "-script": {
        try {
          sc = new Scanner(new FileInputStream(sc.next()));
          ImageUtil.batchCommand(sc, null);
        } catch (FileNotFoundException e) {
          throw new IllegalArgumentException("File not found!");
        }
      }
      break;
      case "-text": {
        System.out.println("Input Commands Below");
        sc = new Scanner(System.in);
        ImageUtil.batchCommand(sc, null);
      }
      break;
      case "-interactive": {
        ISwingController controller = new SwingController();
        IView view = new JFrameView("Edit an Image");
        controller.setView(view);
      }
      break;
      default: throw new IllegalArgumentException("invalid command");
    }
  }
}
