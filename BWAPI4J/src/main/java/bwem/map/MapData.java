package bwem.map;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;

import java.util.List;

public interface MapData {

    // Returns the size of the Map in Tiles.
    public abstract TilePosition getTileSize();

    // Returns the size of the Map in MiniTiles.
    public abstract WalkPosition getWalkSize();

    // Returns the size of the Map in pixels.
    public abstract Position getPixelSize();

    // Returns the center of the Map in pixels.
    public abstract Position getCenter();

    // Returns a reference to the starting Locations.
    // Note: these correspond to BWAPI::getStartLocations().
    public abstract List<TilePosition> getStartingLocations();

    public abstract boolean isValid(TilePosition tilePosition);

    public abstract boolean isValid(WalkPosition walkPosition);

    public abstract boolean isValid(Position position);

    public abstract TilePosition crop(TilePosition tilePosition);

    public abstract WalkPosition crop(WalkPosition walkPosition);

    public abstract Position crop(Position position);

    // Returns a random position in the Map in pixels.
    public abstract Position getRandomPosition();

}
