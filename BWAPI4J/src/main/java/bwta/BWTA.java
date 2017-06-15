package bwta;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;

public class BWTA {

    private ArrayList<Region> regions;
    private ArrayList<Chokepoint> chokepoints;
    private ArrayList<BaseLocation> baseLocations;
    
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
    
    public void analyze() {
        
        this.clear();
        this.analyze(this);
    }
    
    private native void analyze(BWTA bwta);

    public native void computeDistanceTransform();

    public native void balanceAnalysis();

    private native void cleanMemory();
    
    public Region getRegion(TilePosition tilePosition) {
        
        return Region.getCachedRegion(getRegionT(tilePosition.getX(), tilePosition.getY()));
    }
    
    public Region getRegion(Position position) {
        
        return Region.getCachedRegion(getRegionP(position.getX(), position.getY()));
    }
 
    public Region getRegion(int x, int y) {
        
        return Region.getCachedRegion(getRegionT(x, y));
    }
    
    private native int getRegionT(int x, int y);

    private native int getRegionP(int x, int y);
    
    public double getGroundDistance(TilePosition start, TilePosition end) {
    
        return getGroundDistance(start.getX(), start.getY(), end.getX(), end.getY());
    }
    
    private native double getGroundDistance(int x1, int y1, int x2, int y2);
    
    public native boolean isConnected(int x1, int y1, int x2, int y2);

    public boolean isConnected(TilePosition a, TilePosition b) {
        
        return this.isConnected(a.getX(), a.getY(), b.getX(), b.getY());
    }
    
    public List<TilePosition> getShortestPath(TilePosition start, TilePosition end) {
        
        return this.getShortestPath(start.getX(), start.getY(), end.getX(), end.getY());
    }
    
    private native List<TilePosition> getShortestPath(int x1, int y1, int x2, int y2);
    
    // TODO
/*
    public static native int getMaxDistanceTransform();

    public static native List<Polygon> getUnwalkablePolygons();

    public static native BaseLocation getStartLocation(Player player);

    public static native Region getRegion(int x, int y);

    public static native Chokepoint getNearestChokepoint(int x, int y);

    public static native Chokepoint getNearestChokepoint(TilePosition tileposition);

    public static native Chokepoint getNearestChokepoint(Position position);

    public static native BaseLocation getNearestBaseLocation(int x, int y);

    public static native BaseLocation getNearestBaseLocation(TilePosition tileposition);

    public static native BaseLocation getNearestBaseLocation(Position position);

    public static native Polygon getNearestUnwalkablePolygon(int x, int y);

    public static native Polygon getNearestUnwalkablePolygon(TilePosition tileposition);

    public static native Position getNearestUnwalkablePosition(Position position);

    public static native Pair<TilePosition, Double> getNearestTilePosition(TilePosition start, List<TilePosition> targets);

    public static native Map<TilePosition, Double> getGroundDistances(TilePosition start, List<TilePosition> targets);


    public static native List<TilePosition> getShortestPath(TilePosition start, List<TilePosition> targets);

    public static native void buildChokeNodes();

    public static native int getGroundDistance2(TilePosition start, TilePosition end);
*/

}