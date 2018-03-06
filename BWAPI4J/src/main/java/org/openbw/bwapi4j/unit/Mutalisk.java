package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

import static org.openbw.bwapi4j.type.UnitCommandType.Morph;

public class Mutalisk extends MobileUnit implements Organic, Armed, Morphable {

    protected Mutalisk(int id) {

        super(id, UnitType.Zerg_Mutalisk);
    }

    @Override
    public boolean morph(UnitType unitType) {
        if (unitType != UnitType.Zerg_Guardian && unitType != UnitType.Zerg_Devourer) {
            throw new IllegalArgumentException("Cannot morph to " + type);
        }
        return issueCommand(this.id, Morph, -1, -1, -1, unitType.getId());
    }

    public boolean morphGuardian() {
        return morph(UnitType.Zerg_Guardian);
    }

    public boolean morphDevourer() {
        return morph(UnitType.Zerg_Devourer);
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
