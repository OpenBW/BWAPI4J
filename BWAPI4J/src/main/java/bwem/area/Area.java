package bwem.area;

import bwem.Base;
import bwem.ChokePoint;
import bwem.area.typedef.AreaId;
import bwem.area.typedef.GroupId;
import bwem.typedef.Altitude;
import bwem.unit.Geyser;
import bwem.unit.Mineral;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;

import java.util.AbstractMap;
import java.util.List;

//////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                          //
//                                  class Area
//                                                                                          //
//////////////////////////////////////////////////////////////////////////////////////////////
//
// Areas are regions that BWEM automatically computes from Brood War's maps
// Areas aim at capturing relevant regions that can be walked, though they may contain small inner non walkable regions called lakes.
// More formally:
//  - An area consists in a set of 4-connected MiniTiles, which are either Terrain-MiniTiles or Lake-MiniTiles.
//  - An Area is delimited by the side of the Map, by Water-MiniTiles, or by other Areas. In the latter case
//    the adjoining Areas are called neighbouring Areas, and each pair of such Areas defines at least one ChokePoint.
// Like ChokePoints and Bases, the number and the addresses of Area instances remain unchanged.
// To access Areas one can use their ids or their addresses with equivalent efficiency.
//
// Areas inherit utils::Markable, which provides marking ability
// Areas inherit utils::UserData, which provides free-to-use data.
//
//////////////////////////////////////////////////////////////////////////////////////////////

public interface Area {

    // Unique id > 0 of this Area. Range = 1 .. Map::areas().size()
	// this == Map::getArea(id())
	// id() == Map::GetMiniTile(w).AreaId() for each walkable MiniTile w in this Area.
	// Area::ids are guaranteed to remain unchanged.
    public abstract AreaId getId();

	// Unique id > 0 of the group of areas which are accessible from this Area.
	// For each pair (a, b) of areas: a->groupId() == b->groupId()  <==>  a->accessibleFrom(b)
	// A groupId uniquely identifies a maximum set of mutually accessible areas, that is, in the absence of blocking getChokePoints, a continent.
    public abstract GroupId getGroupId();

    public abstract TilePosition getTopLeft();

    public abstract TilePosition getBottomRight();

    public abstract TilePosition getBoundingBoxSize();

    // Position of the MiniTile with the highest Altitude() value.
    public abstract WalkPosition getWalkPositionWithHighestAltitude();

	// Returns Map::GetMiniTile(top()).Altitude().
    public abstract Altitude getHighestAltitude();

    /**
     * This most accurately defines the size of this Area.
     */
    public abstract int getMiniTileCount();

	// Returns the percentage of low ground Tiles in this Area.
    public abstract int getLowGroundPercentage();

	// Returns the percentage of high ground Tiles in this Area.
    public abstract int getHighGroundPercentage();

	// Returns the percentage of very high ground Tiles in this Area.
    public abstract int getVeryHighGroundPercentage();

	// Returns the getChokePoints between this Area and the neighbouring ones.
	// Note: if there are no neighbouring areas, then an empty set is returned.
	// Note there may be more getChokePoints returned than the number of neighbouring areas, as there may be several getChokePoints between two areas (Cf. getChokePoints(const Area * pArea)).
    public abstract List<ChokePoint> getChokePoints();

	// Returns the getChokePoints between this Area and pArea.
	// Assumes pArea is a neighbour of this Area, i.e. getChokePointsByArea().find(pArea) != getChokePointsByArea().end()
	// Note: there is always at least one ChokePoint between two neighbouring areas.
    public abstract List<ChokePoint> getChokePoints(Area area);

	// Returns the getChokePoints of this Area grouped by neighbouring areas
	// Note: if there are no neighbouring areas, than an empty set is returned.
	public abstract AbstractMap<Area, List<ChokePoint>> getChokePointsByArea();

	// Returns the accessible neighbouring areas.
	// The accessible neighbouring areas are a subset of the neighbouring areas (the neighbouring areas can be iterated using getChokePointsByArea()).
	// Two neighbouring areas are accessible from each over if at least one the getChokePoints they share is not blocked (Cf. ChokePoint::blocked).
	public abstract List<Area> getAccessibleNeighbors();

	// Returns whether this Area is accessible from pArea, that is, if they share the same groupId().
	// Note: accessibility is always symmetrical.
	// Note: even if a and b are neighbouring areas,
	//       we can have: a->accessibleFrom(b)
	//       and not:     contains(a->AccessibleNeighbours(), b)
	// See also groupId()
	public abstract boolean isAccessibleFrom(Area area);

	// Returns the minerals contained in this Area.
	// Note: only a call to Map::onMineralDestroyed(BWAPI::unit u) may change the result (by removing eventually one element).
	public abstract List<Mineral> getMinerals();

	// Returns the geysers contained in this Area.
	// Note: the result will remain unchanged.
	public abstract List<Geyser> getGeysers();

	// Returns the bases contained in this Area.
	// Note: the result will remain unchanged.
	public abstract List<Base> getBases();

}
