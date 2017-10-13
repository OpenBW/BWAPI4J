package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public class Bunker extends Building implements Mechanical {

    private boolean isLoaded;

    // TODO units in bunker
    
    protected Bunker(int id, int timeSpotted) {
        
        super(id, UnitType.Terran_Bunker, timeSpotted);
    }

    @Override
    public void update(int[] unitData, int index) {

        this.isLoaded = unitData[index + Unit.IS_LOADED_INDEX] == 1;
        super.update(unitData, index);
    }

    public boolean isLoaded() {
        
        return this.isLoaded;
    }

    protected boolean unload(Unit target) {
        
        return issueCommand(this.id, UnitCommandType.Unload.ordinal(), target.getId(), -1, -1, -1);
    }

    protected boolean unloadAll(boolean queued) {
        
        return issueCommand(this.id, UnitCommandType.Unload_All.ordinal(), -1, -1, -1, queued ? 1 : 0);
    }
}
