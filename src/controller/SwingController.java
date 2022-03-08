package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import model.BlurFilter;
import model.ComplexMultiLayerIModel;
import model.ComplexMultiLayerModel;
import model.GreyScaleFilter;
import model.IImage;
import model.IModel;
import model.SepiaFilter;
import model.SharpenFilter;
import view.IView;

/**
 * Swing Controller class that controls the image processing application.
 */
public class SwingController implements ISwingController, ActionListener {

  IView view;
  ComplexMultiLayerIModel model;

  /**
   * Constructor takes in no parameters and initializes the model to null until a user selects a
   * file to process.
   */
  public SwingController() {
    this.model = null;
  }

  /**
   * initializes the view to the supplied view, sets this as the view's listener, displays the
   * view.
   *
   * @param view the view to initialize to.
   */
  @Override
  public void setView(IView view) {
    this.view = view;
    this.view.setListener(this);
    this.view.display();
  }

  /**
   * standard action performed method for an action listener class.
   *
   * @param e the action event performed.
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      //read from the input textfield
      case "open file": {
        view.openFile();
        List<IImage> images = ImageUtil.readFile(view.getImageFilePath());
        model = new ComplexMultiLayerModel("layer1", images.get(0)) {
        };
        for (int i = 1; i < images.size(); i++) {
          model.addLayer("layer" + (i + 1), images.get(i));
        }
        view.setLayerNames(model.getLayerNames());
        view.setImage(model);
      }
      break;
      case "add layer": {
        if (model == null) {
          throw new IllegalStateException("select an image to operate on!");
        }
        view.openAddFile();
        List<IImage> images = ImageUtil.readFile(view.getAdditionalFilePath());
        for (int i = 0; i < images.size(); i++) {
          if (model != null) {
            model.addLayer("layer" + (model.numLayers() + i + 1), images.get(i));
          } else {
            throw new IllegalStateException("No file loaded");
          }
        }
        view.setLayerNames(model.getLayerNames());
        view.setImage(model);
      }
      break;
      case "Option": {
        if (model == null) {
          throw new IllegalStateException("select an image to operate on!");
        }
        view.openOptions();
      }
      break;
      case "Apply Filter": {
        if (model == null) {
          throw new IllegalStateException("select an image to operate on!");
        }
        String command = view.getFilter();
        String layer = view.getCurrentLayer();
        applyFilter(command, layer);
        view.setImage(model);
      }
      break;
      case "shrink": {
        if (model == null) {
          throw new IllegalStateException("select an image to operate on!");
        }
        model.downScale(view.getShrinkX(), view.getShrinkY());
        view.setImage(model);
      }
      break;
      case "mosaic": {
        if (model == null) {
          throw new IllegalStateException("select an image to operate on!");
        }
        model.applyMosaic(view.getMosaicVal());
        view.setImage(model);
      }
      break;
      case "Select Layer": {
        String name = view.getCurrentLayer();
        model.setCurrent(name);
        view.setImage(model);
      }
      break;
      case "Remove Layer": {
        if (model == null) {
          throw new IllegalStateException("select an image to operate on!");
        }
        String name = view.getCurrentLayer();
        model.removeLayer(name);
        view.setLayerNames(model.getLayerNames());
        view.setImage(model);
      }
      break;
      case "flip": {
        if (model == null) {
          throw new IllegalStateException("select an image to operate on!");
        }
        model.flipTransparency(model.getCurName());
        view.setImage(model);
      }
      break;
      case "Save file": {
        if (model == null) {
          throw new IllegalStateException("select an image to operate on!");
        }
        view.saveFile();
        if (model == null || model.numLayers() < 1) {
          break;
        }
        String saveFilePath = view.getSaveFilePath();
        ArrayList<IModel> imageModelList3 = model.getLayers();
        ArrayList<IImage> imageList3 = new ArrayList<>();
        for (IModel mod : imageModelList3) {
          imageList3.add(mod.getImage());
        }
        ImageUtil.writeFile(imageList3, saveFilePath);
      }
      break;
      case "Exit Button":
        System.exit(0);
        break;
      default: throw new IllegalArgumentException("No such command");
    }
  }

  /**
   * Helper method applying the supplied filter to the supplied layer.
   *
   * @param filter the name of the filter to apply to the current model layer
   */
  private void applyFilter(String filter, String layer) {
    switch (filter) {
      case "Sepia": {
        ImageUtil.batchApply(layer, model, new SepiaFilter());
      }
      break;
      case "GreyScale": {
        ImageUtil.batchApply(layer, model, new GreyScaleFilter());
      }
      break;
      case "Blur": {
        ImageUtil.batchApply(layer, model, new BlurFilter());
      }
      break;
      case "Sharpen": {
        ImageUtil.batchApply(layer, model, new SharpenFilter());
      }
      break;
      default: throw new IllegalArgumentException("No such filter");
    }
  }
}
