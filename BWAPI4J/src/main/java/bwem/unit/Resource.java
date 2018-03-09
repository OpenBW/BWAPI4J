package bwem.unit;

import bwem.map.Map;
import org.openbw.bwapi4j.unit.Unit;

/**
 * A Resource is either a Mineral or a Geyser.
 */
public abstract class Resource extends NeutralImpl {

    protected Resource(final Unit unit, final Map map) {
        super(unit, map);
    }

    /**
     * Returns the initial amount of resources for this Resource (same as unit()-&gt;getInitialResources).
     */
    public abstract int getInitialAmount();

    /**
     * Returns the current amount of resources for this Resource (same as unit()-&gt;getResources).
     */
    public abstract int getAmount();

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        } else if (!(object instanceof Resource)) {
            return false;
        } else {
            final Resource that = (Resource) object;
            return (this.getUnit().getId() == that.getUnit().getId());
        }
    }

    @Override
    public int hashCode() {
        return getUnit().hashCode();
    }

}
