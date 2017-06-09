package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

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
    public void update(int[] unitData, int index) {

        this.energy = unitData[index + Unit.ENERGY_INDEX];
        super.update(unitData, index);
    }

    public boolean healing(PlayerUnit unit) {
        
        return issueCommand(this.id, UnitCommandType.Use_Tech.ordinal(), unit.getId(), -1, -1,
                TechType.Healing.getId());
    }

    public boolean opticalFlare(PlayerUnit unit) {
        
        return issueCommand(this.id, UnitCommandType.Use_Tech.ordinal(), unit.getId(), -1, -1,
                TechType.Optical_Flare.getId());
    }

    public boolean restoration(PlayerUnit unit) {
        
        return issueCommand(this.id, UnitCommandType.Use_Tech.ordinal(), unit.getId(), -1, -1,
                TechType.Restoration.getId());
    }

    @Override
    public int getEnergy() {
        
        return this.energy;
    }
}
