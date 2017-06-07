package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class Goliath extends MobileUnit implements Mechanical {

    Goliath(int id) {
        super(id, UnitType.Terran_Goliath);
    }
}
