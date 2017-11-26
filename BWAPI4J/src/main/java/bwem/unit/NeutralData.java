package bwem.unit;

import bwem.unit.Geyser;
import bwem.unit.Mineral;
import bwem.unit.StaticBuilding;
import org.openbw.bwapi4j.unit.Unit;

import java.util.List;

public interface NeutralData {

    // Returns a reference to the Minerals (Cf. Mineral).
    public abstract List<Mineral> getMinerals();

    // If a Mineral wrappers the given BWAPI unit, returns a pointer to it.
    // Otherwise, returns nullptr.
    public abstract Mineral getMineral(Unit u);

    // Returns a reference to the Geysers (Cf. Geyser).
    public abstract List<Geyser> getGeysers();

    // If a Geyser wrappers the given BWAPI unit, returns a pointer to it.
    // Otherwise, returns nullptr.
    public abstract Geyser getGeyser(Unit g);

    // Returns a reference to the StaticBuildings (Cf. StaticBuilding).
    public abstract List<StaticBuilding> getStaticBuildings();


}
