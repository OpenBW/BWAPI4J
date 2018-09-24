package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public abstract class GasMiningFacilityImpl extends BuildingImpl implements GasMiningFacility {

  protected GasMiningFacilityImpl(UnitType unitType, int timeSpotted) {
    super(unitType, timeSpotted);
  }

  @Override
  public int getResources() {
    return this.resources;
  }

  @Override
  public int getInitialResources() {
    return this.initialResources;
  }

  @Override
  public boolean isBeingGathered() {
    return this.beingGathered;
  }
}
