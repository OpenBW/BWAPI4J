package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public class NydusCanal extends Building implements Organic {

    private int nydusExitId;
    
    protected NydusCanal(int id, int timeSpotted) {
        
        super(id, UnitType.Zerg_Nydus_Canal, timeSpotted);
    }

    @Override
    public void update(int[] unitData, int index, int frame) {
        
        this.nydusExitId = unitData[index + Unit.NYDUS_EXIT_INDEX];
        super.update(unitData, index, frame);
    }
    
    public Unit getNydusExit() {
        
        return super.getUnit(nydusExitId);
    }
    
    /**
     * Builds a nydus exit for this nydus canal at given position.
     * @param position the position of the nydus exit
     * @return true if command successful, false else
     */
    public boolean buildNydusExit(Position position) {
        
        return issueCommand(this.id, UnitCommandType.Build.ordinal(), -1,
                position.getX(), position.getY(), UnitType.Zerg_Nydus_Canal.getId()); 
    }
}
