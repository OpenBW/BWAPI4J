/*
Status: Incomplete
*/

package bwem.map;

import bwem.Altitude;
import bwem.CPPath;
import bwem.check_t;
import bwem.Pred;
import bwem.area.Area;
import bwem.area.AreaId;
import bwem.tile.MiniTile;
import bwem.tile.Tile;
import bwem.unit.Geyser;
import bwem.unit.Mineral;
import bwem.unit.StaticBuilding;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableInt;
import org.openbw.bwapi4j.BW;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;
import org.openbw.bwapi4j.unit.Unit;
import org.openbw.bwapi4j.util.Pair;

public abstract class Map {

    private final BW m_pBW;
    protected int m_size = 0;
    protected TilePosition m_Size = null;
    protected int m_walkSize;
    protected WalkPosition m_WalkSize = null;
    protected int m_pixelSize;
    protected Position m_PixelSize = null;
    protected Position m_center = null;
    protected List<Tile> m_Tiles = null;
    protected List<MiniTile> m_MiniTiles = null;

    protected Map(BW bw) {
        m_pBW = bw;
    }

    protected BW getBW() {
        return m_pBW;
    }

	// This has to be called before any other function is called.
	// A good place to do this is in ExampleAIModule::onStart()
    public abstract void Initialize();

    // Will return true once Initialize() has been called.
    public boolean Initialized() {
        return (m_size != 0);
    }

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

    // Returns the union of the geometry of all the ChokePoints. Cf. ChokePoint::Geometry()
    public abstract List<Pair<Pair<AreaId, AreaId>, WalkPosition>> RawFrontier();

    // Returns the size of the Map in Tiles.
    public TilePosition Size() {
        return m_Size;
    }

    // Returns the size of the Map in MiniTiles.
    public WalkPosition WalkSize() {
        return m_WalkSize;
    }

    // Returns the size of the Map in pixels.
    public Position PixelSize() {
        return m_PixelSize;
    }

    // Returns the center of the Map in pixels.
    public Position Center() {
        return m_center;
    }

    // Returns a random position in the Map in pixels.
    public Position RandomPosition() {
        Random random = new Random();
        int x = random.nextInt(PixelSize().getX());
        int y = random.nextInt(PixelSize().getY());
        return new Position(x, y);
    }

    // Returns the maximum altitude in the whole Map (Cf. MiniTile::Altitude()).
    public abstract Altitude MaxAltitude();

    // Returns the number of Bases.
    public abstract int BaseCount();

    // Returns the number of ChokePoints.
    public abstract int ChokePointCount();

	// Returns a Tile, given its position.
	public Tile GetTile(TilePosition p, check_t checkMode) {
//        bwem_assert((checkMode == utils::check_t::no_check) || Valid(p)); utils::unused(checkMode);
        if (!(checkMode == check_t.no_check) || Valid(p)) {
            throw new IllegalArgumentException();
        }
        return m_Tiles.get(Size().getX() * p.getY() + p.getX());
    }

    public Tile GetTile(TilePosition p) {
        return GetTile(p, check_t.check);
    }

    public Tile GetTile_(TilePosition p, check_t checkMode) {
        return m_Tiles.get(Size().getX() * p.getY() + p.getX());
    }

    public Tile GetTile_(TilePosition p) {
        return GetTile_(p, check_t.check);
    }

	// Returns a MiniTile, given its position.
    public MiniTile GetMiniTile(WalkPosition p, check_t checkMode) {
//        bwem_assert((checkMode == utils::check_t::no_check) || Valid(p));
        if (!((checkMode == check_t.no_check) || Valid(p))) {
            throw new IllegalArgumentException();
        }
        return m_MiniTiles.get(WalkSize().getX() * p.getY() + p.getX());
    }

    public MiniTile GetMiniTile(WalkPosition p) {
        return GetMiniTile(p, check_t.check);
    }

    public MiniTile GetMiniTile_(WalkPosition p, check_t checkMode) {
        return m_MiniTiles.get(WalkSize().getX() * p.getY() + p.getX());
    }

    public MiniTile GetMiniTile_(WalkPosition p) {
        return GetMiniTile_(p, check_t.check);
    }

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

    // Provides access to the internal array of Tiles.
    public abstract List<Tile> Tiles();

    // Provides access to the internal array of MiniTiles.
    public abstract List<MiniTile> MiniTiles();

    public boolean Valid(TilePosition p) {
        return (0 <= p.getX()) && (p.getX() < Size().getX()) && (0 <= p.getY()) && (p.getY() < Size().getY());
    }

    public boolean Valid(WalkPosition p) {
        return (0 <= p.getX()) && (p.getX() < WalkSize().getX()) && (0 <= p.getY()) && (p.getY() < WalkSize().getY());
    }

    public boolean Valid(Position p) {
        return Valid(p.toTilePosition());
    }

    public TilePosition Crop(TilePosition t) {
        int x = t.getX();
        int y = t.getY();

        if (x < 0) x = 0;
        else if (x >= Size().getX()) x = Size().getX() - 1;

        if (y < 0) y = 0;
        else if (y >= Size().getY()) y = Size().getY() - 1;

        return new TilePosition(x, y);
    }

    public WalkPosition Crop(WalkPosition w) {
        int x = w.getX();
        int y = w.getY();

        if (x < 0) x = 0;
        else if (x >= WalkSize().getX()) x = WalkSize().getX() - 1;

        if (y < 0) y = 0;
        else if (y >= WalkSize().getY()) y = WalkSize().getY() - 1;

        return new WalkPosition(x, y);
    }

    public Position Crop(Position p) {
        int x = p.getX();
        int y = p.getY();

        if (x < 0) x = 0;
        else if (x >= PixelSize().getX()) x = PixelSize().getX() - 1;

        if (y < 0) y = 0;
        else if (y >= PixelSize().getY()) y = PixelSize().getY() - 1;

        return new Position(x, y);
    }

	// Returns a reference to the starting Locations.
	// Note: these correspond to BWAPI::getStartLocations().
    public abstract List<TilePosition> StartingLocations();

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

    public TilePosition BreadthFirstSearch(TilePosition start, Pred findCond, Pred visitCond, boolean connect8) {
        if (findCond.is(GetTile(start), start, this)) {
            return start;
        }

        List<TilePosition> Visited = new ArrayList<>();
        Queue<TilePosition> ToVisit = new LinkedList<>();

        ToVisit.add(start);
        Visited.add(start);

        TilePosition[] dir8 = {
            new TilePosition(-1, -1), new TilePosition(0, -1), new TilePosition(1, -1),
            new TilePosition(-1,  0),                          new TilePosition(1,  0),
            new TilePosition(-1,  1), new TilePosition(0,  1), new TilePosition(1,  1)
        };
        TilePosition[] dir4 = { new TilePosition(0, -1), new TilePosition(-1, 0), new TilePosition(1, 0), new TilePosition(0, 1)};
        TilePosition[] directions = connect8 ? dir8 : dir4;

        while (!ToVisit.isEmpty()) {
            TilePosition current = ToVisit.remove();
            for (TilePosition delta : directions) {
                TilePosition next = current.add(delta);
                if (Valid(next)) {
                    Tile nextTile = GetTile(next, check_t.no_check);
                    if (findCond.is(nextTile, next, this)) {
                        return next;
                    }
                    if (visitCond.is(nextTile, next, this) && !Visited.contains(next)) {
                        ToVisit.add(next);
                        Visited.add(next);
                    }
                }
            }
        }

        //TODO: ???
//        bwem_assert(false);

        return start;
    }

    public TilePosition BreadthFirstSearch(TilePosition start, Pred findCond, Pred visitCond) {
        return BreadthFirstSearch(start, findCond, visitCond, true);
    }

    public WalkPosition BreadthFirstSearch(WalkPosition start, Pred findCond, Pred visitCond, boolean connect8) {
        if (findCond.is(GetMiniTile(start), start, this)) {
            return start;
        }

        List<WalkPosition> Visited = new ArrayList<>();
        Queue<WalkPosition> ToVisit = new LinkedList<>();

        ToVisit.add(start);
        Visited.add(start);

        WalkPosition[] dir8 = {
            new WalkPosition(-1, -1), new WalkPosition(0, -1), new WalkPosition(1, -1),
            new WalkPosition(-1,  0),                          new WalkPosition(1,  0),
            new WalkPosition(-1,  1), new WalkPosition(0,  1), new WalkPosition(1,  1)
        };
        WalkPosition[] dir4 = {new WalkPosition(0, -1), new WalkPosition(-1, 0), new WalkPosition(1, 0), new WalkPosition(0, 1)};
        WalkPosition[] directions = connect8 ? dir8 : dir4;

        while (!ToVisit.isEmpty()) {
            WalkPosition current = ToVisit.remove();
            for (WalkPosition delta : directions) {
                WalkPosition next = current.add(delta);
                if (Valid(next)) {
                    MiniTile Next = GetMiniTile(next, check_t.no_check);
                    if (findCond.is(Next, next, this)) {
                        return next;
                    }
                    if (visitCond.is(Next, next, this) && !Visited.contains(next)) {
                        ToVisit.add(next);
                        Visited.add(next);
                    }
                }
            }
        }

        //TODO: ???
//        bwem_assert(false);

        return start;
    }

    public WalkPosition BreadthFirstSearch(WalkPosition start, Pred findCond, Pred visitCond) {
        return BreadthFirstSearch(start, findCond, visitCond, true);
    }

}
