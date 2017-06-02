package bwta;

public class Tmp_TilePosition {

    public static final int SIZE_IN_PIXELS = 32;

    private int x;
    private int y;

    public Tmp_TilePosition(int x, int y) {
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
        if (!(o instanceof Tmp_TilePosition)) {
            return false;
        }

        Tmp_TilePosition that = (Tmp_TilePosition) o;

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

    public Tmp_Position toPosition() {
        return new Tmp_Position(x * SIZE_IN_PIXELS, y * SIZE_IN_PIXELS);
    }
}
