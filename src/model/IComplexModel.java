package model;

/**
 * An interface for an image model, containing all previous operations supported by IModel in
 * addition to supporting downScaling images and applying mosaic filers.
 */
public interface IComplexModel extends IModel {

  /**
   * Returns a downscaled image by the provided width and height ratios.
   *
   * @param xRatio the ratio of the new width to the original image width from 0.0 - 1.0
   * @param yRatio the ratio of the new height to the original image height 0.0 - 1.0
   * @return downscaled image by the given ratios
   * @throws IllegalStateException    if the model image is null or created image would be empty
   * @throws IllegalArgumentException if width or height ratio is not in range
   */
  IImage downScale(double xRatio, double yRatio)
      throws IllegalStateException, IllegalArgumentException;

  /**
   * Returns an image with a mosaic filter and the given number of seeds applied.
   *
   * @param seeds number of seeds in the new image
   * @return new image with a mosaic filter with the given seeds applied
   * @throws IllegalStateException    if the model image is null or model image is not of valid
   *                                  dimensions
   * @throws IllegalArgumentException if the provided seed number is invalid
   */
  IImage applyMosaic(int seeds) throws IllegalStateException, IllegalArgumentException;
}
