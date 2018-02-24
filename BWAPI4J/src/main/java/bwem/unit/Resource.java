package bwem.unit;

import bwem.map.Map;
import org.openbw.bwapi4j.unit.Unit;

//////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                          //
//                                  class Ressource
//                                                                                          //
//////////////////////////////////////////////////////////////////////////////////////////////
//
// A Ressource is either a Mineral or a Geyser
//
//////////////////////////////////////////////////////////////////////////////////////////////

public abstract class Resource extends Neutral {

    protected Resource(Unit u, Map pMap) {
        super(u, pMap);
    }

    // Returns the initial amount of ressources for this Ressource (same as unit()->getInitialResources).
    public abstract int initialAmount();

    // Returns the current amount of ressources for this Ressource (same as unit()->getResources).
    public abstract int amount();

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (!(object instanceof Resource)) {
            return false;
        } else {
            Resource that = (Resource) object;
            return (this.unit().getId() == that.unit().getId());
        }
    }

    @Override
    public int hashCode() {
        return unit().hashCode();
    }

}
