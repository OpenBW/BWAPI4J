package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public class Mutalisk extends MobileUnit implements Organic {

    protected Mutalisk(int id) {

        super(id, UnitType.Zerg_Mutalisk);
    }

    public boolean morph(UnitType type) {
        if (type == UnitType.Zerg_Guardian) {
            return morphGuardian();
        }
        if (type == UnitType.Zerg_Defiler) {
            return morphDefiler();
        }
        return false;
    }

    public boolean morphGuardian() {

        return issueCommand(this.id, UnitCommandType.Morph.ordinal(), -1, -1, -1, UnitType.Zerg_Guardian.getId());
    }

    public boolean morphDefiler() {
        return issueCommand(this.id, UnitCommandType.Morph.ordinal(), -1, -1, -1, UnitType.Zerg_Defiler.getId());
    }
}
