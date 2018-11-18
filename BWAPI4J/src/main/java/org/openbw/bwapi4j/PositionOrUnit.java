package org.openbw.bwapi4j;


public interface PositionOrUnit {

    public enum PositionOrUnitType{
        UNIT, POSITION, TILE_POS, WALK_POS
    }

    public PositionOrUnitType getPOType();

}
