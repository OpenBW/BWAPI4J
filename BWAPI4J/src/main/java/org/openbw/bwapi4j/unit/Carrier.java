package org.openbw.bwapi4j.unit;

import java.util.List;
import java.util.stream.Collectors;

import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

import static org.openbw.bwapi4j.type.UnitCommandType.Train;
import static org.openbw.bwapi4j.type.UnitType.Protoss_Interceptor;

public class Carrier extends MobileUnit implements Mechanical {

    private int interceptorCount;
    
    protected Carrier(int id) {
        
        super(id, UnitType.Protoss_Carrier);
    }
    
    @Override
    public void initialize(int[] unitData, int index) {

        this.interceptorCount = 0;
        super.initialize(unitData, index);
    }

    @Override
    public void update(int[] unitData, int index, int frame) {

        this.interceptorCount = unitData[index + Unit.INTERCEPTOR_COUNT_INDEX];
        super.update(unitData, index, frame);
    }
    
    public int getInterceptorCount() {
        return this.interceptorCount;
    }
    
    /**
     * Retrieves a list of all interceptors of this carrier.
     * @return list of interceptors
     */
    public List<Interceptor> getInterceptors() {
        
        return this.getAllUnits().stream()
                .filter(u -> u instanceof Interceptor && ((Interceptor)u).getCarrier().getId() == this.getId())
                .map(u -> (Interceptor)u).collect(Collectors.toList());
    }
    
    public boolean trainInterceptor() {
        
        return issueCommand(id, Train, Protoss_Interceptor.getId(), -1, -1, -1);
    }
}
