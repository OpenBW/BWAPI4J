package bwem.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openbw.bwapi4j.BWMap;
import org.openbw.bwapi4j.Player;
import org.openbw.bwapi4j.WalkPosition;
import org.openbw.bwapi4j.unit.MineralPatch;
import org.openbw.bwapi4j.unit.PlayerUnit;
import org.openbw.bwapi4j.unit.VespeneGeyser;

public class MiniTileTest {

	@BeforeClass
    public static void setUpClass() {

    }

    @Test
    public void heightTest() throws AssertionError {

    	BWMap mapMock = new BWMapMock();
    	Collection<Player> players = new ArrayList<>();
    	List<MineralPatch> mineralPatches = new ArrayList<>();
    	List<VespeneGeyser> geysers = new ArrayList<>();
    	List<PlayerUnit> playerUnits = new ArrayList<>();
    	
    	Map map = new MapImpl(mapMock, null, players, mineralPatches, geysers, playerUnits);
    	map.Initialize();

        Assert.assertEquals("mini tile altitude is wrong.", 243, map.GetMiniTile(new WalkPosition(248, 249)).Altitude().intValue());
        Assert.assertEquals("mini tile altitude is wrong.", 198, map.GetMiniTile(new WalkPosition(273, 260)).Altitude().intValue());
        Assert.assertEquals("mini tile altitude is wrong.", 192, map.GetMiniTile(new WalkPosition(273, 261)).Altitude().intValue());
    }
}
