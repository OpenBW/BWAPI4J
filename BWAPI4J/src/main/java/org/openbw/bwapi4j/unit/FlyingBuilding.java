package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.Position;

interface FlyingBuilding {

    public boolean lift();

    public boolean land(Position p);

    public boolean move(Position p);

    public boolean isLifted();
}
