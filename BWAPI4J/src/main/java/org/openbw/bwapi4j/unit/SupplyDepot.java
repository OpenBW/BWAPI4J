package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class SupplyDepot extends Building implements Mechanical {

    public SupplyDepot(int id, int timeSpotted) {
        super(id, UnitType.Terran_Supply_Depot, timeSpotted);
    }
}
