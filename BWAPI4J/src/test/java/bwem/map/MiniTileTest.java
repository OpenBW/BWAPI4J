package bwem.map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openbw.bwapi4j.BW;

public class MiniTileTest {

	@BeforeClass 
    public static void setUpClass() {
        
    }
    
    @Test
    public void heightTest() throws AssertionError {
    	
    	BW bw = new BWMock();
    	Map map = new MapImpl(bw);
    	map.Initialize();
    	
    	Assert.assertEquals("mini tile altitude is wrong.", 193, map.GetMiniTile(null).Altitude().intValue());
    }
}
