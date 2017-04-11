package org.openbw.bwapi4j;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.unit.Unit;
import org.openbw.bwapi4j.unit.UnitFactory;

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

        System.setProperty("java.library.path", "./../BWAPI4JBridge/Release/");
//        System.setProperty("java.library.path", "./");

        logger.debug("DLL exists: {}",
                new File(System.getProperty("java.library.path") + "BWAPI4JBridge.dll").exists());
        logger.debug(System.getProperty("user.dir"));

        System.loadLibrary(System.getProperty("java.library.path") + "BWAPI4JBridge");
        logger.debug("DLL loaded.");
    }

    public BW(BWEventListener listener) {

        this.players = new HashMap<Integer, Player>();
        this.units = new HashMap<Integer, Unit>();
        this.listener = listener;
        this.unitFactory = new UnitFactory();
        this.interactionHandler = new InteractionHandler(this);
        this.mapDrawer = new MapDrawer();
        this.damageEvaluator = new DamageEvaluator();
        this.bwMap = new BWMap();

        try {
            charset = Charset.forName("Cp949"); // Korean char set
        } catch (UnsupportedCharsetException e) {
            logger.warn("Korean character set not available. Some characters may not be read properly.");
            charset = StandardCharsets.ISO_8859_1;
        }
    }

    private native void startGame(BW bw);

    private native int[] getAllUnitsData();

    private native int[] getAllPlayersData();

    private native int[] getGameData();

    private native byte[] getPlayerName(int playerId);

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
            if (unit == null) {
                logger.debug("creating unit for id " + unitId + " and type " + typeId + " ...");
                unit = unitFactory.createUnit(unitId, UnitType.values()[typeId], frame);
                this.units.put(unitId, unit);
                unit.initialize(unitData, index, this.units);
                logger.debug(" done.");
            }
            unit.update(unitData, index);
        }
    }

    private void updateAllPlayers() {

        int[] playerData = this.getAllPlayersData();

        for (int index = 0; index < playerData.length; index += Player.TOTAL_PROPERTIES) {

            int playerId = playerData[index + 0];
            Player player = this.players.get(playerId);
            if (player == null) {

                logger.debug("creating player for id " + playerId + " ...");
                String playerName = new String(this.getPlayerName(playerId), this.charset);
                player = new Player(playerId, playerName);
                this.players.put(playerId, player);
                player.initialize(playerData, index, this.units);
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

    public Collection<Unit> getAllUnits() {
        return this.units.values();
    }

    public void startGame() {
        startGame(this);
    }

    private void onStart() {

        this.frame = 0;
        updateGame();
        updateAllPlayers();
        updateAllUnits(this.frame);
        listener.onStart();
    }

    private void onEnd(boolean isWinner) {

        listener.onEnd(isWinner);
    }

    private void onFrame() {

        this.frame++;
        updateGame();
        updateAllPlayers();
        updateAllUnits(this.frame);
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
        listener.onUnitDiscover(unit);
    }

    private void onUnitEvade(int unitId) {

        Unit unit = this.units.get(unitId);
        listener.onUnitEvade(unit);
    }

    private void onUnitShow(int unitId) {

        Unit unit = this.units.get(unitId);
        listener.onUnitShow(unit);
    }

    private void onUnitHide(int unitId) {

        Unit unit = this.units.get(unitId);
        listener.onUnitHide(unit);
    }

    private void onUnitCreate(int unitId) {

        Unit unit = this.units.get(unitId);
        listener.onUnitCreate(unit);
    }

    private void onUnitDestroy(int unitId) {

        Unit unit = this.units.get(unitId);
        listener.onUnitDestroy(unit);
    }

    private void onUnitMorph(int unitId) {

        Unit unit = this.units.get(unitId);
        listener.onUnitMorph(unit);
    }

    private void onUnitRenegade(int unitId) {

        Unit unit = this.units.get(unitId);
        listener.onUnitRenegade(unit);
    }

    private void onSaveGame(String gameName) {

        listener.onSaveGame(gameName);
    }

    private void onUnitComplete(int unitId) {

        Unit unit = this.units.get(unitId);
        listener.onUnitComplete(unit);
    }
}
