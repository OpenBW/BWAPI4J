package bwem.map;

import bwem.MapPrinter;
import bwem.area.Area;
import bwem.area.typedef.AreaId;
import bwem.check_t;
import bwem.tile.MiniTile;
import bwem.tile.Tile;
import bwem.typedef.Altitude;
import bwem.typedef.CPPath;
import bwem.typedef.Pred;
import bwem.unit.Geyser;
import bwem.unit.Mineral;
import bwem.unit.StaticBuilding;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.tuple.MutablePair;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;
import org.openbw.bwapi4j.type.Color;
import org.openbw.bwapi4j.unit.Unit;

import java.util.List;

//////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                          //
//                                  class Map
//                                                                                          //
//////////////////////////////////////////////////////////////////////////////////////////////
//
// Map is the entry point:
//	- to access general information on the Map
//	- to access the Tiles and the MiniTiles
//	- to access the Areas
//	- to access the StartingLocations
//	- to access the Minerals, the Geysers and the StaticBuildings
//	- to parametrize the analysis process
//	- to update the information
// Map also provides some useful tools such as Paths between ChokePoints and generic algorithms like BreadthFirstSearch
//
// Map functionnality is provided through its singleton Map::Instance().
//
//////////////////////////////////////////////////////////////////////////////////////////////

public interface Map {

    public abstract MapPrinter getMapPrinter();

	// This has to be called before any other function is called.
	// A good place to do this is in ExampleAIModule::onStart()
    public abstract void Initialize();

    // Will return true once Initialize() has been called.
    public abstract boolean Initialized();

	// Returns the status of the automatic path update (off (false) by default).
	// When on, each time a blocking Neutral (either Mineral or StaticBuilding) is destroyed,
	// any information relative to the paths through the Areas is updated accordingly.
	// For this to function, the Map still needs to be informed of such destructions
	// (by calling OnMineralDestroyed and OnStaticBuildingDestroyed).
    public abstract MutableBoolean AutomaticPathUpdate();

	// Enables the automatic path update (Cf. AutomaticPathUpdate()).
	// One might NOT want to call this function, in order to make the accessibility between Areas remain the same throughout the game.
	// Even in this case, one should keep calling OnMineralDestroyed and OnStaticBuildingDestroyed.
    public abstract void EnableAutomaticPathAnalysis();

	// Tries to assign one Base for each starting Location in StartingLocations().
	// Only nearby Bases can be assigned (Cf. detail::max_tiles_between_StartingLocation_and_its_AssignedBase).
	// Each such assigned Base then has Starting() == true, and its Location() is updated.
	// Returns whether the function succeeded (a fail may indicate a failure in BWEM's Base placement analysis
	// or a suboptimal placement in one of the starting Locations).
	// You normally should call this function, unless you want to compare the StartingLocations() with
	// BWEM's suggested locations for the Bases.
    public abstract boolean FindBasesForStartingLocations();

    // Returns the maximum altitude in the whole Map (Cf. MiniTile::Altitude()).
    public abstract Altitude MaxAltitude();

    // Returns the number of Bases.
    public abstract int BaseCount();

    // Returns the number of ChokePoints.
    public abstract int ChokePointCount();

	// Returns a Tile, given its position.
	public abstract Tile GetTile(TilePosition p, check_t checkMode);

    public abstract Tile GetTile(TilePosition p);

    public abstract Tile GetTile_(TilePosition p, check_t checkMode);

    public abstract Tile GetTile_(TilePosition p);

	// Returns a MiniTile, given its position.
    public abstract MiniTile GetMiniTile(WalkPosition p, check_t checkMode);

    public abstract MiniTile GetMiniTile(WalkPosition p);

    public abstract MiniTile GetMiniTile_(WalkPosition p, check_t checkMode);

    public abstract MiniTile GetMiniTile_(WalkPosition p);

    // Provides access to the internal array of Tiles.
    public abstract List<Tile> Tiles();

    // Provides access to the internal array of MiniTiles.
    public abstract List<MiniTile> MiniTiles();

    // Returns a reference to the Minerals (Cf. Mineral).
    public abstract List<Mineral> Minerals();

    // Returns a reference to the Geysers (Cf. Geyser).
    public abstract List<Geyser> Geysers();

    // Returns a reference to the StaticBuildings (Cf. StaticBuilding).
    public abstract List<StaticBuilding> StaticBuildings();

	// If a Mineral wrappers the given BWAPI unit, returns a pointer to it.
	// Otherwise, returns nullptr.
    public abstract Mineral GetMineral(Unit u);

	// If a Geyser wrappers the given BWAPI unit, returns a pointer to it.
	// Otherwise, returns nullptr.
    public abstract Geyser GetGeyser(Unit g);

    /**
     * Alternative handler for destroyed unit tracking. Not present in BWEM 1.4.1 C++.
     */
    public abstract void OnUnitDestroyed(Unit u);

    // Should be called for each destroyed BWAPI unit u having u->getType().isMineralField() == true
    public abstract void OnMineralDestroyed(Unit u);

    // Should be called for each destroyed BWAPI unit u having u->getType().isSpecialBuilding() == true
    public abstract void OnStaticBuildingDestroyed(Unit u);

    // Should be called for each destroyed BWAPI unit u having u->getType().isSpecialBuilding() == true
    public abstract List<Area> Areas();

    // Returns a reference to the Areas.
    public abstract Area GetArea(AreaId id);

	// If the MiniTile at w is walkable and is part of an Area, returns that Area.
	// Otherwise, returns nullptr;
	// Note: because of the lakes, GetNearestArea should be prefered over GetArea.
    public abstract Area GetArea(WalkPosition w);

	// If the Tile at t contains walkable sub-MiniTiles which are all part of the same Area, returns that Area.
	// Otherwise, returns nullptr;
	// Note: because of the lakes, GetNearestArea should be prefered over GetArea.
    public abstract Area GetArea(TilePosition t);

	// Returns the nearest Area from w.
	// Returns nullptr only if Areas().empty()
	// Note: Uses a breadth first search.
    public abstract Area GetNearestArea(WalkPosition w);

	// Returns the nearest Area from t.
	// Returns nullptr only if Areas().empty()
	// Note: Uses a breadth first search.
    public abstract Area GetNearestArea(TilePosition t);

	// Returns a list of ChokePoints, which is intended to be the shortest walking path from 'a' to 'b'.
	// Furthermore, if pLength != nullptr, the pointed integer is set to the corresponding length in pixels.
	// If 'a' is not accessible from 'b', the empty Path is returned, and -1 is put in *pLength (if pLength != nullptr).
	// If 'a' and 'b' are in the same Area, the empty Path is returned, and a.getApproxDistance(b) is put in *pLength (if pLength != nullptr).
	// Otherwise, the function relies on ChokePoint::GetPathTo.
	// Cf. ChokePoint::GetPathTo for more information.
	// Note: in order to retrieve the Areas of 'a' and 'b', the function starts by calling
	//       GetNearestArea(TilePosition(a)) and GetNearestArea(TilePosition(b)).
	//       While this brings robustness, this could yield surprising results in the case where 'a' and/or 'b' are in the Water.
	//       To avoid this and the potential performance penalty, just make sure GetArea(a) != nullptr and GetArea(b) != nullptr.
	//       Then GetPath should perform very quick.
    public abstract CPPath GetPath(Position a, Position b, MutableInt pLength);

    public abstract CPPath GetPath(Position a, Position b);

	// Generic algorithm for breadth first search in the Map.
	// See the several use cases in BWEM source files.
    public abstract TilePosition BreadthFirstSearch(TilePosition start, Pred findCond, Pred visitCond, boolean connect8);

    public abstract TilePosition BreadthFirstSearch(TilePosition start, Pred findCond, Pred visitCond);

    public abstract WalkPosition BreadthFirstSearch(WalkPosition start, Pred findCond, Pred visitCond, boolean connect8);

    public abstract WalkPosition BreadthFirstSearch(WalkPosition start, Pred findCond, Pred visitCond);

    // Returns the union of the geometry of all the ChokePoints. Cf. ChokePoint::Geometry()
    public abstract List<MutablePair<MutablePair<AreaId, AreaId>, WalkPosition>> RawFrontier();

    public abstract void drawDiagonalCrossMap(Position topLeft, Position bottomRight, Color col);

}
