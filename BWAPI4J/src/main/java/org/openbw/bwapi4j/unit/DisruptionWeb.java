package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class DisruptionWeb extends Spell {

    protected DisruptionWeb(int id, int timeSpotted) {
        
        super(id, timeSpotted, UnitType.Spell_Disruption_Web);
    }

}
