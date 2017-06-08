package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class DarkSwarm extends Spell {

    protected DarkSwarm(int id, int timeSpotted) {
        
        super(id, timeSpotted, UnitType.Spell_Dark_Swarm);
    }

}
