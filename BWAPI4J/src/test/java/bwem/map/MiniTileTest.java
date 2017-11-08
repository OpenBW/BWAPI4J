package bwem.map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openbw.bwapi4j.BW;
import org.openbw.bwapi4j.WalkPosition;

public class MiniTileTest {

	@BeforeClass
    public static void setUpClass() {

    }

    @Test
    public void heightTest() throws AssertionError {

    	BW bw = new BWMock();
    	Map map = new MapImpl(bw);
    	map.Initialize();

        Assert.assertEquals("mini tile altitude is wrong.", 243, map.GetMiniTile(new WalkPosition(248, 249)).Altitude().intValue());
        Assert.assertEquals("mini tile altitude is wrong.", 198, map.GetMiniTile(new WalkPosition(273, 260)).Altitude().intValue());
        Assert.assertEquals("mini tile altitude is wrong.", 192, map.GetMiniTile(new WalkPosition(273, 261)).Altitude().intValue());
    }
}
