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

package bwta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openbw.bwapi4j.Position;

public class Region {

    private long id;
    
    private Polygon polygon;
    private Position center;
    private List<Chokepoint> chokepoints;
    private List<BaseLocation> baseLocations;
    private List<Region> reachableRegions;
    private int maxDistance;
    
    // internal caching start
    
    private ArrayList<Long> chokepointIds;
    private ArrayList<Long> baseLocationIds;
    private ArrayList<Long> reachableRegionIds;
    
    private static Map<Long, Region> regionsCache = new HashMap<>();
    
    static void clearCache() {
        
        Region.regionsCache.clear();
    }
    
    static Region getCachedRegion(long id) {
        
        return Region.regionsCache.get(id);
    }
    
    // internal caching end
    
    /**
     * Creates a new region.
     */
    public Region(long id) {
        
        this.id = id;
        
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
            for (long id : this.chokepointIds) {
                
                Chokepoint chokepoint = Chokepoint.getCachedChokepoint(id);
                this.chokepoints.add(chokepoint);
            }
        }
        
        return this.chokepoints;
    }

    public List<BaseLocation> getBaseLocations() {
        
        if (this.baseLocations == null) {
            
            this.baseLocations = new ArrayList<>();
            for (long id : this.baseLocationIds) {
                
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
            for (long id : this.reachableRegionIds) {
                
                this.reachableRegions.add(Region.getCachedRegion(id));
            }
        }

        return this.reachableRegions;
    }

    public int getMaxDistance() {
        
        return this.maxDistance;
    }

    
    @Override
    public String toString() {
        
        return "region " + id;
    }

    @Override
    public int hashCode() {
        
        return (int)id;
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
