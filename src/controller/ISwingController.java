package controller;

import view.IView;

/**
 * Controller interface for a swing GUI image application.
 */
public interface ISwingController {

  /**
   * initializes the view of this controller to the supplied view.
   *
   * @param view the view to initialize to.
   */
  void setView(IView view);
}
