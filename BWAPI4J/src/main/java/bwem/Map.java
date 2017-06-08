package bwem;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openbw.bwapi4j.BW;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;

public class Map {

    private static final Logger logger = LogManager.getLogger();

    private static final Map INSTANCE = new Map();

    private BW bw = null;

    private int m_size = 0;
    private TilePosition m_Size = null;

    private int m_walkSize = 0;
    private WalkPosition m_WalkSize = null;

    private Position m_center = null;
    private List<Tile> m_Tiles = null;
    private List<MiniTile> m_MiniTiles = null;

    private List<TilePosition> m_StartingLocations = null;

    private Map() {

    }

    public static Map Instance() {
        return INSTANCE;
    }

    /* mapImpl.cpp:68:void MapImpl::Initialize() */
    public void Initialize(BW bw) {
        this.bw = bw;

        this.m_Tiles = new ArrayList<>();
        this.m_MiniTiles = new ArrayList<>();
        this.m_StartingLocations = new ArrayList<>();

        m_Size = new TilePosition(this.bw.getBWMap().mapWidth(), this.bw.getBWMap().mapHeight());
        m_size = Size().getX() * Size().getY();

        m_WalkSize = new WalkPosition(Size());
        m_walkSize = WalkSize().getX() * WalkSize().getY();

        m_center = new Position(Size().getX() / 2, Size().getY() / 2);

        for (TilePosition tilePosition : this.bw.getBWMap().getStartLocations()) {
            m_StartingLocations.add(tilePosition);
        }

        LoadData(); // TODO

        // TODO
    }

    public boolean Initialized() {
        return (m_size != 0);
    }

    public TilePosition Size() {
        /* map.h:92:const BWAPI::TilePosition & Size() const { return m_Size; } */
        /* bwapi4j.TilePosition is not immutable. */
        return new TilePosition(m_Size.getX(), m_Size.getY());
    }

    public WalkPosition WalkSize() {
        /* map.h:95:const BWAPI::WalkPosition & WalkSize() const { return m_WalkSize; } */
        /* bwapi4j.WalkPosition is not immutable. */
        return new WalkPosition(m_WalkSize.getX(), m_WalkSize.getY());
    }

    public Position Center() {
        /* map.h:98:const BWAPI::Position & Center() const { return m_center; }
        /* bwapi4j.Position is not immutable. */
        return new Position(m_center.getX(), m_center.getY());
    }

    // Returns a random position in the Map in pixels.
    //TODO: BWAPI::Position	RandomPosition() const;

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
        return m_Tiles.get(Size().getX() * p.getY() + p.getX());
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
        return (p.getX() >= 0 && p.getX() < Size().getX()
                && p.getY() >= 0 && p.getY() < Size().getY());
    }

    public boolean Valid(WalkPosition p) {
        return (p.getX() >= 0 && p.getX() < WalkSize().getX()
                && p.getY() >= 0 && p.getY() < WalkSize().getY());
    }

    public boolean Valid(Position p) {
        return Valid(new TilePosition(p.getX() / TilePosition.SIZE_IN_PIXELS, p.getY() / TilePosition.SIZE_IN_PIXELS));
    }

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
        for (int y = 0 ; y < Size().getY() ; y++)
        for (int x = 0 ; x < Size().getX() ; x++) {
            TilePosition t = new TilePosition(x, y);
//            if (broodwar.getBWMap().isBuildable(t)) { // TODO: isBuildable is not a member yet.
//                // TODO
//            }
        }
    }

}
