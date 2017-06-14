package bwta;

import java.util.HashMap;
import java.util.Map;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.util.Pair;

public class Chokepoint {

    private int id;
    private BWTA bwta;
    
    private Pair<Region, Region> regions;
    private Pair<Position, Position> sides;
    private Position center;
    private double width;
    
    // internal caching start
    
    private int region1Id;
    private int region2Id;
    
    private static Map<Integer, Chokepoint> chokepointCache = new HashMap<>();
    
    static void clearCache() {
        
        Chokepoint.chokepointCache.clear();
    }
    
    static Chokepoint getCachedChokepoint(int id) {
        
        return Chokepoint.chokepointCache.get(id);
    }
    
    // internal caching end
    
    /**
     * Creates a new Chokepoint.
     */
    public Chokepoint(int id, BWTA bwta) {
        
        this.id = id;
        this.bwta = bwta;
        
        Chokepoint.chokepointCache.put(id, this);
    }
    
    /**
     * Gets the two regions adjacent to this choke point.
     * @return pair of two regions
     */
    public Pair<Region, Region> getRegions() {
    
        if (this.regions == null) {
        
            this.regions = new Pair<>(Region.getCachedRegion(region1Id), Region.getCachedRegion(region2Id));
        }
        
        return this.regions;
    }

    public Pair<Position, Position> getSides() {
    
        return this.sides;
    }

    public Position getCenter() {
    
        return this.center;
    }

    public double getWidth() {

        return this.width;
    }
    
    @Override
    public int hashCode() {
        
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        
        if (obj == null || !(obj instanceof Chokepoint)) {
            return false;
        } else {
            
            return this.id == ((Chokepoint) obj).id;
        }
    }
}