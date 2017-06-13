package bwem;

import java.util.ArrayList;
import java.util.List;
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

    public Area getArea(int id) {
        //bwem_assert(Valid(id))
        if (!isValid(id)) {
            throw new IllegalArgumentException();
        }
        return this.areas.get(id - 1);
    }

    public Area getArea(WalkPosition w) {
        int id = this.map.getMiniTile(w).AreaId();
        return (id > 0) ? getArea(id) : null;
    }

    private boolean isValid(int id) {
        return (id >= 1) && (this.areas.size() >= id);
    }

}
