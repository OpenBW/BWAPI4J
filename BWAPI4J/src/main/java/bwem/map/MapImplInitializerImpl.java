package bwem.map;

import org.openbw.bwapi4j.BWMap;
import org.openbw.bwapi4j.MapDrawer;
import org.openbw.bwapi4j.Player;
import org.openbw.bwapi4j.unit.MineralPatch;
import org.openbw.bwapi4j.unit.Unit;
import org.openbw.bwapi4j.unit.VespeneGeyser;

import java.util.Collection;
import java.util.List;

public class MapImplInitializerImpl extends MapImpl implements MapInitializer {

    public MapImplInitializerImpl(
            BWMap bwMap,
            MapDrawer mapDrawer,
            Collection<Player> players,
            List<MineralPatch> mineralPatches,
            List<VespeneGeyser> vespeneGeysers,
            Collection<Unit> units
    ) {
        super(bwMap, mapDrawer, players, mineralPatches, vespeneGeysers, units);
    }

}
