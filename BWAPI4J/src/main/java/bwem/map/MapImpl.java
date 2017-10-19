/*
Status: Incomplete
*/

package bwem.map;

import bwem.Altitude;
import bwem.area.Area;
import bwem.area.AreaId;
import bwem.tile.MiniTile;
import bwem.tile.Tile;
import bwem.unit.Geyser;
import bwem.unit.Mineral;
import bwem.unit.StaticBuilding;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.openbw.bwapi4j.BW;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;
import org.openbw.bwapi4j.unit.Unit;
import org.openbw.bwapi4j.util.Pair;

public final class MapImpl extends Map {

    private Altitude m_maxAltitude;
    private MutableBoolean m_automaticPathUpdate = new MutableBoolean(false);
    private List<Mineral> m_Minerals;
    private List<Geyser> m_Geysers;
    private List<StaticBuilding> m_StaticBuildings;
    private List<TilePosition> m_StartingLocations;
    private List<Pair<Pair<AreaId, AreaId>, WalkPosition>> m_RawFrontier;

    public MapImpl(BW bw) {
        super(bw);
    }

    @Override
    public void Initialize() {
        m_Size = new TilePosition(getBW().getBWMap().mapWidth(), getBW().getBWMap().mapHeight());
        m_size = Size().getX() * Size().getY();
        m_WalkSize = Size().toPosition().toWalkPosition();
        m_walkSize = WalkSize().getX() * WalkSize().getY();
        m_PixelSize = Size().toPosition();
        m_pixelSize = PixelSize().getX() * PixelSize().getY();
        m_center = new Position(PixelSize().getX() / 2, PixelSize().getY() / 2);

        m_Tiles = new ArrayList<>();
        for (int i = 0; i < m_size; ++i) {
            m_Tiles.add(new Tile());
        }

        m_MiniTiles = new ArrayList<>();
        for (int i = 0; i < m_walkSize; ++i) {
            m_MiniTiles.add(new MiniTile());
        }

        for (TilePosition t: getBW().getBWMap().getStartPositions()) {
            m_StartingLocations.add(t);
        }

        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public Altitude MaxAltitude() {
        return m_maxAltitude;
    }

    @Override
    public List<Pair<Pair<AreaId, AreaId>, WalkPosition>> RawFrontier() {
        return m_RawFrontier;
    }

    @Override
    public List<Tile> Tiles() {
        return m_Tiles;
    }

    @Override
    public List<MiniTile> MiniTiles() {
        return m_MiniTiles;
    }

    @Override
    public MutableBoolean AutomaticPathUpdate() {
        return m_automaticPathUpdate;
    }

    @Override
    public void EnableAutomaticPathAnalysis() {
        m_automaticPathUpdate.setTrue();
    }

    @Override
    public boolean FindBasesForStartingLocations() {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public int BaseCount() {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public int ChokePointCount() {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public List<TilePosition> StartingLocations() {
        return m_StartingLocations;
    }

    @Override
    public List<Mineral> Minerals() {
        return m_Minerals;
    }

    @Override
    public List<Geyser> Geysers() {
        return m_Geysers;
    }

    @Override
    public List<StaticBuilding> StaticBuildings() {
        return m_StaticBuildings;
    }

    @Override
    public Mineral GetMineral(Unit u) {
        for (Mineral mineral : m_Minerals) {
            if (mineral.Unit().getId() == u.getId()) {
                return mineral;
            }
        }
        return null;
    }

    @Override
    public Geyser GetGeyser(Unit g) {
        for (Geyser geyser : m_Geysers) {
            if (geyser.Unit().getId() == g.getId()) {
                return geyser;
            }
        }
        return null;
    }

    @Override
    public void OnMineralDestroyed(Unit u) {
        int i;
        for (i = 0; i < m_Minerals.size(); ++i) {
            Mineral mineral = m_Minerals.get(i);
            if (mineral.Unit().getId() == u.getId()) {
                m_Minerals.remove(i--);
                return;
            }
        }
//        bwem_assert(iMineral != m_Minerals.end());
        throw new IllegalArgumentException("Unit is not a Mineral");
    }

    @Override
    public void OnStaticBuildingDestroyed(Unit u) {
        int i;
        for (i = 0; i < m_StaticBuildings.size(); ++i) {
            StaticBuilding building = m_StaticBuildings.get(i);
            if (building.Unit().getId() == u.getId()) {
                m_StaticBuildings.remove(i--);
                return;
            }
        }
//        bwem_assert(iStaticBuilding != m_StaticBuildings.end());
        throw new IllegalArgumentException("Unit is not a StaticBuilding");
    }

    @Override
    public List<Area> Areas() {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public Area GetArea(AreaId id) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public Area GetArea(WalkPosition w) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public Area GetArea(TilePosition t) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public Area GetNearestArea(WalkPosition w) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public Area GetNearestArea(TilePosition t) {
        throw new UnsupportedOperationException("TODO");
    }

}
