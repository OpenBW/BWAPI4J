package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

import static org.openbw.bwapi4j.type.TechType.*;
import static org.openbw.bwapi4j.type.UnitCommandType.Use_Tech_Position;
import static org.openbw.bwapi4j.type.UnitCommandType.Use_Tech_Unit;

public class ScienceVessel extends MobileUnit implements Mechanical, SpellCaster, Detector {

    private int energy;

    protected ScienceVessel(int id) {
        
        super(id, UnitType.Terran_Science_Vessel);
    }

    @Override
    public void initialize(int[] unitData, int index) {

        this.energy = 0;
        super.initialize(unitData, index);
    }

    @Override
    public void update(int[] unitData, int index, int frame) {

        this.energy = unitData[index + Unit.ENERGY_INDEX];
        super.update(unitData, index, frame);
    }

    public boolean defensiveMatrix(PlayerUnit unit) {
        
        return issueCommand(this.id, Use_Tech_Unit, unit.getId(), -1, -1,
                Defensive_Matrix.getId());
    }

    public boolean irradiate(Organic unit) {
        
        return issueCommand(this.id, Use_Tech_Unit, ((Unit) unit).getId(), -1, -1,
                Irradiate.getId());
    }

    public boolean empShockWave(Position p) {
        
        return issueCommand(this.id, Use_Tech_Position, -1, p.getX(), p.getY(),
                EMP_Shockwave.getId());
    }

    @Override
    public int getEnergy() {
        
        return this.energy;
    }
}
