package org.openbw.bwapi4j;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.unit.MineralPatch;
import org.openbw.bwapi4j.unit.PlayerUnit;
import org.openbw.bwapi4j.unit.SCV;
import org.openbw.bwapi4j.unit.Unit;

public class MainTest implements BWEventListener {

    private static final Logger logger = LogManager.getLogger();
    
    private BW bw;
    
    @BeforeClass 
    public static void setUpClass() {
        
    }
    
    @Test
    public void smokeTest() throws AssertionError {
        
    	logger.info("test start.");
        BW bw = new BW(this);
        this.bw = bw;
        bw.startGame();
        logger.info("test done.");
    }

    private void testMapInfo() throws AssertionError {
    
        BWMap map = bw.getBWMap();
        assertEquals("map name wrong.", "(4)Fighting Spirit.scx", map.mapFileName());
    }
    
    private void testNumberOfScvs() throws AssertionError {
    
    	Player self = this.bw.getInteractionHandler().self();
    	
    	List<PlayerUnit> units = this.bw.getUnits(self);
    	for (PlayerUnit unit : units) {
    		logger.debug(unit);
    	}
    	assertEquals("wrong number of units.", 5, units.size());
    }
    
    private void testMineralMining() throws AssertionError {
    	
    	boolean commandSuccessful = false;
    	Player self = this.bw.getInteractionHandler().self();
    	
    	Collection<Unit> allUnits = this.bw.getAllUnits();
    	MineralPatch patch = null;
    	for (Unit unit : allUnits) {
    		if (unit instanceof MineralPatch) {
    			patch = (MineralPatch) unit;
    		}
    	}
    	List<PlayerUnit> units = this.bw.getUnits(self);
    	SCV scv = null;
    	for (PlayerUnit unit : units) {
    		if (unit instanceof SCV) {
    			scv = (SCV) unit;
    		}
    	}
    	if (patch != null && scv != null) {
    		
    		commandSuccessful = scv.gather(patch);
    	} else {
    		
    		logger.error("no scv and patch found.");
    	}
    	assertTrue("gather command failed.", commandSuccessful);
    }
    
    @Override
    public void onStart() {
        
        logger.info("onStart");
        testMapInfo();
        testNumberOfScvs();
        testMineralMining();
        
        int damage = this.bw.getDamageEvaluator().getDamageFrom(UnitType.Terran_Marine, UnitType.Terran_SCV);
        assertEquals("damage evaluator wrong.", 6, damage);
        
        this.bw.getInteractionHandler().leaveGame();
        logger.info("left game.");
    }

    @Override
    public void onEnd(boolean isWinner) {
        
    	logger.info("onEnd");
    }

    @Override
    public void onFrame() {
        
        logger.info("onFrame");
    }

    @Override
    public void onSendText(String text) {
    	// do nothing
        
    }

    @Override
    public void onReceiveText(Player player, String text) {
    	// do nothing
        
    }

    @Override
    public void onPlayerLeft(Player player) {
    	// do nothing
        
    }

    @Override
    public void onNukeDetect(Position target) {
    	// do nothing
        
    }

    @Override
    public void onUnitDiscover(Unit unit) {
    	// do nothing
        
    }

    @Override
    public void onUnitEvade(Unit unit) {
    	// do nothing
        
    }

    @Override
    public void onUnitShow(Unit unit) {
    	// do nothing
        
    }

    @Override
    public void onUnitHide(Unit unit) {
    	// do nothing
        
    }

    @Override
    public void onUnitCreate(Unit unit) {
    	
//    	logger.info("onUnitCreate");
    }

    @Override
    public void onUnitDestroy(Unit unit) {
    	// do nothing
        
    }

    @Override
    public void onUnitMorph(Unit unit) {
    	// do nothing
        
    }

    @Override
    public void onUnitRenegade(Unit unit) {
    	// do nothing
        
    }

    @Override
    public void onSaveGame(String gameName) {
    	// do nothing
        
    }

    @Override
    public void onUnitComplete(Unit unit) {
        
//    	logger.info("onUnitComplete");
    }
}
