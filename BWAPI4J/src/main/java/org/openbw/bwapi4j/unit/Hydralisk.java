package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

import static org.openbw.bwapi4j.type.UnitCommandType.*;
import static org.openbw.bwapi4j.type.UnitType.Zerg_Lurker;

public class Hydralisk extends MobileUnit implements Organic, Burrowable, Armed, Morphable {

    private boolean burrowed;
    
    protected Hydralisk(int id) {
        super(id, UnitType.Zerg_Hydralisk);
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
        
        return issueCommand(this.id, Burrow, -1, -1, -1, -1);
    }

    @Override
    public boolean unburrow() {
        
        return issueCommand(this.id, Unburrow, -1, -1, -1, -1);
    }

    @Override
    public boolean isBurrowed() {
        
        return this.burrowed;
    }

    @Override
    public boolean morph(UnitType type) {
        if (type != Zerg_Lurker) {
            throw new IllegalArgumentException("Can only morph into Lurker");
        }
        return issueCommand(this.id, Morph, -1, -1, -1, Zerg_Lurker.getId());
    }

    public boolean morph() {
        return morph(Zerg_Lurker);
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
