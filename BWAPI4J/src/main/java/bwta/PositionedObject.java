package bwta;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.util.AbstractPoint;

/**
 * Interrmediate class used to translate getPoint() calls to getPosition() calls.
 */
public abstract class PositionedObject extends AbstractPoint<Position> {

    public Position getPoint(){
        return getPosition();
    }

    public abstract Position getPosition();
}
