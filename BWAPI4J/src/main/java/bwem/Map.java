package bwem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openbw.bwapi4j.BW;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;

public class Map {

    private static final Logger logger = LogManager.getLogger();

    private static final Map m_gInstance = new Map();

    private BW bw = null;

    /* Note: originals were m_size and m_Size */
    private int m_tileSize = 0;
    private TilePosition m_TileSize = null;

    private int m_walkSize = 0;
    private WalkPosition m_WalkSize = null;

    private int m_pixelSize = 0;
    private Position m_PixelSize = null;

    private Position m_center = null;
    private List<Tile> m_Tiles = null;
    private List<MiniTile> m_MiniTiles = null;

    private List<TilePosition> m_StartingLocations = null;

    private Random rng;

    private Map() {
        this.rng = new Random();
    }

    public static Map Instance() {
        return m_gInstance;
    }

    /* mapImpl.cpp:68:void MapImpl::Initialize() */
    public void Initialize(BW bw) {
        this.bw = bw;

        this.m_Tiles = new ArrayList<>();
        this.m_MiniTiles = new ArrayList<>();
        this.m_StartingLocations = new ArrayList<>();

        m_TileSize = new TilePosition(this.bw.getBWMap().mapWidth(), this.bw.getBWMap().mapHeight());
        m_tileSize = TileSize().getX() * TileSize().getY();

        m_WalkSize = new WalkPosition(TileSize());
        m_walkSize = WalkSize().getX() * WalkSize().getY();

        m_PixelSize = new Position(TileSize().getX() * TilePosition.SIZE_IN_PIXELS, TileSize().getY() * TilePosition.SIZE_IN_PIXELS);
        m_pixelSize = PixelSize().getX() * PixelSize().getY();

        m_center = new Position(PixelSize().getX() / 2, PixelSize().getY() / 2);

        for (TilePosition tilePosition : this.bw.getBWMap().getStartLocations()) {
            m_StartingLocations.add(tilePosition);
        }

        LoadData(); // TODO

        // TODO
    }

    public boolean Initialized() {
        return (m_tileSize != 0);
    }



//////////////////////////////////////////////////////////////////////
/// map.h:75
//////////////////////////////////////////////////////////////////////

//	// Returns the status of the automatic path update (off (false) by default).
//	// When on, each time a blocking Neutral (either Mineral or StaticBuilding) is destroyed,
//	// any information relative to the paths through the Areas is updated accordingly.
//	// For this to function, the Map still needs to be informed of such destructions
//	// (by calling OnMineralDestroyed and OnStaticBuildingDestroyed).
//	virtual bool						AutomaticPathUpdate() const = 0;
//
//	// Enables the automatic path update (Cf. AutomaticPathUpdate()).
//	// One might NOT want to call this function, in order to make the accessibility between Areas remain the same throughout the game.
//	// Even in this case, one should keep calling OnMineralDestroyed and OnStaticBuildingDestroyed.
//	virtual void						EnableAutomaticPathAnalysis() const = 0;
//
//	// Tries to assign one Base for each starting Location in StartingLocations().
//	// Only nearby Bases can be assigned (Cf. detail::max_tiles_between_StartingLocation_and_its_AssignedBase).
//	// Each such assigned Base then has Starting() == true, and its Location() is updated.
//	// Returns whether the function succeeded (a fail may indicate a failure in BWEM's Base placement analysis
//	// or a suboptimal placement in one of the starting Locations).
//	// You normally should call this function, unless you want to compare the StartingLocations() with
//	// BWEM's suggested locations for the Bases.
//	virtual bool						FindBasesForStartingLocations() = 0;

//////////////////////////////////////////////////////////////////////



    public TilePosition TileSize() {
        /* map.h:92:const BWAPI::TilePosition & Size() const { return m_Size; } */
        /* bwapi4j.TilePosition is not immutable. */
        return new TilePosition(m_TileSize.getX(), m_TileSize.getY());
    }

    public WalkPosition WalkSize() {
        /* map.h:95:const BWAPI::WalkPosition & WalkSize() const { return m_WalkSize; } */
        /* bwapi4j.WalkPosition is not immutable. */
        return new WalkPosition(m_WalkSize.getX(), m_WalkSize.getY());
    }

    public Position PixelSize() {
        return new Position(m_PixelSize.getX(), m_PixelSize.getY());
    }

    public Position Center() {
        /* map.h:98:const BWAPI::Position & Center() const { return m_center; }
        /* bwapi4j.Position is not immutable. */
        return new Position(m_center.getX(), m_center.getY());
    }



//////////////////////////////////////////////////////////////////////
/// map.h:101
//////////////////////////////////////////////////////////////////////

    // Returns a random position in the Map in pixels.
    /* map.h:101:BWAPI::Position RandomPosition() const; */
    public Position RandomPosition() {
        int x = this.rng.nextInt(PixelSize().getX());
        int y = this.rng.nextInt(PixelSize().getY());
        return new Position(x, y);
    }

//	// Returns the maximum altitude in the whole Map (Cf. MiniTile::Altitude()).
//	virtual altitude_t					MaxAltitude() const = 0;
//
//	// Returns the number of Bases.
//	virtual int							BaseCount() const = 0;
//
//	// Returns the number of ChokePoints.
//	virtual int							ChokePointCount() const = 0;

//////////////////////////////////////////////////////////////////////



    // Returns a Tile, given its position.
    /* map.h:113:const Tile & GetTile(const BWAPI::TilePosition & p, utils::check_t checkMode = utils::check_t::check) const	{ bwem_assert((checkMode == utils::check_t::no_check) || Valid(p)); utils::unused(checkMode); return m_Tiles[Size().x * p.y + p.x]; } */
    public Tile GetTile(TilePosition p, BwemUtils.check_t checkMode) {
        if (checkMode == null) {
            checkMode = BwemUtils.check_t.check;
        }
//        assert ((checkMode == BwemUtils.check_t.no_check) || Valid(p));
        if (!((checkMode == BwemUtils.check_t.no_check) || Valid(p))) {
            throw new IllegalStateException(); /* Assertions shouldn't be used in public methods as validation even though these methods are used by BWEM's internals. */
        }
        BwemUtils.unused(checkMode);
        //TODO: The original C++ function is const. Solution: return new object?
        return m_Tiles.get(TileSize().getX() * p.getY() + p.getX());
    }

    public Tile GetTile(TilePosition p) {
        return GetTile(p, BwemUtils.check_t.check);
    }

    // Returns a MiniTile, given its position.
    /* map.h:116:const MiniTile & GetMiniTile(const BWAPI::WalkPosition & p, utils::check_t checkMode = utils::check_t::check) const	{ bwem_assert((checkMode == utils::check_t::no_check) || Valid(p)); utils::unused(checkMode); return m_MiniTiles[WalkSize().x * p.y + p.x]; } */
	public MiniTile GetMiniTile(WalkPosition wp, BwemUtils.check_t checkMode) {
        if (checkMode == null) {
            checkMode = BwemUtils.check_t.check;
        }
//        assert ((checkMode == BwemUtils.check_t.no_check) || Valid(wp));
        if (!(checkMode == null || (checkMode == BwemUtils.check_t.no_check) || Valid(wp))) {
            throw new IllegalStateException(); /* Assertions shouldn't be used in public methods as validation even though these methods are used by BWEM's internals. */
        }
        BwemUtils.unused(checkMode);
        //TODO: The original C++ function is const. Solution: return new object?
        return m_MiniTiles.get(WalkSize().getX() * wp.getY() + wp.getX());
    }

    public MiniTile GetMiniTile(WalkPosition wp) {
        return GetMiniTile(wp, BwemUtils.check_t.check);
    }

    //TODO: Delete? I do not think this is required. It is here for future reference.
    /* map.h:120 */
//    // Returns a Tile or a MiniTile, given its position.
//    // Provided as a support of generic algorithms.
//    template<class TPosition>
//    typename const utils::TileOfPosition<TPosition>::type & GetTTile(const TPosition & p, utils::check_t checkMode = utils::check_t::check) const;

    // Provides access to the internal array of Tiles.
    /* map.h:124:const std::vector<Tile> & Tiles() const { return m_Tiles; } */
    public List<Tile> Tiles() {
        //TODO: The original C++ function is const. Solution: return new object?
        return this.m_Tiles;
    }

    // Provides access to the internal array of MiniTiles.
    /* const std::vector<MiniTile> & MiniTiles() const { return m_MiniTiles; } */
    public List<MiniTile> MiniTiles() {
        //TODO: The original C++ function is const. Solution: return new object?
        return this.m_MiniTiles;
    }

    public boolean Valid(TilePosition p) {
        return (p.getX() >= 0 && p.getX() < TileSize().getX()
                && p.getY() >= 0 && p.getY() < TileSize().getY());
    }

    public boolean Valid(WalkPosition p) {
        return (p.getX() >= 0 && p.getX() < WalkSize().getX()
                && p.getY() >= 0 && p.getY() < WalkSize().getY());
    }

    public boolean Valid(Position p) {
        return Valid(new TilePosition(p.getX() / TilePosition.SIZE_IN_PIXELS, p.getY() / TilePosition.SIZE_IN_PIXELS));
    }

    private <TPosition> TPosition crop(TPosition p, int sizeX, int sizeY) {
        int x;
        int y;

        if (p instanceof TilePosition) {
            x = ((TilePosition) p).getX();
            y = ((TilePosition) p).getY();
        } else if (p instanceof WalkPosition) {
            x = ((WalkPosition) p).getX();
            y = ((WalkPosition) p).getY();
        } else if (p instanceof Position) {
            x = ((Position) p).getX();
            y = ((Position) p).getY();
        } else {
            throw new IllegalStateException("failed to determine x and y: unsupported type");
        }

        if      (x < 0) x = 0;
        else if (x >= sizeX) x = sizeX - 1;

        if      (y < 0) y = 0;
        else if (y >= sizeY) y = sizeY - 1;

        if (p instanceof TilePosition) {
            return (TPosition) new TilePosition(x, y);
        } else if (p instanceof WalkPosition) {
            return (TPosition) new WalkPosition(x, y);
        } else if (p instanceof Position) {
            return (TPosition) new Position(x, y);
        } else {
            throw new IllegalStateException("failed to create return object: unsupported type");
        }
    }

    public TilePosition Crop(TilePosition p) {
        return crop(p, TileSize().getX(), TileSize().getY());
    }

    public WalkPosition Crop(WalkPosition p) {
        return crop(p, WalkSize().getX(), WalkSize().getY());
    }

    public Position Crop(Position p) {
        return crop(p, PixelSize().getX(), PixelSize().getY());
    }

	// Returns a reference to the starting Locations.
	// Note: these correspond to BWAPI::getStartLocations().
    /* virtual const std::vector<BWAPI::TilePosition> & StartingLocations() const = 0; */
    public List<TilePosition> StartingLocations() {
        //TODO: The original C++ function is const. Solution: return new object?
        return this.m_StartingLocations;
    }



//////////////////////////////////////////////////////////////////////
/// map.h:144
//////////////////////////////////////////////////////////////////////

//	// Returns a reference to the Minerals (Cf. Mineral).
//	virtual const std::vector<std::unique_ptr<Mineral>> &			Minerals() const = 0;
//
//	// Returns a reference to the Geysers (Cf. Geyser).
//	virtual const std::vector<std::unique_ptr<Geyser>> &			Geysers() const = 0;
//
//	// Returns a reference to the StaticBuildings (Cf. StaticBuilding).
//	virtual const std::vector<std::unique_ptr<StaticBuilding>> &	StaticBuildings() const = 0;
//
//	// If a Mineral wrappers the given BWAPI unit, returns a pointer to it.
//	// Otherwise, returns nullptr.
//	virtual Mineral *					GetMineral(BWAPI::Unit u) const = 0;
//
//	// If a Geyser wrappers the given BWAPI unit, returns a pointer to it.
//	// Otherwise, returns nullptr.
//	virtual Geyser *					GetGeyser(BWAPI::Unit g) const = 0;
//
//	// Should be called for each destroyed BWAPI unit u having u->getType().isMineralField() == true
//	virtual void						OnMineralDestroyed(BWAPI::Unit u) = 0;
//
//	// Should be called for each destroyed BWAPI unit u having u->getType().isSpecialBuilding() == true
//	virtual void						OnStaticBuildingDestroyed(BWAPI::Unit u) = 0;
//
//	// Returns a reference to the Areas.
//	virtual const std::vector<Area> &	Areas() const = 0;
//
//	// Returns an Area given its id.
//	virtual const Area *				GetArea(Area::id id) const = 0;
//
//	// If the MiniTile at w is walkable and is part of an Area, returns that Area.
//	// Otherwise, returns nullptr;
//	// Note: because of the lakes, GetNearestArea should be prefered over GetArea.
//	virtual const Area *				GetArea(BWAPI::WalkPosition w) const = 0;
//
//	// If the Tile at t contains walkable sub-MiniTiles which are all part of the same Area, returns that Area.
//	// Otherwise, returns nullptr;
//	// Note: because of the lakes, GetNearestArea should be prefered over GetArea.
//	virtual const Area *				GetArea(BWAPI::TilePosition t) const = 0;
//
//	// Returns the nearest Area from w.
//	// Returns nullptr only if Areas().empty()
//	// Note: Uses a breadth first search.
//	virtual const Area *				GetNearestArea(BWAPI::WalkPosition w) const = 0;
//
//	// Returns the nearest Area from t.
//	// Returns nullptr only if Areas().empty()
//	// Note: Uses a breadth first search.
//	virtual const Area *				GetNearestArea(BWAPI::TilePosition t) const = 0;
//
//
//	// Returns a list of ChokePoints, which is intended to be the shortest walking path from 'a' to 'b'.
//	// Furthermore, if pLength != nullptr, the pointed integer is set to the corresponding length in pixels.
//	// If 'a' is not accessible from 'b', the empty Path is returned, and -1 is put in *pLength (if pLength != nullptr).
//	// If 'a' and 'b' are in the same Area, the empty Path is returned, and a.getApproxDistance(b) is put in *pLength (if pLength != nullptr).
//	// Otherwise, the function relies on ChokePoint::GetPathTo.
//	// Cf. ChokePoint::GetPathTo for more information.
//	// Note: in order to retrieve the Areas of 'a' and 'b', the function starts by calling
//	//       GetNearestArea(TilePosition(a)) and GetNearestArea(TilePosition(b)).
//	//       While this brings robustness, this could yield surprising results in the case where 'a' and/or 'b' are in the Water.
//	//       To avoid this and the potential performance penalty, just make sure GetArea(a) != nullptr and GetArea(b) != nullptr.
//	//       Then GetPath should perform very quick.
//	virtual const CPPath &				GetPath(const BWAPI::Position & a, const BWAPI::Position & b, int * pLength = nullptr) const = 0;
//
//	// Generic algorithm for breadth first search in the Map.
//	// See the several use cases in BWEM source files.
//	template<class TPosition, class Pred1, class Pred2>
//	TPosition							BreadthFirstSearch(TPosition start, Pred1 findCond, Pred2 visitCond) const;
//
//
//	// Returns the union of the geometry of all the ChokePoints. Cf. ChokePoint::Geometry()
//	virtual const std::vector<std::pair<std::pair<Area::id, Area::id>, BWAPI::WalkPosition>> & RawFrontier() const = 0;
//
//	virtual								~Map() = default;

//////////////////////////////////////////////////////////////////////



    // Computes walkability, buildability and groundHeight and doodad information, using BWAPI corresponding functions
    public void LoadData() {
        // Mark unwalkable minitiles (minitiles are walkable by default)
        for (int y = 0; y < WalkSize().getY(); y++)
        for (int x = 0; x < WalkSize().getX(); x++) {
            if (!bw.getBWMap().isWalkable(x, y)) { // For each unwalkable minitile, we also mark its 8 neighbours as not walkable.
                for (int dy = -1; dy <= 1; dy++) // According to some tests, this prevents from wrongly pretending one Marine can go by some thin path.
                for (int dx = -1; dx <= 1; dx++) {
                    WalkPosition w = new WalkPosition(x + dx, y + dy);
                    // TODO
                    if (Valid(w)) {
                        GetMiniTile(w, BwemUtils.check_t.no_check).SetWalkable(false);
                    }
                }
            }
        }

        // Mark buildable tiles (tiles are unbuildable by default)
        for (int y = 0 ; y < TileSize().getY() ; y++)
        for (int x = 0 ; x < TileSize().getX() ; x++) {
            TilePosition t = new TilePosition(x, y);
//            if (broodwar.getBWMap().isBuildable(t)) { /* isBuildable is not implemented yet. */
//                // TODO
//            }
        }
    }

    //TODO: Delete? I do not think these two are required. They are here for future reference.
    /* map.h:239 */
//    template<>
//    inline typename const Tile & Map::GetTTile<BWAPI::TilePosition>(const BWAPI::TilePosition & t, utils::check_t checkMode) const
//    {
//        return GetTile(t, checkMode);
//    }
//
//    template<>
//    inline typename const MiniTile & Map::GetTTile<BWAPI::WalkPosition>(const BWAPI::WalkPosition & w, utils::check_t checkMode) const
//    {
//        return GetMiniTile(w, checkMode);
//    }

    //TODO
    /* map.h:252 */
//    template<class TPosition, class Pred1, class Pred2>
//    inline TPosition Map::BreadthFirstSearch(TPosition start, Pred1 findCond, Pred2 visitCond) const
//    {
//        typedef typename utils::TileOfPosition<TPosition>::type Tile_t;
//        if (findCond(GetTTile(start), start)) return start;
//
//        std::vector<TPosition> Visited;
//        std::queue<TPosition> ToVisit;
//
//        ToVisit.push(start);
//        Visited.push_back(start);
//
//        while (!ToVisit.empty())
//        {
//            TPosition current = ToVisit.front();
//            ToVisit.pop();
//            for (TPosition delta : {	TPosition(-1, -1), TPosition(0, -1), TPosition(+1, -1),
//                                        TPosition(-1,  0),                   TPosition(+1,  0),
//                                        TPosition(-1, +1), TPosition(0, +1), TPosition(+1, +1)})
//            {
//                TPosition next = current + delta;
//                if (Valid(next))
//                {
//                    const Tile_t & Next = GetTTile(next, check_t::no_check);
//                    if (findCond(Next, next)) return next;
//
//                    if (visitCond(Next, next) && !contains(Visited, next))
//                    {
//                        ToVisit.push(next);
//                        Visited.push_back(next);
//                    }
//                }
//            }
//        }
//
//        bwem_assert(false);
//        return start;
//    }

}
