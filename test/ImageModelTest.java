import static org.junit.Assert.assertEquals;

import controller.ImageController;
import controller.ImageUtil;
import java.awt.Color;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.BlurFilter;

import model.GreyScaleFilter;
import model.IImage;
import model.ImageModel;
import model.SepiaFilter;
import model.SharpenFilter;
import model.SupplyColorProcessor;
import model.SupplyFilter;
import org.junit.Test;

/**
 * testing class for the photo filtering model.
 */
public class ImageModelTest {

  ImageModel imageModel1 = new ImageModel();
  IImage rainbowImage = imageModel1.getRainbow(5, 1,
      new ArrayList<Color>(Arrays.asList(Color.RED, Color.ORANGE, Color.YELLOW)));
  ImageModel rainbowModel = new ImageModel(rainbowImage);
  ImageController control2 = new ImageController(new StringReader("res\\rainbowNoFilterMulti.txt"));

  @Test(expected = IllegalArgumentException.class)
  public void testGetCheckBoardNullColorOne() {
    imageModel1.getCheckerboard(1, 4, null, Color.BLACK);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCheckBoardNullColorTwo() {
    imageModel1.getCheckerboard(1, 4, Color.BLACK, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCheckBoardNotSquare() {
    imageModel1.getCheckerboard(1, 3, Color.BLACK, Color.WHITE);
  }

  @Test
  public void testGetCheckBoardEmpty() {
    IImage image = imageModel1.getCheckerboard(0, 0, Color.BLACK, Color.WHITE);
    assertEquals(0, image.getHeight());
    assertEquals(0, image.getWidth());
    assertEquals(0, image.getPixels().length);
  }

  @Test
  public void testGetCheckBoardSmall() {
    IImage image = imageModel1.getCheckerboard(1, 4, Color.BLACK, Color.WHITE);
    assertEquals(2, image.getHeight());
    assertEquals(2, image.getWidth());
    assertEquals(0, image.getPixelAt(0, 0).getBlue());
    assertEquals(255, image.getPixelAt(0, 1).getBlue());
    assertEquals(255, image.getPixelAt(1, 0).getBlue());
    assertEquals(0, image.getPixelAt(1, 1).getBlue());
  }

  @Test
  public void testGetCheckBoardMedium() {
    IImage image = imageModel1.getCheckerboard(2, 4, Color.BLACK, Color.WHITE);
    assertEquals(4, image.getHeight());
    assertEquals(4, image.getWidth());
    assertEquals(0, image.getPixelAt(0, 0).getBlue());
    assertEquals(0, image.getPixelAt(1, 0).getBlue());
    assertEquals(255, image.getPixelAt(2, 0).getBlue());
    assertEquals(0, image.getPixelAt(3, 3).getBlue());
  }

  @Test
  public void testGetCheckBoardLarge() {
    IImage image = imageModel1.getCheckerboard(10, 100, Color.BLACK, Color.WHITE);
    assertEquals(100, image.getHeight());
    assertEquals(100, image.getWidth());
    assertEquals(0, image.getPixelAt(0, 0).getBlue());
    assertEquals(0, image.getPixelAt(9, 0).getBlue());
    assertEquals(255, image.getPixelAt(10, 0).getBlue());
    assertEquals(0, image.getPixelAt(99, 99).getBlue());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetRainbowNegWidth() {
    imageModel1.getRainbow(-1, 1, new ArrayList<Color>(Arrays.asList(Color.BLACK, Color.WHITE)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetRainbowNegHeight() {
    imageModel1.getRainbow(1, -1, new ArrayList<Color>(Arrays.asList(Color.BLACK, Color.WHITE)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetRainbowNull() {
    imageModel1.getRainbow(1, 1, null);
  }

  @Test
  public void testGetRainbowEmpty() {
    IImage image = imageModel1.getRainbow(0, 0, new ArrayList<Color>(Arrays.asList(Color.BLACK)));
    assertEquals(0, image.getWidth());
    assertEquals(0, image.getHeight());
    assertEquals(0, image.getPixels().length);
  }

  @Test
  public void testGetRainbowSmall() {
    IImage image = imageModel1.getRainbow(1, 1, new ArrayList<Color>(Arrays.asList(Color.BLACK)));
    assertEquals(1, image.getWidth());
    assertEquals(1, image.getHeight());
    assertEquals(0, image.getPixelAt(0, 0).getBlue());
  }

  @Test
  public void testGetRainbowMedium() {
    IImage image = imageModel1
        .getRainbow(2, 1, new ArrayList<Color>(Arrays.asList(Color.BLACK, Color.WHITE)));
    assertEquals(2, image.getWidth());
    assertEquals(2, image.getHeight());
    assertEquals(0, image.getPixelAt(0, 0).getBlue());
    assertEquals(0, image.getPixelAt(1, 0).getBlue());
    assertEquals(255, image.getPixelAt(0, 1).getBlue());
  }

  @Test
  public void testGetRainbowLarge() {
    assertEquals(5, rainbowImage.getWidth());
    assertEquals(3, rainbowImage.getHeight());
    assertEquals(0, rainbowImage.getPixelAt(0, 0).getGreen());
    assertEquals(200, rainbowImage.getPixelAt(0, 1).getGreen());
    assertEquals(255, rainbowImage.getPixelAt(0, 2).getGreen());
  }

  @Test(expected = IllegalStateException.class)
  public void testApplyFilterNullImage() {
    ImageModel nullIM = new ImageModel(null);
    BlurFilter bf = new BlurFilter();
    nullIM.applyFilter(bf);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplyFilterSupplyFilterNotSquare() {
    ImageModel model = new ImageModel();
    SupplyFilter sf = new SupplyFilter(new double[5][3]);
    model.applyFilter(sf);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplyFilterSupplyFilterNotOdd() {
    ImageModel model = new ImageModel();
    SupplyFilter sf = new SupplyFilter(new double[2][2]);
    model.applyFilter(sf);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplyFilterSCPNotSquare() {
    ImageModel model = new ImageModel();
    SupplyColorProcessor scp = new SupplyColorProcessor(new double[5][3]);
    model.applyFilter(scp);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplyFilterSCPNotThree() {
    ImageModel model = new ImageModel();
    SupplyColorProcessor scp = new SupplyColorProcessor(new double[2][2]);
    model.applyFilter(scp);
  }

  @Test
  public void testApplyDefaultBlurFilter() {
    BlurFilter bf = new BlurFilter();
    IImage newImage = imageModel1.applyFilter(bf);
    assertEquals(62, newImage.getPixelAt(0, 0).getRed());
    assertEquals(78, newImage.getPixelAt(1, 0).getRed());
    assertEquals(78, newImage.getPixelAt(0, 1).getRed());
    assertEquals(62, newImage.getPixelAt(1, 1).getRed());
  }

  @Test
  public void testApplyDefaultBlurFilterBlackSquare() {
    BlurFilter bf = new BlurFilter();
    IImage newImage = imageModel1
        .getRainbow(2, 1, new ArrayList<Color>(Arrays.asList(Color.BLACK, Color.BLACK)));
    ImageModel newImageModelBlur = new ImageModel(newImage);
    assertEquals(0, newImageModelBlur.getImage().getPixelAt(0, 0).getRed());
    assertEquals(0, newImageModelBlur.getImage().getPixelAt(1, 0).getRed());
    assertEquals(0, newImageModelBlur.getImage().getPixelAt(0, 1).getRed());
    assertEquals(0, newImageModelBlur.getImage().getPixelAt(1, 1).getRed());
  }

  @Test
  public void testApplyDefaultSharpenFilter() {
    SharpenFilter sf = new SharpenFilter();
    IImage newImage = imageModel1.applyFilter(sf);
    assertEquals(126, newImage.getPixelAt(0, 0).getRed());
    assertEquals(255, newImage.getPixelAt(1, 0).getRed());
    assertEquals(255, newImage.getPixelAt(0, 1).getRed());
    assertEquals(126, newImage.getPixelAt(1, 1).getRed());
  }

  @Test(expected = IllegalStateException.class)
  public void testApplyApplyColorProcessingNullImage() {
    ImageModel nullIM = new ImageModel(null);
    GreyScaleFilter gsf = new GreyScaleFilter();
    nullIM.applyColorProcessing(gsf);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplyApplyColorProcessingInvalidKernel() {
    ImageModel model = new ImageModel();
    SupplyColorProcessor scp = new SupplyColorProcessor(new double[4][4]);
    model.applyColorProcessing(scp);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplyApplyColorProcessingNotSquare() {
    ImageModel model = new ImageModel();
    SupplyColorProcessor scp = new SupplyColorProcessor(new double[4][3]);
    model.applyColorProcessing(scp);
  }

  @Test
  public void testApplyApplyColorProcessingGSFChecker() {
    GreyScaleFilter gsf = new GreyScaleFilter();
    IImage newImage = imageModel1.applyColorProcessing(gsf);
    assertEquals(0, newImage.getPixelAt(0, 0).getRed());
    assertEquals(254, newImage.getPixelAt(1, 0).getRed());
    assertEquals(254, newImage.getPixelAt(0, 1).getRed());
    assertEquals(0, newImage.getPixelAt(1, 1).getRed());
  }

  @Test
  public void testApplyApplyColorProcessingSFChecker() {
    SepiaFilter sf = new SepiaFilter();
    IImage newImage = imageModel1.applyColorProcessing(sf);
    assertEquals(0, newImage.getPixelAt(0, 0).getRed());
    assertEquals(255, newImage.getPixelAt(1, 0).getRed());
    assertEquals(255, newImage.getPixelAt(0, 1).getRed());
    assertEquals(0, newImage.getPixelAt(1, 1).getRed());
  }

  @Test
  public void testApplyApplyColorProcessingGSFRainbow() {
    GreyScaleFilter gsf = new GreyScaleFilter();
    IImage newImage = rainbowModel.applyColorProcessing(gsf);
    assertEquals(54, newImage.getPixelAt(0, 0).getRed());
    assertEquals(54, newImage.getPixelAt(0, 0).getGreen());
    assertEquals(54, newImage.getPixelAt(0, 0).getBlue());
    assertEquals(197, newImage.getPixelAt(0, 1).getRed());
    assertEquals(236, newImage.getPixelAt(0, 2).getRed());
  }

  @Test
  public void testApplyApplyColorProcessingSFRainbow() {
    SepiaFilter sf = new SepiaFilter();
    IImage newImage = rainbowModel.applyColorProcessing(sf);
    assertEquals(100, newImage.getPixelAt(0, 0).getRed());
    assertEquals(88, newImage.getPixelAt(0, 0).getGreen());
    assertEquals(69, newImage.getPixelAt(0, 0).getBlue());
    assertEquals(254, newImage.getPixelAt(0, 1).getRed());
    assertEquals(226, newImage.getPixelAt(0, 1).getGreen());
    assertEquals(176, newImage.getPixelAt(0, 1).getBlue());
    assertEquals(255, newImage.getPixelAt(0, 2).getRed());
    assertEquals(255, newImage.getPixelAt(0, 2).getGreen());
    assertEquals(205, newImage.getPixelAt(0, 2).getBlue());
  }

  @Test
  public void testOutToInNoFilter() {
    IImage rainbowImage = imageModel1.getRainbow(99, 33,
        new ArrayList<Color>(Arrays.asList(Color.RED, Color.ORANGE, Color.YELLOW)));
    ImageUtil.writePPM(rainbowImage, "res\\RainbowNoFilter.ppm");
    IImage out = ImageUtil
        .ppmToImage("res\\RainbowNoFilter.ppm");
    assertEquals(rainbowImage.getHeight(), out.getHeight());
    assertEquals(rainbowImage.getWidth(), out.getWidth());
    System.out.println("Checking pixel values");
    for (int i = 0; i < rainbowImage.getHeight(); i++) {
      for (int j = 0; j < rainbowImage.getWidth(); j++) {
        assertEquals(rainbowImage.getPixelAt(i, j).getGreen(), out.getPixelAt(i, j).getGreen());
      }
    }
    System.out.println("Successfully checked pixel values");
  }

  @Test
  public void testOutToInFilter() {
    IImage rainbowImage = imageModel1.getRainbow(99, 33,
        new ArrayList<Color>(Arrays.asList(Color.RED, Color.ORANGE, Color.YELLOW)));
    ImageModel imageModelRainbow = new ImageModel(rainbowImage);
    SepiaFilter sf = new SepiaFilter();
    IImage rainbowImageSepia = imageModelRainbow.applyColorProcessing(sf);
    ImageUtil.writePPM(rainbowImageSepia, "res\\RainbowSepia.ppm");
    IImage out = ImageUtil
        .ppmToImage("res\\RainbowSepia.ppm");
    assertEquals(rainbowImageSepia.getHeight(), out.getHeight());
    assertEquals(rainbowImageSepia.getWidth(), out.getWidth());
    System.out.println("Checking pixel values");
    for (int i = 0; i < rainbowImageSepia.getHeight(); i++) {
      for (int j = 0; j < rainbowImageSepia.getWidth(); j++) {
        assertEquals(rainbowImageSepia.getPixelAt(i, j).getGreen(),
            out.getPixelAt(i, j).getGreen());
      }
    }
    System.out.println("Successfully checked pixel values");
  }

  @Test
  public void testReadFileJPG() {
    List<IImage> result = ImageUtil.readFile("res\\simple.jpg");
    IImage image = result.get(0);
    assertEquals(37, image.getWidth());
    assertEquals(28, image.getHeight());
    assertEquals(0, image.getPixelAt(36, 27).getRed());
  }

  @Test
  public void testReadFilePPM() {
    List<IImage> result = ImageUtil.readFile("res\\RainbowNoFilter.ppm");
    IImage image = result.get(0);
    assertEquals(99, image.getWidth());
    assertEquals(99, image.getHeight());
    assertEquals(255, image.getPixelAt(0, 0).getRed());
  }

  @Test
  public void testReadFilePNG() {
    List<IImage> result = ImageUtil.readFile("res\\simple.png");
    IImage image = result.get(0);
    assertEquals(37, image.getWidth());
    assertEquals(28, image.getHeight());
    assertEquals(0, image.getPixelAt(36, 27).getRed());
  }

  @Test
  public void testWriteFileJPG() {
    IImage rainbowImage = imageModel1.getRainbow(99, 33,
        new ArrayList<Color>(Arrays.asList(Color.RED, Color.ORANGE, Color.YELLOW)));
    List<IImage> images = new ArrayList<>();
    images.add(rainbowImage);
    ImageUtil.writeFile(images, "res\\rainbow.jpg");
    IImage jpg = ImageUtil.readFile("res\\rainbow.jpg").get(0);
    assertEquals(0, jpg.getPixelAt(0, 0).getGreen());
  }

  @Test
  public void testReadJPGWriteFilePNG() {
    IImage jpg = ImageUtil.readFile("res\\simple.jpg").get(0);
    List<IImage> images = new ArrayList<>();
    images.add(jpg);
    ImageUtil.writeFile(images, "res\\simple.png");
    IImage png = ImageUtil.readFile("res\\simple.png").get(0);
    assertEquals(255, png.getPixelAt(0, 0).getRed());
  }

  @Test
  public void testReadPPMWriteFileJPG() {
    IImage ppm = ImageUtil.readFile("res\\KenjiNoFilter.ppm").get(0);
    List<IImage> images = new ArrayList<>();
    images.add(ppm);
    ImageUtil.writeFile(images, "res\\KenjiNoFilter.jpg");
    IImage jpg = ImageUtil.readFile("res\\KenjiNoFilter.jpg").get(0);
    assertEquals(129, jpg.getHeight());
    assertEquals(107, jpg.getWidth());
  }

  @Test
  public void testReadPPMWriteFilePNG() {
    IImage ppm = ImageUtil.readFile("res\\KenjiNoFilter.ppm").get(0);
    List<IImage> images = new ArrayList<>();
    images.add(ppm);
    ImageUtil.writeFile(images, "res\\KenjiNoFilter.png");
    IImage png = ImageUtil.readFile("res\\KenjiNoFilter.png").get(0);
    assertEquals(129, png.getHeight());
    assertEquals(107, png.getWidth());
  }


  @Test
  public void testReadPNGWriteFilePPM() {
    IImage ppm = ImageUtil.readFile("res\\KenjiNoFilter.png").get(0);
    List<IImage> images = new ArrayList<>();
    images.add(ppm);
    ImageUtil.writeFile(images, "res\\KenjiNoFilter.ppm");
    IImage png = ImageUtil.readFile("res\\KenjiNoFilter.ppm").get(0);
    assertEquals(129, png.getHeight());
    assertEquals(107, png.getWidth());
  }

  @Test
  public void testGetImage() {
    IImage checker = this.imageModel1.getCheckerboard(2, 4, Color.BLACK, Color.WHITE);
    ImageModel model = new ImageModel(checker);

    assertEquals(0, model.getImage().getPixels()[0][0].getBlue());
    assertEquals(0, model.getImage().getPixels()[0][0].getRed());
    assertEquals(0, model.getImage().getPixels()[0][0].getGreen());

    assertEquals(0, model.getImage().getPixels()[0][1].getBlue());
    assertEquals(0, model.getImage().getPixels()[0][1].getRed());
    assertEquals(0, model.getImage().getPixels()[0][1].getGreen());

    assertEquals(255, model.getImage().getPixels()[0][2].getBlue());
    assertEquals(255, model.getImage().getPixels()[0][2].getRed());
    assertEquals(255, model.getImage().getPixels()[0][2].getGreen());

    assertEquals(255, model.getImage().getPixels()[0][3].getBlue());
    assertEquals(255, model.getImage().getPixels()[0][3].getRed());
    assertEquals(255, model.getImage().getPixels()[0][3].getGreen());

    assertEquals(255, model.getImage().getPixels()[2][0].getBlue());
    assertEquals(255, model.getImage().getPixels()[2][0].getRed());
    assertEquals(255, model.getImage().getPixels()[2][0].getGreen());

    assertEquals(255, model.getImage().getPixels()[2][1].getBlue());
    assertEquals(255, model.getImage().getPixels()[2][1].getRed());
    assertEquals(255, model.getImage().getPixels()[2][1].getGreen());

    assertEquals(0, model.getImage().getPixels()[2][2].getBlue());
    assertEquals(0, model.getImage().getPixels()[2][2].getRed());
    assertEquals(0, model.getImage().getPixels()[2][2].getGreen());

    assertEquals(0, model.getImage().getPixels()[2][3].getBlue());
    assertEquals(0, model.getImage().getPixels()[2][3].getRed());
    assertEquals(0, model.getImage().getPixels()[2][3].getGreen());
  }

  @Test
  public void testGetImageRainbow() {
    IImage rainbow = this.imageModel1.getRainbow(3, 1,
        new ArrayList<Color>(Arrays.asList(Color.RED, Color.ORANGE, Color.YELLOW)));
    ImageModel model = new ImageModel(rainbow);

    assertEquals(0, model.getImage().getPixels()[0][0].getBlue());
    assertEquals(255, model.getImage().getPixels()[0][0].getRed());
    assertEquals(0, model.getImage().getPixels()[0][0].getGreen());

    assertEquals(0, model.getImage().getPixels()[0][1].getBlue());
    assertEquals(255, model.getImage().getPixels()[0][1].getRed());
    assertEquals(200, model.getImage().getPixels()[0][1].getGreen());

    assertEquals(0, model.getImage().getPixels()[0][2].getBlue());
    assertEquals(255, model.getImage().getPixels()[0][2].getRed());
    assertEquals(255, model.getImage().getPixels()[0][2].getGreen());

    assertEquals(0, model.getImage().getPixels()[1][0].getBlue());
    assertEquals(255, model.getImage().getPixels()[1][0].getRed());
    assertEquals(0, model.getImage().getPixels()[1][0].getGreen());

    assertEquals(0, model.getImage().getPixels()[1][1].getBlue());
    assertEquals(255, model.getImage().getPixels()[1][1].getRed());
    assertEquals(200, model.getImage().getPixels()[1][1].getGreen());

    assertEquals(0, model.getImage().getPixels()[1][2].getBlue());
    assertEquals(255, model.getImage().getPixels()[1][2].getRed());
    assertEquals(255, model.getImage().getPixels()[1][2].getGreen());

    assertEquals(0, model.getImage().getPixels()[2][0].getBlue());
    assertEquals(255, model.getImage().getPixels()[2][0].getRed());
    assertEquals(0, model.getImage().getPixels()[2][0].getGreen());

    assertEquals(0, model.getImage().getPixels()[2][1].getBlue());
    assertEquals(255, model.getImage().getPixels()[2][1].getRed());
    assertEquals(200, model.getImage().getPixels()[2][1].getGreen());

    assertEquals(0, model.getImage().getPixels()[2][2].getBlue());
    assertEquals(255, model.getImage().getPixels()[2][2].getRed());
    assertEquals(255, model.getImage().getPixels()[2][2].getGreen());
  }

  @Test
  public void testImageControllerFileReadSepiaWrite() {
    ImageController control1 = new ImageController(
        new StringReader("res\\commandRainbowSepia.txt"));
    control1.controlImage();

    IImage result = ImageUtil.readFile("res\\commandRainbowSepia.jpg").get(0);

    assertEquals(99, result.getHeight());
    assertEquals(99, result.getWidth());
    assertEquals(100, result.getPixelAt(0, 0).getRed());
    assertEquals(87, result.getPixelAt(0, 0).getGreen());
    assertEquals(68, result.getPixelAt(0, 0).getBlue());
  }

  @Test
  public void testReadFileMulti() {
    List<IImage> images = ImageUtil.readFile("res\\readMultipleTest.txt");
    assertEquals(140, images.get(0).getPixelAt(0, 0).getRed());
    assertEquals(54, images.get(1).getPixelAt(0, 0).getRed());
    assertEquals(255, images.get(2).getPixelAt(0, 0).getRed());
    assertEquals(100, images.get(3).getPixelAt(0, 0).getRed());
    assertEquals(255, images.get(4).getPixelAt(0, 0).getRed());
  }

  @Test
  public void testReadWriteFileMulti() {
    List<IImage> images = ImageUtil.readFile("res\\readMultipleTest.txt");
    ImageUtil.writeFile(images, "res\\multipleTestOut.jpg");

    ImageUtil.readFile("res\\multipleTestOutPath.txt");

    assertEquals(140, images.get(0).getPixelAt(0, 0).getRed());
    assertEquals(54, images.get(1).getPixelAt(0, 0).getRed());
    assertEquals(255, images.get(2).getPixelAt(0, 0).getRed());
    assertEquals(100, images.get(3).getPixelAt(0, 0).getRed());
    assertEquals(255, images.get(4).getPixelAt(0, 0).getRed());
  }

  @Test
  public void testMultiBlur() {
    control2.controlImage();

    List<IImage> result = ImageUtil.readFile("res\\rainbowNoFilterMultiOutPath.txt");

    assertEquals(100, result.get(0).getPixelAt(0, 0).getRed());
  }

  @Test
  public void testMultiSharpen() {
    control2.controlImage();

    List<IImage> result = ImageUtil.readFile("res\\rainbowNoFilterMultiOutPath.txt");

    assertEquals(54, result.get(1).getPixelAt(0, 0).getRed());
  }

  @Test
  public void testMultiSepia() {
    control2.controlImage();

    List<IImage> result = ImageUtil.readFile("res\\rainbowNoFilterMultiOutPath.txt");

    assertEquals(178, result.get(2).getPixelAt(0, 0).getRed());
  }

  @Test
  public void testMultiGreyScale() {
    control2.controlImage();

    List<IImage> result = ImageUtil.readFile("res\\rainbowNoFilterMultiOutPath.txt");

    assertEquals(254, result.get(3).getPixelAt(0, 0).getRed());
  }

  @Test
  public void testMultiRemoveLayer() {
    control2.controlImage();

    List<IImage> result = ImageUtil.readFile("res\\rainbowNoFilterMultiOutPath.txt");

    assertEquals(4, result.size());
  }

  @Test
  public void testControlImageStart() {
    Reader in = new StringReader(
        "start");
    StringBuilder out = new StringBuilder();

    ImageController control = new ImageController(in, out);

    control.controlImage();

    assertEquals("Input Commands Below", out.toString());
  }

  @Test
  public void testSingleBlur() {
    ImageController control = new ImageController(new StringReader("res\\singleBlur.txt"));
    control.controlImage();

    List<IImage> result = ImageUtil.readFile("res\\singleBlurOut.jpg");
    assertEquals(178, result.get(0).getPixelAt(0, 0).getRed());
  }

  @Test
  public void testSingleSharpen() {
    ImageController control = new ImageController(new StringReader("res\\singleSharpen.txt"));
    control.controlImage();

    List<IImage> result = ImageUtil.readFile("res\\singleSharpenOut.jpg");
    assertEquals(254, result.get(0).getPixelAt(0, 0).getRed());
  }

  @Test
  public void testSingleSepia() {
    ImageController control = new ImageController(new StringReader("res\\singleSepia.txt"));
    control.controlImage();

    List<IImage> result = ImageUtil.readFile("res\\singleSepiaOut.jpg");
    assertEquals(100, result.get(0).getPixelAt(0, 0).getRed());
  }

  @Test
  public void testSingleGreyFilter() {
    ImageController control = new ImageController(new StringReader("res\\singleGrey.txt"));
    control.controlImage();

    List<IImage> result = ImageUtil.readFile("res\\singleGreyOut.jpg");
    assertEquals(54, result.get(0).getPixelAt(0, 0).getRed());
  }

  @Test
  public void testSingleAddLayer() {
    ImageController control = new ImageController(new StringReader("res\\singleAdd.txt"));
    control.controlImage();

    List<IImage> result = ImageUtil.readFile("res\\singleAddLayerOutPath.txt");
    assertEquals(2, result.size());
  }

  @Test
  public void testSingleRemoveLayer() {
    ImageController control = new ImageController(new StringReader("res\\singleRemove.txt"));
    control.controlImage();

    List<IImage> result = ImageUtil.readFile("res\\singleRemoveLayerOutPath.txt");
    assertEquals(2, result.size());
  }
}