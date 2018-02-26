package bwem.unit;

import bwem.map.Map;
import org.openbw.bwapi4j.unit.Unit;

//////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                          //
//                                  class Resource
//                                                                                          //
//////////////////////////////////////////////////////////////////////////////////////////////
//
// A Resource is either a Mineral or a Geyser
//
//////////////////////////////////////////////////////////////////////////////////////////////

public abstract class Resource extends Neutral {

    protected Resource(Unit u, Map map) {
        super(u, map);
    }

    // Returns the initial amount of resources for this Resource (same as unit()->getInitialResources).
    public abstract int getInitialAmount();

    // Returns the current amount of resources for this Resource (same as unit()->getResources).
    public abstract int getAmount();

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (!(object instanceof Resource)) {
            return false;
        } else {
            Resource that = (Resource) object;
            return (this.getUnit().getId() == that.getUnit().getId());
        }
    }

    @Override
    public int hashCode() {
        return getUnit().hashCode();
    }

}
