package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * the model for an model.Image containing various method operations that should be carried out on
 * an image.
 */
public class ImageModel implements IModel {

  final IImage image;

  /**
   * Constructor for an model.Image model that takes in a supplied image.
   *
   * @param image the image supplied to build a model on.
   * @throws IllegalStateException if the supplied image is null.
   */
  public ImageModel(IImage image) throws IllegalStateException {
    if (image == null) {
      throw new IllegalStateException("Image cannot be null");
    }
    this.image = image;
  }

  /**
   * Constructor for an model.Image model that supplies a default checkerboard image.
   */
  public ImageModel() {
    this.image = this.getCheckerboard(1, 4, Color.BLACK, Color.WHITE);
  }

  /**
   * creates a new checkerboard image based on the user specified tile size, number of tiles, and
   * colors.
   *
   * @param tileSize the size of each tile in the checkerboard image
   * @param tilesNum the number of tiles in the checkerboard image
   * @param c1       the first color of the checkerboard
   * @param c2       the other color of the checkerboard
   * @return a new checkerboard image
   * @throws IllegalArgumentException if tileSize or tilesNum is less than 0, tilesNum is not
   *                                  square, or either color is null.
   */
  @Override
  public IImage getCheckerboard(int tileSize, int tilesNum, Color c1, Color c2)
      throws IllegalArgumentException {

    if (c1 == null || c2 == null) {
      throw new IllegalArgumentException("Invalid Supplied Colors");
    }

    if (tileSize < 0 || tilesNum < 0) {
      throw new IllegalArgumentException("Invalid Tile Size or Number of Tiles");
    }

    double tilesSqrt = Math.sqrt(tilesNum);

    if (tilesSqrt - Math.floor(tilesSqrt) != 0) {
      throw new IllegalArgumentException("Number Of Tiles Supplied Will Not Form A Square Board");
    }

    List<Pixel> resultPixels = new ArrayList<>();

    int intSideTiles = (int) Math.floor(tilesSqrt);

    for (int i = 0; i < intSideTiles; i++) {
      for (int l = 0; l < tileSize; l++) {
        for (int j = 0; j < intSideTiles; j++) {
          for (int k = 0; k < tileSize; k++) {
            if ((i + j) % 2 == 0) {
              resultPixels.add(new Pixel(c1.getRed(), c1.getGreen(), c1.getBlue()));
            } else {
              resultPixels.add(new Pixel(c2.getRed(), c2.getGreen(), c2.getBlue()));
            }
          }
        }
      }
    }
    return new Image(tileSize * intSideTiles, tileSize * intSideTiles, resultPixels);
  }

  /**
   * creates a new rainbow image based on the user specified width and height, and colors.
   *
   * @param width     the supplied width for the image
   * @param rowHeight the supplied height of each row in the image
   * @param colors    the supplied list of colors to be used in the rainbow image
   * @return a new rainbow image with the specified width, row height, and number of colors.
   * @throws IllegalArgumentException if the width or rowHeight are less than 0, or the list of *
   *                                  colors is null.
   */
  @Override
  public IImage getRainbow(int width, int rowHeight, List<Color> colors) {

    if (colors == null) {
      throw new IllegalArgumentException("Invalid Colors");
    }

    if (width < 0 || rowHeight < 0) {
      throw new IllegalArgumentException("Invalid Width or Row Height");
    }

    List<Pixel> resultPixels = new ArrayList<>();

    for (Color c : colors) {
      for (int i = 0; i < rowHeight; i++) {
        for (int j = 0; j < width; j++) {
          resultPixels.add(new Pixel(c.getRed(), c.getGreen(), c.getBlue()));
        }
      }
    }
    return new Image(width, rowHeight * colors.size(), resultPixels);
  }

  /**
   * This method creates and returns a filtered copy of the supplied image with each pixel's color
   * values modified according to the supplied kernel.
   *
   * @param kernel the kernel to be used to modify the supplied image
   * @return a new image with the filter applied
   * @throws IllegalStateException    if the supplied kernel is null
   * @throws IllegalArgumentException if the kernel even in size, or not square.
   */
  @Override
  public IImage applyFilter(IKernel kernel) throws IllegalArgumentException {
    if (kernel == null) {
      throw new IllegalStateException("kernel cannot be null");
    }

    if (this.image == null) {
      throw new IllegalStateException("model.Image cannot be null");
    }

    if (kernel.getLength() % 2 == 0) {
      throw new IllegalArgumentException("Kernel must have odd dimensions for filtering");
    }

    if (kernel.getLength() != kernel.getHeight()) {
      throw new IllegalArgumentException("Kernel must be square");
    }

    List<Pixel> resultPixels = new ArrayList<>();

    for (int j = 0; j < this.image.getHeight(); j++) {
      for (int i = 0; i < this.image.getWidth(); i++) {
        resultPixels.add(applyFilterToPixel(i, j, kernel));
      }
    }
    return new Image(this.image.getWidth(), this.image.getHeight(), resultPixels);
  }

  /**
   * returns a new pixel with the modified color values according to the supplied kernel's values.
   *
   * @param x      the width of the supplied image
   * @param y      the height of the supplied image
   * @param kernel the supplied kernel to be applied to each pixel
   * @return a new pixel with modified color values as specified by the filter kernel
   */
  private Pixel applyFilterToPixel(int x, int y, IKernel kernel) {
    int resultR = 0;
    int resultG = 0;
    int resultB = 0;

    int kernelX = 0;
    int kernelY = 0;

    for (int indexY = y - kernel.getHeight() / 2; indexY <= y + kernel.getHeight() / 2; indexY++) {
      for (int indexX = x - kernel.getLength() / 2; indexX <= x + kernel.getLength() / 2;
          indexX++) {
        resultR += kernel.getInKernel(kernelX, kernelY) * calcPixel(indexX, indexY, 'R');
        resultG += kernel.getInKernel(kernelX, kernelY) * calcPixel(indexX, indexY, 'G');
        resultB += kernel.getInKernel(kernelX, kernelY) * calcPixel(indexX, indexY, 'B');
        kernelX++;
      }
      kernelY++;
      kernelX = 0;
    }
    return new Pixel(resultR, resultG, resultB);
  }

  /**
   * This method creates and returns a color processed copy of the supplied image with each pixel's
   * color values modified according to the supplied kernel.
   *
   * @param kernel the kernel to be used to modify the supplied image
   * @return a new image with the filter applied
   * @throws IllegalStateException    if the supplied kernel is null
   * @throws IllegalArgumentException if the kernel is not a 3x3 square.
   */
  @Override
  public IImage applyColorProcessing(IKernel kernel) throws IllegalArgumentException {
    if (kernel == null) {
      throw new IllegalStateException("Kernel cannot be null");
    }

    if (this.image == null) {
      throw new IllegalStateException("model.Image cannot be null");
    }

    if (kernel.getLength() != 3) {
      throw new IllegalArgumentException("Color Transformation Kernels must be 3 x 3");
    }
    if (kernel.getLength() != kernel.getHeight()) {
      throw new IllegalArgumentException("Kernel must be square");
    }

    List<Pixel> resultPixels = new ArrayList<>();

    for (int j = 0; j < this.image.getHeight(); j++) {
      for (int i = 0; i < this.image.getWidth(); i++) {
        resultPixels.add(applyProcessingToPixel(i, j, kernel));
      }
    }
    return new Image(this.image.getWidth(), this.image.getHeight(), resultPixels);
  }

  /**
   * returns a new pixel with the modified color values according to the supplied kernel's values.
   *
   * @param x      the width of the supplied image
   * @param y      the height of the supplied image
   * @param kernel the supplied kernel to be applied to each pixel
   * @return a new pixel with modified color values as specified by the filter kernel
   */
  private Pixel applyProcessingToPixel(int x, int y, IKernel kernel) {
    int initRed = calcPixel(x, y, 'R');
    int initGreen = calcPixel(x, y, 'G');
    int initBlue = calcPixel(x, y, 'B');
    int resultR = (int) ((kernel.getInKernel(0, 0) * initRed)
        + (kernel.getInKernel(1, 0) * initGreen)
        + (kernel.getInKernel(2, 0) * initBlue));
    int resultG = (int) ((kernel.getInKernel(0, 1) * initRed)
        + (kernel.getInKernel(1, 1) * initGreen)
        + (kernel.getInKernel(2, 1) * initBlue));
    int resultB = (int) ((kernel.getInKernel(0, 2) * initRed)
        + (kernel.getInKernel(1, 2) * initGreen)
        + (kernel.getInKernel(2, 2) * initBlue));

    return new Pixel(resultR, resultG, resultB);
  }

  /**
   * gets the specified color value of the pixel at the provided coordinates in the supplied image.
   *
   * @param x the x coordinate of the pixel.
   * @param y the y coordinate of the pixel.
   * @param c the letter designating the color to get from the pixel.
   * @return the int value of the specified color of the specified pixel
   */
  int calcPixel(int x, int y, char c) {
    try {
      switch (c) {
        case 'R':
          return this.image.getPixelAt(x, y).getRed();
        case 'G':
          return this.image.getPixelAt(x, y).getGreen();
        case 'B':
          return this.image.getPixelAt(x, y).getBlue();
        default:
          return 0;
      }
    } catch (IndexOutOfBoundsException e) {
      return 0;
    }
  }

  /**
   * getter method for the image in this image model.
   *
   * @return this model's image
   */
  // CORRECTION: flipped the for loops for i and j to properly index image copy creation.
  @Override
  public IImage getImage() {
    ArrayList<Pixel> resultArray = new ArrayList<>();
    for (int j = 0; j < this.image.getPixels()[0].length; j++) {
      for (int i = 0; i < this.image.getPixels().length; i++) {
        int redVal = this.image.getPixelAt(i, j).getRed();
        int greenVal = this.image.getPixelAt(i, j).getGreen();
        int blueVal = this.image.getPixelAt(i, j).getBlue();
        resultArray.add(
            new Pixel(redVal, greenVal, blueVal));
      }
    }
    return new Image(this.image.getWidth(), this.image.getHeight(), resultArray);
  }
}