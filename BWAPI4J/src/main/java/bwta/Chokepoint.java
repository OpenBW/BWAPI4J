package bwta;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.util.Pair;

public class Chokepoint {

    private static final Logger logger = LogManager.getLogger();
    
    private long id;
    
    private Pair<Region, Region> regions;
    private Pair<Position, Position> sides;
    private Position center;
    private double width;
    
    // internal caching start
    
    private long region1Id;
    private long region2Id;
    
    private static Map<Long, Chokepoint> chokepointCache = new HashMap<>();
    
    static void clearCache() {
        
        Chokepoint.chokepointCache.clear();
    }
    
    static Chokepoint getCachedChokepoint(long id) {
        
        return Chokepoint.chokepointCache.get(id);
    }
    
    // internal caching end
    
    /**
     * Creates a new Chokepoint.
     */
    public Chokepoint(long id) {
        
        this.id = id;
        
        Chokepoint.chokepointCache.put(id, this);
    }
    
    /**
     * Gets the two regions adjacent to this choke point.
     * @return pair of two regions
     */
    public Pair<Region, Region> getRegions() {
    
        if (this.regions == null) {
            
            logger.debug("attempting to get region pair for id {} and {}...", region1Id, region2Id);
            this.regions = new Pair<>(Region.getCachedRegion(region1Id), Region.getCachedRegion(region2Id));
            logger.debug("retrieved {} and {}.", this.regions.first, this.regions.second);
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
        
        return (int)id;
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