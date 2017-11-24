package bwem.map;

import bwem.area.TempAreaInfo;
import bwem.area.typedef.AreaId;
import bwem.tile.MiniTile;
import bwem.typedef.Altitude;
import bwem.unit.Geyser;
import bwem.unit.Mineral;
import bwem.unit.Neutral;
import bwem.unit.StaticBuilding;
import org.apache.commons.lang3.tuple.MutablePair;
import org.openbw.bwapi4j.BWMap;
import org.openbw.bwapi4j.Player;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;
import org.openbw.bwapi4j.unit.MineralPatch;
import org.openbw.bwapi4j.unit.PlayerUnit;
import org.openbw.bwapi4j.unit.Unit;
import org.openbw.bwapi4j.unit.VespeneGeyser;

import java.util.Collection;
import java.util.List;

public interface MapInitializer {

    // This has to be called before any other function is called.
    // A good place to do this is in ExampleAIModule::onStart()
    public abstract void Initialize();

    public abstract void compileAdvancedData(int mapTileWidth, int mapTileHeight, List<TilePosition> startingLocations);

    public abstract void initializeNeutrals(
            List<MineralPatch> mineralPatches,
            List<VespeneGeyser> vespeneGeysers,
            List<PlayerUnit> neutralUnits
    );

    public abstract void ComputeAltitude(AdvancedData advancedData);
    public abstract List<MutablePair<WalkPosition, Altitude>> getSortedDeltasByAscendingAltitude(int mapWalkTileWidth, int mapWalkTileHeight, int altitude_scale);
    public abstract List<MutablePair<WalkPosition, Altitude>> getActiveSeaSideList(MapData mapData);
    public abstract Altitude setAltitudesAndGetUpdatedMaxAltitude(
            Altitude currentMaxAltitude,
            AdvancedData advancedData,
            List<MutablePair<WalkPosition, Altitude>> DeltasByAscendingAltitude,
            List<MutablePair<WalkPosition, Altitude>> ActiveSeaSideList,
            int altitude_scale
    );
    public abstract void setMaxAltitude(Altitude altitude);

    public abstract void ProcessBlockingNeutrals(List<Neutral> Candidates);
    public abstract List<Neutral> getCandidates(List<StaticBuilding> staticBuildings, List<Mineral> minerals);
    public abstract List<WalkPosition> getOuterMiniTileBorderOfNeutral(Neutral pCandidate);
    public abstract List<WalkPosition> trimOuterMiniTileBorder(List<WalkPosition> Border);
    public abstract List<WalkPosition> getDoors(List<WalkPosition> Border);
    public abstract List<WalkPosition> getTrueDoors(List<WalkPosition> Doors, Neutral pCandidate);
    public abstract void markBlockingStackedNeutrals(Neutral pCandidate, List<WalkPosition> TrueDoors);

    public abstract void ComputeAreas(List<TempAreaInfo> TempAreaList, int area_min_miniTiles);
    public abstract List<MutablePair<WalkPosition, MiniTile>> getSortedMiniTilesByDescendingAltitude();
    public abstract List<TempAreaInfo> ComputeTempAreas(List<MutablePair<WalkPosition, MiniTile>> MiniTilesByDescendingAltitude);
    public abstract void ReplaceAreaIds(WalkPosition p, AreaId newAreaId);
    public abstract void CreateAreas(List<TempAreaInfo> TempAreaList, int area_min_miniTiles);
    public abstract void SetMinAltitudeInTile(TilePosition t);

    public abstract List<PlayerUnit> filterPlayerUnits(Collection<Unit> units, Player player);
    public abstract List<PlayerUnit> filterNeutralPlayerUnits(Collection<Unit> units, Collection<Player> players);

}
