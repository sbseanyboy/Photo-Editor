package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class implementing a multi layer image model that has a hashmap of strings to IModels and
 * booleans, representing the name of the image model layer, the image model related to the name,
 * and the boolean transparency related to the name. Sets the newest added layer as the current
 * layer to be operated on.
 */
public class SimpleMultiLayerModel implements MultiLayerIModel {

  final Map<String, IModel> layers;
  final Map<String, Boolean> transparency;
  IModel current;

  /**
   * Constructor for a SimpleMultiLayerModel that creates a new multilayer model when supplied an
   * image to be the first layer, and the name of the first layer of the model.
   *
   * @param firstLayer the image to be the first layer of the model
   * @param name       the name of the first layer
   */
  public SimpleMultiLayerModel(String name, IImage firstLayer) {
    layers = new HashMap<String, IModel>();
    transparency = new HashMap<String, Boolean>();
    this.addLayer(name, firstLayer);
    current = layers.get(name);
  }

  /**
   * adds a layer to the multi layer image with the supplied string as the name, and the supplied
   * Image as the image to use in the layer's model.
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
    this.layers.put(name, new ImageModel(image));
    this.transparency.put(name, false);
    setCurrent(name);
  }

  /**
   * removes the layer of the supplied name from this multi layer image model.
   *
   * @param name the name of the layer to be removed.
   * @throws IllegalArgumentException if the name is null, or no such layer exists.
   */
  @Override
  public void removeLayer(String name) throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException("name cannot be null");
    }
    if (!this.layers.containsKey(name)) {
      throw new IllegalArgumentException("no such layer exists!");
    }
    this.layers.remove(name);
  }

  /**
   * gets a layer of a multi layer image model.
   *
   * @param name the name of the layer to get
   * @return the IModel of the layer from the supplied name.
   * @throws IllegalArgumentException if the name is null or the layer doesn't exist.
   */
  @Override
  public IModel getLayer(String name) throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException("name cannot be null");
    }
    if (!this.layers.containsKey(name)) {
      throw new IllegalArgumentException("no such layer exists");
    }
    return new ImageModel(this.layers.get(name).getImage());
  }

  /**
   * returns the current layer of this multi layer image model.
   *
   * @return the IModel of the current layer of this multi layer image.
   */
  @Override
  public IModel getCurrent() {
    return new ImageModel(this.current.getImage());
  }

  /**
   * sets the current layer of this multi layer image to the supplied image.
   *
   * @param name sets the current layer of this multi layer image model to the IModel layer of the
   *             specified name
   * @throws IllegalArgumentException if the name is null or the layer doesn't exist.
   */
  @Override
  public void setCurrent(String name) throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException("name cannot be null");
    }
    if (!this.layers.containsKey(name)) {
      throw new IllegalArgumentException("no such layer exists");
    }
    this.current = this.layers.get(name);
  }

  /**
   * flips the transparency value of the supplied layer (false meaning not transparent, and true
   * meaning transparent).
   *
   * @param name flips the transparency value of the IModel layer of the specified name
   * @throws IllegalArgumentException if the name is null or the layer doesn't exist.
   */
  @Override
  public void flipTransparency(String name) throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException("name cannot be null");
    }
    if (!this.layers.containsKey(name)) {
      throw new IllegalArgumentException("no such layer exists");
    }
    if (this.transparency.get(name)) {
      this.transparency.replace(name, false);
    } else {
      this.transparency.replace(name, true);
    }
  }

  /**
   * gets the Transparency value of the specified image layer.
   *
   * @param name the name of the layer to get the value of.
   */
  @Override
  public boolean getTransparency(String name) {
    if (name == null) {
      throw new IllegalArgumentException("name cannot be null");
    }
    if (!this.layers.containsKey(name)) {
      throw new IllegalArgumentException("no such layer exists");
    }
    return this.transparency.get(name);
  }

  /**
   * gets the layers from this multi layer image model and returns them as an ArrayList.
   *
   * @return the layers of this multi layer image model in an ArrayList
   */
  @Override
  public ArrayList<IModel> getLayers() {
    Collection<IModel> values = this.layers.values();
    ArrayList<IModel> layerList = new ArrayList<>();
    for (IModel m : values) {
      layerList.add(new ImageModel(m.getImage()));
    }
    return layerList;
  }

  /**
   * counts the number of layers in this Multi Layer Image model.
   *
   * @return the number of layers in this Multi Layer Image model
   */
  @Override
  public int numLayers() {
    return layers.size();
  }

  /**
   * getter method that returns all the layer names for this image model.
   *
   * @return the names of all layers in this image model.
   */
  @Override
  public List<String> getLayerNames() {
    Set<String> names = this.layers.keySet();
    return new ArrayList<>(names);
  }

  @Override
  public String getCurName() {
    String returnVal = null;
    for (String key: layers.keySet()) {
      if (current.equals(layers.get(key))) {
        returnVal = key;
      }
    }
    return returnVal;
  }

  /**
   * creates a new Rainbow image, by supplying the current layer as an ImageModel and delegating to
   * the ImageModel class.
   *
   * @param width     the width of the rainbow image to be made
   * @param rowHeight the height of each row in the rainbow image to be made
   * @param colors    the list of colors this rainbow should have.
   * @return a new rainbow IImage of specified parameters
   */
  @Override
  public IImage getRainbow(int width, int rowHeight, List<Color> colors) {
    return this.current.getRainbow(width, rowHeight, colors);
  }

  /**
   * creates a new checker image, by supplying the current layer as an ImageModel and delegating to
   * the ImageModel class.
   *
   * @param tileSize the size of each square tile in the checkerboard image to be made
   * @param tilesNum the number of tiles this checkerboard should have
   * @param c1       the first color to be used in this checkerboard
   * @param c2       the second color to be used in this checkerboard
   * @return a new checkerboard IImage of specified parameters
   */
  @Override
  public IImage getCheckerboard(int tileSize, int tilesNum, Color c1, Color c2) {
    return this.current.getCheckerboard(tileSize, tilesNum, c1, c2);
  }

  /**
   * applies this image filter to the current layer of this multi layer image model by delegating to
   * the ImageModel class.
   *
   * @param kernel the kernel to be used to filter the image
   * @return the IImage of the current layer IModel with the filter applied
   */
  @Override
  public IImage applyFilter(IKernel kernel) {
    return this.current.applyFilter(kernel);
  }

  /**
   * applies this image color processor to the current layer of this multi layer image model by
   * delegating to the ImageModel class.
   *
   * @param kernel the kernel to be used to process the image
   * @return the IImage of the current layer IModel with the color processing applied
   */
  @Override
  public IImage applyColorProcessing(IKernel kernel) {
    return this.current.applyColorProcessing(kernel);
  }

  /**
   * gets the current layer of this multi layer image model's IImage by delegating to the ImageModel
   * class.
   *
   * @return the IImage of the current ImageModel in this multi layer image model.
   */
  @Override
  public IImage getImage() {
    return this.current.getImage();
  }
}
