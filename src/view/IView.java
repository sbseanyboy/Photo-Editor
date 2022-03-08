package view;

import java.awt.event.ActionListener;
import java.util.List;
import model.MultiLayerIModel;

/**
 * View interface that specifies various methods a view should support.
 */
public interface IView {

  /**
   * Set the listener for any actions.
   */
  void setListener(ActionListener listener);

  /**
   * Display this view.
   */
  void display();

  /**
   * gets the filepath from the user selected file.
   *
   * @return the filepath to the image as a string
   */
  String getImageFilePath();

  /**
   * gets the filepath from the user selected save path.
   *
   * @return the filepath to save the image to
   */
  String getSaveFilePath();

  /**
   * sets the image being viewed to the supplied image.
   *
   * @param model sets the image panel of the implementing class to the current layer of the
   *              supplied image model.
   */
  void setImage(MultiLayerIModel model);

  /**
   * opens a file panel allowing a user to select an image to operate on.
   */
  void openFile();

  /**
   * opens a file panel allowing a user to select an images to add layers from.
   */
  void openAddFile();

  /**
   * opens a file panel allowing a user to select an image to operate on.
   */
  void saveFile();

  /**
   * adds the specified name to the list of layer names in the view.
   *
   * @param s the layer name to be added.
   */
  void setLayerNames(List<String> s);

  /**
   * opens the options panel showing the user what options they have to apply to an image.
   */
  void openOptions();

  /**
   * getter method that gets the filter name selected by the user.
   *
   * @return the filter name as a string
   */
  String getFilter();

  /**
   * getter method that gets the layer name as selected by the user in the view.
   *
   * @return the current layer selected by the user.
   */
  String getCurrentLayer();

  /**
   * gets the filepath of the image file that the user wants to add.
   *
   * @return the filepath of the image layers to add
   */
  String getAdditionalFilePath();

  /**
   * getter method for the specified shrink X value inputted by the user in the view.
   *
   * @return the shrink's x value
   */
  double getShrinkX();

  /**
   * getter method for the specified shrink Y value inputted by the user in the view.
   *
   * @return the shrink's y value
   */
  double getShrinkY();

  /**
   * getter method for the specified mosaic value inputted by the user in the view.
   *
   * @return the mosaic value
   */
  int getMosaicVal();
}
