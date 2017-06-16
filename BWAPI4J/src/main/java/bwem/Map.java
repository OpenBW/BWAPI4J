package bwem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
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

    //TODO
    public void initialize(BW bw) {
        this.bw = bw;
        this.tileSize = new TilePosition(this.bw.getBWMap().mapWidth(), this.bw.getBWMap().mapHeight());
        this.walkSize = new WalkPosition(getTileSize());
        this.pixelSize = getTileSize().toPosition();
        this.miniTiles = new ArrayList<>();
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

    public WalkPosition breadFirstSearch(WalkPosition start, Pred findCond, Pred visitCond) {
        if (findCond.is(getMiniTile(start), start)) {
            return start;
        }

        List<WalkPosition> visited = new ArrayList<>();
        Queue<WalkPosition> toVisit = new LinkedList<>();

        toVisit.add(start);
        visited.add(start);

        WalkPosition[] deltas = {
            new WalkPosition(-1, -1), new WalkPosition(0, -1), new WalkPosition(1, -1),
            new WalkPosition( 1,  0),                          new WalkPosition(1,  0),
            new WalkPosition(-1,  1), new WalkPosition(0,  1), new WalkPosition(1,  1)
        };
        while (!toVisit.isEmpty()) {
            WalkPosition current = toVisit.remove();
            for (WalkPosition delta : deltas) {
                WalkPosition next = current.add(delta);
                if (isValid(next)) {
                    MiniTile nextTile = getMiniTile(next, CheckMode.NoCheck);
                    if (findCond.is(nextTile, next)) {
                        return next;
                    }
                    if (visitCond.is(nextTile, next) && !visited.contains(next)) {
                        toVisit.add(next);
                        visited.add(next);
                    }
                }
            }
        }

//        bwem_assert(false);
        return start;
    }

}
