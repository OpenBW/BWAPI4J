package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public class Probe extends Worker implements Mechanical, Robotic {


    protected Probe(int id) {
        super(id, UnitType.Protoss_Probe);
    }
}
