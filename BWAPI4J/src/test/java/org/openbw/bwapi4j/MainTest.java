package org.openbw.bwapi4j;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openbw.bwapi4j.unit.PlayerUnit;
import org.openbw.bwapi4j.unit.Unit;

public class MainTest implements BWEventListener {

    private static final Logger logger = LogManager.getLogger();
    
    private BW bw;
    
    @BeforeClass 
    public static void setUpClass() {
        
    }
    
    @Test
    public void smokeTest() throws AssertionError {
        
        BW bw = new BW(this);
        this.bw = bw;
        bw.startGame();
        
    }

    private void testMapInfo() throws AssertionError {
    
        BWMap map = bw.getBWMap();
        assertEquals("test message", true, true);
        assertEquals("map name wrong.", "(4)Fighting Spirit.scx", map.mapFileName());
    }
    
    private void testNumberOfScvs() throws AssertionError {
    
    	Player self = this.bw.getInteractionHandler().self();
    	
    	List<PlayerUnit> units = this.bw.getUnits(self);
    	for (PlayerUnit unit : units) {
    		System.out.println(unit);
    	}
    }
    
    @Override
    public void onStart() throws AssertionError {
        
        logger.info("onStart");
        testMapInfo();
        
        testNumberOfScvs();
        
        this.bw.getInteractionHandler().leaveGame();
        logger.info("left game.");
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
