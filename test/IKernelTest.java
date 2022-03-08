

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import model.BlurFilter;
import model.GreyScaleFilter;
import model.IImage;
import model.IKernel;
import model.ImageModel;
import model.SepiaFilter;
import model.SharpenFilter;
import model.SupplyColorProcessor;
import model.SupplyFilter;
import org.junit.Test;

/**
 * Tester class for all Command classes implementing model.IKernel.
 */
public class IKernelTest {

  @Test
  public void testSharpen() {
    IKernel sharpen = new SharpenFilter();
    assertEquals(5, sharpen.getLength());
    assertEquals(5, sharpen.getHeight());
    assertEquals(5, sharpen.getKernelArray().length);
    assertEquals(5, sharpen.getKernelArray()[0].length);
    assertEquals(0.25, sharpen.getInKernel(1, 1), 0.001);
  }

  @Test
  public void testBlur() {
    IKernel blur = new BlurFilter();
    assertEquals(3, blur.getLength());
    assertEquals(3, blur.getHeight());
    assertEquals(3, blur.getKernelArray().length);
    assertEquals(3, blur.getKernelArray()[0].length);
    assertEquals(0.25, blur.getInKernel(1, 1), 0.001);
  }

  @Test
  public void testSepia() {
    IKernel sepia = new SepiaFilter();
    assertEquals(3, sepia.getLength());
    assertEquals(3, sepia.getHeight());
    assertEquals(3, sepia.getKernelArray().length);
    assertEquals(3, sepia.getKernelArray()[0].length);
    assertEquals(0.686, sepia.getInKernel(1, 1), 0.001);
  }

  @Test
  public void testGreyScale() {
    IKernel greyScale = new GreyScaleFilter();
    assertEquals(3, greyScale.getLength());
    assertEquals(3, greyScale.getHeight());
    assertEquals(3, greyScale.getKernelArray().length);
    assertEquals(3, greyScale.getKernelArray()[0].length);
    assertEquals(0.7152, greyScale.getInKernel(1, 1), 0.0001);
  }

  @Test
  public void testApplyDefaultBlurFilter() {
    BlurFilter bf = new BlurFilter();
    ImageModel im = new ImageModel();
    IImage newImage = bf.apply(im);
    assertEquals(62, newImage.getPixelAt(0, 0).getRed());
    assertEquals(78, newImage.getPixelAt(1, 0).getRed());
    assertEquals(78, newImage.getPixelAt(0, 1).getRed());
    assertEquals(62, newImage.getPixelAt(1, 1).getRed());
  }

  @Test
  public void testApplyDefaultSharpenFilter() {
    SharpenFilter sf = new SharpenFilter();
    ImageModel im = new ImageModel();
    IImage newImage = sf.apply(im);
    assertEquals(126, newImage.getPixelAt(0, 0).getRed());
    assertEquals(255, newImage.getPixelAt(1, 0).getRed());
    assertEquals(255, newImage.getPixelAt(0, 1).getRed());
    assertEquals(126, newImage.getPixelAt(1, 1).getRed());
  }

  @Test
  public void testApplyDefaultSepiaFilter() {
    SepiaFilter sf = new SepiaFilter();
    ImageModel im = new ImageModel();
    IImage newImage = sf.apply(im);
    assertEquals(0, newImage.getPixelAt(0, 0).getRed());
    assertEquals(255, newImage.getPixelAt(1, 0).getRed());
    assertEquals(255, newImage.getPixelAt(0, 1).getRed());
    assertEquals(0, newImage.getPixelAt(1, 1).getRed());
  }

  @Test
  public void testApplyDefaultGreyScaleFilter() {
    GreyScaleFilter gsf = new GreyScaleFilter();
    ImageModel im = new ImageModel();
    IImage newImage = gsf.apply(im);
    assertEquals(0, newImage.getPixelAt(0, 0).getRed());
    assertEquals(254, newImage.getPixelAt(1, 0).getRed());
    assertEquals(254, newImage.getPixelAt(0, 1).getRed());
    assertEquals(0, newImage.getPixelAt(1, 1).getRed());
  }

  @Test
  public void testApplyDefaultSupplyFilter() {
    double[] column = new double[]{1, 1, 1};
    double[][] def = new double[][]{column, column, column};
    SupplyFilter gsf = new SupplyFilter(def);
    ImageModel im = new ImageModel(new ImageModel().getCheckerboard(20, 4,
        Color.BLACK, Color.WHITE));
    IImage newImage = gsf.apply(im);
    assertEquals(0, newImage.getPixelAt(0, 0).getRed());
    assertEquals(255, newImage.getPixelAt(39, 0).getRed());
    assertEquals(255, newImage.getPixelAt(0, 39).getRed());
    assertEquals(0, newImage.getPixelAt(39, 39).getRed());
  }

  @Test
  public void testApplyDefaultSupplyColorProcessor() {
    double[] column = new double[]{1, 1, 1};
    double[][] def = new double[][]{column, column, column};
    SupplyColorProcessor scp = new SupplyColorProcessor(def);
    ImageModel im = new ImageModel();
    IImage newImage = scp.apply(im);
    assertEquals(0, newImage.getPixelAt(0, 0).getRed());
    assertEquals(255, newImage.getPixelAt(1, 0).getRed());
    assertEquals(255, newImage.getPixelAt(0, 1).getRed());
    assertEquals(0, newImage.getPixelAt(1, 1).getRed());
  }

  @Test
  public void testApplyDefaultBlurFilterToRainBow() {
    BlurFilter bf = new BlurFilter();
    ArrayList<Color> colors = new ArrayList<>(Arrays.asList(Color.RED, Color.GREEN, Color.BLUE));
    ImageModel im = new ImageModel(new ImageModel().getRainbow(100, 40, colors));
    IImage newImage = bf.apply(im);
    assertEquals(140, newImage.getPixelAt(0, 0).getRed());
    assertEquals(186, newImage.getPixelAt(0, 60).getGreen());
    assertEquals(140, newImage.getPixelAt(0, 119).getBlue());
    assertEquals(0, newImage.getPixelAt(0, 60).getRed());
    assertEquals(0, newImage.getPixelAt(0, 119).getRed());
  }

  @Test
  public void testApplyDefaultSharpenFilterToRainBow() {
    SharpenFilter sf = new SharpenFilter();
    ArrayList<Color> colors = new ArrayList<>(Arrays.asList(Color.RED, Color.GREEN, Color.BLUE));
    ImageModel im = new ImageModel(new ImageModel().getRainbow(100, 40, colors));
    IImage newImage = sf.apply(im);
    assertEquals(255, newImage.getPixelAt(0, 0).getRed());
    assertEquals(255, newImage.getPixelAt(0, 60).getGreen());
    assertEquals(255, newImage.getPixelAt(0, 119).getBlue());
    assertEquals(0, newImage.getPixelAt(0, 60).getRed());
    assertEquals(0, newImage.getPixelAt(0, 119).getRed());
  }

  @Test
  public void testApplyDefaultSepiaFilterToRainBow() {
    SepiaFilter sf = new SepiaFilter();
    ArrayList<Color> colors = new ArrayList<>(Arrays.asList(Color.RED, Color.GREEN, Color.BLUE));
    ImageModel im = new ImageModel(new ImageModel().getRainbow(100, 40, colors));
    IImage newImage = sf.apply(im);
    assertEquals(100, newImage.getPixelAt(0, 0).getRed());
    assertEquals(174, newImage.getPixelAt(0, 60).getGreen());
    assertEquals(33, newImage.getPixelAt(0, 119).getBlue());
    assertEquals(196, newImage.getPixelAt(0, 60).getRed());
    assertEquals(48, newImage.getPixelAt(0, 119).getRed());
  }

  @Test
  public void testApplyDefaultGreyScaleFilterToRainBow() {
    GreyScaleFilter gsf = new GreyScaleFilter();
    ArrayList<Color> colors = new ArrayList<>(Arrays.asList(Color.RED, Color.GREEN, Color.BLUE));
    ImageModel im = new ImageModel(new ImageModel().getRainbow(100, 40, colors));
    IImage newImage = gsf.apply(im);
    assertEquals(54, newImage.getPixelAt(0, 0).getRed());
    assertEquals(182, newImage.getPixelAt(0, 60).getGreen());
    assertEquals(18, newImage.getPixelAt(0, 119).getBlue());
    assertEquals(182, newImage.getPixelAt(0, 60).getRed());
    assertEquals(18, newImage.getPixelAt(0, 119).getRed());
  }

  @Test
  public void testApplyDefaultSupplyFilterToRainBow() {
    double[] column = new double[]{1, 1, 1};
    double[][] def = new double[][]{column, column, column};
    SupplyFilter gsf = new SupplyFilter(def);
    ArrayList<Color> colors = new ArrayList<>(Arrays.asList(Color.RED, Color.GREEN, Color.BLUE));
    ImageModel im = new ImageModel(new ImageModel().getRainbow(100, 40, colors));
    IImage newImage = gsf.apply(im);
    assertEquals(255, newImage.getPixelAt(0, 0).getRed());
    assertEquals(255, newImage.getPixelAt(39, 0).getRed());
    assertEquals(255, newImage.getPixelAt(0, 39).getRed());
    assertEquals(255, newImage.getPixelAt(39, 39).getRed());
  }

  @Test
  public void testApplyDefaultSupplyColorProcessorToRainBow() {
    double[] column = new double[]{1, 1, 1};
    double[][] def = new double[][]{column, column, column};
    SupplyColorProcessor scp = new SupplyColorProcessor(def);
    ArrayList<Color> colors = new ArrayList<>(Arrays.asList(Color.RED, Color.GREEN, Color.BLUE));
    ImageModel im = new ImageModel(new ImageModel().getRainbow(100, 40, colors));
    IImage newImage = scp.apply(im);
    assertEquals(255, newImage.getPixelAt(0, 0).getRed());
    assertEquals(255, newImage.getPixelAt(0, 60).getGreen());
    assertEquals(255, newImage.getPixelAt(0, 119).getBlue());
    assertEquals(255, newImage.getPixelAt(0, 60).getRed());
    assertEquals(255, newImage.getPixelAt(0, 119).getRed());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetInKernelNegIndex1() {
    IKernel blur = new BlurFilter();

    blur.getInKernel(-1, 0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetInKernelNegIndex2() {
    IKernel blur = new BlurFilter();

    blur.getInKernel(0, -1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetInKernelOverIndex1() {
    IKernel blur = new BlurFilter();

    blur.getInKernel(100, 0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetInKernelOverIndex2() {
    IKernel blur = new BlurFilter();

    blur.getInKernel(0, 100);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testApplyNull() {
    IKernel blur = new BlurFilter();

    blur.apply(null);
  }
}