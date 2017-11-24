package bwem.map;

import org.openbw.bwapi4j.BWMap;

public interface AdvancedDataInit {

    public abstract void markUnwalkableMiniTiles(BWMap bwMap);

    public abstract void markBuildableTilesAndGroundHeight(BWMap bwMap);

}
