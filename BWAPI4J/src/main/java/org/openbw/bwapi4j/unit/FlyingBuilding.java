package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.Position;

public interface FlyingBuilding {

    boolean lift();

    boolean land(Position p);

    boolean move(Position p);

    boolean isLifted();
}
