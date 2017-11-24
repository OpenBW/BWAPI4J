package org.openbw.bwapi4j;

public class TilePosition {

    public static final int SIZE_IN_PIXELS = 32;

    private final int x;
    private final int y;

    public TilePosition(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public TilePosition(final WalkPosition walkPosition) {
        this.x = (walkPosition.getX() * WalkPosition.SIZE_IN_PIXELS) / TilePosition.SIZE_IN_PIXELS;
        this.y = (walkPosition.getY() * WalkPosition.SIZE_IN_PIXELS) / TilePosition.SIZE_IN_PIXELS;
    }

    public TilePosition(final Position position) {
        this.x = position.getX() / TilePosition.SIZE_IN_PIXELS;
        this.y = position.getY() / TilePosition.SIZE_IN_PIXELS;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public WalkPosition toWalkPosition() {
        final int x = (getX() * TilePosition.SIZE_IN_PIXELS) / WalkPosition.SIZE_IN_PIXELS;
        final int y = (getY() * TilePosition.SIZE_IN_PIXELS) / WalkPosition.SIZE_IN_PIXELS;
        return new WalkPosition(x, y);
    }

    public Position toPosition() {
        final int x = getX() * TilePosition.SIZE_IN_PIXELS;
        final int y = getY() * TilePosition.SIZE_IN_PIXELS;
        return new Position(x, y);
    }

    public TilePosition add(final TilePosition tilePosition) {
        final int x = getX() + tilePosition.getX();
        final int y = getY() + tilePosition.getY();
        return new TilePosition(x, y);
    }

    public TilePosition subtract(final TilePosition tilePosition) {
        final int x = getX() - tilePosition.getX();
        final int y = getY() - tilePosition.getY();
        return new TilePosition(x, y);
    }

    public TilePosition multiply(final TilePosition tilePosition) {
        final int x = getX() * tilePosition.getX();
        final int y = getY() * tilePosition.getY();
        return new TilePosition(x, y);
    }

    public TilePosition divide(final TilePosition tilePosition) {
        final int x = getX() / tilePosition.getX();
        final int y = getY() / tilePosition.getY();
        return new TilePosition(x, y);
    }

    @Override
    public String toString() {
        return "[" + getX() + ", " + getY() + "]";
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof TilePosition)) {
            return false;
        }

        final TilePosition tilePosition = (TilePosition) object;
        if (getX() != tilePosition.getX()) {
            return false;
        }
        if (getY() != tilePosition.getY()) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return (getX() * 2048 + getY());
    }

}
