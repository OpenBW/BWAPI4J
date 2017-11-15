package bwem.example;

import bwem.Base;
import bwem.ChokePoint;
import bwem.MapPrinter;
import bwem.area.Area;
import bwem.area.typedef.AreaId;
import bwem.check_t;
import bwem.map.Map;
import bwem.tile.MiniTile;
import bwem.typedef.CPPath;
import bwem.unit.Geyser;
import bwem.unit.Mineral;
import bwem.unit.Neutral;
import bwem.unit.StaticBuilding;
import bwem.util.BwemExt;
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

    private boolean getZoneColor_cpp_algorithm_any_of(AbstractMap<Area, List<ChokePoint>> ChokePointsByArea, AbstractMap<Integer, Color> map_Zone_Color, Color color) {
        for (Area neighbor: ChokePointsByArea.keySet()) {
            int neighborId = neighbor.Id().intValue();
            Color neighboringColor = map_Zone_Color.get(neighborId);
            if (neighboringColor != null
                    && (Math.abs(color.getRed() - neighboringColor.getRed()) +
                    Math.abs(color.getGreen() - neighboringColor.getGreen()) < 150)) {
                return true;
            }
        }
        return false;
    }

    private Color getZoneColor(Area area, AbstractMap<Integer, Color> map_Zone_Color) {
        final Random rand = new Random();
        final int zoneId = mapPrinter.showAreas ? area.Id().intValue() : area.GroupId().intValue();
        Color color = map_Zone_Color.get(zoneId);
        if (color == null) { // zoneId was not find --> insertion did occur --> we have do define the new color:
            int tries = 0;
            do {
                color = new Color(rand.nextInt(256), rand.nextInt(256), 0); // blue unused for Terrain so that Water can be easily distinguished.
                if (++tries > 100) break;
            } while (
                    // 1) color should not be too dark
                    (color.getRed() + color.getGreen() < 150) ||

                    // 2) color should differ enough from the colors of the neighbouring Areas
                    (mapPrinter.showAreas && getZoneColor_cpp_algorithm_any_of(area.ChokePointsByArea(), map_Zone_Color, color))
            );
            map_Zone_Color.put(zoneId, color);
        }

        return color;
    }

    private void printNeutral(Map theMap, Neutral n, Color col) {
        final WalkPosition delta = new WalkPosition(n.Pos().getX() < theMap.getCenter().getX() ? +1 : -1, n.Pos().getY() < theMap.getCenter().getY() ? +1 : -1);
        final int stackSize = mapPrinter.showStackedNeutrals ? theMap.GetTile(n.TopLeft()).StackedNeutrals() : 1;

        for (int i = 0 ; i < stackSize ; ++i) {
            WalkPosition origin = (new WalkPosition(n.TopLeft())).add(new WalkPosition(delta.getX() * i, delta.getY() * i));
            WalkPosition size = n.Size().toPosition().toWalkPosition();
            if (!theMap.isValid(origin) || !theMap.isValid(origin.add(size).subtract(new WalkPosition(1, 1)))) break;

            mapPrinter.Rectangle(origin, origin.add(size).subtract(new WalkPosition(1, 1)), col, MapPrinter.fill_t.fill);

            if (mapPrinter.showBlockingBuildings && n.Blocking())
                if (i < stackSize - 1) {
                    mapPrinter.Point(origin, MapPrinter.CustomColor.BLOCKING_NEUTRALS.color());
                    mapPrinter.Point(origin.add(size).subtract(new WalkPosition(1, 1)), MapPrinter.CustomColor.BLOCKING_NEUTRALS.color());
                    mapPrinter.Point(new WalkPosition(origin.getX(), (origin.add(size).subtract(new WalkPosition(1, 1))).getY()), MapPrinter.CustomColor.BLOCKING_NEUTRALS.color());
                    mapPrinter.Point(new WalkPosition((origin.add(size).subtract(new WalkPosition(1, 1))).getX(), origin.getY()), MapPrinter.CustomColor.BLOCKING_NEUTRALS.color());
                } else {
                    mapPrinter.Rectangle(origin, origin.add(size).subtract(new WalkPosition(1, 1)), MapPrinter.CustomColor.BLOCKING_NEUTRALS.color());
                }
        }
    }

    public void printMap(Map theMap) {
        AbstractMap<Integer, Color> map_Zone_Color = new ConcurrentHashMap<>();

        for (int y = 0; y < theMap.getWalkSize().getY(); ++y)
        for (int x = 0; x < theMap.getWalkSize().getX(); ++x) {
            WalkPosition p = new WalkPosition(x, y);
            MiniTile miniTile = theMap.GetMiniTile(p, check_t.no_check);

            Color col;
            if (miniTile.Sea()) {
                if (mapPrinter.showSeaSide && BwemExt.seaSide(p, theMap)) col = MapPrinter.CustomColor.SEA_SIDE.color();
                else col = MapPrinter.CustomColor.SEA.color();
            } else {
                if (mapPrinter.showLakes && miniTile.Lake()) {
                    col = MapPrinter.CustomColor.LAKE.color();
                } else {
                    if (mapPrinter.showAltitude) {
                        int c = 255 - ((miniTile.Altitude().intValue() * 255) / theMap.MaxAltitude().intValue());
                        col = new Color(c, c, c);
                    } else {
                        col = MapPrinter.CustomColor.TERRAIN.color();
                    }

                    if (mapPrinter.showAreas || mapPrinter.showContinents)
                        if (miniTile.AreaId().intValue() > 0) {
                            Area area = theMap.GetArea(miniTile.AreaId());
                            Color zoneColor = getZoneColor(area, map_Zone_Color);
                            int red = zoneColor.getRed() * col.getRed() / 255;
                            int green = zoneColor.getGreen() * col.getGreen() / 255;
                            col = new Color(red, green, 0);
                        } else {
                            col = MapPrinter.CustomColor.TINY_AREA.color();
                        }
                }
            }

            mapPrinter.Point(p, col);
        }

        if (mapPrinter.showData)
            for (int y = 0; y < theMap.getTileSize().getY(); ++y)
            for (int x = 0; x < theMap.getTileSize().getX(); ++x) {
                int data = theMap.GetTile(new TilePosition(x, y)).InternalData().intValue();
                int c = (((data / 1) * 1) % 256);
                Color col = new Color(c, c, c);
                WalkPosition origin = (new TilePosition(x, y)).toPosition().toWalkPosition();
                mapPrinter.Rectangle(origin, origin.add(new WalkPosition(3, 3)), col, MapPrinter.fill_t.fill);
            }

        if (mapPrinter.showUnbuildable)
            for (int y = 0; y < theMap.getTileSize().getY(); ++y)
            for (int x = 0; x < theMap.getTileSize().getX(); ++x)
                if (!theMap.GetTile(new TilePosition(x, y)).Buildable()) {
                    WalkPosition origin = (new TilePosition(x, y)).toPosition().toWalkPosition();
                    mapPrinter.Rectangle(origin.add(new WalkPosition(1, 1)), origin.add(new WalkPosition(2, 2)), MapPrinter.CustomColor.UNBUILDABLE.color());
                }

        if (mapPrinter.showGroundHeight)
            for (int y = 0; y < theMap.getTileSize().getY(); ++y)
            for (int x = 0; x < theMap.getTileSize().getX(); ++x) {
                int groundHeight = theMap.GetTile(new TilePosition(x, y)).GroundHeight();
                if (groundHeight >= 1)
                    for (int dy = 0; dy < 4; ++dy)
                    for (int dx = 0; dx < 4; ++dx) {
                        WalkPosition p = (new TilePosition(x, y).toPosition().toWalkPosition()).add(new WalkPosition(dx, dy));
                        if (theMap.GetMiniTile(p, check_t.no_check).Walkable()) // groundHeight is usefull only for walkable miniTiles
                            if (((dx + dy) & (groundHeight == 1 ? 1 : 3)) != 0)
                                mapPrinter.Point(p, MapPrinter.CustomColor.HIGHER_GROUND.color());
                    }
            }

        if (mapPrinter.showAssignedRessources)
            for (Area area : theMap.Areas())
                for (Base base : area.Bases()) {
                    for (Mineral m : base.Minerals())
                        mapPrinter.Line(base.Center().toWalkPosition(), m.Pos().toWalkPosition(), MapPrinter.CustomColor.BASES.color());
                    for (Geyser g : base.Geysers())
                        mapPrinter.Line(base.Center().toWalkPosition(), g.Pos().toWalkPosition(), MapPrinter.CustomColor.BASES.color());
                }

        if (mapPrinter.showGeysers)
            for (Geyser g : theMap.Geysers())
                printNeutral(theMap, g, MapPrinter.CustomColor.GEYSERS.color());

        if (mapPrinter.showMinerals)
            for (Mineral m : theMap.Minerals())
                printNeutral(theMap, m, MapPrinter.CustomColor.MINERALS.color());

        if (mapPrinter.showStaticBuildings)
            for (StaticBuilding s : theMap.StaticBuildings())
                printNeutral(theMap, s, MapPrinter.CustomColor.STATIC_BUILDINGS.color());

        if (mapPrinter.showStartingLocations)
            for (TilePosition t : theMap.getStartingLocations()) {
                WalkPosition origin = t.toPosition().toWalkPosition();
                WalkPosition size = (UnitType.Terran_Command_Center.tileSize()).toPosition().toWalkPosition(); // same size for other races
                mapPrinter.Rectangle(origin, origin.add(size).subtract(new WalkPosition(1, 1)), MapPrinter.CustomColor.STARTING_LOCATIONS.color(), MapPrinter.fill_t.fill);
            }

        if (mapPrinter.showBases)
            for (Area area : theMap.Areas()) {
                for (Base base : area.Bases()) {
                    WalkPosition origin = base.Location().toPosition().toWalkPosition();
                    WalkPosition size = (UnitType.Terran_Command_Center.tileSize()).toPosition().toWalkPosition(); // same size for other races
                    MapPrinter.dashed_t dashMode = base.BlockingMinerals().isEmpty() ? MapPrinter.dashed_t.not_dashed : MapPrinter.dashed_t.dashed;
                    mapPrinter.Rectangle(origin, origin.add(size).subtract(new WalkPosition(1, 1)), MapPrinter.CustomColor.BASES.color(), MapPrinter.fill_t.do_not_fill, dashMode);
                }
            }

        if (mapPrinter.showChokePoints) {
            for (MutablePair<MutablePair<AreaId, AreaId>, WalkPosition> f : theMap.RawFrontier())
                mapPrinter.Point(f.right, mapPrinter.showAreas ? MapPrinter.CustomColor.CHOKE_POINTS_SHOW_AREAS.color() : MapPrinter.CustomColor.CHOKE_POINTS_SHOW_CONTINENTS.color());

            for (Area area : theMap.Areas())
                for (ChokePoint cp : area.ChokePoints()) {
                    ChokePoint.Node[] nodes = {ChokePoint.Node.end1, ChokePoint.Node.end2};
                    for (ChokePoint.Node n : nodes)
                        mapPrinter.Square(cp.Pos(n), 1, new Color(255, 0, 255), MapPrinter.fill_t.fill);
                    mapPrinter.Square(cp.Center(), 1, new Color(0, 0, 255), MapPrinter.fill_t.fill);
                }
        }

        try {
            mapPrinter.writeImageToFile(Paths.get("map.bmp"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void pathExample(Map theMap) {
    	if (theMap.getStartingLocations().size() < 2) return;

        Random rand = new Random();

    	Color col = new Color(255, 255, 255);

    	WalkPosition a = (theMap.getStartingLocations().get(rand.nextInt(theMap.getStartingLocations().size()))).toPosition().toWalkPosition();

    	WalkPosition b = a;
    	while (b.equals(a)) {
            b = (theMap.getStartingLocations().get(rand.nextInt(theMap.getStartingLocations().size()))).toPosition().toWalkPosition();
        }

    //	Uncomment this to use random positions for a and b:
    //	a = WalkPosition(theMap.RandomPosition());
    //	b = WalkPosition(theMap.RandomPosition());

    	mapPrinter.Circle(a, 6, col, MapPrinter.fill_t.fill);
    	mapPrinter.Circle(b, 6, col, MapPrinter.fill_t.fill);

    	MutableInt length = new MutableInt(0);
    	CPPath Path = theMap.GetPath(a.toPosition(), b.toPosition(), length);

    	if (length.intValue() < 0) return;		// cannot reach b from a

    	if (Path.isEmpty()) { // no ChokePoint between a and b:
    		// let's verify that a and b are in the same Area:
//    		bwem_assert(theMap.GetNearestArea(a) == theMap.GetNearestArea(b));
            if (!(theMap.GetNearestArea(a).equals(theMap.GetNearestArea(b)))) {
                throw new IllegalStateException();
            }

    		// just draw a single line between them:
    		mapPrinter.Line(a, b, col, MapPrinter.dashed_t.dashed);
    	} else { // at least one ChokePoint between a and b:
    		// let's verify that a and b are not in the same Area:
//    		bwem_assert(theMap.GetNearestArea(a) != theMap.GetNearestArea(b));
            if (!(!theMap.GetNearestArea(a).equals(theMap.GetNearestArea(b)))) {
                throw new IllegalStateException();
            }

    		// draw a line between each ChokePoint in Path:
    		ChokePoint cpPrevious = null;
    		for (ChokePoint cp : Path) {
    			if (cpPrevious != null) {
                    mapPrinter.Line(cpPrevious.Center(), cp.Center(), col, MapPrinter.dashed_t.dashed);
                }
    			mapPrinter.Circle(cp.Center(), 6, col);
    			cpPrevious = cp;
    		}

    		mapPrinter.Line(a, Path.get(0).Center(), col, MapPrinter.dashed_t.dashed);
    		mapPrinter.Line(b, Path.get(Path.size() - 1).Center(), col, MapPrinter.dashed_t.dashed);
    	}

        //TODO: Handle exception.
        try {
            mapPrinter.writeImageToFile(Paths.get("map.bmp"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
