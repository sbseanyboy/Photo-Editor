package model;

/**
 * A class representing a simple implementation of a pixel in an image, containing it's red, blue.
 * and green color values.
 */
public class Pixel implements IPixel {

  private final int red;
  private final int green;
  private final int blue;

  /**
   * constructor method for a pixel that ensures color values are between 0 and 255, and otherwise
   * initializes to the minimum of 0 or maximum of 255 respectively.
   *
   * @param red the red value for this pixel
   * @param green the green value for this pixel
   * @param blue the blue value for this pixel
   */
  // CORRECTION: Constructor made public instead of default.
  public Pixel(int red, int green, int blue) {
    this.red = capColor(red);
    this.green = capColor(green);
    this.blue = capColor(blue);
  }

  @Override
  public int getRed() {
    return this.red;
  }

  @Override
  public int getGreen() {
    return this.green;
  }

  @Override
  public int getBlue() {
    return this.blue;
  }

  private int capColor(int i) {
    if (i < 0) {
      return 0;
    } else if (i > 255) {
      return 255;
    } else {
      return i;
    }
  }

}
