package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public class DarkTemplar extends MobileUnit implements Organic, Cloakable {

    protected DarkTemplar(int id) {
        
        super(id, UnitType.Protoss_Dark_Templar);
    }
    
    /**
     * Merges two dark templars into one dark archon. Both templars must be selected.
     * @return true if command successful, false else
     */
    public boolean darkArchonMeld() {
        
        // TODO how does this spell work? does the other templars ID have to be passed as well?
        return issueCommand(this.id, UnitCommandType.Use_Tech.ordinal(), -1, -1, -1, TechType.Dark_Archon_Meld.getId());
    }

    @Override
    public boolean cloak() {
        
        return true;
    }

    @Override
    public boolean decloak() {
        
        return false;
    }
}
