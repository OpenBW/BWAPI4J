package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class Shuttle extends Transporter implements Mechanical, Robotic {

    protected Shuttle(int id) {
        
        super(id, UnitType.Protoss_Shuttle);
    }
}
