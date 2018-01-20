package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class Overlord extends Transporter implements Organic, Detector, SupplyProvider {

    protected Overlord(int id) {
        
        super(id, UnitType.Zerg_Overlord);
    }

    @Override
    public int supplyProvided() {
        return type.supplyProvided();
    }
}
