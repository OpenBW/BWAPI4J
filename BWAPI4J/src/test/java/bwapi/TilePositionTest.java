package bwapi;

import java.util.HashMap;
import org.junit.Assert;
import org.junit.Test;

public class TilePositionTest {
  private final int[] TILE_POSITION_X_ARR = {
    0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,
    26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49,
    50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73,
    74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97,
    98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116,
    117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 128, 129, 130, 131, 132, 133, 134, 135,
    136, 137, 138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148, 149, 150, 151, 152, 153, 154,
    155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170, 171, 172, 173,
    174, 175, 176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191, 192,
    193, 194, 195, 196, 197, 198, 199, 200, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211,
    212, 213, 214, 215, 216, 217, 218, 219, 220, 221, 222, 223, 224, 225, 226, 227, 228, 229, 230,
    231, 232, 233, 234, 235, 236, 237, 238, 239, 240, 241, 242, 243, 244, 245, 246, 247, 248, 249,
    250, 251, 252, 253, 254, 255
  };
  private final int[] TILE_POSITION_Y_ARR = {
    0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,
    26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49,
    50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73,
    74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97,
    98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116,
    117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 128, 129, 130, 131, 132, 133, 134, 135,
    136, 137, 138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148, 149, 150, 151, 152, 153, 154,
    155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170, 171, 172, 173,
    174, 175, 176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191, 192,
    193, 194, 195, 196, 197, 198, 199, 200, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211,
    212, 213, 214, 215, 216, 217, 218, 219, 220, 221, 222, 223, 224, 225, 226, 227, 228, 229, 230,
    231, 232, 233, 234, 235, 236, 237, 238, 239, 240, 241, 242, 243, 244, 245, 246, 247, 248, 249,
    250, 251, 252, 253, 254, 255
  };

  private void testConversionFactors() {
    Assert.assertEquals(32, TilePosition.SIZE_IN_PIXELS);
    Assert.assertEquals(8, WalkPosition.SIZE_IN_PIXELS);
  }

  @Test
  public void testCasting() {
    testConversionFactors();

    for (int y = 0; y < 256; ++y) {
      for (int x = 0; x < 256; ++x) {
        final TilePosition t = new TilePosition(x, y);
        Assert.assertEquals(
            "TilePosition: wrong value for 'x': " + t.toString(), TILE_POSITION_X_ARR[x], t.getX());
        Assert.assertEquals(
            "TilePosition: wrong value for 'y': " + t.toString(), TILE_POSITION_Y_ARR[y], t.getY());

        final Position p = t.toPosition();
        Assert.assertEquals(
            "Position: wrong value for 'x': " + p.toString(),
            TILE_POSITION_X_ARR[x] * TilePosition.SIZE_IN_PIXELS,
            p.getX());
        Assert.assertEquals(
            "Position: wrong value for 'y': " + p.toString(),
            TILE_POSITION_Y_ARR[y] * TilePosition.SIZE_IN_PIXELS,
            p.getY());

        final WalkPosition w = t.toWalkPosition();
        Assert.assertEquals(
            "WalkPosition: wrong value for 'x': " + w.toString(),
            (TILE_POSITION_X_ARR[x] * TilePosition.SIZE_IN_PIXELS) / WalkPosition.SIZE_IN_PIXELS,
            w.getX());
        Assert.assertEquals(
            "WalkPosition: wrong value for 'y': " + w.toString(),
            (TILE_POSITION_Y_ARR[y] * TilePosition.SIZE_IN_PIXELS) / WalkPosition.SIZE_IN_PIXELS,
            w.getY());
      }
    }
  }

  private void testAddition() {
    Assert.assertEquals(
        "wrong result", new TilePosition(0, 0), new TilePosition(0, 0).add(new TilePosition(0, 0)));
    Assert.assertEquals(
        "wrong result", new TilePosition(1, 1), new TilePosition(0, 0).add(new TilePosition(1, 1)));
    Assert.assertEquals(
        "wrong result", new TilePosition(5, 5), new TilePosition(0, 0).add(new TilePosition(5, 5)));
    Assert.assertEquals(
        "wrong result",
        new TilePosition(245, 157),
        new TilePosition(15, 48).add(new TilePosition(230, 109)));
  }

  private void testSubtraction() {
    Assert.assertEquals(
        "wrong result",
        new TilePosition(0, 0),
        new TilePosition(0, 0).subtract(new TilePosition(0, 0)));
    Assert.assertEquals(
        "wrong result",
        new TilePosition(1, 1),
        new TilePosition(1, 1).subtract(new TilePosition(0, 0)));
    Assert.assertEquals(
        "wrong result",
        new TilePosition(5, 5),
        new TilePosition(5, 5).subtract(new TilePosition(0, 0)));
    Assert.assertEquals(
        "wrong result",
        new TilePosition(15, 48),
        new TilePosition(245, 157).subtract(new TilePosition(230, 109)));
  }

  @Test
  public void testOperations() {
    testAddition();
    testSubtraction();
  }

  @Test
  public void testEquals() {
    {
      TilePosition a1 = new TilePosition(0, 0);
      TilePosition a2 = new TilePosition(0, 0);
      Assert.assertTrue(a1.equals(a2) && a2.equals(a1));
    }

    {
      TilePosition b1 = new TilePosition(27, 98);
      TilePosition b2 = new TilePosition(27, 98);
      Assert.assertTrue(b1.equals(b2) && b2.equals(b1));
    }

    {
      TilePosition c1 = new TilePosition(114, 8);
      TilePosition c2 = new TilePosition(114, 8);
      Assert.assertTrue(c1.equals(c2) && c2.equals(c1));
    }

    Assert.assertTrue((new TilePosition(0, 0).equals(new TilePosition(0, 0))));
    Assert.assertTrue((new TilePosition(1, 1).equals(new TilePosition(1, 1))));
    Assert.assertTrue((new TilePosition(127, 37).equals(new TilePosition(127, 37))));

    for (int y = 0; y < 256; ++y) {
      for (int x = 0; x < 256; ++x) {
        TilePosition t1 = new TilePosition(x, y);
        TilePosition t2 = new TilePosition(x, y);
        Assert.assertTrue(t1.equals(t2) && t2.equals(t1));
      }
    }
  }

  @Test
  public void testHashCode() {
    Assert.assertEquals(new TilePosition(0, 0).hashCode(), new TilePosition(0, 0).hashCode());
    Assert.assertEquals(new TilePosition(3, 87).hashCode(), new TilePosition(3, 87).hashCode());
    Assert.assertEquals(new TilePosition(112, 6).hashCode(), new TilePosition(112, 6).hashCode());

    HashMap<TilePosition, Integer> map = new HashMap<>();
    int index = 0;
    for (int y = 0; y < 256; ++y) {
      for (int x = 0; x < 256; ++x) {
        map.put(new TilePosition(x, y), index++);
      }
    }
    Assert.assertEquals(map.get(new TilePosition(0, 0)).intValue(), 0);
    Assert.assertEquals(map.get(new TilePosition(3, 87)).intValue(), 22275);
    Assert.assertEquals(map.get(new TilePosition(112, 6)).intValue(), 1648);
    Assert.assertEquals(map.get(new TilePosition(128, 136)).intValue(), 34944);
    Assert.assertEquals(map.get(new TilePosition(37, 255)).intValue(), 65317);
    Assert.assertEquals(map.get(new TilePosition(255, 37)).intValue(), 9727);
    Assert.assertEquals(map.get(new TilePosition(136, 128)).intValue(), 32904);
    Assert.assertEquals(map.get(new TilePosition(255, 255)).intValue(), 65535);
  }
}
