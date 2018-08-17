package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public abstract class GasMiningFacilityImpl extends BuildingImpl implements GasMiningFacility {

    protected GasMiningFacilityImpl(int id, UnitType type, int timeSpotted) {
        
        super(id, type, timeSpotted);
    }

	@Override
	public int getResources() {
		// TODO Auto-generated method stub
		return this.resources;
	}

	@Override
	public int getInitialResources() {
		// TODO Auto-generated method stub
		return this.initialResources;
	}

	@Override
	public boolean isBeingGathered() {
		// TODO Auto-generated method stub
		return this.isBeingGathered;
	}

}
