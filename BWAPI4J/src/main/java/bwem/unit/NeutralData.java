package bwem.unit;

import bwem.unit.Geyser;
import bwem.unit.Mineral;
import bwem.unit.StaticBuilding;

import java.util.List;

public interface NeutralData {

    public abstract List<Mineral> getMinerals();

    public abstract List<Geyser> getGeysers();

    public abstract List<StaticBuilding> getStaticBuildings();

}
