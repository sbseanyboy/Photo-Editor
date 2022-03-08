package model;

/**
 * An interface representing an image and containing all methods an image class should implement.
 */
public interface IImage {

  /**
   * Method that returns the pixel at the specified coordinates in an model.IImage.
   *
   * @param column the column of the pixel.
   * @param row the row of the pixel.
   * @return the pixel at the specified coordinates in the image.
   */
  Pixel getPixelAt(int column, int row);

  /**
   * Getter method for the width of any implementation of an model.IImage.
   *
   * @return the width of this image.
   */
  int getWidth();

  /**
   * Getter method for the height of any implementation of an model.IImage.
   *
   * @return the height of this image.
   */
  int getHeight();

  /**
   * Getter method for the pixel array of any implementation of an model.IImage.
   *
   * @return the pixel array of this image.
   */
  Pixel[][] getPixels();

  /**
   * equals method for IImage, tells if this and that IImage are the same.
   *
   * @return whether this and that image are equal.
   * @throws IllegalArgumentException if the supplied image is null.
   */
  // CORRECTION: throws an Illegal argument exception if the image is null.
  boolean equalImages(IImage that) throws IllegalArgumentException;
}
