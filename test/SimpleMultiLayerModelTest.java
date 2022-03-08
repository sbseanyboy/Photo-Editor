import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import model.GreyScaleFilter;
import model.IImage;
import model.IModel;
import model.ImageModel;
import model.MultiLayerIModel;
import model.SharpenFilter;
import model.SimpleMultiLayerModel;
import org.junit.Before;
import org.junit.Test;

/**
 * testing class for SimpleMultiLayerModel.
 */
public class SimpleMultiLayerModelTest {

  MultiLayerIModel simp;
  ImageModel imageModel1;
  IImage rainbowImage;
  IImage checkerImage;

  @Before
  public void initData() {
    this.imageModel1 = new ImageModel();
    this.rainbowImage = imageModel1.getRainbow(5, 1,
        new ArrayList<>(Arrays.asList(Color.RED, Color.ORANGE, Color.YELLOW)));
    this.checkerImage = imageModel1.getCheckerboard(4, 4, Color.BLACK, Color.WHITE);
    this.simp = new SimpleMultiLayerModel("rainbowBase", rainbowImage);
  }

  @Test
  public void testAddLayer() {
    assertTrue(this.rainbowImage.equalImages(this.simp.getImage()));
    assertTrue(this.rainbowImage.equalImages(this.simp.getCurrent().getImage()));
    this.simp.addLayer("checkerBase", this.checkerImage);
    assertTrue(this.checkerImage.equalImages(this.simp.getImage()));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddLayerNull() {
    this.simp.addLayer("checkerBase", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddLayerNullName() {
    this.simp.addLayer(null, this.checkerImage);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddLayerExisting() {
    this.simp.addLayer("rainbowBase", this.checkerImage);
  }

  @Test
  public void testRemoveLayer() {
    assertTrue(this.rainbowImage.equalImages(this.simp.getImage()));
    this.simp.addLayer("checkerBase", this.checkerImage);
    assertTrue(this.checkerImage.equalImages(this.simp.getImage()));
    this.simp.removeLayer("rainbowBase");
    assertTrue(this.checkerImage.equalImages(this.simp.getImage()));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveLayerNull() {
    this.simp.addLayer("checkerBase", this.checkerImage);
    this.simp.removeLayer(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveLayerNotExisting() {
    this.simp.addLayer("checkerBase", this.checkerImage);
    this.simp.removeLayer("hi");
  }

  @Test
  public void testGetLayer() {
    this.simp.addLayer("checkerBase", this.checkerImage);
    assertTrue(this.checkerImage.equalImages(this.simp.getLayer("checkerBase").getImage()));
    assertTrue(this.rainbowImage.equalImages(this.simp.getLayer("rainbowBase").getImage()));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetLayerNull() {
    this.simp.addLayer("checkerBase", this.checkerImage);
    this.simp.getLayer(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetLayerNonExistent() {
    this.simp.addLayer("checkerBase", this.checkerImage);
    this.simp.getLayer("hi");
  }

  @Test
  public void testGetCurrent() {
    assertTrue(this.rainbowImage.equalImages(this.simp.getCurrent().getImage()));
    this.simp.addLayer("checkerBase", this.checkerImage);
    assertTrue(this.checkerImage.equalImages(this.simp.getCurrent().getImage()));
  }

  @Test
  public void testSetCurrent() {
    this.simp.addLayer("checkerBase", this.checkerImage);
    assertTrue(this.checkerImage.equalImages(this.simp.getCurrent().getImage()));
    this.simp.setCurrent("rainbowBase");
    assertTrue(this.rainbowImage.equalImages(this.simp.getCurrent().getImage()));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetCurrentNull() {
    this.simp.addLayer("checkerBase", this.checkerImage);
    this.simp.setCurrent(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetCurrentNonExistent() {
    this.simp.addLayer("checkerBase", this.checkerImage);
    this.simp.setCurrent("hi");
  }

  @Test
  public void testFlip() {
    this.simp.addLayer("checkerBase", this.checkerImage);
    this.simp.flipTransparency("checkerBase");
    this.simp.flipTransparency("rainbowBase");
    assertTrue(this.simp.getTransparency("checkerBase"));
    assertTrue(this.simp.getTransparency("rainbowBase"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFlipNull() {
    this.simp.addLayer("checkerBase", this.checkerImage);
    this.simp.flipTransparency(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFlipNonExistent() {
    this.simp.addLayer("checkerBase", this.checkerImage);
    this.simp.flipTransparency("hi");
  }

  @Test
  public void testGetTransparency() {
    this.simp.addLayer("checkerBase", this.checkerImage);
    assertFalse(this.simp.getTransparency("checkerBase"));
    assertFalse(this.simp.getTransparency("rainbowBase"));
    this.simp.flipTransparency("checkerBase");
    this.simp.flipTransparency("rainbowBase");
    assertTrue(this.simp.getTransparency("checkerBase"));
    assertTrue(this.simp.getTransparency("rainbowBase"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetTransparencyNull() {
    this.simp.addLayer("checkerBase", this.checkerImage);
    this.simp.getTransparency(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetTransparencyNonExistent() {
    this.simp.addLayer("checkerBase", this.checkerImage);
    this.simp.getTransparency("hi");
  }

  @Test
  public void testGetLayers() {
    this.simp.addLayer("checkerBase", this.checkerImage);
    ArrayList<IModel> list = this.simp.getLayers();
    assertEquals(2, list.size());
    assertTrue(list.get(1).getImage().equalImages(this.simp.getCurrent().getImage()));
  }

  @Test
  public void testNumLayers() {
    assertEquals(1, this.simp.numLayers());
    this.simp.addLayer("checkerBase", this.checkerImage);
    assertEquals(2, this.simp.numLayers());
  }

  @Test
  public void testGetRainbow() {
    assertTrue(this.rainbowImage.equalImages(this.simp
        .getRainbow(5, 1, new ArrayList<>(Arrays.asList(Color.RED, Color.ORANGE, Color.YELLOW)))));
  }

  @Test
  public void testGetChecker() {
    assertTrue(this.checkerImage.equalImages(this.simp
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
    assertTrue(this.rainbowImage.equalImages(this.simp.getImage()));
    this.simp.addLayer("checkerBase", this.checkerImage);
    assertTrue(this.checkerImage.equalImages(this.simp.getImage()));
  }
}