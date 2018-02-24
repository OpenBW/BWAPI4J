package bwem;

import bwem.map.Map;
import bwem.area.Area;
import bwem.unit.Geyser;
import bwem.unit.Mineral;
import bwem.unit.Resource;
import bwem.util.BwemExt;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.type.UnitType;

//////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                          //
//                                  class Base
//                                                                                          //
//////////////////////////////////////////////////////////////////////////////////////////////
//
// After areas and chokePoints, bases are the third kind of object BWEM automatically computes from Brood War's maps.
// A Base is essentially a suggested location (intended to be optimal) to put a Command center, Nexus, or Hatchery.
// It also provides information on the ressources available, and some statistics.
// A Base alway belongs to some Area. An Area may contain zero, one or several bases.
// Like areas and chokePoints, the number and the addresses of Base instances remain unchanged.
//
// bases inherit utils::UserData, which provides free-to-use data.
//
//////////////////////////////////////////////////////////////////////////////////////////////

public final class Base {

	private final Map pMap;
	private final Area pArea;
	private TilePosition location;
	private Position center;
	private final List<Mineral> Minerals = new ArrayList<>();
	private final List<Geyser> Geysers = new ArrayList<>();
	private final List<Mineral> BlockingMinerals;
	private boolean starting = false;

    public Base(Area pArea, TilePosition location, List<Resource> assignedResources, List<Mineral> blockingMinerals) {
        this.pArea = pArea;
        pMap = pArea.getMap();
        this.location = location;
        center = BwemExt.centerOfBuilding(location, UnitType.Terran_Command_Center.tileSize());
        this.BlockingMinerals = blockingMinerals;

//        bwem_assert(!AssignedRessources.empty());
        if (assignedResources.isEmpty()) {
            throw new IllegalArgumentException();
        }

        for (Resource r : assignedResources) {
            if (r instanceof Mineral) {
                Mineral m = (Mineral) r;
                Minerals.add(m);
            } else if (r instanceof Geyser) {
                Geyser g = (Geyser) r;
                Geysers.add(g);
            }
        }
    }

	// Tells whether this Base's location is contained in Map::StartingLocations()
	// Note: all players start at locations taken from Map::StartingLocations(),
	//       which doesn't mean all the locations in Map::StartingLocations() are actually used.
	public boolean starting() {
        return starting;
    }

	// Returns the Area this Base belongs to.
	public Area getArea() {
        return pArea;
    }

	// Returns the location of this Base (top left Tile position).
	// If starting() == true, it is guaranteed that the loction corresponds exactly to one of Map::StartingLocations().
	public TilePosition location() {
        return location;
    }

	// Returns the location of this Base (center in pixels).
	public Position center() {
        return center;
    }

	// Returns the available minerals.
	// These minerals are assigned to this Base (it is guaranteed that no other Base provides them).
	// Note: The size of the returned list may decrease, as some of the minerals may get destroyed.
	public List<Mineral> minerals() {
        return Minerals;
    }

	// Returns the available geysers.
	// These geysers are assigned to this Base (it is guaranteed that no other Base provides them).
	// Note: The size of the returned list may NOT decrease, as geysers never get destroyed.
	public List<Geyser> geysers() {
        return Geysers;
    }

	// Returns the blocking minerals.
	// These minerals are special ones: they are placed at the exact location of this Base (or very close),
	// thus blocking the building of a Command center, Nexus, or Hatchery.
	// So before trying to build this Base, one have to finish gathering these minerals first.
	// Fortunately, these are guaranteed to have their initialAmount() <= 8.
	// As an example of blocking minerals, see the two islands in Andromeda.scx.
	// Note: if starting() == true, an empty list is returned.
	// Note Base::blockingMinerals() should not be confused with ChokePoint::blockingNeutral() and Neutral::blocking():
	//      the last two refer to a Neutral blocking a ChokePoint, not a Base.
	public List<Mineral> blockingMinerals() {
        return BlockingMinerals;
    }

    public void setStartingLocation(TilePosition actualLocation) {
        starting = true;
        location = actualLocation;
        center = BwemExt.centerOfBuilding(actualLocation, UnitType.Terran_Command_Center.tileSize());
    }

    public void onMineralDestroyed(Mineral pMineral) {
//    	bwem_assert(pMineral);
        if (pMineral == null) {
            throw new IllegalArgumentException();
        }
        Minerals.remove(pMineral);
        BlockingMinerals.remove(pMineral);
    }

    /**
     * Returns the internal Map object. Not used in BWEM 1.4.1. Remains for portability consistency.
     */
    private Map getMap() {
        return pMap;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (!(object instanceof Base)) {
            return false;
        } else {
            Base that = (Base) object;
            return (this .pArea.equals(that .pArea)
                    && this .location.equals(that .location)
                    && this .center.equals(that .center));
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this .pArea,
                this .location,
                this .center
        );
    }

}
