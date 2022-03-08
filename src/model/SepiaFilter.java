package model;

/**
 * Sepia implementation of a Filter Kernel.
 */
public class SepiaFilter implements IKernel {

  private final double[][] sepiaKernel;

  /**
   * Sepia filter constructor that initializes the kernel to the values on the assignment page.
   */
  public SepiaFilter() {
    double[] column1 = new double[]{0.393, 0.349, 0.272};
    double[] column2 = new double[]{0.769, 0.686, 0.534};
    double[] column3 = new double[]{0.189, 0.168, 0.131};
    this.sepiaKernel = new double[][]{column1, column2, column3};
  }

  /**
   * getter method that returns this Filter's Kernel as a 2D array of double.
   *
   * @return the 2D array of doubles that represents this kernel.
   */
  @Override
  public double[][] getKernelArray() {

    double[][] returnMatrix = new double[this.getLength()][this.getHeight()];
    for (int i = 0; i < this.sepiaKernel.length; i++) {
      for (int j = 0; j < this.sepiaKernel.length; j++) {
        returnMatrix[i][j] = this.getInKernel(i, j);
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
    return this.sepiaKernel[x][y];
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
    return 3;
  }

  /**
   * getter method that returns the height of this kernel.
   *
   * @return the height of this kernel
   */
  @Override
  public int getHeight() {
    return 3;
  }
}
