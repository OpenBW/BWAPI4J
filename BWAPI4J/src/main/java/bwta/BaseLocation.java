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
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.unit.MineralPatch;
import org.openbw.bwapi4j.unit.VespeneGeyser;

public class BaseLocation {

    private long id;
    
    private Position position;
    private TilePosition tilePosition;
    private Region region;
    private List<MineralPatch> mineralPatches;
    private List<VespeneGeyser> geysers;
    
    private boolean isIsland;
    private boolean isMineralsOnly;
    private boolean isStartLocation;
    
    // internal caching start
    
    private long regionId;
    
    private static Map<Long, BaseLocation> baseLocationsCache = new HashMap<>();
    
    static void clearCache() {
        
        BaseLocation.baseLocationsCache.clear();
    }
    
    static BaseLocation getCachedBaseLocation(long id) {
        
        return BaseLocation.baseLocationsCache.get(id);
    }
    
    // internal caching start
    
    /**
     * Creates a new base location.
     */
    public BaseLocation(long id) {
        
        this.id = id;
        
        this.mineralPatches = new ArrayList<MineralPatch>();
        this.geysers = new ArrayList<VespeneGeyser>();
        
        BaseLocation.baseLocationsCache.put(id, this);
    }
    
    public Position getPosition() {
        
        return this.position;
    }

    public TilePosition getTilePosition() {
        
        return this.tilePosition;
    }

    public Region getRegion() {
        
        return Region.getCachedRegion(regionId);
    }

    public int minerals() {
        
        return this.mineralPatches.stream().mapToInt(MineralPatch::getLastKnownResources).sum();
    }

    public int gas() {
        
        return this.geysers.stream().mapToInt(VespeneGeyser::getLastKnownResources).sum();
    }

    public List<MineralPatch> getMinerals() {
        
        return this.mineralPatches;
    }

    public List<VespeneGeyser> getGeysers() {
        
        return this.geysers;
    }

    public native double getGroundDistance(BaseLocation other);

    public native double getAirDistance(BaseLocation other);

    public boolean isIsland() {
        
        return this.isIsland;
    }

    public boolean isMineralOnly() {
        
        return this.isMineralsOnly;
    }

    public boolean isStartLocation() {
    
        return this.isStartLocation;
    }
    
    @Override
    public int hashCode() {
        
        return (int)id;
    }

    @Override
    public boolean equals(Object obj) {
        
        if (obj == null || !(obj instanceof BaseLocation)) {
            return false;
        } else {
            
            return this.id == ((BaseLocation) obj).id;
        }
    }
}
