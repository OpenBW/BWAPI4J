package org.openbw.bwapi4j;

public class TilePosition {

    public static final int SIZE_IN_PIXELS = 32;

    private int x;
    private int y;

    public TilePosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "[" + x + ", " + y + "]";
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TilePosition)) {
            return false;
        }

        TilePosition that = (TilePosition) o;

        if (x != that.x) {
            return false;
        }
        if (y != that.y) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return x * 256 + y;
    }

    public Position toPosition() {
        return new Position(x * SIZE_IN_PIXELS, y * SIZE_IN_PIXELS);
    }

    public TilePosition add(TilePosition rhs) {
        int x = this.x + rhs.getX();
        int y = this.y + rhs.getY();
        return new TilePosition(x, y);
    }

    public TilePosition subtract(TilePosition rhs) {
        int x = this.x - rhs.getX();
        int y = this.y - rhs.getY();
        return new TilePosition(x, y);
    }
}
