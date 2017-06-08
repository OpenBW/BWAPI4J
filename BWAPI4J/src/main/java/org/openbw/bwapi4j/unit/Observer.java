package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class Observer extends MobileUnit implements Robotic, Detector, Mechanical, Cloakable {

    protected Observer(int id) {
        
        super(id, UnitType.Protoss_Observer);
    }
    
    @Override
    public boolean cloak() {
        
        return true;
    }

    @Override
    public boolean decloak() {
        
        return false;
    }
}
