package bwem.unit;

import bwem.map.Map;
import java.util.Objects;
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

    // Returns the initial amount of ressources for this Ressource (same as Unit()->getInitialResources).
    public abstract int InitialAmount();

    // Returns the current amount of ressources for this Ressource (same as Unit()->getResources).
    public abstract int Amount();

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (!(object instanceof Resource)) {
            return false;
        } else {
            Resource that = (Resource) object;
            return (this.Unit().getId() == that.Unit().getId());
        }
    }

    @Override
    public int hashCode() {
        return Unit().hashCode();
    }

}
