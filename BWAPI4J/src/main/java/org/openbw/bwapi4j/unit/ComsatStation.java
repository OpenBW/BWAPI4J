package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public class ComsatStation extends Addon implements Mechanical, SpellCaster {

    private int energy;

    ComsatStation(int id, int timeSpotted) {
        super(id, UnitType.Terran_Comsat_Station, timeSpotted);
    }

    public boolean scannerSweep(Position p) {
        return issueCommand(this.id, UnitCommandType.Use_Tech.ordinal(), -1, p.getX(), p.getY(),
                TechType.Scanner_Sweep.getId());
    }

    @Override
    public int getEnergy() {
        return this.energy;
    }
}
