package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public class BattleCruiser extends MobileUnit implements Mechanical, SpellCaster, Armed {

    private int energy;

    protected BattleCruiser(int id) {
        
        super(id, UnitType.Terran_Battlecruiser);
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

    public boolean yamatoGun(PlayerUnit unit) {
        
        return issueCommand(this.id, UnitCommandType.Use_Tech_Unit.ordinal(), unit.getId(), -1, -1,
                TechType.Yamato_Gun.getId());
    }

    @Override
    public int getEnergy() {
        
        return this.energy;
    }


    @Override
    public Weapon getGroundWeapon() {
        return groundWeapon;
    }

    @Override
    public Weapon getAirWeapon() {
        return airWeapon;
    }
}
