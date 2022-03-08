package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * A representation of a ComplexImageModel that extends ImageModel and implements the IComplexModel
 * interface. Represents a more complex image model that supports all functionality of a ImageModel
 * while also supporting downscaling images and applying mosaic filters.
 */
public class ComplexImageModel extends ImageModel implements IComplexModel {

  int imageHeight;
  int imageWidth;

  /**
   * Constructor to create a ComplexImageModel with a specified IImage.
   *
   * @param image provided image to operate on
   */
  public ComplexImageModel(IImage image) {
    super(image);
    this.imageHeight = image.getHeight();
    this.imageWidth = image.getWidth();
  }

  /**
   * Default Constructor to create a ComplexImageModel with a default checkerboard image.
   */
  public ComplexImageModel() {
    super();
    this.imageHeight = image.getHeight();
    this.imageWidth = image.getWidth();
  }

  /**
   * Returns a downscaled image of this model's image by a given width and height ratio.
   *
   * @param widthRatio  new width to original image width ratio
   * @param heightRatio new height to original image height ratio
   * @return IImage of a downscaled version of the model's image by the given width and height
   * @throws IllegalStateException    if the model image is null or created image would be empty
   * @throws IllegalArgumentException if width or height ratio is not in range
   */
  @Override
  public IImage downScale(double widthRatio, double heightRatio) {

    if (image == null) {
      throw new IllegalStateException("Image provided is invalid");
    }

    if (imageWidth * widthRatio < 1 || imageHeight * heightRatio < 1) {
      throw new IllegalStateException("Image produced will not be valid");
    }

    if (0 > widthRatio || widthRatio > 1) {
      throw new IllegalArgumentException("Width ratio not in range");
    }

    if (0 > heightRatio || heightRatio > 1) {
      throw new IllegalArgumentException("Height ratio not in range");
    }

    List<Pixel> resultPixels = new ArrayList<>();

    double resultWidth = Math.floor(imageWidth * widthRatio);
    double resultHeight = Math.floor(imageHeight * heightRatio);

    for (int newY = 0; newY < resultHeight; newY++) {
      for (int newX = 0; newX < resultWidth; newX++) {

        double oldX = (newX * imageWidth) / resultWidth;
        double oldY = (newY * imageHeight) / resultHeight;

        if (isDoubleInt(oldX) && isDoubleInt(oldY)) {

          IPixel sourcePixel = image.getPixelAt((int) oldX, (int) oldY);

          resultPixels
              .add(new Pixel(sourcePixel.getRed(), sourcePixel.getGreen(), sourcePixel.getBlue()));

        } else if (!isDoubleInt(oldX) && isDoubleInt(oldY)) {

          resultPixels.add(
              calcAverageTwoPixel(oldX, (int) Math.floor(oldX), (int) oldY, (int) Math.ceil(oldX),
                  (int) oldY));

        } else if (isDoubleInt(oldX) && !isDoubleInt(oldY)) {

          resultPixels.add(calcAverageTwoPixel(oldY, (int) oldX, (int) Math.floor(oldY), (int) oldX,
              (int) Math.ceil(oldY)));

        } else {
          resultPixels.add(
              new Pixel(calcAverageFourPixelVal(oldX, oldY, 'R'),
                  calcAverageFourPixelVal(oldX, oldY, 'G'),
                  calcAverageFourPixelVal(oldX, oldY, 'B')));
        }
      }
    }
    return new Image((int) resultWidth, (int) resultHeight, resultPixels);
  }

  /**
   * Helper to determine if a given double is an integer.
   *
   * @param val double that is being queried
   * @return true if the double is an integer and false if not
   */
  private boolean isDoubleInt(double val) {
    return (val % 1) == 0;
  }

  /**
   * Helper to return the channel value of a floating pixel by averaging the value of the same
   * channel of two pixel according to the floating pixel's position on the 1D plane.
   *
   * @param val position of the floating pixel in the 1D plane
   * @param a   value of the first pixel's channel
   * @param b   value of the second pixel's channel
   * @return floating pixel's supposed channel value according to the averaging formula
   */
  private int calcColorVal(double val, int a, int b) {
    return (int) (b * (val - Math.floor(val)) + a * (Math.ceil(val) - val));
  }

  /**
   * Helper to return a Pixel with average values according to its position in a 1D plane and the
   * Pixels next to it.
   *
   * @param val position of the floating pixel in the 1D plane
   * @param x1  x-coordinate of the first pixel
   * @param y1  y-coordinate of the first pixel
   * @param x2  x-coordinate of the second pixel
   * @param y2  y-coordinate of the second pixel
   * @return
   */
  private Pixel calcAverageTwoPixel(double val, int x1, int y1, int x2, int y2) {
    List<Character> colorChannels = new ArrayList<>(Arrays.asList('R', 'G', 'B'));
    List<Integer> colorVals = new ArrayList<>();

    for (Character c : colorChannels) {
      colorVals.add(calcColorVal(val, calcPixel(x1, y1, c), calcPixel(x2, y2, c)));
    }

    return new Pixel(colorVals.get(0), colorVals.get(1), colorVals.get(2));
  }

  /**
   * Helper to return a channel value by average values according a floating pixel's position in a
   * 2D plane and the Pixels next to it.
   *
   * @param x x-coordinate of the floating pixel
   * @param y y-coordinate of the floating pixel
   * @param c character corresponding to the color channel
   * @return averaged channel value according to the floating pixel's position in a 2D plane
   */
  private int calcAverageFourPixelVal(double x, double y, char c) {
    int m = calcColorVal(x, calcPixel((int) Math.floor(x), (int) Math.floor(y), c),
        calcPixel((int) Math.ceil(x), (int) Math.floor(y), c));
    int n = calcColorVal(x, calcPixel((int) Math.floor(x), (int) Math.ceil(y), c),
        calcPixel((int) Math.ceil(x), (int) Math.ceil(y), c));
    return calcColorVal(y, m, n);
  }

  /**
   * Returns a image that is the model's image with a mosaic filter with the given number of seeds
   * applied.
   *
   * @param seeds number of seeds the image will be broken into
   * @return New IImage of this model's image with a mosaic effect applied
   * @throws IllegalStateException    if the model image is null or model image is not of valid
   *                                  dimensions
   * @throws IllegalArgumentException if the provided seed number is invalid
   */
  public IImage applyMosaic(int seeds) {

    if (image == null) {
      throw new IllegalStateException("Image provided is invalid");
    }
    if (seeds < 1) {
      throw new IllegalArgumentException("Provided number of seeds is invalid");
    }
    if (imageWidth < 1 || imageHeight < 1) {
      throw new IllegalStateException("Image provided is not of valid dimensions");
    }
    if (seeds >= imageWidth * imageHeight) {
      return image;
    }

    HashMap<Point, List<Point>> mapSeeds = new HashMap<Point, List<Point>>();
    Random random = new Random();

    //create Hashmap of random seeds (special points)
    for (int seedNum = 0; seedNum < seeds; seedNum++) {
      mapSeeds.put(new Point(random.nextInt(imageWidth + 1), random.nextInt(imageHeight + 1)),
          new ArrayList<>());
    }

    //for each point find the closest seed and add that point to the value list of that seed
    for (int y = 0; y < imageHeight; y++) {
      for (int x = 0; x < imageWidth; x++) {
        mapSeeds.get(getClosestSeed(x, y, mapSeeds.keySet())).add(new Point(x, y));
      }
    }

    HashMap<Integer, Pixel> resultMap = new HashMap<Integer, Pixel>();

    //for each seed
    for (List<Point> points : mapSeeds.values()) {
      if (points.size() == 0) {
        continue;
      }
      int numPoints = points.size();
      int totalR = 0;
      int totalG = 0;
      int totalB = 0;

      //for each point corresponding to this seed
      //sum the rgb values
      for (Point point : points) {
        Pixel pixel = image.getPixelAt(point.x, point.y);
        totalR += pixel.getRed();
        totalG += pixel.getGreen();
        totalB += pixel.getBlue();
      }

      //finds the average rgb values
      totalR = totalR / numPoints;
      totalG = totalG / numPoints;
      totalB = totalB / numPoints;

      for (Point p : points) {
        resultMap.put(p.x + (imageWidth * p.y), new Pixel(totalR, totalG, totalB));
      }
    }

    List<Pixel> resultPixels = new ArrayList<Pixel>();

    for (int y = 0; y < imageHeight; y++) {
      for (int x = 0; x < imageWidth; x++) {
        resultPixels.add(resultMap.get(x + (imageWidth * y)));
      }
    }

    //returns the final image
    return new Image(imageWidth, imageHeight, resultPixels);
  }

  /**
   * Helper to return a Point corresponding to the closest seed to a given coordinate.
   *
   * @param x          x-coordinate in question
   * @param y          y-coordinate in question
   * @param seedPoints Set of Points corresponding to all the seeds of the new image
   * @return Point of the closest seed
   */
  private Point getClosestSeed(int x, int y, Set<Point> seedPoints) {

    double minDist = Double.MAX_VALUE;

    Point closestSeed = null;

    for (Point p : seedPoints) {

      double distance = Math.sqrt(Math.pow(p.getX() - x, 2) + Math.pow(p.getY() - y, 2));

      if (distance == 0) {
        return p;
      } else if (distance < minDist) {
        minDist = distance;
        closestSeed = p;
      }
    }
    return closestSeed;
  }
}