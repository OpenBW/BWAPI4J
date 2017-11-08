package org.openbw.bwapi4j;

public class WalkPosition {

	public static final int SIZE_IN_PIXELS = 8;

	private int x;
    private int y;

    public WalkPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public WalkPosition(TilePosition p) {
        this.x = p.getX() * TilePosition.SIZE_IN_PIXELS / WalkPosition.SIZE_IN_PIXELS;
        this.y = p.getY() * TilePosition.SIZE_IN_PIXELS / WalkPosition.SIZE_IN_PIXELS;
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
        
        if (!(o instanceof WalkPosition)) {
            return false;
        }

        WalkPosition that = (WalkPosition) o;

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
        return x * 256 * 4 + y;
    }

    public Position toPosition() {
        return new Position(x * SIZE_IN_PIXELS, y * SIZE_IN_PIXELS);
    }

    public WalkPosition add(WalkPosition rhs) {
        int x = this.x + rhs.getX();
        int y = this.y + rhs.getY();
        return new WalkPosition(x, y);
    }

    public WalkPosition subtract(WalkPosition rhs) {
        int x = this.x - rhs.getX();
        int y = this.y - rhs.getY();
        return new WalkPosition(x, y);
    }
}
