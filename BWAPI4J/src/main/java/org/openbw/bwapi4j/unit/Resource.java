package org.openbw.bwapi4j.unit;

public interface Resource {
    int getResources();
    int getInitialResources();
    int getLastKnownResources() ;
    boolean isBeingGathered();
}
