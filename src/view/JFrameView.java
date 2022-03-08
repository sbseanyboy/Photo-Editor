package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.IImage;
import model.MultiLayerIModel;
import model.Pixel;

/**
 * view class utilizing the swing framework to create a GUI for this image processing application.
 */
public class JFrameView extends JFrame implements IView {

  private JButton exitButton;
  private JButton fileOpenButton;
  private JButton optionButton;
  private JButton applyFilterButton;
  private JButton fileSaveButton;
  private JButton removeButton;
  private JButton addFileOpenButton;
  private JButton shrinkButton;
  private JButton mosaicButton;
  private JButton flipTransparency;

  private JButton menuExitButton;
  private JButton menuFileOpenButton;
  private JButton menuOptionButton;
  private JButton menuApplyFilterButton;
  private JButton menuFileSaveButton;
  private JButton menuRemoveButton;
  private JButton menuAddFileOpenButton;
  private JButton menuShrinkButton;
  private JButton menuMosaicButton;
  private JComboBox<String> menuCombobox;
  private JTextField menuShrinkX;
  private JTextField menuShrinkY;
  private JTextField menuMosaicNum;
  private JButton menuFlipTransparency;

  private JLabel fileOpenDisplay;
  private JLabel optionDisplay;
  private JComboBox<String> combobox;
  private List<String> layerNames;
  private JLabel imageLabel;
  private JLabel fileSaveDisplay;
  private JLabel addFileOpenDisplay;

  /**
   * Constructor for a JFrameView that takes in a string as a caption. Initializes image panel, file
   * load, exit button, and various operations to apply to an image.
   *
   * @param caption the caption for the window
   */
  public JFrameView(String caption) {
    super(caption);

    setSize(600, 600);
    setLocation(300, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // set layout
    this.setLayout(new FlowLayout());

    layerNames = new ArrayList<>();
    JPanel mainPanel = new JPanel();
    //for elements to be arranged vertically within this panel
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    //scroll bars around this main panel
    JScrollPane mainScrollPane = new JScrollPane(mainPanel);
    this.add(mainScrollPane);

    //file open
    JPanel fileopenPanel = new JPanel();
    fileopenPanel.setLayout(new FlowLayout());
    mainPanel.add(fileopenPanel);
    fileOpenButton = new JButton("Open a file");
    fileOpenButton.setActionCommand("open file");
    fileopenPanel.add(fileOpenButton);
    fileOpenDisplay = new JLabel("File path will appear here");
    fileopenPanel.add(fileOpenDisplay);

    //show an image with a scrollbar
    JPanel imagePanel = new JPanel();
    //a border around the panel with a caption
    imagePanel.setBorder(BorderFactory.createTitledBorder("Showing an image"));
    imagePanel.setLayout(new GridLayout(1, 0, 10, 10));
    //imagePanel.setMaximumSize(null);
    mainPanel.add(imagePanel);

    imageLabel = new JLabel();
    JScrollPane imageScrollPane = new JScrollPane(imageLabel);
    imageScrollPane.setPreferredSize(new Dimension(200, 200));
    imagePanel.add(imageScrollPane);

    //combo boxes
    JPanel comboboxPanel = new JPanel();
    comboboxPanel.setBorder(BorderFactory.createTitledBorder("Image Layers"));
    comboboxPanel.setLayout(new BoxLayout(comboboxPanel, BoxLayout.PAGE_AXIS));
    mainPanel.add(comboboxPanel);
    combobox = new JComboBox<>();
    //the event listener when an option is selected
    combobox.setActionCommand("Select Layer");

    comboboxPanel.add(combobox);

    //additional file open
    addFileOpenButton = new JButton("add layers");
    addFileOpenButton.setActionCommand("add layer");
    comboboxPanel.add(addFileOpenButton);
    addFileOpenDisplay = new JLabel("File path will appear here");
    comboboxPanel.add(addFileOpenDisplay);

    // remove button
    removeButton = new JButton("remove selected layer");
    removeButton.setActionCommand("Remove Layer");
    comboboxPanel.add(removeButton);

    // flip transparency button
    flipTransparency = new JButton("Flip Transparency");
    flipTransparency.setActionCommand("flip");
    comboboxPanel.add(flipTransparency);

    //dialog boxes
    JPanel dialogBoxesPanel = new JPanel();
    dialogBoxesPanel.setBorder(BorderFactory.createTitledBorder("Filtering"));
    dialogBoxesPanel.setLayout(new BoxLayout(dialogBoxesPanel, BoxLayout.PAGE_AXIS));
    mainPanel.add(dialogBoxesPanel);

    //JOptionsPane options dialog
    JPanel optionsDialogPanel = new JPanel();
    optionsDialogPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(optionsDialogPanel);

    optionButton = new JButton("Select a filter");
    optionButton.setActionCommand("Option");
    optionsDialogPanel.add(optionButton);

    optionDisplay = new JLabel("No Filter Selected Yet");
    optionsDialogPanel.add(optionDisplay);

    // apply filter button
    applyFilterButton = new JButton("Apply filter");
    applyFilterButton.setActionCommand("Apply Filter");
    optionsDialogPanel.add(applyFilterButton);

    //Shrink and Mosaic
    JPanel shrinkMosaic = new JPanel();
    shrinkMosaic.setBorder(BorderFactory.createTitledBorder("Shrink and Mosaic"));
    shrinkMosaic.setLayout(new BoxLayout(shrinkMosaic, BoxLayout.X_AXIS));
    mainPanel.add(shrinkMosaic);

    //Shrink text fields and button
    shrinkButton = new JButton("shrink!");
    shrinkButton.setActionCommand("shrink");
    shrinkMosaic.add(shrinkButton);

    //mosaic text field and button
    mosaicButton = new JButton("make mosaic!");
    mosaicButton.setActionCommand("mosaic");
    shrinkMosaic.add(mosaicButton);

    //file save
    fileSaveButton = new JButton("Save file");
    fileSaveButton.setActionCommand("Save file");
    mainPanel.add(fileSaveButton);
    fileSaveDisplay = new JLabel("File path will appear here");
    mainPanel.add(fileSaveDisplay);

    //exit button
    exitButton = new JButton("Exit");
    exitButton.setActionCommand("Exit Button");
    mainPanel.add(exitButton);

    // initialize menu buttons
    menuExitButton = new JButton("Exit");
    menuExitButton.setActionCommand("Exit Button");
    menuFileOpenButton = new JButton("Open file");
    menuFileOpenButton.setActionCommand("open file");
    menuOptionButton = new JButton("Filters");
    menuOptionButton.setActionCommand("Option");
    menuApplyFilterButton = new JButton("Apply filter");
    menuApplyFilterButton.setActionCommand("Apply Filter");
    menuFileSaveButton = new JButton("Save");
    menuFileSaveButton.setActionCommand("Save file");
    menuRemoveButton = new JButton("Remove layer");
    menuRemoveButton.setActionCommand("Remove Layer");
    menuAddFileOpenButton = new JButton("Add layer");
    menuAddFileOpenButton.setActionCommand("add layer");
    menuShrinkButton = new JButton("Apply shrink");
    menuShrinkButton.setActionCommand("shrink");
    menuMosaicButton = new JButton("Apply mosaic");
    menuMosaicButton.setActionCommand("mosaic");
    menuCombobox = new JComboBox<>();
    menuCombobox.setActionCommand("Select Layer");
    menuShrinkX = new JTextField("0.5");
    menuShrinkY = new JTextField("0.5");
    menuMosaicNum = new JTextField("50");
    menuFlipTransparency = new JButton("Flip transparency");
    menuFlipTransparency.setActionCommand("flip");

    //menu bar
    JMenuBar menuBar = new JMenuBar();
    JMenu file = new JMenu("File");
    file.add(menuFileOpenButton);
    file.add(menuAddFileOpenButton);
    file.add(menuCombobox);
    file.add(menuRemoveButton);
    file.add(menuFlipTransparency);
    file.add(menuFileSaveButton);
    file.add(menuExitButton);
    JMenu filterMenu = new JMenu("Filter");
    menuBar.add(file);
    filterMenu.add(menuOptionButton);
    filterMenu.add(menuApplyFilterButton);
    menuBar.add(filterMenu);
    JMenu mosaic = new JMenu("Mosaic");
    JLabel menuMosaic = new JLabel("Number of Mosaic Seeds");
    mosaic.add(menuMosaic);
    mosaic.add(menuMosaicNum);
    mosaic.add(menuMosaicButton);
    filterMenu.add(mosaic);
    JMenu shrink = new JMenu("Shrink");
    JLabel menuX = new JLabel("Set x shrink ratio");
    JLabel menuY = new JLabel("Set y shrink ratio");
    shrink.add(menuX);
    shrink.add(menuShrinkX);
    shrink.add(menuY);
    shrink.add(menuShrinkY);
    shrink.add(menuShrinkButton);
    filterMenu.add(shrink);
    // menu bar
    this.setJMenuBar(menuBar);

    pack();
  }

  /**
   * adds the supplied layer name to the list of layers.
   *
   * @param s the layer name to be added.
   */
  @Override
  public void setLayerNames(List<String> s) {
    List<String> prevNames = new ArrayList<>();
    for (String temp : layerNames) {
      prevNames.add(temp);
    }
    layerNames = s;
    for (String name : layerNames) {
      combobox.addItem(name);
      menuCombobox.addItem(name);
    }
    for (String remove : prevNames) {
      combobox.removeItem(remove);
      menuCombobox.removeItem(remove);
    }
  }

  /**
   * Method that displays this view by setting the visibility to true.
   */
  @Override
  public void display() {
    setVisible(true);
  }

  /**
   * Sets the image of this image panel to the current layer of the supplied multi layer model.
   *
   * @param m the model to be visualized
   */
  @Override
  public void setImage(MultiLayerIModel m) {
    IImage image = m.getImage();
    if (!m.getTransparency(m.getCurName())) {
      BufferedImage bi = new BufferedImage(image.getWidth(), image.getHeight(),
          BufferedImage.TYPE_INT_RGB);
      for (int h = 0; h < image.getHeight(); h++) {
        for (int w = 0; w < image.getWidth(); w++) {
          Pixel p = image.getPixelAt(w, h);
          bi.setRGB(w, h, 65536 * p.getRed() + 256 * p.getGreen() + p.getBlue());
        }
      }
      imageLabel.setIcon(new ImageIcon(bi));
    }
  }

  /**
   * opens the file panel and allows a user to select a jpg, ppm, png, or text file to read to be
   * operated on.
   */
  @Override
  public void openFile() {
    final JFileChooser fchooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "JPG, PPM & PNG Images, txt files", "jpg", "png", "txt", "ppm");
    fchooser.setFileFilter(filter);
    int retvalue = fchooser.showOpenDialog(JFrameView.this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      fileOpenDisplay.setText(f.getAbsolutePath());
    }
  }

  /**
   * opens the file panel and allows a user to select a jpg, ppm, png, or text file to read to add
   * to the model to be operated on.
   */
  @Override
  public void openAddFile() {
    final JFileChooser faddchooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "JPG, PPM & PNG Images, txt files", "jpg", "png", "txt", "ppm");
    faddchooser.setFileFilter(filter);
    int returnvalue = faddchooser.showOpenDialog(JFrameView.this);
    if (returnvalue == JFileChooser.APPROVE_OPTION) {
      File fadd = faddchooser.getSelectedFile();
      addFileOpenDisplay.setText(fadd.getAbsolutePath());
    }
  }

  /**
   * save file method that sets the filepath to save the image to as supplied by the user.
   */
  @Override
  public void saveFile() {
    final JFileChooser fchooser = new JFileChooser(".");
    int retvalue = fchooser.showSaveDialog(JFrameView.this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      fileSaveDisplay.setText(f.getAbsolutePath());
    }
  }

  /**
   * opens the options panel in the view, allowing a user to select an operation to apply to an
   * image.
   */
  @Override
  public void openOptions() {
    String[] options = {"Sepia", "GreyScale", "Blur", "Sharpen"};
    int retvalue = JOptionPane
        .showOptionDialog(JFrameView.this, "Please choose filter", "Filters",
            JOptionPane.YES_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[3]);
    optionDisplay.setText(options[retvalue]);
  }

  /**
   * sets the listeners up.
   *
   * @param listener the action listener supplied by the controller.
   */
  @Override
  public void setListener(ActionListener listener) {
    fileOpenButton.addActionListener(listener);
    exitButton.addActionListener(listener);
    optionButton.addActionListener(listener);
    applyFilterButton.addActionListener(listener);
    combobox.addActionListener(listener);
    fileSaveButton.addActionListener(listener);
    addFileOpenButton.addActionListener(listener);
    removeButton.addActionListener(listener);
    flipTransparency.addActionListener(listener);
    shrinkButton.addActionListener(listener);
    mosaicButton.addActionListener(listener);
    menuExitButton.addActionListener(listener);
    menuFileOpenButton.addActionListener(listener);
    menuOptionButton.addActionListener(listener);
    menuApplyFilterButton.addActionListener(listener);
    menuFileSaveButton.addActionListener(listener);
    menuRemoveButton.addActionListener(listener);
    menuCombobox.addActionListener(listener);
    menuAddFileOpenButton.addActionListener(listener);
    menuShrinkButton.addActionListener(listener);
    menuMosaicButton.addActionListener(listener);
    menuFlipTransparency.addActionListener(listener);
  }

  /**
   * gets the filepath of the current image being operated on.
   *
   * @return the filepath as a string
   */
  @Override
  public String getImageFilePath() {
    return fileOpenDisplay.getText();
  }

  /**
   * gets the filepath to save the image to, as specified by the user in the GUI.
   *
   * @return the filepath to save the image to as a string
   */
  @Override
  public String getSaveFilePath() {
    return fileSaveDisplay.getText();
  }

  /**
   * gets the selected filter that the user chose to apply to their image in the GUI.
   *
   * @return the selected filter as a string
   */
  @Override
  public String getFilter() {
    return optionDisplay.getText();
  }

  /**
   * gets the current layer of the Image the user is operating on.
   *
   * @return the current layer of the image in the GUI as selected by the User.
   */
  @Override
  public String getCurrentLayer() {
    return (String) combobox.getSelectedItem();
  }

  /**
   * gets the file path that the user selected in the GUI to add layers to their model.
   *
   * @return the filepath of the file to be added to the current image in the GUI.
   */
  @Override
  public String getAdditionalFilePath() {
    return addFileOpenDisplay.getText();
  }

  /**
   * getter method for the shrink x value suppllied by the user in the GUI.
   *
   * @return the shrink x value as specified by the user.
   */
  @Override
  public double getShrinkX() {
    double returnDub;
    try {
      returnDub = Double.parseDouble(menuShrinkX.getText());
    } catch (IllegalStateException e) {
      throw new IllegalArgumentException("mosaic field value must be an integer");
    }
    return returnDub;
  }

  /**
   * getter method for the shrink y value suppllied by the user in the GUI.
   *
   * @return the shrink y value as specified by the user.
   */
  @Override
  public double getShrinkY() {
    double returnDub;
    try {
      returnDub = Double.parseDouble(menuShrinkY.getText());
    } catch (IllegalStateException e) {
      throw new IllegalArgumentException("mosaic field value must be an integer");
    }
    return returnDub;
  }

  /**
   * getter method for the mosaic value suppllied by the user in the GUI.
   *
   * @return the shrink mosaic value as specified by the user.
   */
  @Override
  public int getMosaicVal() {
    int returnInt;
    try {
      returnInt = Integer.parseInt(menuMosaicNum.getText());
    } catch (IllegalStateException e) {
      throw new IllegalArgumentException("mosaic field value must be an integer");
    }
    return returnInt;
  }
}
