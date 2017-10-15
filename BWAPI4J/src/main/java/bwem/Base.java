package bwem;

import bwem.unit.Geyser;
import bwem.unit.Mineral;
import bwem.unit.Resource;
import java.util.ArrayList;
import java.util.List;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.type.UnitType;

/**
 * After Areas and ChokePoints, Bases are the third kind of object BWEM automatically computes from Brood War's maps.
 * A Base is essentially a suggested location (intended to be optimal) to put a Command Center, Nexus, or Hatchery.
 * It also provides information on the ressources available, and some statistics.
 * A Base alway belongs to some Area. An Area may contain zero, one or several Bases.
 * Like Areas and ChokePoints, the number and the addresses of Base instances remain unchanged.
 */
public class Base {

    private Map map;
    private Area area;
    private TilePosition location;
    private Position center;
    private List<Mineral> assignedMineralPatches;
    private List<Geyser> assignedVespeneGeysers;
    private List<Mineral> blockingMinerals;
    private boolean isStarting;
    private UserData userData;

    public Base(Area area, TilePosition location, List<Resource> assignedResources, List<Mineral> assignedBlockingMinerals) {
        if (assignedResources.isEmpty()) {
            throw new IllegalStateException();
        }

        this.area = area;
        this.map = area.getMap();
        this.location = new TilePosition(location.getX(), location.getY());
        this.center = calcCenter(location);

        for (Resource r : assignedBlockingMinerals) {
            if (r instanceof Mineral) {
                Mineral m = (Mineral) r;
                this.assignedMineralPatches.add(m);
            } else if (r instanceof Geyser) {
                Geyser g = (Geyser) r;
                this.assignedVespeneGeysers.add(g);
            }
        }
    }

    public UserData getUserData() {
        return this.userData;
    }

    /**
	 * Indicates whether this Base's location is contained in Map::StartingLocations().
     *
	 * Note: All players start at locations taken from Map::StartingLocations(),
	 * which doesn't mean all the locations in Map::StartingLocations() are actually used.
     */
    public boolean isStarting() {
        return this.isStarting;
    }

    /**
     * Returns the Area this Base belongs to.
     */
    public Area getArea() {
        return this.area;
    }

    /**
	 * Returns the location of this Base (top left Tile position).
     * If Starting() == true, it is guaranteed that the loction corresponds
     * exactly to one of Map::StartingLocations().
     */
    public TilePosition getLocation() {
        return new TilePosition(this.location.getX(), this.location.getY());
    }

    public void setStartingLocation(TilePosition location) {
        this.isStarting = true;
        this.location = location;
        this.center = calcCenter(location);
    }

    /**
     * Returns the location of this Base (center in pixels).
     */
    public Position getCenter() {
        return new Position(this.center.getX(), this.center.getY());
    }

    /**
	 * Returns the assigned mineral patches to this base.
	 * It is guaranteed that no other base provides these.
     *
	 * Note: The size of the returned list may decrease, as some of the Minerals may get destroyed.
     */
    public List<Mineral> getAssignedMineralPatches() {
        return new ArrayList<>(this.assignedMineralPatches);
    }

    /**
	 * Returns the assigned vespene geysers to this base.
	 * It is guaranteed that no other base provides these.
     *
	 * Note: The size of the returned list may NOT decrease, as Geysers never get destroyed.
     */
    public List<Geyser> getAssignedVespeneGeysers() {
        return new ArrayList<>(this.assignedVespeneGeysers);
    }

    /**
	 * Returns the blocking Minerals.
	 * These Minerals are special ones: they are placed at the exact location of this Base (or very close),
	 * thus blocking the building of a Command Center, Nexus, or Hatchery.
	 * So before trying to build this Base, one have to finish gathering these Minerals first.
	 * Fortunately, these are guaranteed to have their InitialAmount() less than or equal to 8.
	 * As an example of blocking Minerals, see the two islands in Andromeda.scx.
     *
	 * Note: if Starting() == true, an empty list is returned.
	 * Note Base::BlockingMinerals() should not be confused with ChokePoint::BlockingNeutral() and Neutral::Blocking():
     * the last two refer to a Neutral blocking a ChokePoint, not a Base.
     */
    public List<Mineral> getBlockingMinerals() {
       return new ArrayList<>(this.blockingMinerals);
    }

    private Map GetMap() {
        return this.map;
    }

    private Position calcCenter(TilePosition location) {
        return BWEM.getCenter(new Position(location.getX(), location.getY()).add(UnitType.Terran_Command_Center.tileSize().toPosition()).toWalkPosition());
    }

}
