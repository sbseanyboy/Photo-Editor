package model;

import java.awt.Color;
import java.util.List;

/**
 * An interface for an image model, containing various operations that should be able to be applied
 * to an image.
 */
public interface IModel {

  /**
   * Method Producing a rainbow image in any implementation of an model.IModel.
   *
   * @param width     the width of the rainbow image to be made
   * @param rowHeight the height of each row in the rainbow image to be made
   * @param colors    the list of colors this rainbow should have.
   * @return the rainbow image built to the specified parameters.
   * @throws IllegalArgumentException if the width or rowHeight are less than 0, or the list of
   *                                  colors is null.
   */
  IImage getRainbow(int width, int rowHeight, List<Color> colors) throws IllegalArgumentException;

  /**
   * Method Producing a checkerboard image in any implementation of an model.IModel.
   *
   * @param tileSize the size of each square tile in the checkerboard image to be made
   * @param tilesNum the number of tiles this checkerboard should have
   * @param c1       the first color to be used in this checkerboard
   * @param c2       the second color to be used in this checkerboard
   * @return the checkerboard image built to the specified parameters.
   * @throws IllegalArgumentException if tileSize or tilesNum is less than 0, or either color is
   *                                  null.
   */
  IImage getCheckerboard(int tileSize, int tilesNum, Color c1, Color c2)
      throws IllegalArgumentException;

  /**
   * method that applies an model.Image Filter to the any implementation of an model.IModel.
   *
   * @param kernel the kernel to be used to filter the image
   * @return the image with the kernel filter applied.
   * @throws IllegalStateException    if the supplied kernel is null
   * @throws IllegalArgumentException if the kernel even in size, or not square.
   */
  IImage applyFilter(IKernel kernel) throws IllegalArgumentException;

  /**
   * method that applies an model.Image Color Processor to the any implementation of an
   * model.IModel.
   *
   * @param kernel the kernel to be used to process the image
   * @return the image with the kernel processing applied.
   * @throws IllegalStateException    if the supplied kernel is null
   * @throws IllegalArgumentException if the kernel is not a 3x3 square.
   */
  IImage applyColorProcessing(IKernel kernel) throws IllegalArgumentException;

  /**
   * getter method returning the model.IImage of any model.IModel implementation.
   *
   * @return the image of the model.IModel
   */
  IImage getImage();
}
