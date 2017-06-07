package bwem;

import java.util.ArrayList;
import org.openbw.bwapi4j.BW;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;

public class Map {

    private BW broodwar;

    private int m_size;
    private TilePosition m_Size;

    private int m_walkSize;
    private WalkPosition m_WalkSize;

    private Position m_center;
    private ArrayList<Tile> m_Tiles;
    private ArrayList<MiniTile> m_MiniTiles;

    private ArrayList<TilePosition> m_StartingLocations;

    private Map() {}

    /* mapImpl.cpp:68:void MapImpl::Initialize() */
    public Map(BW bw) {
        broodwar = bw;

        m_Size = new TilePosition(broodwar.getBWMap().mapWidth(), broodwar.getBWMap().mapHeight());
        m_size = Size().getX() * Size().getY();

        m_WalkSize = new WalkPosition(Size());
        m_walkSize = WalkSize().getX() * WalkSize().getY();

        m_center = new Position(Size().getX() / 2, Size().getY() / 2);

        m_Tiles = new ArrayList<>();
        m_MiniTiles = new ArrayList<>();

        m_StartingLocations = new ArrayList<>();
        for (TilePosition tilePosition : broodwar.getBWMap().getStartLocations()) {
            m_StartingLocations.add(tilePosition);
        }

        LoadData(); // TODO

        // TODO
    }

    public TilePosition Size() {
        /* map.h:92:const BWAPI::TilePosition & Size() const { return m_Size; } */
        /* TilePosition is not immutable. */
        return new TilePosition(m_Size.getX(), m_Size.getY());
    }

    public WalkPosition WalkSize() {
        /* map.h:95:const BWAPI::WalkPosition & WalkSize() const { return m_WalkSize; } */
        /* WalkPosition is not immutable. */
        return new WalkPosition(m_WalkSize.getX(), m_WalkSize.getY());
    }

    public Position Center() {
        /* map.h:98:const BWAPI::Position & Center() const { return m_center; }
        /* Position is not immutable. */
        return new Position(m_center.getX(), m_center.getY());
    }

    // Returns a Tile, given its position.
    public Tile GetTile(TilePosition p, BwemUtils.check_t checkMode) {
        if (checkMode == null) {
            checkMode = BwemUtils.check_t.check;
        }
//        assert ((checkMode == BwemUtils.check_t.no_check) || Valid(p));
        if (!((checkMode == BwemUtils.check_t.no_check) || Valid(p))) {
            throw new IllegalStateException();
        }
        BwemUtils.unused(checkMode);
        return m_Tiles.get(Size().getX() * p.getY() + p.getX());
    }

    public Tile GetTile(TilePosition p) {
        return GetTile(p, BwemUtils.check_t.check);
    }

    // Returns a MiniTile, given its position.
	public MiniTile GetMiniTile(WalkPosition wp, BwemUtils.check_t checkMode) {
        if (checkMode == null) {
            checkMode = BwemUtils.check_t.check;
        }
//        assert ((checkMode == BwemUtils.check_t.no_check) || Valid(wp));
        if (!(checkMode == null || (checkMode == BwemUtils.check_t.no_check) || Valid(wp))) {
            throw new IllegalStateException(); /* Assertions shouldn't be used in public methods as validation even though these methods are used by BWEM's internals. */
        }
        BwemUtils.unused(checkMode);
        return m_MiniTiles.get(WalkSize().getX() * wp.getY() + wp.getX());
    }

    public MiniTile GetMiniTile(WalkPosition wp) {
        return GetMiniTile(wp, BwemUtils.check_t.check);
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
        // TODO: Change/optimize.
        int sizePositionX = Size().getX() * BW.TILE_SIZE;
        int sizePositionY = Size().getY() * BW.TILE_SIZE;
        return (p.getX() >= 0 && p.getX() < sizePositionX
                && p.getY() >= 0 && p.getY() < sizePositionY);
    }

    // Computes walkability, buildability and groundHeight and doodad information, using BWAPI corresponding functions
    public void LoadData() {
        // Mark unwalkable minitiles (minitiles are walkable by default)
        for (int y = 0; y < WalkSize().getY(); y++)
        for (int x = 0; x < WalkSize().getX(); x++) {
            if (!broodwar.getBWMap().isWalkable(x, y)) { // For each unwalkable minitile, we also mark its 8 neighbours as not walkable.
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
