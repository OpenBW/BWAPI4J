package bwta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.openbw.bwapi4j.Player;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.util.Pair;

public class BWTA {

    private List<Region> regions;
    private List<Chokepoint> chokepoints;
    private List<BaseLocation> baseLocations;
    
    public BWTA() {
        
        this.regions = new ArrayList<Region>();
        this.chokepoints = new ArrayList<Chokepoint>();
        this.baseLocations = new ArrayList<BaseLocation>();
    }
    
    public List<Region> getRegions() {
    
        return this.regions;
    }
    
    public List<Chokepoint> getChokepoints() {
        
        return this.chokepoints;
    }
    
    public List<BaseLocation> getBaseLocations() {
        
        return this.baseLocations;
    }
    
    public List<BaseLocation> getStartLocations() {
        
        return this.baseLocations.stream().filter(location -> location.isStartLocation()).collect(Collectors.toList());
    }
    
    public void clear() {
    
        Region.clearCache();
        BaseLocation.clearCache();
        Chokepoint.clearCache();
        cleanMemory();
    }
    
    public native void analyze();

    public native void computeDistanceTransform();

    public native void balanceAnalysis();

    public native void cleanMemory();
    
    // TODO

    public static native int getMaxDistanceTransform();

    public static native List<Polygon> getUnwalkablePolygons();

    public static native BaseLocation getStartLocation(Player player);

    public static native Region getRegion(int x, int y);

    public static native Region getRegion(TilePosition tileposition);

    public static native Region getRegion(Position position);

    public static native Chokepoint getNearestChokepoint(int x, int y);

    public static native Chokepoint getNearestChokepoint(TilePosition tileposition);

    public static native Chokepoint getNearestChokepoint(Position position);

    public static native BaseLocation getNearestBaseLocation(int x, int y);

    public static native BaseLocation getNearestBaseLocation(TilePosition tileposition);

    public static native BaseLocation getNearestBaseLocation(Position position);

    public static native Polygon getNearestUnwalkablePolygon(int x, int y);

    public static native Polygon getNearestUnwalkablePolygon(TilePosition tileposition);

    public static native Position getNearestUnwalkablePosition(Position position);

    public static native boolean isConnected(int x1, int y1, int x2, int y2);

    public static native boolean isConnected(TilePosition a, TilePosition b);

    public static native double getGroundDistance(TilePosition start, TilePosition end);

    public static native Pair<TilePosition, Double> getNearestTilePosition(TilePosition start, List<TilePosition> targets);

    public static native Map<TilePosition, Double> getGroundDistances(TilePosition start, List<TilePosition> targets);

    public static native List<TilePosition> getShortestPath(TilePosition start, TilePosition end);

    public static native List<TilePosition> getShortestPath(TilePosition start, List<TilePosition> targets);

    public static native void buildChokeNodes();

    public static native int getGroundDistance2(TilePosition start, TilePosition end);


}