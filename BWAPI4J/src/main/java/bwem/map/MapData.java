package bwem.map;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;

import java.util.List;

public interface MapData {

    // Returns the size of the Map in Tiles.
    TilePosition getTileSize();

    // Returns the size of the Map in MiniTiles.
    WalkPosition getWalkSize();

    // Returns the size of the Map in pixels.
    Position getPixelSize();

    // Returns the center of the Map in pixels.
    Position getCenter();

    // Returns a reference to the starting Locations.
    // Note: these correspond to BWAPI::getStartLocations().
    List<TilePosition> getStartingLocations();

    boolean isValid(TilePosition tilePosition);

    boolean isValid(WalkPosition walkPosition);

    boolean isValid(Position position);

    TilePosition crop(TilePosition tilePosition);

    WalkPosition crop(WalkPosition walkPosition);

    Position crop(Position position);

    // Returns a random position in the Map in pixels.
    Position getRandomPosition();

}
