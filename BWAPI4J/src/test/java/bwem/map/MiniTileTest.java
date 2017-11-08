package bwem.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openbw.bwapi4j.BW;
import org.openbw.bwapi4j.BWEventListener;
import org.openbw.bwapi4j.BWMap;
import org.openbw.bwapi4j.Player;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.WalkPosition;
import org.openbw.bwapi4j.unit.MineralPatch;
import org.openbw.bwapi4j.unit.Unit;
import org.openbw.bwapi4j.unit.VespeneGeyser;

import bwem.BWEM;

public class MiniTileTest implements BWEventListener {

	private int[] results1 = {8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	private int[] results2 = {8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	private int[] results3 = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	private int[] results4 = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8,8};
	private int[] results5 = {8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8};
	private int[] results6 = {8,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16};

	private BW bw;
	
	@BeforeClass
    public static void setUpClass() {

    }

	@Test
    public void heightTestReal() throws AssertionError {
		
		this.bw = new BW(this);
		bw.startGame();
	}
	
    @Test
    public void heightTest() throws AssertionError {

    	BWMap mapMock = new BWMapMock();
    	Collection<Player> players = new ArrayList<>();
    	List<MineralPatch> mineralPatches = new ArrayList<>();
    	List<VespeneGeyser> geysers = new ArrayList<>();
    	List<Unit> units = new ArrayList<>();
    	
    	Map map = new MapImpl(mapMock, null, players, mineralPatches, geysers, units);
    	map.Initialize();

    	for (int i = 0; i < results1.length; i++) {
    		if (map.GetMiniTile(new WalkPosition(i, 0)).Altitude().intValue() != results1[i]) {
    			System.out.println(i + ": should be " + results1[i] + " but was " + map.GetMiniTile(new WalkPosition(i, 0)).Altitude().intValue());
    		}
//    		Assert.assertEquals("mini tile altitude is wrong (" + i + ").", results1[i], map.GetMiniTile(new WalkPosition(i, 0)).Altitude().intValue());
    	}
    	Assert.assertEquals("mini tile altitude is wrong.", 243, map.GetMiniTile(new WalkPosition(148, 149)).Altitude().intValue());
        Assert.assertEquals("mini tile altitude is wrong.", 198, map.GetMiniTile(new WalkPosition(273, 260)).Altitude().intValue());
        Assert.assertEquals("mini tile altitude is wrong.", 192, map.GetMiniTile(new WalkPosition(273, 261)).Altitude().intValue());
    }

	@Override
	public void onStart() {
		
		Map map = new BWEM(this.bw).GetMap();
    	map.Initialize();

    	for (int i = 0; i < results1.length; i++) {
    		if (map.GetMiniTile(new WalkPosition(i, 0)).Altitude().intValue() != results1[i]) {
    			System.out.println(i + ": should be " + results1[i] + " but was " + map.GetMiniTile(new WalkPosition(i, 0)).Altitude().intValue());
    		} else {
    			System.out.println("OK");
    		}
    	}
    	
    	this.bw.exit();
        this.bw.getInteractionHandler().leaveGame();
	}

	@Override
	public void onEnd(boolean isWinner) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFrame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSendText(String text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReceiveText(Player player, String text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPlayerLeft(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNukeDetect(Position target) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUnitDiscover(Unit unit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUnitEvade(Unit unit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUnitShow(Unit unit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUnitHide(Unit unit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUnitCreate(Unit unit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUnitDestroy(Unit unit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUnitMorph(Unit unit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUnitRenegade(Unit unit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSaveGame(String gameName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUnitComplete(Unit unit) {
		// TODO Auto-generated method stub
		
	}
}
