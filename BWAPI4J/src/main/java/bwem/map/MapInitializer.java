////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (C) 2017-2018 OpenBW Team
//
//    This file is part of BWAPI4J.
//
//    BWAPI4J is free software: you can redistribute it and/or modify
//    it under the terms of the Lesser GNU General Public License as published 
//    by the Free Software Foundation, version 3 only.
//
//    BWAPI4J is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with BWAPI4J.  If not, see <http://www.gnu.org/licenses/>.
//
////////////////////////////////////////////////////////////////////////////////

package bwem.map;

import bwem.area.TempAreaInfo;
import bwem.area.typedef.AreaId;
import bwem.tile.MiniTile;
import bwem.typedef.Altitude;
import bwem.unit.Mineral;
import bwem.unit.Neutral;
import bwem.unit.StaticBuilding;
import org.apache.commons.lang3.tuple.MutablePair;
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
    void initialize(boolean enableTimer);

    void initializeAdvancedData(int mapTileWidth, int mapTileHeight, List<TilePosition> startingLocations);

    void initializeNeutralData(
            List<MineralPatch> mineralPatches,
            List<VespeneGeyser> vespeneGeysers,
            List<PlayerUnit> neutralUnits
    );

    void computeAltitude(AdvancedData advancedData);
    List<MutablePair<WalkPosition, Altitude>> getSortedDeltasByAscendingAltitude(int mapWalkTileWidth, int mapWalkTileHeight, int altitudeScale);
    List<MutablePair<WalkPosition, Altitude>> getActiveSeaSideList(AdvancedData advancedData);
    Altitude setAltitudesAndGetUpdatedHighestAltitude(
            Altitude currentHighestAltitude,
            AdvancedData advancedData,
            List<MutablePair<WalkPosition, Altitude>> deltasByAscendingAltitude,
            List<MutablePair<WalkPosition, Altitude>> activeSeaSideList,
            int altitudeScale
    );
    void setHighestAltitude(Altitude altitude);

    void processBlockingNeutrals(List<Neutral> candidates);
    List<Neutral> getCandidates(List<StaticBuilding> staticBuildings, List<Mineral> minerals);
    List<WalkPosition> getOuterMiniTileBorderOfNeutral(Neutral pCandidate);
    List<WalkPosition> trimOuterMiniTileBorder(List<WalkPosition> border);
    List<WalkPosition> getDoors(List<WalkPosition> border);
    List<WalkPosition> getTrueDoors(List<WalkPosition> doors, Neutral pCandidate);
    void markBlockingStackedNeutrals(Neutral pCandidate, List<WalkPosition> trueDoors);

    void computeAreas(List<TempAreaInfo> tempAreaList, int areaMinMiniTiles);
    List<MutablePair<WalkPosition, MiniTile>> getSortedMiniTilesByDescendingAltitude();
    List<TempAreaInfo> computeTempAreas(List<MutablePair<WalkPosition, MiniTile>> miniTilesByDescendingAltitude);
    void replaceAreaIds(WalkPosition p, AreaId newAreaId);
    void createAreas(List<TempAreaInfo> tempAreaList, int areaMinMiniTiles);
    void setLowestAltitudeInTile(TilePosition t);

    List<PlayerUnit> filterPlayerUnits(Collection<Unit> units, Player player);
    List<PlayerUnit> filterNeutralPlayerUnits(Collection<Unit> units, Collection<Player> players);

}
