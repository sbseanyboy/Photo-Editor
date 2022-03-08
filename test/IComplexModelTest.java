import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import controller.ImageUtil;
import java.awt.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.ComplexImageModel;
import model.IComplexModel;
import model.IImage;
import model.ImageModel;
import org.junit.Test;


/**
 * Tester class for all classes implementing model.IComplexModel.
 */
public class IComplexModelTest {

  ImageModel imageModel1 = new ImageModel();
  IImage rainbowImage = imageModel1.getRainbow(5, 1,
      new ArrayList<Color>(Arrays.asList(Color.RED, Color.ORANGE, Color.YELLOW)));

  @Test
  public void testComplexImageModelDefault() {
    ComplexImageModel cim = new ComplexImageModel();
    IImage image = new ImageModel().getImage();
    assertTrue(cim.getImage().equalImages(image));
  }

  @Test
  public void testComplexImageModelConstructor() {
    ImageModel im = new ImageModel();
    ComplexImageModel cim = new ComplexImageModel(im.getImage());
    assertEquals(2, cim.getImage().getHeight());
    assertEquals(2, cim.getImage().getWidth());
  }

  @Test(expected = IllegalStateException.class)
  public void testComplexImageModelNull() {
    ComplexImageModel cim = new ComplexImageModel(null);
  }


  @Test
  public void testDownSizeKenji() {
    List<IImage> imageList = ImageUtil.readFile("res\\KenjiNoFilter.jpg");
    IComplexModel model = new ComplexImageModel(imageList.get(0));
    List<IImage> resultList = new ArrayList<IImage>();
    IImage sourceImage = model.getImage();
    assertEquals(107, sourceImage.getWidth());
    assertEquals(129, sourceImage.getHeight());
    resultList.add(model.downScale(0.5, 1));
    assertEquals(53, resultList.get(0).getWidth());
    assertEquals(129, resultList.get(0).getHeight());
    ImageUtil.writeFile(resultList, "kenjiOut.jpg");
  }


  @Test(expected = IllegalStateException.class)
  public void testDownScaleProduceInvalid() {
    IComplexModel model = new ComplexImageModel(
        imageModel1.getRainbow(1, 1,
            new ArrayList<Color>(Arrays.asList(Color.RED))));
    List<IImage> result = new ArrayList<>();
    result.add(model.downScale(0.5, 0.5));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDownScaleBigWidth() {
    IComplexModel model = new ComplexImageModel(
        imageModel1.getRainbow(10, 2,
            new ArrayList<Color>(Arrays.asList(Color.RED, Color.ORANGE, Color.YELLOW))));
    List<IImage> result = new ArrayList<>();
    result.add(model.downScale(10.0, 0.5));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDownScaleBigHeight() {
    IComplexModel model = new ComplexImageModel(
        imageModel1.getRainbow(10, 2,
            new ArrayList<Color>(Arrays.asList(Color.RED, Color.ORANGE, Color.YELLOW))));
    List<IImage> result = new ArrayList<>();
    result.add(model.downScale(0.5, 10.0));
  }

  @Test
  public void testDownScaleHalfHalf() {
    IComplexModel model = new ComplexImageModel(
        imageModel1.getRainbow(10, 2,
            new ArrayList<Color>(Arrays.asList(Color.RED, Color.ORANGE, Color.YELLOW))));
    List<IImage> result = new ArrayList<>();
    IImage sourceImage = model.getImage();
    assertEquals(10, sourceImage.getWidth());
    assertEquals(6, sourceImage.getHeight());
    result.add(model.downScale(0.5, 0.5));
    assertEquals(5, result.get(0).getWidth());
    assertEquals(3, result.get(0).getHeight());
  }

  @Test
  public void testDownScaleHalfThird() {
    IComplexModel model = new ComplexImageModel(
        imageModel1.getRainbow(10, 2,
            new ArrayList<Color>(Arrays.asList(Color.RED, Color.ORANGE, Color.YELLOW))));
    List<IImage> result = new ArrayList<>();
    IImage sourceImage = model.getImage();
    assertEquals(10, sourceImage.getWidth());
    assertEquals(6, sourceImage.getHeight());
    result.add(model.downScale(0.5, 0.334));
    assertEquals(5, result.get(0).getWidth());
    assertEquals(2, result.get(0).getHeight());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMosaicFewSeeds() {
    IComplexModel model = new ComplexImageModel(
        imageModel1.getRainbow(10, 2,
            new ArrayList<Color>(Arrays.asList(Color.RED, Color.ORANGE, Color.YELLOW))));
    List<IImage> result = new ArrayList<>();
    result.add(model.applyMosaic(0));
  }

  @Test
  public void testMosaic() {
    IComplexModel model = new ComplexImageModel(rainbowImage);
    IImage input = model.getImage();
    List<IImage> resultList = new ArrayList<IImage>();
    resultList.add(model.applyMosaic(2));
    assertFalse(input.equalImages(resultList.get(0)));
  }

  @Test
  public void testMosaicOneSeed() {
    List<IImage> imageList = ImageUtil.readFile("res\\KenjiNoFilter.jpg");
    ComplexImageModel model = new ComplexImageModel(imageList.get(0));
    List<IImage> resultList = new ArrayList<IImage>();
    resultList.add(model.applyMosaic(1));
    assertEquals(resultList.get(0).getPixelAt(0, 0).getBlue(),
        resultList.get(0).getPixelAt(50, 50).getBlue());
  }

  @Test
  public void testApplyMosaicLargeSeeds() {
    List<IImage> imageList = ImageUtil.readFile("res\\KenjiNoFilter.jpg");
    IComplexModel model = new ComplexImageModel(imageList.get(0));
    List<IImage> resultList = new ArrayList<IImage>();
    resultList.add(model.applyMosaic(20000));
    assertTrue(model.getImage().equalImages(resultList.get(0)));
  }

}
