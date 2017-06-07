package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public class PhotonCannon extends Building implements Detector, Mechanical {

    PhotonCannon(int id, int timeSpotted) {
        super(id, UnitType.Protoss_Photon_Cannon, timeSpotted);
    }

    protected boolean attack(Unit target, boolean queued) {
        return issueCommand(this.id, UnitCommandType.Attack_Unit.ordinal(), target.getId(), -1, -1, queued ? 1 : 0);
    }
}
