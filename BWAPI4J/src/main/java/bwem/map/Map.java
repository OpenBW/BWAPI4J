package bwem.map;

import bwem.MapPrinter;
import bwem.area.Area;
import bwem.area.typedef.AreaId;
import bwem.typedef.Altitude;
import bwem.typedef.CPPath;
import bwem.typedef.Pred;
import bwem.unit.Geyser;
import bwem.unit.Mineral;
import bwem.unit.NeutralData;
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

    AdvancedData getData();

    MapPrinter getMapPrinter();

    // Will return true once initialize() has been called.
    boolean initialized();

	// Returns the status of the automatic path update (off (false) by default).
	// When on, each time a blocking Neutral (either Mineral or StaticBuilding) is destroyed,
	// any information relative to the paths through the areas is updated accordingly.
	// For this to function, the Map still needs to be informed of such destructions
	// (by calling onMineralDestroyed and onStaticBuildingDestroyed).
    MutableBoolean automaticPathUpdate();

	// Enables the automatic path update (Cf. automaticPathUpdate()).
	// One might NOT want to call this function, in order to make the accessibility between areas remain the same throughout the game.
	// Even in this case, one should keep calling onMineralDestroyed and onStaticBuildingDestroyed.
    void enableAutomaticPathAnalysis();

	// Tries to assign one Base for each starting location in StartingLocations().
	// Only nearby bases can be assigned (Cf. detail::max_tiles_between_StartingLocation_and_its_AssignedBase).
	// Each such assigned Base then has starting() == true, and its location() is updated.
	// Returns whether the function succeeded (a fail may indicate a failure in BWEM's Base placement analysis
	// or a suboptimal placement in one of the starting Locations).
	// You normally should call this function, unless you want to compare the StartingLocations() with
	// BWEM's suggested locations for the bases.
    boolean findBasesForStartingLocations();

    // Returns the maximum altitude in the whole Map (Cf. MiniTile::Altitude()).
    Altitude maxAltitude();

    // Returns the number of bases.
    int baseCount();

    // Returns the number of chokePoints.
    int chokePointCount();

    NeutralData getNeutralData();

    /**
     * Alternative handler for destroyed unit tracking. Not present in BWEM 1.4.1 C++.
     */
    void onUnitDestroyed(Unit u);

    // Should be called for each destroyed BWAPI unit u having u->getType().isMineralField() == true
    void onMineralDestroyed(Unit u);

    // Should be called for each destroyed BWAPI unit u having u->getType().isSpecialBuilding() == true
    void onStaticBuildingDestroyed(Unit u);

    // Should be called for each destroyed BWAPI unit u having u->getType().isSpecialBuilding() == true
    List<Area> areas();

    // Returns a reference to the areas.
    Area getArea(AreaId id);

	// If the MiniTile at w is walkable and is part of an Area, returns that Area.
	// Otherwise, returns nullptr;
	// Note: because of the lakes, getNearestArea should be prefered over getArea.
    Area getArea(WalkPosition w);

	// If the Tile at t contains walkable sub-miniTiles which are all part of the same Area, returns that Area.
	// Otherwise, returns nullptr;
	// Note: because of the lakes, getNearestArea should be prefered over getArea.
    Area getArea(TilePosition t);

	// Returns the nearest Area from w.
	// Returns nullptr only if areas().empty()
	// Note: Uses a breadth first search.
    Area getNearestArea(WalkPosition w);

	// Returns the nearest Area from t.
	// Returns nullptr only if areas().empty()
	// Note: Uses a breadth first search.
    Area getNearestArea(TilePosition t);

    Area getMainArea(TilePosition topLeft, TilePosition size);

	// Returns a list of chokePoints, which is intended to be the shortest walking path from 'a' to 'b'.
	// Furthermore, if pLength != nullptr, the pointed integer is set to the corresponding length in pixels.
	// If 'a' is not accessible from 'b', the empty Path is returned, and -1 is put in *pLength (if pLength != nullptr).
	// If 'a' and 'b' are in the same Area, the empty Path is returned, and a.getApproxDistance(b) is put in *pLength (if pLength != nullptr).
	// Otherwise, the function relies on ChokePoint::getPathTo.
	// Cf. ChokePoint::getPathTo for more information.
	// Note: in order to retrieve the areas of 'a' and 'b', the function starts by calling
	//       getNearestArea(TilePosition(a)) and getNearestArea(TilePosition(b)).
	//       While this brings robustness, this could yield surprising results in the case where 'a' and/or 'b' are in the Water.
	//       To avoid this and the potential performance penalty, just make sure getArea(a) != nullptr and getArea(b) != nullptr.
	//       Then getPath should perform very quick.
    CPPath getPath(Position a, Position b, MutableInt pLength);

    CPPath getPath(Position a, Position b);

	// Generic algorithm for breadth first search in the Map.
	// See the several use cases in BWEM source files.
    TilePosition breadthFirstSearch(TilePosition start, Pred findCond, Pred visitCond, boolean connect8);

    TilePosition breadthFirstSearch(TilePosition start, Pred findCond, Pred visitCond);

    WalkPosition breadthFirstSearch(WalkPosition start, Pred findCond, Pred visitCond, boolean connect8);

    WalkPosition breadthFirstSearch(WalkPosition start, Pred findCond, Pred visitCond);

    // Returns the union of the geometry of all the chokePoints. Cf. ChokePoint::geometry()
    List<MutablePair<MutablePair<AreaId, AreaId>, WalkPosition>> rawFrontier();

    void drawDiagonalCrossMap(Position topLeft, Position bottomRight, Color col);

}
