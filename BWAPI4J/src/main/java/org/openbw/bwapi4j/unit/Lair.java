package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.type.UpgradeType;

import static org.openbw.bwapi4j.type.UnitCommandType.Morph;
import static org.openbw.bwapi4j.type.UnitType.Zerg_Hive;

public class Lair extends Hatchery {

    protected Lair(int id, int timeSpotted) {

        super(id, UnitType.Zerg_Lair, timeSpotted);
    }

    protected Lair(int id, UnitType type, int timeSpotted) {

        super(id, type, timeSpotted);
    }

    public boolean upgradeVentralSacs() {

        return this.researcher.upgrade(UpgradeType.Ventral_Sacs);
    }

    public boolean upgradeAntennae() {

        return this.researcher.upgrade(UpgradeType.Antennae);
    }

    public boolean upgradePneumatizedCarapace() {

        return this.researcher.upgrade(UpgradeType.Pneumatized_Carapace);
    }

    @Override
    public boolean morph(UnitType type) {
        if (type != Zerg_Hive) {
            throw new IllegalArgumentException("Cannot morph to " + type);
        }
        return issueCommand(this.id, Morph, -1, -1, -1, Zerg_Hive.getId());
    }

    @Override
    public boolean morph() {
        return morph(Zerg_Hive);
    }
}
