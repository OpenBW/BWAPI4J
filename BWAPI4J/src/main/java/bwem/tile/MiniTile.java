package bwem.tile;

import bwem.typedef.Altitude;
import bwem.area.typedef.AreaId;

//////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                          //
//                                  class MiniTile
//                                                                                          //
//////////////////////////////////////////////////////////////////////////////////////////////
//
// Corresponds to BWAPI/Starcraft's concept of minitile (8x8 pixels).
// MiniTiles are accessed using WalkPositions (Cf. Map::GetMiniTile).
// A Map holds Map::WalkSize().x * Map::WalkSize().y MiniTiles as its "MiniTile map".
// A MiniTile contains essentialy 3 informations:
//	- its Walkability
//	- its altitude (distance from the nearest non walkable MiniTile, except those which are part of small enough zones (lakes))
//	- the id of the Area it is part of, if ever.
// The whole process of analysis of a Map relies on the walkability information
// from which are derived successively : altitudes, Areas, ChokePoints.
//
//////////////////////////////////////////////////////////////////////////////////////////////

public interface MiniTile {

	// Corresponds approximatively to BWAPI::isWalkable
	// The differences are:
	//  - For each BWAPI's unwalkable MiniTile, we also mark its 8 neighbours as not walkable.
	//    According to some tests, this prevents from wrongly pretending one small unit can go by some thin path.
	//  - The relation buildable ==> walkable is enforced, by marking as walkable any MiniTile part of a buildable Tile (Cf. Tile::Buildable)
	// Among the MiniTiles having Altitude() > 0, the walkable ones are considered Terrain-MiniTiles, and the other ones Lake-MiniTiles.
    public abstract boolean isWalkable();

	// Distance in pixels between the center of this MiniTile and the center of the nearest Sea-MiniTile
	// Sea-MiniTiles all have their Altitude() equal to 0.
	// MiniTiles having Altitude() > 0 are not Sea-MiniTiles. They can be either Terrain-MiniTiles or Lake-MiniTiles.
    public abstract Altitude getAltitude();

    // Sea-MiniTiles are unwalkable MiniTiles that have their Altitude() equal to 0.
    public abstract boolean isSea();

	// Lake-MiniTiles are unwalkable MiniTiles that have their Altitude() > 0.
	// They form small zones (inside Terrain-zones) that can be eaysily walked around (e.g. Starcraft's doodads)
	// The intent is to preserve the continuity of altitudes inside Areas.
    public abstract boolean isLake();

    // Terrain MiniTiles are just walkable MiniTiles
    public abstract boolean isTerrain();

	// For Sea and Lake MiniTiles, returns 0
	// For Terrain MiniTiles, returns a non zero id:
	//    - if (id > 0), id uniquely identifies the Area A that contains this MiniTile.
	//      Moreover we have: A.Id() == id and Map::GetArea(id) == A
	//      For more information about positive Area::ids, see Area::Id()
	//    - if (id < 0), then this MiniTile is part of a Terrain-zone that was considered too small to create an Area for it.
	//      Note: negative Area::ids start from -2
	// Note: because of the lakes, Map::GetNearestArea should be prefered over Map::GetArea.
    public abstract AreaId getAreaId();

}
