package org.openbw.bwapi4j.unit;

public interface ResearchingFacility {

	public boolean isUpgrading();

	public boolean isResearching();
	
	public boolean cancelResearch();
	
	public boolean cancelUpgrade();
}
