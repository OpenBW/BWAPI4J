package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public class Zergling extends MobileUnit implements Organic, Burrowable, Armed {

    private boolean burrowed;
    
    protected Zergling(int id) {
        
        super(id, UnitType.Zerg_Zergling);
    }
    
    @Override
    public void initialize(int[] unitData, int index) {

        this.burrowed = false;
        super.initialize(unitData, index);
    }

    @Override
    public void update(int[] unitData, int index, int frame) {

        this.burrowed = unitData[index + Unit.IS_BURROWED_INDEX] == 1;
        super.update(unitData, index, frame);
    }
    
    @Override
    public boolean burrow() {
        
        return issueCommand(this.id, UnitCommandType.Burrow.ordinal(), -1, -1, -1, -1);
    }

    @Override
    public boolean unburrow() {
        
        return issueCommand(this.id, UnitCommandType.Unburrow.ordinal(), -1, -1, -1, -1);
    }

    @Override
    public boolean isBurrowed() {
        
        return this.burrowed;
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
