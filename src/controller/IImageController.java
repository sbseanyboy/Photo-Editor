package controller;

/**
 * interface for an Image Controller designed to take in user input in the command model, or a text
 * file of instructions on what image to create and process.
 */
public interface IImageController {

  /**
   * main method of the controller that takes in user input and processes images based on it.
   */
  void controlImage();
}
