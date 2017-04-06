package org.openbw.bwapi4j;

import org.openbw.bwapi4j.type.Key;
import org.openbw.bwapi4j.unit.Unit;

public class TestListener implements BWEventListener {

	private BW bw;
	
	@Override
	public void onStart() {
		System.out.println("onStart");
		this.bw.getInteractionHandler().enablePlayerInteraction();
	}

	@Override
	public void onEnd(boolean isWinner) {
		System.out.println("onEnd: winner: " + isWinner);
	}

	@Override
	public void onFrame() {
		
		System.out.println("onFrame");
		if (bw.getInteractionHandler().isKeyPressed(Key.K_D)) {
			System.out.println("D");
		}
		for (Player player : bw.getAllPlayers()) {
			System.out.println("Player " + player.getName() + " has minerals " + player.minerals());
		}
	}

	@Override
	public void onSendText(String text) {
		System.out.println("onSendText: " + text);
	}

	@Override
	public void onReceiveText(Player player, String text) {
		System.out.println("onReceiveText: by " + player + ": " + text);
	}

	@Override
	public void onPlayerLeft(Player player) {
		System.out.println("onPlayerLeft: " + player);
	}

	@Override
	public void onNukeDetect(Position target) {
		System.out.println("onNukeDetect: " + target);
	}

	@Override
	public void onUnitDiscover(Unit unit) {
		System.out.println("onUnitDiscover: " + unit);
	}

	@Override
	public void onUnitEvade(Unit unit) {
		System.out.println("onUnitEvade: " + unit);
	}

	@Override
	public void onUnitShow(Unit unit) {
		System.out.println("onUnitShow: " + unit);
	}

	@Override
	public void onUnitHide(Unit unit) {
		System.out.println("onUnitHide: " + unit);
	}

	@Override
	public void onUnitCreate(Unit unit) {
		System.out.println("onUnitCreate: " + unit);
	}

	@Override
	public void onUnitDestroy(Unit unit) {
		System.out.println("onUnitDestroy: " + unit);
	}

	@Override
	public void onUnitMorph(Unit unit) {
		System.out.println("onUnitMorph: " + unit);
	}

	@Override
	public void onUnitRenegade(Unit unit) {
		System.out.println("onUnitRenegade: " + unit);
	}

	@Override
	public void onSaveGame(String gameName) {
		System.out.println("onSaveGame: " + gameName);
	}

	@Override
	public void onUnitComplete(Unit unit) {
		System.out.println("onUnitComplete: " + unit);
	}

	public static void main(String[] args) {
		
		TestListener listener = new TestListener();
		BW bw = new BW(listener);
		listener.bw = bw;
		
		bw.startGame();
	}
}
