package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class Dropship extends Transporter implements Mechanical {

    protected Dropship(int id) {
        super(id, UnitType.Terran_Dropship);
    }
}
