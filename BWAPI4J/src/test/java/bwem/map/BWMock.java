package bwem.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.openbw.bwapi4j.BW;
import org.openbw.bwapi4j.BWMap;
import org.openbw.bwapi4j.Player;
import org.openbw.bwapi4j.unit.MineralPatch;
import org.openbw.bwapi4j.unit.VespeneGeyser;

public class BWMock extends BW {

	private BWMap bwMap;
	private Collection<Player> players;
	private List<MineralPatch> mineralPatches;
	private List<VespeneGeyser> vespeneGeysers;
	
	public BWMock() {
		super(null);
		initializeMock();
	}

	private void initializeMock() {
		
		this.players = new ArrayList<>();
		// TODO fill in players here
		
		this.mineralPatches = new ArrayList<>();
		// TODO fill in mineral patches here
		
		this.vespeneGeysers = new ArrayList<>();
		// TODO fill in vespene geysers here
		
		this.bwMap = new BWMapMock();
	}
	
	@Override
	public BWMap getBWMap() {
		
		return this.bwMap;
	}

	@Override
	public Collection<Player> getAllPlayers() {
		
		return this.players;
	}

	@Override
	public List<MineralPatch> getMineralPatches() {
		
		return this.mineralPatches;
	}

	@Override
	public List<VespeneGeyser> getVespeneGeysers() {
		
		return this.vespeneGeysers;
	}
}
