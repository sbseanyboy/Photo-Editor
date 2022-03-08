package controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.imageio.ImageIO;
import model.BlurFilter;
import model.GreyScaleFilter;
import model.IImage;
import model.IKernel;
import model.IModel;
import model.Image;
import model.MultiLayerIModel;
import model.Pixel;
import model.SepiaFilter;
import model.SharpenFilter;
import model.SimpleMultiLayerModel;

/**
 * This class contains utility methods to read a PPM image from file and simply print its contents.
 * Feel free to change this method as required.
 */
public class ImageUtil {

  /**
   * Read an image file in the PPM format and print the colors.
   *
   * @param filename the path of the file.
   */
  public static void readPPM(String filename) {
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      System.out.println("File " + filename + " not found!");
      return;
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    System.out.println("Width of image: " + width);
    int height = sc.nextInt();
    System.out.println("Height of image: " + height);
    int maxValue = sc.nextInt();
    System.out.println("Maximum value of a color in this file (usually 256): " + maxValue);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        System.out.println("Color of pixel (" + j + "," + i + "): " + r + "," + g + "," + b);
      }
    }
  }


  /**
   * helper method for batchCommand that takes in the correct scanner based on the user input.
   *
   * @param sc the scanner to be used
   */
  public static void batchCommand(Scanner sc, MultiLayerIModel model) {
    while (sc.hasNextLine()) {
      String line = sc.nextLine();
      System.out.println(line);
      String[] wordList = line.split(" ");

      switch (wordList[0]) {
        case "load":
          List<IImage> imageList1 = ImageUtil.readFile(wordList[1]);
          model = new SimpleMultiLayerModel("layer1", imageList1.get(0));
          for (int i = 1; i < imageList1.size(); i++) {
            model.addLayer("layer" + (i + 1), imageList1.get(i));
          }
          break;
        case "save":
          if (model == null || model.numLayers() < 1) {
            break;
          }
          ArrayList<IModel> imageModelList3 = model.getLayers();
          ArrayList<IImage> imageList3 = new ArrayList<>();
          for (IModel mod : imageModelList3) {
            imageList3.add(mod.getImage());
          }
          writeFile(imageList3, wordList[1]);
          break;
        case "blur":
          model = batchApply(wordList[1], model, new BlurFilter());
          break;
        case "sharpen":
          model = batchApply(wordList[1], model, new SharpenFilter());
          break;
        case "sepia":
          model = batchApply(wordList[1], model, new SepiaFilter());
          break;
        case "greyscale":
          model = batchApply(wordList[1], model, new GreyScaleFilter());
          break;
        case "addLayerFromFile":
          List<IImage> imageList2 = readFile(wordList[1]);
          for (int i = 0; i < imageList2.size(); i++) {
            if (model != null) {
              model.addLayer("layer" + (model.numLayers() + i + 1), imageList2.get(i));
            } else {
              throw new IllegalStateException("No file loaded");
            }
          }
          break;
        case "removeLayer":
          if (model != null) {
            model.removeLayer(wordList[1]);
          } else {
            throw new IllegalStateException("No file loaded");
          }
          break;
        case "flipTransparency":
          if (model != null) {
            model.flipTransparency(wordList[1]);
          } else {
            throw new IllegalStateException("No file loaded");
          }
          break;
        case "setCurrent":
          if (model != null) {
            model.setCurrent(wordList[1]);
          } else {
            throw new IllegalStateException("No file loaded");
          }
          break;
        default:
          throw new IllegalArgumentException("Invalid command");
      }
    }
  }

  /**
   * If the model is not null applies the given kernel to the filter specified for the given model,
   * otherwise throws an illegalStateException to the caller.
   *
   * @param name   Name of the specified layer
   * @param model  Model containing the layer
   * @param kernel Filter Kernel that is being applied
   * @return Updated model with the kernel applied to the given layer
   */
  public static MultiLayerIModel batchApply(String name, MultiLayerIModel model, IKernel kernel) {
    if (model != null) {
      model.setCurrent(name);
      model.removeLayer(name);
      model.addLayer(name, kernel.apply(model));
      return model;
    } else {
      throw new IllegalStateException("No file loaded");
    }
  }

  /**
   * Reads a file and returns the contents as a IImage.
   *
   * @param filename the path of the file
   * @return the provided image in the form of an IImage
   */
  public static List<IImage> readFile(String filename) {
    BufferedImage img;

    List<IImage> result = new ArrayList<>();

    if (filename == null || filename.equals("")) {
      throw new IllegalArgumentException("Cannot read from empty path");
    } else if (filename.endsWith(".txt")) {
      return readMultiImages(filename);
    } else if (filename.endsWith(".ppm")) {
      result.add(ppmToImage(filename));
    } else {
      try {
        img = ImageIO.read(new File(filename));
      } catch (IOException e) {
        throw new IllegalArgumentException("Reading Failed");
      }

      if (img == null) {
        throw new IllegalArgumentException("Cannot read this file");
      }

      ArrayList<Pixel> pixelList = new ArrayList<>();

      for (int h = 0; h < img.getHeight(); h++) {
        for (int w = 0; w < img.getWidth(); w++) {
          Color c = new Color(img.getRGB(w, h));
          pixelList.add(new Pixel(c.getRed(), c.getGreen(), c.getBlue()));
        }
      }
      result.add(new Image(img.getWidth(), img.getHeight(), pixelList));
    }
    return result;
  }

  /**
   * method that reads a text file path and creates a multi layer image model from it.
   *
   * @param filepath Path to the text file that holds the locations of all the layer files
   * @return a list of IImage of all the layers of the multiImage
   */
  public static List<IImage> readMultiImages(String filepath) {

    Scanner sc;
    List<IImage> result = new ArrayList<>();
    try {
      sc = new Scanner(new FileInputStream(filepath));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File not found!");
    }
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      result.add(readFile(s).get(0));
    }
    return result;
  }

  /**
   * Read an image file in the PPM format and print the colors.
   *
   * @param filename the path of the file.
   * @throws IllegalArgumentException when the file is invalid
   */
  public static IImage ppmToImage(String filename) throws IllegalArgumentException {
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File not found!");
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }
    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());
    String token;
    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    // dispose of the max Color value
    sc.nextInt();
    ArrayList<Pixel> pixels = new ArrayList<>();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        pixels.add(new Pixel(r, g, b));
      }
    }
    return new Image(width, height, pixels);
  }

  /**
   * takes in a list of images from a multi layer image model and writes it to a text file.
   *
   * @param imageList the list of images to make a file out of
   * @param name      the name of the file to write
   */
  // should no longer take in IImage - should work with some sort of list
  public static void writeFile(List<IImage> imageList, String name) {
    if (imageList == null || imageList.size() == 0) {
      throw new IllegalArgumentException("No Image Provided");
    }

    if (name == null || name.equals("")) {
      throw new IllegalArgumentException("Cannot write image to empty path");
    }

    if (imageList.size() == 1) {
      ImageUtil.writeSingleFile(imageList.get(0), name);
    } else if (imageList.size() > 1) {
      String[] nameAsArray = name.split("\\.");
      List<String> pathList = new ArrayList<>();
      for (int i = 0; i < imageList.size(); i++) {
        String fileName = nameAsArray[0] + "image" + (i + 1) + "." + nameAsArray[1];
        writeSingleFile(imageList.get(i), fileName);
        pathList.add(fileName);
      }
      try (PrintWriter out = new PrintWriter(nameAsArray[0] + "Path.txt")) {
        for (String s : pathList) {
          out.println(s);
        }
      } catch (FileNotFoundException e) {
        throw new IllegalStateException("Failed to write to file");
      }
    }
  }

  private static void writeSingleFile(IImage image, String name) {
    if (name.endsWith(".ppm")) {
      writePPM(image, name);
    } else {
      File file;
      try {
        file = new File(name);
        if (file.createNewFile()) {
          System.out.println("File Created");
        } else {
          System.out.println("File Already Exists");
        }
      } catch (IOException e) {
        throw new IllegalStateException("File could not be created");
      }

      BufferedImage bi = new BufferedImage(image.getWidth(), image.getHeight(),
          BufferedImage.TYPE_INT_RGB);
      for (int h = 0; h < image.getHeight(); h++) {
        for (int w = 0; w < image.getWidth(); w++) {
          Pixel p = image.getPixelAt(w, h);
          bi.setRGB(w, h, 65536 * p.getRed() + 256 * p.getGreen() + p.getBlue());
        }
      }
      try {
        ImageIO.write(bi, "jpg", file);
      } catch (IOException e) {
        throw new IllegalStateException("File could not be written");
      }
    }
  }


  /**
   * Attempts to create a PPM image file with the desired name and writes the information of the
   * given image correctly to the file.
   *
   * @param image the image desired to be written as a file in ppm format
   * @param name  desired name of the file
   */
  public static void writePPM(IImage image, String name) {
    StringBuilder outputSB = new StringBuilder();

    if (image == null) {
      throw new IllegalArgumentException("Null image provided");
    }

    outputSB.append("P3 \n" + image.getWidth() + "\n" + image.getHeight() + "\n255");

    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        Pixel p = image.getPixelAt(j, i);
        outputSB.append("\n" + p.getRed() + " " + p.getGreen() + " " + p.getBlue());
      }
    }

    String outputString = outputSB.toString();

    try {
      File file = new File(name);
      if (file.createNewFile()) {
        System.out.println("File Created");
      } else {
        System.out.println("File Already Exists");
      }
    } catch (IOException e) {
      throw new IllegalStateException("File could not be created");
    }

    try {
      FileOutputStream fos = new FileOutputStream(name);
      byte[] b = outputString.getBytes();
      fos.write(b);
      fos.close();
      System.out.println("Successfully Wrote To File");
    } catch (Exception e) {
      throw new IllegalStateException("Failed To Write To File");
    }
  }
}