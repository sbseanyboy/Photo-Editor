package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface for an Image Model extending to add additional functionality supporting multi layer
 * Image models.
 */
public interface MultiLayerIModel extends IModel {

  /**
   * adds a layer to the multi layer image with the supplied string as the name, and the supplied
   * Image as the image to use in its model.
   *
   * @param name  the name of the image layer
   * @param image the image to use in the image layer's model
   * @throws IllegalArgumentException if the image or name are null.
   */
  void addLayer(String name, IImage image) throws IllegalArgumentException;

  /**
   * removes an IModel layer from any implementation of a multi layer image model.
   *
   * @param name the name of the layer to be removed.
   * @throws IllegalArgumentException if the image or name are null or the layer does not exist.
   */
  void removeLayer(String name) throws IllegalArgumentException;

  /**
   * returns the model of the layer that is called.
   *
   * @param name the name of the layer to get
   * @return the model of the layer that is called
   * @throws IllegalArgumentException if the name is null or the layer doesn't exist.
   */
  IModel getLayer(String name) throws IllegalArgumentException;

  /**
   * returns the model of the layer that is called.
   *
   * @return the current layer
   */
  IModel getCurrent();

  /**
   * Sets the "current" image layer to the supplied layer.
   *
   * @param name the name of the layer to set current.
   * @throws IllegalArgumentException if the name is null or the layer doesn't exist.
   */
  void setCurrent(String name) throws IllegalArgumentException;

  /**
   * flips the transparency of the supplied image layer.
   *
   * @param name the name of the layer to flip the transparency of.
   * @throws IllegalArgumentException if the name is null or the layer doesn't exist.
   */
  void flipTransparency(String name) throws IllegalArgumentException;

  /**
   * gets the transparency value of the specified image layer.
   *
   * @param name the name of the layer to get the value of.
   */
  boolean getTransparency(String name);

  /**
   * gets an arraylist of all layers from the hashmap of image layers.
   *
   * @return every IModel layer from a HashMap as an ArrayList
   */
  ArrayList<IModel> getLayers();

  /**
   * getter method returning the number of layers in a multi layer image model.
   *
   * @return the number of layers in this multi layer image model.
   */
  int numLayers();

  /**
   * getter method that returns a list of all names of layers of a multi layer image model.
   *
   * @return the names of the layers from this multi layer image model
   */
  List<String> getLayerNames();

  /**
   * Getter method for the key of the current layer.
   */
  String getCurName();
}
