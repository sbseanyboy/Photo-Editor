package model;

/**
 * A representation of a ComplexMultiLayerModel that extends SimpleMultiLayerModel and implements
 * the ComplexMultiLayerIModel interface. Represents a more complex multi-image model that supports
 * all functionality of a SimpleMultiLayerModel while also supporting downscaling images and
 * applying mosaic filters.
 */
public class ComplexMultiLayerModel extends SimpleMultiLayerModel implements
    ComplexMultiLayerIModel {

  /**
   * Constructor to create a ComplexMultiLayerModel with a given layer name, and a provided IImage
   * for that first layer.
   *
   * @param name       name of the first layer of the model
   * @param firstLayer IImage for the first image layer for this model
   */
  public ComplexMultiLayerModel(String name, IImage firstLayer) {
    super(name, firstLayer);
    removeLayer(name);
    this.addLayer(name, firstLayer);
    setCurrent(name);
  }

  /**
   * adds a ComplexImageModel layer to the ComplexMultiLayerModel with the supplied string as the
   * name, and the supplied Image as the image to use in the layer's model.
   *
   * @param name  the name of the image layer
   * @param image the image to use in the image layer's model
   * @throws IllegalArgumentException if the name or image supplied are null, or if the name already
   *                                  exists.
   */
  @Override
  public void addLayer(String name, IImage image) throws IllegalArgumentException {
    if (name == null || image == null) {
      throw new IllegalArgumentException("image and name cannot be null");
    }
    if (this.layers.containsKey(name)) {
      throw new IllegalArgumentException("duplicate names!");
    }
    this.layers.put(name, new ComplexImageModel(image));
    this.transparency.put(name, false);
    setCurrent(name);
  }

  /**
   * returns the current layer of this multi layer image model as a ComplexImageModel.
   *
   * @return the IModel of the current layer of this multi layer image.
   */
  @Override
  public IModel getCurrent() {
    return new ComplexImageModel(current.getImage());
  }

  /**
   * Returns a downscaled image of this model's current image by the provided ratios.
   *
   * @param widthRatio  the ratio of the new width to the original image width from 0.0 - 1.0
   * @param heightRatio the ratio of the new height to the original image height 0.0 - 1.0
   * @return downscaled image by the given ratios
   * @throws IllegalStateException    if the current model image is null, created image would be
   *                                  empty, or current model does not support downscaling
   * @throws IllegalArgumentException if width or height ratio is not in range
   */
  public IImage downScale(double widthRatio, double heightRatio)
      throws IllegalStateException, IllegalArgumentException {

    if (getCurrent().getImage() == null) {
      throw new IllegalStateException("Image provided is invalid");
    }

    if (getCurrent().getImage().getWidth() * widthRatio < 1
        || getCurrent().getImage().getHeight() * heightRatio < 1) {
      throw new IllegalStateException("Image produced will not be valid");
    }

    if (0 > widthRatio || widthRatio > 1) {
      throw new IllegalArgumentException("Width ratio not in range");
    }

    if (0 > heightRatio || heightRatio > 1) {
      throw new IllegalArgumentException("Height ratio not in range");
    }

    if (getCurrent() instanceof IComplexModel) {
      return ((IComplexModel) getCurrent()).downScale(widthRatio, heightRatio);
    } else {
      throw new IllegalStateException("This image does not support down sizing");
    }
  }

  /**
   * Returns an image of the this model's current image with a mosaic filter with the given number
   * of seeds applied.
   *
   * @param seeds number of seeds in the new image
   * @return new image with a mosaic filter with the given seeds applied
   * @throws IllegalStateException    if provided image is null, current image is not of valid
   *                                  dimensions, or the current model does not support downscaling
   * @throws IllegalArgumentException if the provided seed number is invalid
   */
  public IImage applyMosaic(int seeds) throws IllegalStateException, IllegalArgumentException {

    if (getCurrent().getImage() == null) {
      throw new IllegalStateException("Image provided is invalid");
    }
    if (seeds < 1) {
      throw new IllegalArgumentException("Provided number of seeds is invalid");
    }
    if (getCurrent().getImage().getWidth() < 1 || getCurrent().getImage().getHeight() < 1) {
      throw new IllegalStateException("Image provided is not of valid dimensions");
    }

    if (getCurrent() instanceof IComplexModel) {
      return ((IComplexModel) getCurrent()).applyMosaic(seeds);
    } else {
      throw new IllegalStateException("This image does not support applying mosaic filter");
    }
  }
}
