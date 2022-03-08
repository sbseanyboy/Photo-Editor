package model;

/**
 * Sharpen implementation of a Filter Kernel.
 */
public class SharpenFilter implements IKernel {

  private final double[][] sharpenKernel;

  /**
   * Sharpen filter constructor that initializes the kernel to the values on the assignment page.
   */
  public SharpenFilter() {
    double[] column1 = new double[]{-0.125, -0.125, -0.125, -0.125, -0.125};
    double[] column2 = new double[]{-0.125, 0.25, 0.25, 0.25, -0.125};
    double[] column3 = new double[]{-0.125, 0.25, 1, 0.25, -0.125};
    this.sharpenKernel = new double[][]{column1, column2, column3, column2, column1};
  }

  /**
   * getter method that returns this Filter's Kernel as a 2D array of double.
   *
   * @return the 2D array of doubles that represents this kernel.
   */
  @Override
  public double[][] getKernelArray() {

    double[][] returnMatrix = new double[this.getLength()][this.getLength()];
    for (int i = 0; i < this.sharpenKernel.length; i++) {
      for (int j = 0; j < this.sharpenKernel.length; j++) {
        returnMatrix[i][j] = this.sharpenKernel[i][j];
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
    return this.sharpenKernel[x][y];
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
    return m.applyFilter(this);
  }

  /**
   * getter method that returns the length of this kernel.
   *
   * @return the length of this kernel
   */
  @Override
  public int getLength() {
    return 5;
  }

  /**
   * getter method that returns the height of this kernel.
   *
   * @return the height of this kernel
   */
  @Override
  public int getHeight() {
    return 5;
  }
}
