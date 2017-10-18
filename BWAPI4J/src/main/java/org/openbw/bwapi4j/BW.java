package org.openbw.bwapi4j;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.unit.MineralPatch;
import org.openbw.bwapi4j.unit.PlayerUnit;
import org.openbw.bwapi4j.unit.Unit;
import org.openbw.bwapi4j.unit.UnitFactory;
import org.openbw.bwapi4j.unit.VespeneGeyser;

public class BW {

    private static final Logger logger = LogManager.getLogger();

    public static int TILE_SIZE = TilePosition.SIZE_IN_PIXELS;
    public static int WALK_SIZE = WalkPosition.SIZE_IN_PIXELS;
    
    private BWEventListener listener;
    private InteractionHandler interactionHandler;
    private MapDrawer mapDrawer;
    private DamageEvaluator damageEvaluator;
    private BWMap bwMap;

    private Map<Integer, Player> players;
    private Map<Integer, Unit> units;
    private UnitFactory unitFactory;
    private int frame;
    private Charset charset;

    static {
        
        String libraryPathProperty = System.getProperty("java.library.path");
        if (libraryPathProperty == null || libraryPathProperty == "") {
            
            logger.warn("library path not set, using CWD as default.");
            System.setProperty("java.library.path", "./");
        } else {
            
            logger.debug("library path set to {}", libraryPathProperty);
        }

        logger.debug("DLL exists: {}",
                new File(System.getProperty("java.library.path") + "\\BWAPI4JBridge.dll").exists());
        logger.debug("SO exists: {}",
                new File(System.getProperty("java.library.path") + "/libOpenBWAPI4JBridge.so").exists());
        logger.debug("user directory: {}", System.getProperty("user.dir"));

        //System.loadLibrary("bwta2");
        System.loadLibrary("OpenBWAPI4JBridge");
        
        logger.debug("DLL/SO loaded.");
    }

    /**
     * Creates a BW instance required to start a game.
     * @param listener listener to inform of various game events
     */
    public BW(BWEventListener listener) {

        this.players = new HashMap<Integer, Player>();
        this.units = new HashMap<Integer, Unit>();
        this.listener = listener;
        this.unitFactory = new UnitFactory(this);
        this.interactionHandler = new InteractionHandler(this);
        this.mapDrawer = new MapDrawer();
        this.damageEvaluator = new DamageEvaluator();
        this.bwMap = new BWMap();
        this.bwMap.setUnits(this.units);

        try {
            charset = Charset.forName("Cp949"); // Korean char set
        } catch (UnsupportedCharsetException e) {
            logger.warn("Korean character set not available. Some characters may not be read properly.");
            charset = StandardCharsets.ISO_8859_1;
        }
    }

    public void startGame() {
        
    	BW myBw = this;
    	Thread thread = new Thread(new Runnable() {

    	    @Override
    	    public void run() {
    	    
    	    	startGame(myBw);
    	    }
    	            
    	});
    	        
    	thread.start();
        mainThread();
    }

    private native void mainThread();
    
    private native void startGame(BW bw);

    private native int[] getAllUnitsData();

    private native int[] getAllPlayersData();

    private native int[] getGameData();

    private native int getClientVersion();

    private native String getPlayerName(int playerId);

    private native int[] getResearchStatus(int playerId);

    private native int[] getUpgradeStatus(int playerId);

    public void setUnitFactory(UnitFactory unitFactory) {
        
        this.unitFactory = unitFactory;
    }

    public BWMap getBWMap() {
        
        return this.bwMap;
    }

    public MapDrawer getMapDrawer() {
        
        return this.mapDrawer;
    }

    public DamageEvaluator getDamageEvaluator() {
        
        return this.damageEvaluator;
    }

    public InteractionHandler getInteractionHandler() {
        
        return this.interactionHandler;
    }

    private void updateGame() {

        int[] data = this.getGameData();
        this.interactionHandler.update(data);
    }

    private void updateAllUnits(int frame) {

        int[] unitData = this.getAllUnitsData();

        for (int index = 0; index < unitData.length; index += Unit.TOTAL_PROPERTIES) {

            int unitId = unitData[index + 0];
            int typeId = unitData[index + 3];
            Unit unit = this.units.get(unitId);
            if (unit == null || !unit.getInitialType().equals(UnitType.values()[typeId])) {
                
            	if (unit != null) {
            		
            		logger.debug("unit {} changed type from {} to {}.", unit.getId(), unit.getInitialType(), UnitType.values()[typeId]);
            	}
                logger.debug("creating unit for id {} and type {} ({}) ...", unitId, typeId, UnitType.values()[typeId]);
                
                unit = unitFactory.createUnit(unitId, UnitType.values()[typeId], frame);
                
                if (unit == null) {
                    logger.error("could not create unit for id {} and type {}.", unitId, UnitType.values()[typeId]);
                } else {
                    
                	logger.trace("state: {}", unit.exists() ? "completed" : "created");
                	
                    this.units.put(unitId, unit);
                    unit.initialize(unitData, index);
                    unit.update(unitData, index);
                    logger.trace("initial pos: {}", unit.getInitialTilePosition());
                    logger.trace("current pos: {}", unit.getTilePosition());
                    
                    logger.debug(" done.");
                }
            } else {
                
                unit.update(unitData, index);
            }
        }
    }

    private void updateAllPlayers() {

        int[] playerData = this.getAllPlayersData();

        for (int index = 0; index < playerData.length; index += Player.TOTAL_PROPERTIES) {

            int playerId = playerData[index + 0];
            Player player = this.players.get(playerId);
            if (player == null) {

                logger.debug("creating player for id {} ...", playerId);
                player = new Player(playerId, this.getPlayerName(playerId));
                logger.debug("player name: {}", player.getName());
                this.players.put(playerId, player);
                player.initialize(playerData, index);
                logger.debug(" done.");
            }
            player.update(playerData, index, this.getResearchStatus(playerId), this.getUpgradeStatus(playerId));
        }
    }

    public Player getPlayer(int id) {
        
        return this.players.get(id);
    }

    public Collection<Player> getAllPlayers() {
        
        return this.players.values();
    }

    public Unit getUnit(int id) {
    
        return this.units.get(id);
    }
    
    /**
     * Gets all units for given player.
     * @param player player whose units to return
     * @return list of <code>PlayerUnit</code>
     */
    public List<PlayerUnit> getUnits(Player player) {
        
        return this.units.values().stream().filter(u -> u instanceof PlayerUnit 
                && ((PlayerUnit)u).getPlayer().equals(player)).map(u -> (PlayerUnit)u).collect(Collectors.toList());
    }
    
    /**
     * Gets a list of all mineral patches.
     * @return list of mineral patches
     */
    public List<MineralPatch> getMineralPatches() {
        
        return this.units.values().stream().filter(u -> u instanceof MineralPatch)
                .map(u -> (MineralPatch)u).collect(Collectors.toList());
    }
    
    /**
     * Gets a list of all vespene geysers.
     * @return list of vespene geysers
     */
    public List<VespeneGeyser> getVespeneGeysers() {
    
        return this.units.values().stream().filter(u -> u instanceof VespeneGeyser)
                .map(u -> (VespeneGeyser)u).collect(Collectors.toList());
    }
    
    public Collection<Unit> getAllUnits() {
        
        return this.units.values();
    }

    private void preFrame() {
        
        logger.debug("updating game state for frame {}...", this.frame);
        updateGame();
        logger.debug("updated game.");
        updateAllPlayers();
        logger.debug("updated players.");
        updateAllUnits(this.frame);
        logger.debug("updated all units.");
    }
    
    private void onStart() {

        this.frame = 0;
        this.players.clear();
        this.units.clear();
        
        preFrame();
        listener.onStart();
    }

    private void onEnd(boolean isWinner) {

        listener.onEnd(isWinner);
    }

    private void onFrame() {

    	logger.debug("onFrame {}", this.frame);
    	preFrame();
    	this.frame++;
        listener.onFrame();
    }

    private void onSendText(String text) {

        listener.onSendText(text);
    }

    private void onReceiveText(int playerId, String text) {

        Player player = this.players.get(playerId);
        listener.onReceiveText(player, text);
    }

    private void onPlayerLeft(int playerId) {

        Player player = this.players.get(playerId);
        listener.onPlayerLeft(player);
    }

    private void onNukeDetect(int x, int y) {

        listener.onNukeDetect(new Position(x, y));
    }

    private void onUnitDiscover(int unitId) {

        Unit unit = this.units.get(unitId);
        if (unit == null) {
            logger.error("onUnitDiscover: no unit found for ID {}.", unitId);
        }
        listener.onUnitDiscover(unit);
    }

    private void onUnitEvade(int unitId) {

        Unit unit = this.units.get(unitId);
        if (unit == null) {
            logger.error("onUnitEvade: no unit found for ID {}.", unitId);
        }
        listener.onUnitEvade(unit);
    }

    private void onUnitShow(int unitId) {

        Unit unit = this.units.get(unitId);
        if (unit == null) {
            logger.error("onUnitShow: no unit found for ID {}.", unitId);
        }
        listener.onUnitShow(unit);
    }

    private void onUnitHide(int unitId) {

        Unit unit = this.units.get(unitId);
        if (unit == null) {
            logger.error("onUnitHide: no unit found for ID {}.", unitId);
        }
        listener.onUnitHide(unit);
    }

    private void onUnitCreate(int unitId) {

        Unit unit = this.units.get(unitId);
        if (unit == null) {
            logger.error("onUnitCreate: no unit found for ID {}.", unitId);
        }
        listener.onUnitCreate(unit);
    }

    private void onUnitDestroy(int unitId) {

        Unit unit = this.units.get(unitId);
        if (unit == null) {
            logger.error("onUnitDestroy: no unit found for ID {}.", unitId);
        }
        listener.onUnitDestroy(unit);
    }

    private void onUnitMorph(int unitId) {

        Unit unit = this.units.get(unitId);
        if (unit == null) {
            logger.error("onUnitMorph: no unit found for ID {}.", unitId);
        }
        listener.onUnitMorph(unit);
    }

    private void onUnitRenegade(int unitId) {

        Unit unit = this.units.get(unitId);
        if (unit == null) {
            logger.error("onUnitRenegade: no unit found for ID {}.", unitId);
        }
        listener.onUnitRenegade(unit);
    }

    private void onSaveGame(String gameName) {

        listener.onSaveGame(gameName);
    }

    private void onUnitComplete(int unitId) {

        Unit unit = this.units.get(unitId);
        if (unit == null) {
            logger.error("onUnitComplete: no unit found for ID {}.", unitId);
        }
        listener.onUnitComplete(unit);
    }
}
