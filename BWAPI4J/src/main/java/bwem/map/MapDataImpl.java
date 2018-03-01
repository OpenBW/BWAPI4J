package bwem.map;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapDataImpl implements MapData {

    private final TilePosition tileSize;
    private final WalkPosition walkSize;
    private final Position pixelSize;
    private final Position center;
    private final List<TilePosition> startingLocations;

    public MapDataImpl(final int tileWidth, final int tileHeight, final List<TilePosition> startingLocations) {
        this.tileSize = new TilePosition(tileWidth, tileHeight);
        this.walkSize = this.tileSize.toWalkPosition();
        this.pixelSize = this.tileSize.toPosition();

        this.center = new Position(this.pixelSize.getX() / 2, this.pixelSize.getY() / 2);

        this.startingLocations = new ArrayList<>(startingLocations);
    }

    @Override
    public TilePosition getTileSize() {
        return this.tileSize;
    }

    @Override
    public WalkPosition getWalkSize() {
        return this.walkSize;
    }

    @Override
    public Position getPixelSize() {
        return this.pixelSize;
    }

    @Override
    public Position getCenter() {
        return this.center;
    }

    @Override
    public List<TilePosition> getStartingLocations() {
        return this.startingLocations;
    }

    @Override
    public boolean isValid(final TilePosition tilePosition) {
        return isValid(tilePosition.getX(), tilePosition.getY(), getTileSize().getX(), getTileSize().getY());
    }

    @Override
    public boolean isValid(final WalkPosition walkPosition) {
        return isValid(walkPosition.getX(), walkPosition.getY(), getWalkSize().getX(), getWalkSize().getY());
    }

    @Override
    public boolean isValid(final Position position) {
        return isValid(position.getX(), position.getY(), getPixelSize().getX(), getPixelSize().getY());
    }

    private boolean isValid(final int x, final int y, final int maxX, final int maxY) {
        return (x >= 0 && x < maxX && y >= 0 && y < maxY);
    }

    @Override
    public TilePosition crop(final TilePosition tilePosition) {
        final int[] ret = crop(tilePosition.getX(), tilePosition.getY(), getTileSize().getX(), getTileSize().getY());
        return new TilePosition(ret[0], ret[1]);
    }

    @Override
    public WalkPosition crop(final WalkPosition walkPosition) {
        final int[] ret = crop(walkPosition.getX(), walkPosition.getY(), getWalkSize().getX(), getWalkSize().getY());
        return new WalkPosition(ret[0], ret[1]);
    }

    @Override
    public Position crop(final Position position) {
        final int[] ret = crop(position.getX(), position.getY(), getPixelSize().getX(), getPixelSize().getY());
        return new Position(ret[0], ret[1]);
    }

    private int[] crop(final int x, final int y, final int maxX, final int maxY) {
        int retX = x;
        int retY = y;

        if (retX < 0) retX = 0;
        else if (retX >= maxX) retX = maxX - 1;

        if (retY < 0) retY = 0;
        else if (retY >= maxY) retY = maxY - 1;

        return new int[]{retX, retY};
    }

    @Override
    public Position getRandomPosition() {
        final Random random = new Random();
        final int x = random.nextInt(getPixelSize().getX());
        final int y = random.nextInt(getPixelSize().getY());
        return new Position(x, y);
    }

}
