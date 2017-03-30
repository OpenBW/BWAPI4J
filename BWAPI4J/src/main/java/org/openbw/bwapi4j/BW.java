package org.openbw.bwapi4j;

import java.io.File;

public class BW {

	static {
		
		System.setProperty("java.library.path", ".");
		
		System.out.println(new File("BWAPI4JBridge.dll").exists());
		
		System.loadLibrary("BWAPI4JBridge");
	}
	
	public BW() {
		
	}
	
	public native void startGame();
	
	private native int[] getUnitTypes();
	
	public static void main(String[] args) {
		
		BW bw = new BW();
		bw.startGame();
		
		// TODO implement complete listener first
		
		int[] typeIds = bw.getUnitTypes();
		for (int i = 0; i < typeIds.length; i++) {
			System.out.println(typeIds[i]);
		}
	}

}
