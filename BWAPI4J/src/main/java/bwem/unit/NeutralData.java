package bwem.unit;

import org.openbw.bwapi4j.unit.Unit;

import java.util.List;

public interface NeutralData {

    List<Mineral> getMinerals();

    /**
     * If a Mineral wrappers the given BWAPI unit, returns a pointer to it.
     * Otherwise, returns null.
     */
    Mineral getMineral(Unit u);

    // Returns a reference to the geysers (Cf. Geyser).
    List<Geyser> getGeysers();

    // If a Geyser wrappers the given BWAPI unit, returns a pointer to it.
    // Otherwise, returns nullptr.
    Geyser getGeyser(Unit g);

    // Returns a reference to the StaticBuildings (Cf. StaticBuilding).
    List<StaticBuilding> getStaticBuildings();

}
