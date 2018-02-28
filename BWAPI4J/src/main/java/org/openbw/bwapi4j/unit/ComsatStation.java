package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

import static org.openbw.bwapi4j.type.TechType.Scanner_Sweep;
import static org.openbw.bwapi4j.type.UnitCommandType.Use_Tech_Position;

public class ComsatStation extends Addon implements Mechanical, SpellCaster {

    private int energy;

    protected ComsatStation(int id, int timeSpotted) {
        
        super(id, UnitType.Terran_Comsat_Station, timeSpotted);
    }

    public boolean scannerSweep(Position p) {
        
        return issueCommand(this.id, Use_Tech_Position, -1, p.getX(), p.getY(),
                Scanner_Sweep.getId());
    }

    @Override
    public int getEnergy() {
        
        return this.energy;
    }
}
