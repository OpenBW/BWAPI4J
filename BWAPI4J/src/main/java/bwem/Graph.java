package bwem;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.mutable.MutableInt;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.WalkPosition;

public class Graph {

    private Map map;
    private List<Area> areas;

    private Graph() {
        throw new IllegalArgumentException("Parameterless instantiation is prohibited.");
    }

    public Graph(Map map) {
        this.map = map;
        this.areas = new ArrayList<>();
    }

    public Map getMap() {
        return this.map;
    }

    public CPPath getPath(Position a, Position b, MutableInt length) {
        //TODO
        return null;
    }

    public CPPath getPath(Position a, Position b) {
        return getPath(a, b, null);
    }

    public Area getArea(Area.Id areaId) {
//        bwem_assert(Valid(id));
        if (!isValid(areaId)) {
            throw new IllegalArgumentException("id is not valid");
        }
        return this.areas.get(areaId.toInt() - 1);
    }

    public Area getArea(WalkPosition w) {
        Area.Id areaId = this.map.getMiniTile(w).getAreaId();
        return (areaId.toInt() > 0) ? getArea(areaId) : null;
    }

    public Area getNearestArea(WalkPosition p) {
        //TODO: For "getPath".
        return null;
    }

    public boolean isValid(Area.Id id) {
        return (id.toInt() >= 1 && this.areas.size() >= id.toInt());
    }

}
