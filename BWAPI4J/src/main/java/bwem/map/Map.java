package bwem.map;

import bwem.Altitude;
import java.util.List;
import java.util.Random;
import org.openbw.bwapi4j.BW;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;

//////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                          //
//                                  class Map
//                                                                                          //
//////////////////////////////////////////////////////////////////////////////////////////////
//
// Map is the entry point:
//	- to access general information on the Map
//	- to access the Tiles and the MiniTiles
//	- to access the Areas
//	- to access the StartingLocations
//	- to access the Minerals, the Geysers and the StaticBuildings
//	- to parametrize the analysis process
//	- to update the information
// Map also provides some useful tools such as Paths between ChokePoints and generic algorithms like BreadthFirstSearch
//
// Map functionnality is provided through its singleton Map::Instance().
public abstract class Map {

    private final BW bw;
    protected int tilePositionCount = 0;
    protected TilePosition tileSize = null;
    protected int walkPositionCount = 0;
    protected WalkPosition walkSize = null;
    protected int pixelCount = 0;
    protected Position pixelSize = null;
    protected Position center = null;

    protected Map(BW bw) {
        this.bw = bw;
    }

    protected BW getBW() {
        return this.bw;
    }

	// This has to be called before any other function is called.
	// A good place to do this is in ExampleAIModule::onStart()
    public abstract void initialize();

    // Will return true once Initialize() has been called.
    public boolean isInitialized() {
        return (this.tilePositionCount != 0);
    }

    // Returns the size of the Map in Tiles.
    public TilePosition getTileSize() {
        return new TilePosition(this.tileSize.getX(), this.tileSize.getY());
    }

    // Returns the size of the Map in MiniTiles.
    public WalkPosition getWalkSize() {
        return new WalkPosition(this.walkSize.getX(), this.walkSize.getY());
    }

    // Returns the size of the Map in pixels.
    public Position getPixelSize() {
        return new Position(this.pixelSize.getX(), this.pixelSize.getY());
    }

    // Returns the center of the Map in pixels.
    public Position getCenter() {
        return this.center;
    }

    // Returns the maximum altitude in the whole Map (Cf. MiniTile::Altitude()).
    public abstract Altitude getMaxAltitude();

    public boolean isValid(TilePosition p) {
        return (p.getX() >= 0 && p.getX() < getTileSize().getX()
                && p.getY() >= 0 && p.getY() < getTileSize().getY());
    }

    public boolean isValid(WalkPosition p) {
        return (p.getX() >= 0 && p.getX() < getWalkSize().getX()
                && p.getY() >= 0 && p.getY() < getWalkSize().getY());
    }

    public boolean isValid(Position p) {
        return (p.getX() >= 0 && p.getX() < getPixelSize().getX()
                && p.getY() >= 0 && p.getY() < getPixelSize().getY());
    }

    public TilePosition crop(TilePosition t) {
        int x = t.getX();
        int y = t.getY();

        if (x < 0) x = 0;
        else if (x >= getTileSize().getX()) x = getTileSize().getX() - 1;

        if (y < 0) y = 0;
        else if (y >= getTileSize().getY()) y = getTileSize().getY() - 1;

        return new TilePosition(x, y);
    }

    public WalkPosition crop(WalkPosition w) {
        int x = w.getX();
        int y = w.getY();

        if (x < 0) x = 0;
        else if (x >= getTileSize().getX()) x = getWalkSize().getX() - 1;

        if (y < 0) y = 0;
        else if (y >= getTileSize().getY()) y = getWalkSize().getY() - 1;

        return new WalkPosition(x, y);
    }

    public Position crop(Position p) {
        int x = p.getX();
        int y = p.getY();

        if (x < 0) x = 0;
        else if (x >= getTileSize().getX()) x = getPixelSize().getX() - 1;

        if (y < 0) y = 0;
        else if (y >= getTileSize().getY()) y = getPixelSize().getY() - 1;

        return new Position(x, y);
    }

    // Returns a random position in the Map in pixels.
    public Position getRandomPosition() {
        Random r = new Random();
        int rx = r.nextInt(getPixelSize().getX());
        int ry = r.nextInt(getPixelSize().getY());
        return new Position(rx, ry);
    }

	// Returns a reference to the starting Locations.
	// Note: these correspond to BWAPI::getStartLocations().
    public abstract List<TilePosition> getStartingLocations();
}
