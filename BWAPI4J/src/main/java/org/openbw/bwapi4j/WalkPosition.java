package org.openbw.bwapi4j;

public class WalkPosition {

    public static final int SIZE_IN_PIXELS = 8;

    private final int x;
    private final int y;

    public WalkPosition(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public WalkPosition(final TilePosition tilePosition) {
        this.x = (tilePosition.getX() * TilePosition.SIZE_IN_PIXELS) / WalkPosition.SIZE_IN_PIXELS;
        this.y = (tilePosition.getY() * TilePosition.SIZE_IN_PIXELS) / WalkPosition.SIZE_IN_PIXELS;
    }

    public WalkPosition(final Position position) {
        this.x = position.getX() / WalkPosition.SIZE_IN_PIXELS;
        this.y = position.getY() / WalkPosition.SIZE_IN_PIXELS;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public TilePosition toTilePosition() {
        final int x = (getX() * WalkPosition.SIZE_IN_PIXELS) / TilePosition.SIZE_IN_PIXELS;
        final int y = (getY() * WalkPosition.SIZE_IN_PIXELS) / TilePosition.SIZE_IN_PIXELS;
        return new TilePosition(x, y);
    }

    public Position toPosition() {
        final int x = getX() * WalkPosition.SIZE_IN_PIXELS;
        final int y = getY() * WalkPosition.SIZE_IN_PIXELS;
        return new Position(x, y);
    }

    public WalkPosition add(WalkPosition walkPosition) {
        final int x = getX() + walkPosition.getX();
        final int y = getY() + walkPosition.getY();
        return new WalkPosition(x, y);
    }

    public WalkPosition subtract(WalkPosition walkPosition) {
        final int x = getX() - walkPosition.getX();
        final int y = getY() - walkPosition.getY();
        return new WalkPosition(x, y);
    }

    public WalkPosition multiply(WalkPosition walkPosition) {
        final int x = getX() * walkPosition.getX();
        final int y = getY() * walkPosition.getY();
        return new WalkPosition(x, y);
    }

    public WalkPosition divide(WalkPosition walkPosition) {
        final int x = getX() / walkPosition.getX();
        final int y = getY() / walkPosition.getY();
        return new WalkPosition(x, y);
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

        if (!(object instanceof WalkPosition)) {
            return false;
        }

        final WalkPosition walkPosition = (WalkPosition) object;
        if (getX() != walkPosition.getX()) {
            return false;
        }
        if (getY() != walkPosition.getY()) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return (getX() * 2048 * WalkPosition.SIZE_IN_PIXELS + getY());
    }

}
