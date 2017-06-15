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
    private TilePosition tileSize = null;
    private WalkPosition walkSize = null;
    private Position pixelSize = null;
    private List<MiniTile> miniTiles = null;

    private Map() {
        /* Do nothing. */
    }

    public static Map Instance() {
        return INSTANCE;
    }

    public void initialize(BW bw) {
        //TODO
        this.bw = bw;
        this.miniTiles = new ArrayList<>();
        this.tileSize = new TilePosition(this.bw.getBWMap().mapWidth(), this.bw.getBWMap().mapHeight());
        this.walkSize = new WalkPosition(getTileSize());
        this.pixelSize = getTileSize().toPosition();
    }

    public TilePosition getTileSize() {
        return new TilePosition(this.tileSize.getX(), this.tileSize.getY());
    }

    public WalkPosition getWalkSize() {
        return new WalkPosition(this.walkSize.getX(), this.walkSize.getY());
    }

    public Position getPixelSize() {
        return new Position(this.pixelSize.getX(), this.pixelSize.getY());
    }

    public MiniTile getMiniTile(WalkPosition w, CheckMode checkMode) {
//        bwem_assert((checkMode == utils::check_t::no_check) || Valid(p));
        if (!(checkMode == CheckMode.NoCheck) || isValid(w)) {
            throw new IllegalArgumentException("WalkPosition is not valid");
        }
        return this.miniTiles.get(getWalkSize().getX() * w.getY() + w.getX());
    }

    public MiniTile getMiniTile(WalkPosition p) {
        return getMiniTile(p, CheckMode.Check);
    }

    public boolean isValid(WalkPosition p) {
        return (p.getX() >= 0)
                && (p.getX() < getWalkSize().getX())
                && (p.getY() >= 0)
                && (p.getY() < getWalkSize().getY());
    }

}
