package model;

import java.util.List;

/**
 * A class representing a simple representation of an image, containing the image's width, height,
 * and 2D array of pixels.
 */
public class Image implements IImage {

  private final int width;
  private final int height;
  private final Pixel[][] pixelArray;

  /**
   * A constructor for an model.Image containing the image width and height, and a 2D model.Pixel
   * array representation of the image.
   *
   * @param width  the width of the image
   * @param height the height of the image
   * @param pixels the pixel array representing the image
   * @throws IllegalArgumentException if pixel array size dows not math width and height.
   */
  public Image(int width, int height, List<Pixel> pixels) throws IllegalArgumentException {
    if (width * height != pixels.size()) {
      throw new IllegalArgumentException("Invalid Pixels for the given model.Image");
    }

    this.width = width;
    this.height = height;
    this.pixelArray = new Pixel[width][height];

    for (int j = 0; j < height; j++) {
      for (int i = 0; i < width; i++) {
        pixelArray[i][j] = pixels.get(i + width * j);
      }
    }
  }

  /**
   * gets the pixel at the supplied column and row (x and y values).
   *
   * @param column the column of the specified pixel
   * @param row    the row of the specified pixel
   * @return the pixel at the specified coordinates
   */
  @Override
  public Pixel getPixelAt(int column, int row) {
    return pixelArray[column][row];
  }

  /**
   * getter method for an image returning the image's width value.
   *
   * @return the width of this image.
   */
  @Override
  public int getWidth() {
    return this.width;
  }

  /**
   * getter method for an image returning the image's height value.
   *
   * @return the height of this image.
   */
  @Override
  public int getHeight() {
    return this.height;
  }

  /**
   * getter method for an image returning the image's 2D pixel array.
   *
   * @return the pixels of this image.
   */
  @Override
  public Pixel[][] getPixels() {

    Pixel[][] returnMatrix = new Pixel[this.width][this.height];
    for (int i = 0; i < this.pixelArray.length; i++) {
      for (int j = 0; j < this.pixelArray[0].length; j++) {
        Pixel newPixel = this.pixelArray[i][j];
        returnMatrix[i][j] = new Pixel(newPixel.getRed(),
            newPixel.getGreen(), newPixel.getBlue());
      }
    }
    return returnMatrix;
  }

  /**
   * determines if two IImages are equal.
   *
   * @param that the other image to compare to this
   * @return whether this image and that image are the same image
   * @throws IllegalArgumentException if the supplied image is null.
   */
  @Override
  public boolean equalImages(IImage that) throws IllegalArgumentException {

    if (that == null) {
      throw new IllegalArgumentException("images cannot be null!");
    }

    boolean base = true;
    base = base && this.getWidth() == that.getWidth();
    base = base && this.getHeight() == that.getHeight();

    for (int i = 0; i < this.getWidth(); i++) {
      for (int j = 0; j < this.getHeight(); j++) {
        base = base && (this.getPixelAt(i, j).getRed() ==
            that.getPixelAt(i, j).getRed());
        base = base && (this.getPixelAt(i, j).getGreen() ==
            that.getPixelAt(i, j).getGreen());
        base = base && (this.getPixelAt(i, j).getBlue() ==
            that.getPixelAt(i, j).getBlue());
      }
    }
    return base;
  }
}
