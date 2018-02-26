package bwem.example;

import bwem.Base;
import bwem.CheckMode;
import bwem.ChokePoint;
import bwem.MapPrinter;
import bwem.area.Area;
import bwem.area.typedef.AreaId;
import bwem.map.Map;
import bwem.tile.MiniTile;
import bwem.tile.Tile;
import bwem.tile.TileImpl;
import bwem.typedef.CPPath;
import bwem.unit.Geyser;
import bwem.unit.Mineral;
import bwem.unit.Neutral;
import bwem.unit.StaticBuilding;

import java.awt.Color;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.tuple.MutablePair;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;
import org.openbw.bwapi4j.type.UnitType;

public class MapPrinterExample {

    private final MapPrinter mapPrinter;

    public MapPrinterExample(MapPrinter mapPrinter) {
        this.mapPrinter = mapPrinter;
    }

    private boolean getZoneColorCppAlgorithmAnyOf(AbstractMap<Area, List<ChokePoint>> chokePointsByArea, AbstractMap<Integer, Color> mapZoneColor, Color color) {
        for (Area neighbor: chokePointsByArea.keySet()) {
            int neighborId = neighbor.getId().intValue();
            Color neighboringColor = mapZoneColor.get(neighborId);
            if (neighboringColor != null
                    && (Math.abs(color.getRed() - neighboringColor.getRed()) +
                    Math.abs(color.getGreen() - neighboringColor.getGreen()) < 150)) {
                return true;
            }
        }
        return false;
    }

    private Color getZoneColor(Area area, AbstractMap<Integer, Color> mapZoneColor) {
        final Random rand = new Random();
        final int zoneId = mapPrinter.showAreas ? area.getId().intValue() : area.getGroupId().intValue();
        Color color = mapZoneColor.get(zoneId);
        if (color == null) { // zoneId was not find --> insertion did occur --> we have do define the new color:
            int tries = 0;
            do {
                color = new Color(rand.nextInt(256), rand.nextInt(256), 0); // blue unused for Terrain so that Water can be easily distinguished.
                if (++tries > 100) break;
            } while (
                    // 1) color should not be too dark
                    (color.getRed() + color.getGreen() < 150) ||

                    // 2) color should differ enough from the colors of the neighboring areas
                    (mapPrinter.showAreas && getZoneColorCppAlgorithmAnyOf(area.getChokePointsByArea(), mapZoneColor, color))
            );
            mapZoneColor.put(zoneId, color);
        }

        return color;
    }

    private void printNeutral(Map theMap, Neutral n, Color col) {
        final WalkPosition delta = new WalkPosition(n.getCenter().getX() < theMap.getData().getMapData().getCenter().getX() ? +1 : -1, n.getCenter().getY() < theMap.getData().getMapData().getCenter().getY() ? +1 : -1);
        final int stackSize = mapPrinter.showStackedNeutrals ? theMap.getData().getTile(n.getTopLeft()).getStackedNeutralCount() : 1;

        for (int i = 0 ; i < stackSize ; ++i) {
            WalkPosition origin = n.getTopLeft().toWalkPosition().add(delta.multiply(new WalkPosition(i, i)));
            WalkPosition size = n.getSize().toWalkPosition();
            if (!theMap.getData().getMapData().isValid(origin) || !theMap.getData().getMapData().isValid(origin.add(size).subtract(new WalkPosition(1, 1)))) break;

            mapPrinter.rectangle(origin, origin.add(size).subtract(new WalkPosition(1, 1)), col, MapPrinter.fill_t.fill);

            if (mapPrinter.showBlockingBuildings && n.isBlocking())
                if (i < stackSize - 1) {
                    mapPrinter.point(origin, MapPrinter.CustomColor.BLOCKING_NEUTRALS.color());
                    mapPrinter.point(origin.add(size).subtract(new WalkPosition(1, 1)), MapPrinter.CustomColor.BLOCKING_NEUTRALS.color());
                    mapPrinter.point(new WalkPosition(origin.getX(), (origin.add(size).subtract(new WalkPosition(1, 1))).getY()), MapPrinter.CustomColor.BLOCKING_NEUTRALS.color());
                    mapPrinter.point(new WalkPosition((origin.add(size).subtract(new WalkPosition(1, 1))).getX(), origin.getY()), MapPrinter.CustomColor.BLOCKING_NEUTRALS.color());
                } else {
                    mapPrinter.rectangle(origin, origin.add(size).subtract(new WalkPosition(1, 1)), MapPrinter.CustomColor.BLOCKING_NEUTRALS.color());
                }
        }
    }

    public void printMap(Map theMap) {
        AbstractMap<Integer, Color> mapZoneColor = new ConcurrentHashMap<>();

        for (int y = 0; y < theMap.getData().getMapData().getWalkSize().getY(); ++y)
        for (int x = 0; x < theMap.getData().getMapData().getWalkSize().getX(); ++x) {
            WalkPosition p = new WalkPosition(x, y);
            MiniTile miniTile = theMap.getData().getMiniTile(p, CheckMode.NO_CHECK);

            Color col;
            if (miniTile.isSea()) {
                if (mapPrinter.showSeaSide && theMap.getData().isSeaWithNonSeaNeighbors(p)) col = MapPrinter.CustomColor.SEA_SIDE.color();
                else col = MapPrinter.CustomColor.SEA.color();
            } else {
                if (mapPrinter.showLakes && miniTile.isLake()) {
                    col = MapPrinter.CustomColor.LAKE.color();
                } else {
                    if (mapPrinter.showAltitude) {
                        int c = 255 - ((miniTile.getAltitude().intValue() * 255) / theMap.getMaxAltitude().intValue());
                        col = new Color(c, c, c);
                    } else {
                        col = MapPrinter.CustomColor.TERRAIN.color();
                    }

                    if (mapPrinter.showAreas || mapPrinter.showContinents)
                        if (miniTile.getAreaId().intValue() > 0) {
                            Area area = theMap.getArea(miniTile.getAreaId());
                            Color zoneColor = getZoneColor(area, mapZoneColor);
                            int red = zoneColor.getRed() * col.getRed() / 255;
                            int green = zoneColor.getGreen() * col.getGreen() / 255;
                            col = new Color(red, green, 0);
                        } else {
                            col = MapPrinter.CustomColor.TINY_AREA.color();
                        }
                }
            }

            mapPrinter.point(p, col);
        }

        if (mapPrinter.showData)
            for (int y = 0; y < theMap.getData().getMapData().getTileSize().getY(); ++y)
            for (int x = 0; x < theMap.getData().getMapData().getTileSize().getX(); ++x) {
                int data = ((TileImpl) theMap.getData().getTile(new TilePosition(x, y))).getInternalData().intValue();
                int c = (((data / 1) * 1) % 256);
                Color col = new Color(c, c, c);
                WalkPosition origin = (new TilePosition(x, y)).toWalkPosition();
                mapPrinter.rectangle(origin, origin.add(new WalkPosition(3, 3)), col, MapPrinter.fill_t.fill);
            }

        if (mapPrinter.showUnbuildable)
            for (int y = 0; y < theMap.getData().getMapData().getTileSize().getY(); ++y)
            for (int x = 0; x < theMap.getData().getMapData().getTileSize().getX(); ++x)
                if (!theMap.getData().getTile(new TilePosition(x, y)).isBuildable()) {
                    WalkPosition origin = (new TilePosition(x, y)).toWalkPosition();
                    mapPrinter.rectangle(origin.add(new WalkPosition(1, 1)), origin.add(new WalkPosition(2, 2)), MapPrinter.CustomColor.UNBUILDABLE.color());
                }

        if (mapPrinter.showGroundHeight)
            for (int y = 0; y < theMap.getData().getMapData().getTileSize().getY(); ++y)
            for (int x = 0; x < theMap.getData().getMapData().getTileSize().getX(); ++x) {
                Tile.GroundHeight groundHeight = theMap.getData().getTile(new TilePosition(x, y)).getGroundHeight();
                if (groundHeight.intValue() >= Tile.GroundHeight.HIGH_GROUND.intValue())
                    for (int dy = 0; dy < 4; ++dy)
                    for (int dx = 0; dx < 4; ++dx) {
                        WalkPosition p = (new TilePosition(x, y).toWalkPosition()).add(new WalkPosition(dx, dy));
                        if (theMap.getData().getMiniTile(p, CheckMode.NO_CHECK).isWalkable()) // groundHeight is usefull only for walkable miniTiles
                            if (((dx + dy) & (groundHeight == Tile.GroundHeight.HIGH_GROUND ? 1 : 3)) != 0)
                                mapPrinter.point(p, MapPrinter.CustomColor.HIGHER_GROUND.color());
                    }
            }

        if (mapPrinter.showAssignedRessources)
            for (Area area : theMap.getAreas())
                for (Base base : area.getBases()) {
                    for (Mineral m : base.getMinerals())
                        mapPrinter.line(base.getCenter().toWalkPosition(), m.getCenter().toWalkPosition(), MapPrinter.CustomColor.BASES.color());
                    for (Geyser g : base.getGeysers())
                        mapPrinter.line(base.getCenter().toWalkPosition(), g.getCenter().toWalkPosition(), MapPrinter.CustomColor.BASES.color());
                }

        if (mapPrinter.showGeysers)
            for (Geyser g : theMap.getNeutralData().getGeysers())
                printNeutral(theMap, g, MapPrinter.CustomColor.GEYSERS.color());

        if (mapPrinter.showMinerals)
            for (Mineral m : theMap.getNeutralData().getMinerals())
                printNeutral(theMap, m, MapPrinter.CustomColor.MINERALS.color());

        if (mapPrinter.showStaticBuildings)
            for (StaticBuilding s : theMap.getNeutralData().getStaticBuildings())
                printNeutral(theMap, s, MapPrinter.CustomColor.STATIC_BUILDINGS.color());

        if (mapPrinter.showStartingLocations)
            for (TilePosition t : theMap.getData().getMapData().getStartingLocations()) {
                WalkPosition origin = t.toWalkPosition();
                WalkPosition size = UnitType.Terran_Command_Center.tileSize().toWalkPosition(); // same size for other races
                mapPrinter.rectangle(origin, origin.add(size).subtract(new WalkPosition(1, 1)), MapPrinter.CustomColor.STARTING_LOCATIONS.color(), MapPrinter.fill_t.fill);
            }

        if (mapPrinter.showBases)
            for (Area area : theMap.getAreas()) {
                for (Base base : area.getBases()) {
                    WalkPosition origin = base.getLocation().toWalkPosition();
                    WalkPosition size = UnitType.Terran_Command_Center.tileSize().toWalkPosition(); // same size for other races
                    MapPrinter.dashed_t dashMode = base.getBlockingMinerals().isEmpty() ? MapPrinter.dashed_t.not_dashed : MapPrinter.dashed_t.dashed;
                    mapPrinter.rectangle(origin, origin.add(size).subtract(new WalkPosition(1, 1)), MapPrinter.CustomColor.BASES.color(), MapPrinter.fill_t.do_not_fill, dashMode);
                }
            }

        if (mapPrinter.showChokePoints) {
            for (MutablePair<MutablePair<AreaId, AreaId>, WalkPosition> f : theMap.getRawFrontier())
                mapPrinter.point(f.getRight(), mapPrinter.showAreas ? MapPrinter.CustomColor.CHOKE_POINTS_SHOW_AREAS.color() : MapPrinter.CustomColor.CHOKE_POINTS_SHOW_CONTINENTS.color());

            for (Area area : theMap.getAreas())
                for (ChokePoint cp : area.getChokePoints()) {
                    ChokePoint.Node[] nodes = {ChokePoint.Node.END_1, ChokePoint.Node.END_2};
                    for (ChokePoint.Node n : nodes)
                        mapPrinter.square(cp.positionOfNode(n), 1, new Color(255, 0, 255), MapPrinter.fill_t.fill);
                    mapPrinter.square(cp.getCenter(), 1, new Color(0, 0, 255), MapPrinter.fill_t.fill);
                }
        }

        try {
            mapPrinter.writeImageToFile(Paths.get("map.bmp"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void pathExample(Map theMap) {
    	if (theMap.getData().getMapData().getStartingLocations().size() < 2) return;

        Random rand = new Random();

    	Color col = new Color(255, 255, 255);

    	WalkPosition a = (theMap.getData().getMapData().getStartingLocations().get(rand.nextInt(theMap.getData().getMapData().getStartingLocations().size()))).toWalkPosition();

    	WalkPosition b = a;
    	while (b.equals(a)) {
            b = (theMap.getData().getMapData().getStartingLocations().get(rand.nextInt(theMap.getData().getMapData().getStartingLocations().size()))).toWalkPosition();
        }

    //	Uncomment this to use random positions for a and b:
    //	a = WalkPosition(theMap.RandomPosition());
    //	b = WalkPosition(theMap.RandomPosition());

    	mapPrinter.circle(a, 6, col, MapPrinter.fill_t.fill);
    	mapPrinter.circle(b, 6, col, MapPrinter.fill_t.fill);

    	MutableInt length = new MutableInt(0);
    	CPPath path = theMap.getPath(a.toPosition(), b.toPosition(), length);

    	if (length.intValue() < 0) return;		// cannot reach b from a

    	if (path.isEmpty()) { // no ChokePoint between a and b:
    		// let's verify that a and b are in the same Area:
//    		bwem_assert(theMap.getNearestArea(a) == theMap.getNearestArea(b));
            if (!(theMap.getNearestArea(a).equals(theMap.getNearestArea(b)))) {
                throw new IllegalStateException();
            }

    		// just draw a single line between them:
    		mapPrinter.line(a, b, col, MapPrinter.dashed_t.dashed);
    	} else { // at least one ChokePoint between a and b:
    		// let's verify that a and b are not in the same Area:
//    		bwem_assert(theMap.getNearestArea(a) != theMap.getNearestArea(b));
            if (theMap.getNearestArea(a).equals(theMap.getNearestArea(b))) {
                throw new IllegalStateException();
            }

    		// draw a line between each ChokePoint in path:
    		ChokePoint cpPrevious = null;
    		for (ChokePoint cp : path) {
    			if (cpPrevious != null) {
                    mapPrinter.line(cpPrevious.getCenter(), cp.getCenter(), col, MapPrinter.dashed_t.dashed);
                }
    			mapPrinter.circle(cp.getCenter(), 6, col);
    			cpPrevious = cp;
    		}

    		mapPrinter.line(a, path.get(0).getCenter(), col, MapPrinter.dashed_t.dashed);
    		mapPrinter.line(b, path.get(path.size() - 1).getCenter(), col, MapPrinter.dashed_t.dashed);
    	}

        //TODO: Handle exception.
        try {
            mapPrinter.writeImageToFile(Paths.get("map.bmp"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
