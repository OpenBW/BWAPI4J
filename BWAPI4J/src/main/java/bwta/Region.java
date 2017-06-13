package bwta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openbw.bwapi4j.Position;

public class Region {

    private int id;
    private BWTA bwta;
    
    private Polygon polygon;
    private Position center;
    private List<Chokepoint> chokepoints;
    private List<BaseLocation> baseLocations;
    private List<Region> reachableRegions;
    private int maxDistance;
    
    // internal caching start
    
    private List<Integer> chokepointIds;
    private List<Integer> baseLocationIds;
    private List<Integer> reachableRegionIds;
    
    private static Map<Integer, Region> regionsCache = new HashMap<>();
    
    static void clearCache() {
        
        Region.regionsCache.clear();
    }
    
    static Region getCachedRegion(int id) {
        
        return Region.regionsCache.get(id);
    }
    
    // internal caching end
    
    /**
     * Creates a new region.
     */
    public Region(int id, BWTA bwta) {
        
        this.id = id;
        this.bwta = bwta;
        
        this.chokepointIds = new ArrayList<>();
        this.baseLocationIds = new ArrayList<>();
        this.reachableRegionIds = new ArrayList<>();
        
        Region.regionsCache.put(id, this);
    }
    
    public Polygon getPolygon() {
        
        return this.polygon;
    }

    public Position getCenter() {

        return this.center;
    }

    public List<Chokepoint> getChokepoints() {
        
        if (this.chokepoints == null) {
        
            this.chokepoints = new ArrayList<>();
            for (int id : this.chokepointIds) {
                
                this.chokepoints.add(Chokepoint.getCachedChokepoint(id));
            }
        }
        
        return this.chokepoints;
    }

    public List<BaseLocation> getBaseLocations() {
        
        if (this.baseLocations == null) {
            
            this.baseLocations = new ArrayList<>();
            for (int id : this.baseLocationIds) {
                
                this.baseLocations.add(BaseLocation.getCachedBaseLocation(id));
            }
        }
        
        return this.baseLocations;
    }

    public boolean isReachable(Region region) {
        
        return this.reachableRegions.contains(region);
    }

    public List<Region> getReachableRegions() {
        
        if (this.reachableRegions == null) {
            
            this.reachableRegions = new ArrayList<>();
            for (int id : this.reachableRegionIds) {
                
                this.reachableRegions.add(Region.getCachedRegion(id));
            }
        }

        return this.reachableRegions;
    }

    public int getMaxDistance() {
        
        return this.maxDistance;
    }

    @Override
    public int hashCode() {
        
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        
        if (obj == null || !(obj instanceof Region)) {
            return false;
        } else {
            
            return this.id == ((Region) obj).id;
        }
    }
}