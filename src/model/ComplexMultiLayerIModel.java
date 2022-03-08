package model;

/**
 * Interface for an Image Model extending MultiLayerIModel to add additional functionality
 * supporting downscaling images and applying mosaic filters.
 */
public interface ComplexMultiLayerIModel extends MultiLayerIModel {

  /**
   * Returns a downscaled image by the provided width and height ratios.
   *
   * @param widthRatio  the ratio of the new width to the original image width from 0.0 - 1.0
   * @param heightRatio the ratio of the new height to the original image height 0.0 - 1.0
   * @return downscaled image by the given ratios
   * @throws IllegalStateException    if the current model image is null, created image would be
   *                                  empty, or current model does not support downscaling
   * @throws IllegalArgumentException if width or height ratio is not in range
   */
  IImage downScale(double widthRatio, double heightRatio)
      throws IllegalStateException, IllegalArgumentException;

  /**
   * Returns an image with a mosaic filter and the given number of seeds applied.
   *
   * @param seeds number of seeds in the new image
   * @return new image with a mosaic filter with the given seeds applied
   * @throws IllegalStateException    if provided image is null, current image is not of valid
   *                                  dimensions, or the current model does not support downscaling
   * @throws IllegalArgumentException if the provided seed number is invalid
   */
  IImage applyMosaic(int seeds) throws IllegalStateException, IllegalArgumentException;
}
