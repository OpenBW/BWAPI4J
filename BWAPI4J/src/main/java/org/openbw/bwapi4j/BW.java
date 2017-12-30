package org.openbw.bwapi4j;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.unit.MineralPatch;
import org.openbw.bwapi4j.unit.PlayerUnit;
import org.openbw.bwapi4j.unit.SCV;
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
    private BWMapImpl bwMap;

    private Map<Integer, Player> players;
    private Map<Integer, Unit> units;
    private Map<Integer, Bullet> bullets;
    private UnitFactory unitFactory;
    private int frame;
    private Charset charset;

    private boolean onStartInitializationDone;
    
    /**
     * Creates a BW instance required to start a game.
     * @param listener listener to inform of various game events
     */
    public BW(BWEventListener listener) {

    	loadDLL();
    	
        this.players = new HashMap<Integer, Player>();
        this.units = new ConcurrentHashMap<Integer, Unit>();
        this.bullets = new ConcurrentHashMap<Integer, Bullet>();
        this.listener = listener;
        this.interactionHandler = new InteractionHandler(this);
        this.mapDrawer = new MapDrawer();
        this.damageEvaluator = new DamageEvaluator();
        this.bwMap = new BWMapImpl();
        setUnitFactory(new UnitFactory());
        
        try {
            charset = Charset.forName("Cp949"); // Korean char set
        } catch (UnsupportedCharsetException e) {
            logger.warn("Korean character set not available. Some characters may not be read properly.");
            charset = StandardCharsets.ISO_8859_1;
        }
    }

    private void loadDLL() {
    	
    	logger.info("jvm: {} ({}bit).", System.getProperty("java.version"), System.getProperty("sun.arch.data.model") );
        logger.info("os: {}", System.getProperty("os.name"));

    	logger.debug("user directory: {}", System.getProperty("user.dir"));
        logger.debug("bot directory: {}", BW.class.getProtectionDomain().getCodeSource().getLocation().getPath());

    	String libraryPathProperty = System.getProperty("java.library.path");

        boolean dllExists = new File(libraryPathProperty + "\\BWAPI4JBridge.dll").exists();
        boolean soExists = new File(libraryPathProperty + "/libOpenBWAPI4JBridge.so").exists();
        
        logger.debug("DLL exists: {}", dllExists);
        logger.debug("SO exists: {}", soExists);
        
        if (!(dllExists || soExists)) {
        	
        	logger.info("Could not find libraries on path {}", System.getProperty("java.library.path"));

        	try {
				
				java.lang.reflect.Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
				fieldSysPath.setAccessible(true);
	            fieldSysPath.set(null, null);
	            
	            String path = BW.class.getProtectionDomain().getCodeSource().getLocation().getPath();
                String decodedPath = java.net.URLDecoder.decode(path, "UTF-8");
                File file = new File(decodedPath);
                
                System.setProperty("java.library.path", file.getParent());
                logger.info("Changed library path to {}", System.getProperty("java.library.path"));
			} catch (Exception e) {
				logger.error("Could not modify library path.", e);
				e.printStackTrace();
			}
        }
        
        
        /* this is pretty hacky but required for now to run BWAPI4J on both Windows and Linux without modifying the source.
         *
         * Possible future solutions:
         *  - name BWAPI4JBridge and OpenBWAPI4JBridge the same. This way linux loads <name>.so and windows loads <name>.dll
         *  - build a single bwta.dll rather than libgmp-10 and libmpfr-4 and load bwta.so accordingly on linux.
         */
        try {
	        if (System.getProperty("os.name").contains("Windows")) {
		        System.loadLibrary("libgmp-10");
		        System.loadLibrary("libmpfr-4");
		        System.loadLibrary("BWAPI4JBridge");
		        logger.debug("DLLs successfully loaded.");
	        } else {
	        	System.loadLibrary("OpenBWAPI4JBridge");
	        }
	        logger.debug("SO successfully loaded.");
        } catch (UnsatisfiedLinkError e) {
        	
        	logger.fatal("Could not load libraries.", e);
        	e.printStackTrace();
        	System.exit(1);
        }
    }
    
    public void startGame() {

    	this.onStartInitializationDone = false;
    	BW myBw = this;
    	Thread thread = new Thread(new Runnable() {

    	    @Override
    	    public void run() {

    	    	startGame(myBw);
    	    }

    	});

    	thread.start();
    	logger.trace("calling native mainThread...");
        mainThread();
        try {
			thread.join();
		} catch (InterruptedException e) {
			logger.error("error joining thread.", e);
			e.printStackTrace();
		}
        logger.trace("finished thread.");
    }

    public void createUnit(Player owner, UnitType type, int posX, int posY) {

    	this.createUnit(owner.getId(), type.getId(), posX, posY);
    }

    private native void createUnit(int ownerId, int unitTypeId, int posX, int posY);

    public void killUnit(Unit unit) {

    	this.killUnit(unit.getId());
    }

    private native void killUnit(int unitID);

    public native void exit();

    private native void mainThread();

    private native void startGame(BW bw);

    private native int[] getAllUnitsData();

    private native int[] getAllBulletsData();

    private native int[] getAllPlayersData();

    private native int[] getGameData();

    private native int getClientVersion();

    private native String getPlayerName(int playerId);

    private native int[] getResearchStatus(int playerId);

    private native int[] getUpgradeStatus(int playerId);

    public void setUnitFactory(UnitFactory unitFactory) {

        this.unitFactory = unitFactory;
        this.unitFactory.setBW(this);
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

    private void updateAllBullets() {
    	
    	int[] bulletData = this.getAllBulletsData();
    	
    	for (int index = 0; index < bulletData.length; index += Bullet.TOTAL_PROPERTIES) {
    		
    		int bulletId = bulletData[index + Bullet.ID_INDEX];
    		Bullet bullet = this.bullets.get(bulletId);
    		if (bullet == null) {
    			bullet = new Bullet(this);
    			this.bullets.put(bulletId, bullet);
    			bullet.initialize(bulletData, index);
    		}
    		bullet.update(bulletData, index);
    	}
    }
    
    private boolean typeChanged(UnitType oldType, UnitType newType) {
    	
    	return !oldType.equals(newType) && !oldType.equals(UnitType.Terran_Siege_Tank_Siege_Mode) && !newType.equals(UnitType.Terran_Siege_Tank_Siege_Mode);
    }
    
    private void updateAllUnits(int frame) {

    	for (Unit unit : this.units.values()) {
    		unit.setExists(false);
    	}
        int[] unitData = this.getAllUnitsData();

        for (int index = 0; index < unitData.length; index += Unit.TOTAL_PROPERTIES) {

            int unitId = unitData[index + 0];
            int typeId = unitData[index + 3];
            Unit unit = this.units.get(unitId);
            if (unit == null || typeChanged(unit.getInitialType(), UnitType.values()[typeId])) {

            	if (unit != null) {

            		logger.debug("unit {} changed type from {} to {}.", unit.getId(), unit.getInitialType(), UnitType.values()[typeId]);
            	}
                logger.trace("creating unit for id {} and type {} ({}) ...", unitId, typeId, UnitType.values()[typeId]);

                unit = unitFactory.createUnit(unitId, UnitType.values()[typeId], frame);

                if (unit == null) {
                    logger.error("could not create unit for id {} and type {}.", unitId, UnitType.values()[typeId]);
                } else {

                	logger.trace("state: {}", unit.exists() ? "completed" : "created");

                    this.units.put(unitId, unit);
                    unit.initialize(unitData, index);
                    unit.update(unitData, index, frame);
                    logger.trace("initial pos: {}", unit.getInitialTilePosition());
                    logger.trace("current pos: {}", unit.getTilePosition());

                    logger.trace(" done.");
                }
            } else {

                unit.update(unitData, index, frame);
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
                player = new Player(playerId, this.getPlayerName(playerId), this);
                logger.trace("player name: {}", player.getName());
                this.players.put(playerId, player);
                logger.trace("initializing...");
                player.initialize(playerData, index);
                logger.trace(" done.");
            }
            player.update(playerData, index, this.getResearchStatus(playerId), this.getUpgradeStatus(playerId));
        }
    }

    public Player getPlayer(int playerId) {

        return this.players.get(playerId);
    }

    public Collection<Player> getAllPlayers() {

        return this.players.values();
    }

    public Unit getUnit(int unitId) {

        return this.units.get(unitId);
    }

    public Collection<Bullet> getBullets() {
    
    	return this.bullets.values();
    }
    
    public Bullet getBullet(int bulletId) {
    	
    	return this.bullets.get(bulletId);
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

    public boolean canBuildHere(TilePosition position, UnitType type) {
    	
    	return this.canBuildHere(position, type, null);
    }
    
	public boolean canBuildHere(TilePosition position, UnitType type, SCV builder) {

		// TODO check for creep for zerg buildings
		
		if (this.bwMap.canBuildHere(position, type)) {
			
			for (Unit unit : this.getAllUnits()) {

				if (unit != builder && !unit.isFlying()
						&& unit.getTilePosition().getX() + unit.tileWidth() > position.getX()
						&& unit.getTilePosition().getX() < position.getX() + type.tileWidth()
						&& unit.getTilePosition().getY() + unit.tileHeight() > position.getY()
						&& unit.getTilePosition().getY() < position.getY() + type.tileHeight()) {

					return false;
				}
			}
			return true;
		}
		return false;
	}
	
    private void preFrame() {

        logger.trace("updating game state for frame {}...", this.frame);
        updateGame();
        logger.trace("updated game.");
        updateAllPlayers();
        logger.trace("updated players.");
        updateAllUnits(this.frame);
        logger.trace("updated all units.");
        updateAllBullets();
        logger.trace("updated all bullets.");
    }

    private void onStart() {

    	try {
	    	logger.trace(" --- onStart called.");
	        this.frame = 0;
	        this.players.clear();
	        this.units.clear();
	        this.bullets.clear();
	
	        logger.trace(" --- calling initial preFrame...");
	        preFrame();
	        logger.trace("done.");
	        listener.onStart();
    	} catch (Exception e) {
    		
    		logger.error("exception during onStart.", e);
    		throw e;
    	} finally {
    		this.onStartInitializationDone = true;
    	}
    }

    private void onEnd(boolean isWinner) {

        listener.onEnd(isWinner);
    }

    private void onFrame() {

//    	logger.debug("onFrame {}", this.frame);
        try {
            preFrame();
            this.frame++;
            listener.onFrame();
        } catch (Exception e) {
            logger.error("exception during onFrame", e);
            throw e;
        }
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
        this.units.remove(unitId);
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
