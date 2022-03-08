package model;

import model.IImage;

/**
 * an interface representing an image modifying filter kernel
 * and methods a kernel should implement.
 */
public interface IKernel {

  /**
   * getter method for the Kernel array matrix of any implementation of a Kernel.
   *
   * @return the kernel array matrix of this kernel
   */
  double[][] getKernelArray();

  /**
   * Getter method that returns the value at the specified coordinates of any implementation
   * of a kernel.
   *
   * @param x the x coordinate of the kernel value
   * @param y the y coordinate of the kernel value
   * @return the kernel value at the specified coordinates
   * @throws IllegalArgumentException if the supplied indices are out of bound.
   */
  // CORRECTION: Throws exception if indices are out of bounds.
  double getInKernel(int x, int y) throws IllegalArgumentException;

  /**
   * apply method to assist in command design pattern. Applies the filter kernel of any
   * implementation of an Ikernel to the specified model.IModel implementation.
   *
   * @param m the image model to apply this kernel to
   * @return the image with the filter kernel applied.
   * @throws IllegalArgumentException if the supplied model is null
   */
  // CORRECTION: Method takes in an IModel rather than an ImageModel.
  // Throws exception if model is null.
  IImage apply(IModel m) throws IllegalArgumentException;

  /**
   * getter method for the length (number of columns) of a kernel matrix in any implementation
   * of a kernel.
   *
   * @return the length of this kernel matrix.
   */
  int getLength();

  /**
   * getter method for the height (number of rows) of a kernel matrix in any implementation
   * of a kernel.
   *
   * @return the length of this kernel matrix.
   */
  int getHeight();
}
