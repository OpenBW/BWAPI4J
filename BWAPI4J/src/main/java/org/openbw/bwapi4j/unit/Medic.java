package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

import static org.openbw.bwapi4j.type.TechType.*;
import static org.openbw.bwapi4j.type.UnitCommandType.Use_Tech_Unit;

public class Medic extends MobileUnit implements SpellCaster, Organic {

    private int energy;

    protected Medic(int id) {
        
        super(id, UnitType.Terran_Medic);
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

    public boolean healing(PlayerUnit unit) {
        
        return issueCommand(this.id, Use_Tech_Unit, unit.getId(), -1, -1,
                Healing.getId());
    }

    public boolean opticalFlare(PlayerUnit unit) {
        
        return issueCommand(this.id, Use_Tech_Unit, unit.getId(), -1, -1,
                Optical_Flare.getId());
    }

    public boolean restoration(PlayerUnit unit) {
        
        return issueCommand(this.id, Use_Tech_Unit, unit.getId(), -1, -1,
                Restoration.getId());
    }

    @Override
    public int getEnergy() {
        
        return this.energy;
    }
}
