package bwem.map;

import org.openbw.bwapi4j.BWMap;

public interface AdvancedDataInitializer {

    void markUnwalkableMiniTiles(BWMap bwMap);

    void markBuildableTilesAndGroundHeight(BWMap bwMap);

    void decideSeasOrLakes(int lakeMaxMiniTiles, int lakeMaxWidthInMiniTiles);

}
