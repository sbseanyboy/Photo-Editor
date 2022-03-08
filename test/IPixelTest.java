import static org.junit.Assert.assertEquals;

import model.IPixel;
import model.Pixel;
import org.junit.Test;

/**
 * testing class for model.IPixel.
 */
public class IPixelTest {
  IPixel black = new Pixel(0, 0, 0);
  IPixel white = new Pixel(255, 255, 255);
  IPixel red = new Pixel(255, 0, 0);
  IPixel green = new Pixel(0, 255, 0);
  IPixel blue = new Pixel(0, 0, 255);

  @Test
  public void testPixel() {
    assertEquals(0, black.getRed());
    assertEquals(0, black.getGreen());
    assertEquals(0, black.getBlue());
    assertEquals(255, white.getRed());
    assertEquals(255, white.getGreen());
    assertEquals(255, white.getBlue());
    assertEquals(255, red.getRed());
    assertEquals(0, red.getGreen());
    assertEquals(0, red.getBlue());
    assertEquals(0, green.getRed());
    assertEquals(255, green.getGreen());
    assertEquals(0, green.getBlue());
    assertEquals(0, blue.getRed());
    assertEquals(0, blue.getGreen());
    assertEquals(255, blue.getBlue());
  }

  @Test
  public void testCap() {
    Pixel capMax = new Pixel(256, 256, 256);
    Pixel capMin = new Pixel(-1, -1, -1);
    assertEquals(255, capMax.getBlue());
    assertEquals(255, capMax.getRed());
    assertEquals(255, capMax.getGreen());
    assertEquals(0, capMin.getBlue());
    assertEquals(0, capMin.getRed());
    assertEquals(0, capMin.getGreen());
  }
}