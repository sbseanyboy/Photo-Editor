package model;

/**
 * class for client to supply their own Color Processing kernel. This method applies the supplied
 * kernel to the supplied model.ImageModel by calling applyColorProcessing, Enabling clients to use
 * their own Filter kernel. Prevents accidentally supplying incorrect kernel type to Blur, Sharpen,
 * Sepia, GreyScale, and other such default filter types.
 */
public class SupplyColorProcessor implements IKernel {

  private final double[][] supplyKernel;

  /**
   * Constructor allows users to input their own Color Processing Kernel.
   */
  public SupplyColorProcessor(double[][] supplyKernel) {
    if (supplyKernel.length != 3 || supplyKernel[0].length != 3) {
      throw new IllegalArgumentException(
          "color processing kernel must be a square 2D array of length 3!");
    } else {
      this.supplyKernel = supplyKernel;
    }
  }

  /**
   * getter method that returns this Filter's Kernel as a 2D array of double.
   *
   * @return the 2D array of doubles that represents this kernel.
   */
  @Override
  public double[][] getKernelArray() {

    double[][] returnMatrix = new double[this.getLength()][this.getHeight()];
    for (int i = 0; i < this.supplyKernel.length; i++) {
      for (int j = 0; j < this.supplyKernel.length; j++) {
        returnMatrix[i][j] = this.supplyKernel[i][j];
      }
    }
    return returnMatrix;
  }

  /**
   * gets the value from the kernel at the supplied indices.
   *
   * @param x the column number of the desired value
   * @param y the row number of the desired value
   * @return the value at the supplied indices
   * @throws IllegalArgumentException if the supplied indices are out of bound.
   */
  // CORRECTION: Throws exception if indices are out of bounds.
  @Override
  public double getInKernel(int x, int y) throws IllegalArgumentException {
    if (x > this.getLength() || y > this.getHeight() || x < 0 || y < 0) {
      throw new IllegalArgumentException("indices provided are out of bounds.");
    }
    return this.supplyKernel[x][y];
  }

  /**
   * the method that applies this kernel filter to the provided image model.
   *
   * @param m the image model to apply the filter to
   * @return a copy of the provided image with the filter applied
   * @throws IllegalArgumentException if the supplied model is null
   */
  // CORRECTION: Method takes in an IModel rather than an ImageModel.
  // Throws exception if model is null.
  @Override
  public IImage apply(IModel m) throws IllegalArgumentException {
    if (m == null) {
      throw new IllegalArgumentException("model cannot be null.");
    }
    return m.applyColorProcessing(this);
  }

  /**
   * getter method that returns the length of this kernel.
   *
   * @return the length of this kernel
   */
  @Override
  public int getLength() {
    return this.supplyKernel.length;
  }

  /**
   * getter method that returns the height of this kernel.
   *
   * @return the height of this kernel
   */
  @Override
  public int getHeight() {
    return this.supplyKernel[0].length;
  }
}
