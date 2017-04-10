package org.openbw.bwapi4j.util;

public abstract class AbstractPoint<T extends AbstractPoint> {

    public abstract T getPoint();

    public int getX() {
        return getPoint().getX();
    }

    public int getY() {
        return getPoint().getY();
    }

    public double getDistance(AbstractPoint<T> otherPosition) {
        return getDistance(otherPosition.getX(), otherPosition.getY());
    }

    public double getDistance(int x, int y) {
        double dx = x - getX();
        double dy = y - getY();
        return Math.sqrt(dx * dx + dy * dy);
    }
}
