package org.openbw.bwapi4j;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.unit.Unit;
import org.openbw.bwapi4j.unit.UnitFactory;

public class BW {

	private BWEventListener listener;
	
	private Map<Integer, Player> players;
	private Map<Integer, Unit> units;
	private UnitFactory unitFactory;
	
	static {
		
		System.setProperty("java.library.path", ".");
		
		System.out.println(new File("../BWAPI4JBridge/Release/BWAPI4JBridge.dll").exists());
		System.out.println(System.getProperty("user.dir"));
		System.loadLibrary("../BWAPI4JBridge/Release/BWAPI4JBridge");
	}
	
	public BW(BWEventListener listener) {
		
		this.players = new HashMap<Integer, Player>();
		this.units = new HashMap<Integer, Unit>();
		this.listener = listener;
		this.unitFactory = new UnitFactory();
	}
	
	private native void startGame(BW bw);
	private native Player[] getPlayers();
	private native int[] getAllUnitsData();
	
	private void updateAllUnits() {
		
		int[] unitData = this.getAllUnitsData();
		
		for (int index = 0; index < unitData.length; index += Unit.TOTAL_PROPERTIES) {
			
			int unitId = unitData[index + 0];
			int typeId = unitData[index + 3];
			Unit unit = this.units.get(unitId);
			if (unit == null) {
				System.out.print("creating unit for id " + unitId + " and type " + typeId + " ...");
				unit = unitFactory.createUnit(unitId, UnitType.values()[typeId]);
				this.units.put(unitId, unit);
				unit.initialize(unitData, index, this.units);
				System.out.println(" done.");
			}
			unit.update(unitData, index);
		}
	}
	public Collection<Unit> getAllUnits() {
		return units.values();
	}
	
	public void startGame() {
		startGame(this);
	}
	
	private void onStart() {
		
		updateAllUnits();
		listener.onStart();
	}

	private void onEnd(boolean isWinner) {
		
		listener.onEnd(isWinner);
	}
	
	private void onFrame() {
		
		updateAllUnits();
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
