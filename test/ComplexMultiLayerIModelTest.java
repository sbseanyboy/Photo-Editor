import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import controller.ImageUtil;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.ComplexImageModel;
import model.ComplexMultiLayerIModel;
import model.ComplexMultiLayerModel;
import model.GreyScaleFilter;
import model.IComplexModel;
import model.IImage;
import model.IModel;
import model.SharpenFilter;
import org.junit.Before;
import org.junit.Test;

/**
 * Tester class for all classes implementing model.ComplexMultiLayerIModel.
 */
public class ComplexMultiLayerIModelTest {

  ComplexMultiLayerIModel complexMultiModel;
  IComplexModel imageModel1;
  IImage rainbowImage;
  IImage checkerImage;

  @Before
  public void initData() {
    this.imageModel1 = new ComplexImageModel();
    this.rainbowImage = imageModel1.getRainbow(5, 1,
        new ArrayList<>(Arrays.asList(Color.RED, Color.ORANGE, Color.YELLOW)));
    this.checkerImage = imageModel1.getCheckerboard(4, 4, Color.BLACK, Color.WHITE);
    this.complexMultiModel = new ComplexMultiLayerModel("rainbowBase", rainbowImage);
  }

  @Test
  public void testAddLayer() {
    assertTrue(this.rainbowImage.equalImages(this.complexMultiModel.getImage()));
    assertTrue(this.rainbowImage.equalImages(this.complexMultiModel.getCurrent().getImage()));
    this.complexMultiModel.addLayer("checkerBase", this.checkerImage);
    assertTrue(this.checkerImage.equalImages(this.complexMultiModel.getImage()));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddLayerNull() {
    this.complexMultiModel.addLayer("checkerBase", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddLayerNullName() {
    this.complexMultiModel.addLayer(null, this.checkerImage);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddLayerExisting() {
    this.complexMultiModel.addLayer("rainbowBase", this.checkerImage);
  }

  @Test
  public void testRemoveLayer() {
    assertTrue(this.rainbowImage.equalImages(this.complexMultiModel.getImage()));
    this.complexMultiModel.addLayer("checkerBase", this.checkerImage);
    assertTrue(this.checkerImage.equalImages(this.complexMultiModel.getImage()));
    this.complexMultiModel.removeLayer("rainbowBase");
    assertTrue(this.checkerImage.equalImages(this.complexMultiModel.getImage()));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveLayerNull() {
    this.complexMultiModel.addLayer("checkerBase", this.checkerImage);
    this.complexMultiModel.removeLayer(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveLayerNotExisting() {
    this.complexMultiModel.addLayer("checkerBase", this.checkerImage);
    this.complexMultiModel.removeLayer("hi");
  }

  @Test
  public void testGetLayer() {
    this.complexMultiModel.addLayer("checkerBase", this.checkerImage);
    assertTrue(
        this.checkerImage.equalImages(this.complexMultiModel.getLayer("checkerBase").getImage()));
    assertTrue(
        this.rainbowImage.equalImages(this.complexMultiModel.getLayer("rainbowBase").getImage()));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetLayerNull() {
    this.complexMultiModel.addLayer("checkerBase", this.checkerImage);
    this.complexMultiModel.getLayer(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetLayerNonExistent() {
    this.complexMultiModel.addLayer("checkerBase", this.checkerImage);
    this.complexMultiModel.getLayer("hi");
  }

  @Test
  public void testGetCurrent() {
    assertTrue(this.rainbowImage.equalImages(this.complexMultiModel.getCurrent().getImage()));
    this.complexMultiModel.addLayer("checkerBase", this.checkerImage);
    assertTrue(this.checkerImage.equalImages(this.complexMultiModel.getCurrent().getImage()));
  }

  @Test
  public void testSetCurrent() {
    this.complexMultiModel.addLayer("checkerBase", this.checkerImage);
    assertTrue(this.checkerImage.equalImages(this.complexMultiModel.getCurrent().getImage()));
    this.complexMultiModel.setCurrent("rainbowBase");
    assertTrue(this.rainbowImage.equalImages(this.complexMultiModel.getCurrent().getImage()));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetCurrentNull() {
    this.complexMultiModel.addLayer("checkerBase", this.checkerImage);
    this.complexMultiModel.setCurrent(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetCurrentNonExistent() {
    this.complexMultiModel.addLayer("checkerBase", this.checkerImage);
    this.complexMultiModel.setCurrent("hi");
  }

  @Test
  public void testFlip() {
    this.complexMultiModel.addLayer("checkerBase", this.checkerImage);
    this.complexMultiModel.flipTransparency("checkerBase");
    this.complexMultiModel.flipTransparency("rainbowBase");
    assertTrue(this.complexMultiModel.getTransparency("checkerBase"));
    assertTrue(this.complexMultiModel.getTransparency("rainbowBase"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFlipNull() {
    this.complexMultiModel.addLayer("checkerBase", this.checkerImage);
    this.complexMultiModel.flipTransparency(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFlipNonExistent() {
    this.complexMultiModel.addLayer("checkerBase", this.checkerImage);
    this.complexMultiModel.flipTransparency("hi");
  }

  @Test
  public void testGetTransparency() {
    this.complexMultiModel.addLayer("checkerBase", this.checkerImage);
    assertFalse(this.complexMultiModel.getTransparency("checkerBase"));
    assertFalse(this.complexMultiModel.getTransparency("rainbowBase"));
    this.complexMultiModel.flipTransparency("checkerBase");
    this.complexMultiModel.flipTransparency("rainbowBase");
    assertTrue(this.complexMultiModel.getTransparency("checkerBase"));
    assertTrue(this.complexMultiModel.getTransparency("rainbowBase"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetTransparencyNull() {
    this.complexMultiModel.addLayer("checkerBase", this.checkerImage);
    this.complexMultiModel.getTransparency(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetTransparencyNonExistent() {
    this.complexMultiModel.addLayer("checkerBase", this.checkerImage);
    this.complexMultiModel.getTransparency("hi");
  }

  @Test
  public void testGetLayers() {
    this.complexMultiModel.addLayer("checkerBase", this.checkerImage);
    ArrayList<IModel> list = this.complexMultiModel.getLayers();
    assertEquals(2, list.size());
    assertTrue(list.get(1).getImage().equalImages(this.complexMultiModel.getCurrent().getImage()));
  }

  @Test
  public void testNumLayers() {
    assertEquals(1, this.complexMultiModel.numLayers());
    this.complexMultiModel.addLayer("checkerBase", this.checkerImage);
    assertEquals(2, this.complexMultiModel.numLayers());
  }

  @Test
  public void testGetRainbow() {
    assertTrue(this.rainbowImage.equalImages(this.complexMultiModel
        .getRainbow(5, 1, new ArrayList<>(Arrays.asList(Color.RED, Color.ORANGE, Color.YELLOW)))));
  }

  @Test
  public void testGetChecker() {
    assertTrue(this.checkerImage.equalImages(this.complexMultiModel
        .getCheckerboard(4, 4, Color.BLACK, Color.WHITE)));
  }

  @Test
  public void testApplyColorProcessing() {
    SharpenFilter sf = new SharpenFilter();
    IImage newImage = imageModel1.applyFilter(sf);
    assertEquals(126, newImage.getPixelAt(0, 0).getRed());
    assertEquals(255, newImage.getPixelAt(1, 0).getRed());
    assertEquals(255, newImage.getPixelAt(0, 1).getRed());
    assertEquals(126, newImage.getPixelAt(1, 1).getRed());
  }

  @Test(expected = IllegalStateException.class)
  public void testApplyColorProcessingNull() {
    this.imageModel1.applyFilter(null);
  }

  @Test
  public void testApplyFilter() {
    GreyScaleFilter gsf = new GreyScaleFilter();
    IImage newImage = imageModel1.applyColorProcessing(gsf);
    assertEquals(0, newImage.getPixelAt(0, 0).getRed());
    assertEquals(254, newImage.getPixelAt(1, 0).getRed());
    assertEquals(254, newImage.getPixelAt(0, 1).getRed());
    assertEquals(0, newImage.getPixelAt(1, 1).getRed());
  }

  @Test(expected = IllegalStateException.class)
  public void testApplyFilterNull() {
    this.imageModel1.applyFilter(null);
  }

  @Test
  public void testGetImage() {
    assertTrue(this.rainbowImage.equalImages(this.complexMultiModel.getImage()));
    this.complexMultiModel.addLayer("checkerBase", this.checkerImage);
    assertTrue(this.checkerImage.equalImages(this.complexMultiModel.getImage()));
  }


  @Test(expected = IllegalStateException.class)
  public void testDownScaleProduceInvalid() {
    ComplexMultiLayerIModel model = new ComplexMultiLayerModel("rainbow",
        imageModel1.getRainbow(1, 1,
            new ArrayList<Color>(Arrays.asList(Color.RED))));
    List<IImage> result = new ArrayList<>();
    result.add(model.downScale(0.5, 0.5));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDownScaleBigWidth() {
    ComplexMultiLayerIModel model = new ComplexMultiLayerModel("rainbow",
        imageModel1.getRainbow(10, 2,
            new ArrayList<Color>(Arrays.asList(Color.RED, Color.ORANGE, Color.YELLOW))));
    List<IImage> result = new ArrayList<>();
    result.add(model.downScale(10.0, 0.5));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDownScaleBigHeight() {
    ComplexMultiLayerIModel model = new ComplexMultiLayerModel("rainbow",
        imageModel1.getRainbow(10, 2,
            new ArrayList<Color>(Arrays.asList(Color.RED, Color.ORANGE, Color.YELLOW))));
    List<IImage> result = new ArrayList<>();
    result.add(model.downScale(0.5, 10.0));
  }

  @Test
  public void testDownScaleHalfHalf() {
    ComplexMultiLayerIModel model = new ComplexMultiLayerModel("rainbow",
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
    ComplexMultiLayerIModel model = new ComplexMultiLayerModel("rainbow",
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
    ComplexMultiLayerIModel model = new ComplexMultiLayerModel("rainbow",
        imageModel1.getRainbow(10, 2,
            new ArrayList<Color>(Arrays.asList(Color.RED, Color.ORANGE, Color.YELLOW))));
    List<IImage> result = new ArrayList<>();
    result.add(model.applyMosaic(0));
  }

  @Test
  public void testMosaic() {
    ComplexMultiLayerIModel model = new ComplexMultiLayerModel("rainbow", rainbowImage);
    IImage input = model.getCurrent().getImage();
    List<IImage> resultList = new ArrayList<IImage>();
    resultList.add(model.applyMosaic(2));
    assertFalse(input.equalImages(resultList.get(0)));
  }

  @Test
  public void testMosaicOneSeed() {
    List<IImage> imageList = ImageUtil.readFile("res\\KenjiNoFilter.jpg");
    ComplexMultiLayerIModel model = new ComplexMultiLayerModel("kenji", imageList.get(0));
    List<IImage> resultList = new ArrayList<IImage>();
    resultList.add(model.applyMosaic(1));
    assertEquals(resultList.get(0).getPixelAt(0, 0).getBlue(),
        resultList.get(0).getPixelAt(50, 50).getBlue());
  }

  @Test
  public void testApplyMosaicLargeSeeds() {
    List<IImage> imageList = ImageUtil.readFile("res\\KenjiNoFilter.jpg");
    ComplexMultiLayerIModel model = new ComplexMultiLayerModel("kenji", imageList.get(0));
    List<IImage> resultList = new ArrayList<IImage>();
    resultList.add(model.applyMosaic(20000));
    assertTrue(model.getImage().equalImages(resultList.get(0)));
  }

}
