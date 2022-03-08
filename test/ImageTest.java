import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import model.IImage;
import model.Image;
import model.ImageModel;
import model.Pixel;
import org.junit.Test;

/**
 * testing class for model.Image.
 */
public class ImageTest {

  ArrayList<Pixel> emptyPixels = new ArrayList<>();
  IImage checker = new ImageModel().getCheckerboard(2, 4, Color.BLACK, Color.WHITE);
  Pixel black = new Pixel(0, 0, 0);
  Pixel white = new Pixel(255, 255, 255);
  ArrayList<Pixel> checkerPixels = new ArrayList<>(Arrays.asList(black, white, white, black));

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorError() {
    Image i = new Image(10, 10, emptyPixels);
  }

  @Test
  public void testEmptyConstructor() {
    Image i = new Image(0, 0, emptyPixels);
    assertEquals(0, i.getPixels().length);
  }

  @Test
  public void testConstructor() {
    Image i = new Image(2, 2, checkerPixels);
    assertEquals(2, i.getPixels().length);
    assertEquals(2, i.getPixels()[1].length);
  }

  @Test
  public void testImage() {
    assertEquals(4, checker.getWidth());
    assertEquals(4, checker.getHeight());
    assertEquals(4, checker.getPixels().length);
    assertEquals(4, checker.getPixels()[0].length);
    assertEquals(0, checker.getPixelAt(0, 0).getBlue());
    assertEquals(0, checker.getPixelAt(0, 0).getRed());
    assertEquals(0, checker.getPixelAt(0, 0).getGreen());

    assertEquals(0, checker.getPixelAt(0, 1).getBlue());
    assertEquals(0, checker.getPixelAt(0, 1).getRed());
    assertEquals(0, checker.getPixelAt(0, 1).getGreen());

    assertEquals(255, checker.getPixelAt(0, 2).getBlue());
    assertEquals(255, checker.getPixelAt(0, 2).getRed());
    assertEquals(255, checker.getPixelAt(0, 2).getGreen());

    assertEquals(255, checker.getPixelAt(0, 3).getBlue());
    assertEquals(255, checker.getPixelAt(0, 3).getRed());
    assertEquals(255, checker.getPixelAt(0, 3).getGreen());

    assertEquals(255, checker.getPixelAt(2, 0).getBlue());
    assertEquals(255, checker.getPixelAt(2, 0).getRed());
    assertEquals(255, checker.getPixelAt(2, 0).getGreen());

    assertEquals(255, checker.getPixelAt(2, 1).getBlue());
    assertEquals(255, checker.getPixelAt(2, 1).getRed());
    assertEquals(255, checker.getPixelAt(2, 1).getGreen());

    assertEquals(0, checker.getPixelAt(2, 2).getBlue());
    assertEquals(0, checker.getPixelAt(2, 2).getRed());
    assertEquals(0, checker.getPixelAt(2, 2).getGreen());

    assertEquals(0, checker.getPixelAt(2, 3).getBlue());
    assertEquals(0, checker.getPixelAt(2, 3).getRed());
    assertEquals(0, checker.getPixelAt(2, 3).getGreen());
  }

  @Test
  public void testGetPixels() {
    assertEquals(0, checker.getPixels()[0][0].getBlue());
    assertEquals(0, checker.getPixels()[0][0].getRed());
    assertEquals(0, checker.getPixels()[0][0].getGreen());

    assertEquals(0, checker.getPixels()[0][1].getBlue());
    assertEquals(0, checker.getPixels()[0][1].getRed());
    assertEquals(0, checker.getPixels()[0][1].getGreen());

    assertEquals(255, checker.getPixels()[0][2].getBlue());
    assertEquals(255, checker.getPixels()[0][2].getRed());
    assertEquals(255, checker.getPixels()[0][2].getGreen());

    assertEquals(255, checker.getPixels()[0][3].getBlue());
    assertEquals(255, checker.getPixels()[0][3].getRed());
    assertEquals(255, checker.getPixels()[0][3].getGreen());

    assertEquals(255, checker.getPixels()[2][0].getBlue());
    assertEquals(255, checker.getPixels()[2][0].getRed());
    assertEquals(255, checker.getPixels()[2][0].getGreen());

    assertEquals(255, checker.getPixels()[2][1].getBlue());
    assertEquals(255, checker.getPixels()[2][1].getRed());
    assertEquals(255, checker.getPixels()[2][1].getGreen());

    assertEquals(0, checker.getPixels()[2][2].getBlue());
    assertEquals(0, checker.getPixels()[2][2].getRed());
    assertEquals(0, checker.getPixels()[2][2].getGreen());

    assertEquals(0, checker.getPixels()[2][3].getBlue());
    assertEquals(0, checker.getPixels()[2][3].getRed());
    assertEquals(0, checker.getPixels()[2][3].getGreen());
  }

  @Test
  public void testEquals() {
    assertTrue(this.checker.equalImages(this.checker));
    assertTrue(
        this.checker.equalImages(new ImageModel().getCheckerboard(2, 4, Color.BLACK, Color.WHITE)));
    assertFalse(this.checker.equalImages(new ImageModel()
        .getRainbow(5, 1, new ArrayList<>(Arrays.asList(Color.RED, Color.GREEN, Color.BLUE)))));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testEqualsNull() {
    this.checker.equalImages(null);
  }
}