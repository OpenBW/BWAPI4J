package bwem.map;

import org.openbw.bwapi4j.BWMap;

public interface AdvancedDataInit {

    public abstract void markUnwalkableMiniTiles(BWMap bwMap);

    public abstract void markBuildableTilesAndGroundHeight(BWMap bwMap);

    public abstract void decideSeasOrLakes(int lake_max_miniTiles, int lake_max_width_in_miniTiles);

}
