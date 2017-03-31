package org.openbw.bwapi4j;

import java.io.File;

import org.openbw.bwapi4j.type.Race;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.type.UpgradeType;
import org.openbw.bwapi4j.type.WeaponType;

public class BW {

	private BWEventListener listener;
	
	static {
		
		System.setProperty("java.library.path", ".");
		
		System.out.println(new File("../BWAPI4JBridge/Release/BWAPI4JBridge.dll").exists());
		System.out.println(System.getProperty("user.dir"));
		System.loadLibrary("../BWAPI4JBridge/Release/BWAPI4JBridge");
	}
	
	public BW() {
		
	}
	
	public native void startGame(BW bw);
	
	private native int[] getUnitTypes();
	
	public static void main(String[] args) {
		
		BW bw = new BW();
		bw.startGame(bw);
	}

	
	private void onStart() {
		
		System.out.println("game started.");
	}

	
	private void onEnd(boolean isWinner) {
		
		System.out.println("game ended.");
	}

	
	private void onFrame() {
		// TODO Auto-generated method stub
		
	}

	
	private void onSendText(String text) {
		// TODO Auto-generated method stub
		
	}

	
	private void onReceiveText(Player player, String text) {
		// TODO Auto-generated method stub
		
	}

	
	private void onPlayerLeft(Player player) {
		// TODO Auto-generated method stub
		
	}

	
	private void onNukeDetect(Position target) {
		// TODO Auto-generated method stub
		
	}

	
	private void onUnitDiscover(Unit unit) {
		// TODO Auto-generated method stub
		
	}

	
	private void onUnitEvade(Unit unit) {
		// TODO Auto-generated method stub
		
	}

	
	private void onUnitShow(Unit unit) {
		// TODO Auto-generated method stub
		
	}

	
	private void onUnitHide(Unit unit) {
		// TODO Auto-generated method stub
		
	}

	
	private void onUnitCreate(Unit unit) {
		// TODO Auto-generated method stub
		
	}

	
	private void onUnitDestroy(Unit unit) {
		// TODO Auto-generated method stub
		
	}

	
	private void onUnitMorph(Unit unit) {
		// TODO Auto-generated method stub
		
	}

	
	private void onUnitRenegade(Unit unit) {
		// TODO Auto-generated method stub
		
	}

	
	private void onSaveGame(String gameName) {
		// TODO Auto-generated method stub
		
	}

	
	private void onUnitComplete(Unit unit) {
		// TODO Auto-generated method stub
		
	}
	
	private void onPlayerDropped(Player player) {
		// TODO Auto-generated method stub
		
	}

}
