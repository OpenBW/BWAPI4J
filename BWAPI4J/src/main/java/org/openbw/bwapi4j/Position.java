package org.openbw.bwapi4j;

public class Position {

    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public Position(TilePosition p) {
        this.x = p.getX() * TilePosition.SIZE_IN_PIXELS;
        this.y = p.getY() * TilePosition.SIZE_IN_PIXELS;
    }
    
    public Position(WalkPosition p) {
        this.x = p.getX() * WalkPosition.SIZE_IN_PIXELS;
        this.y = p.getY() * WalkPosition.SIZE_IN_PIXELS;
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

    public double getDistance(Position pos) {
    
        // TODO replace with BW distance (see tscmoos code)
        return Math.sqrt(this.x * pos.x + this.y * pos.y);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Position)) {
            return false;
        }

        Position position = (Position) o;

        if (x != position.x) {
            return false;
        }
        if (y != position.y) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return x * 256 * 32 + y;
    }

    public TilePosition toTilePosition() {
        return new TilePosition(x / TilePosition.SIZE_IN_PIXELS, y / TilePosition.SIZE_IN_PIXELS);
    }
}
