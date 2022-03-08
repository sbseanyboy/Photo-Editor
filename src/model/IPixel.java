package model;

/**
 * an interface representing a pixel in an image, and all methods that should be called on a pixel.
 */
public interface IPixel {

  /**
   * getter method for the red value of any implementatino of an model.IPixel.
   *
   * @return the integer of the red color value of the model.IPixel.
   */
  int getRed();

  /**
   * getter method for the green value of any implementatino of an model.IPixel.
   *
   * @return the integer of the green color value of the model.IPixel.
   */
  int getGreen();

  /**
   * getter method for the blue value of any implementatino of an model.IPixel.
   *
   * @return the integer of the blue color value of the model.IPixel.
   */
  int getBlue();
}
