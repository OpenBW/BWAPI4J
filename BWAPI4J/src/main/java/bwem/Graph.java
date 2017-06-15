package bwem;

import java.util.ArrayList;
import java.util.List;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;

//TODO
public class Graph {

    private Map map = null;
    private List<Area> areas;

    private Graph() {}

    public Graph(Map map) {
        this.map = map;
        this.areas = new ArrayList<>();
    }

    public Map getMap() {
        return this.map;
    }

    public Area getArea(int id) {
        //bwem_assert(Valid(id))
        if (!isValid(id)) {
            throw new IllegalArgumentException();
        }
        return this.areas.get(id - 1);
    }

    public Area getArea(TilePosition p) {
        int id = this.map.getTile(p).getAreaId();
        return getArea(id);
    }

    public Area getArea(WalkPosition p) {
        int id = this.map.getMiniTile(p).getAreaId();
        return getArea(id);
    }

//    template<class TPosition>
//    const Area * Graph::GetNearestArea(TPosition p) const
//    {
//        typedef typename TileOfPosition<TPosition>::type Tile_t;
//        if (const Area * area = GetArea(p)) return area;
//
//        p = GetMap()->BreadthFirstSearch(p,
//                        [this](const Tile_t & t, TPosition) { return t.AreaId() > 0; },	// findCond
//                        [](const Tile_t &,       TPosition) { return true; });			// visitCond
//
//        return GetArea(p);
//    }
    //TODO
    public Area getNearestArea(TilePosition pos) {
        Area area = getArea(pos);
        if (area != null) {
            return area;
        }

        pos = getMap().breadFirstSearch(
            pos,
            new Pred() {
                @Override
                public boolean is(Object... args) {
                    Object ttile = args[0];
                    if (ttile instanceof Tile) {
                        Tile tile = (Tile) ttile;
                        return (tile.getAreaId() > 0);
                    } else if (ttile instanceof MiniTile) {
                        MiniTile tile = (MiniTile) ttile;
                        return (tile.getAreaId() > 0);
                    } else {
                        throw new IllegalArgumentException("tile type not supported");
                    }
                }
            },
            new Pred() {
                @Override
                public boolean is(Object... args) {
                    return true;
                }
            }
        );

        return getArea(pos);
    }

    private boolean isValid(int id) {
        return (id >= 1) && (this.areas.size() >= id);
    }

}
