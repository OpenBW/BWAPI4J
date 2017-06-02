package bwta;

import org.openbw.bwapi4j.util.AbstractPoint;

/**
 * Intermediate class used to translate getPoint() calls to getPosition() calls.
 */
public abstract class PositionedObject extends AbstractPoint<Tmp_Position> {

    public Tmp_Position getPoint() {
        return getPosition();
    }

    public abstract Tmp_Position getPosition();
}
