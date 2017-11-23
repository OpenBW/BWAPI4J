package org.openbw.bwapi4j;

public class Position {

    private final int x;
    private final int y;

    public Position(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public double getDistance(final Position position) {
        // TODO replace with BW distance (see tscmoos code)
    	double dx = getX() - position.getX();
    	double dy = getY() - position.getY();
    	
        return Math.sqrt(dx * dx + dy * dy);
    }

    public TilePosition toTilePosition() {
        final int x = getX() / TilePosition.SIZE_IN_PIXELS;
        final int y = getY() / TilePosition.SIZE_IN_PIXELS;
        return new TilePosition(x, y);
    }

    public WalkPosition toWalkPosition() {
        final int x = getX() / WalkPosition.SIZE_IN_PIXELS;
        final int y = getY() / WalkPosition.SIZE_IN_PIXELS;
        return new WalkPosition(x, y);
    }

    public Position add(final Position position) {
        final int x = getX() + position.getX();
        final int y = getY() + position.getY();
        return new Position(x, y);
    }

    public Position subtract(final Position position) {
        final int x = getX() - position.getX();
        final int y = getY() - position.getY();
        return new Position(x, y);
    }

    public Position multiply(final Position position) {
        final int x = getX() * position.getX();
        final int y = getY() * position.getY();
        return new Position(x, y);
    }

    public Position divide(final Position position) {
        final int x = getX() / position.getX();
        final int y = getY() / position.getY();
        return new Position(x, y);
    }

    @Override
    public String toString() {
        return "[" + getX() + "," + getY() + "]";
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Position)) {
            return false;
        }

        final Position position = (Position) object;
        if (getX() != position.getX()) {
            return false;
        }
        if (getY() != position.getY()) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return (getX() * 2048 * TilePosition.SIZE_IN_PIXELS + getY());
    }

}
