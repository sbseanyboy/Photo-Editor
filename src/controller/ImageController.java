package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import model.MultiLayerIModel;

/**
 * controller class for an image model that takes in either a file or system input and operates
 * on the image model accordingly.
 */
public class ImageController implements IImageController {

  private final MultiLayerIModel model;
  private final Readable rd;
  private final Appendable ap;

  /**
   * constructor method initializing this controller.
   *
   * @param rd the readable input
   */
  public ImageController(Readable rd) {
    this.model = null;
    this.rd = rd;
    this.ap = System.out;
  }

  /**
   * constructor method initializing this controller taking in an appendable for testing.
   *
   * @param rd readable input to parse commands from
   * @param ap appendable input to output results of commands
   */
  public ImageController(Readable rd, Appendable ap) {
    this.model = null;
    this.rd = rd;
    this.ap = ap;
  }

  /**
   * main command method that takes in a filename of "start" to control image model.
   */
  @Override
  public void controlImage() {
    Scanner sc = new Scanner(this.rd);

    String input = sc.nextLine();

    if (input == null) {
      throw new IllegalArgumentException("Input has to be either a file path or "
          + "a string of commands.");
    }

    if (input.endsWith(".txt")) {
      try {
        sc = new Scanner(new FileInputStream(input));
        ImageUtil.batchCommand(sc, model);
      } catch (FileNotFoundException e) {
        throw new IllegalArgumentException("File not found!");
      }
    }
    if (input.equals("start")) {
      System.out.println("Input Commands Below");
      sc = new Scanner(System.in);
      ImageUtil.batchCommand(sc, model);
    }
  }
}
