package bwem;

import bwem.tile.MiniTile;
import bwem.tile.Tile;
import bwem.unit.Geyser;
import bwem.unit.Mineral;
import bwem.unit.StaticBuilding;
import bwem.util.buffer.BwemDataBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;
import org.openbw.bwapi4j.BW;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;
import org.openbw.bwapi4j.unit.Unit;
import org.openbw.bwapi4j.util.Pair;
import org.openbw.bwapi4j.util.XYCropper;
import org.openbw.bwapi4j.util.buffer.BwapiDataBuffer;
import org.openbw.bwapi4j.util.buffer.DataBuffer;

public class Map {
  private BW bw = null;

  private final SplittableRandom randomNumberGenerator = new SplittableRandom();

  private int tileCount;
  private TilePosition tileSize;
  private int walkTileCount;
  private WalkPosition walkSize;
  private int pixelTileCount;
  private Position pixelSize;
  private Position center;

  private int maxAltitude = 0;
  private boolean isAutomaticPathUpdateEnabled = false;
  private Graph graph = null;
  private List<Mineral> minerals = new ArrayList<>();
  private List<Geyser> geysers = new ArrayList<>();
  private List<StaticBuilding> staticBuildings = new ArrayList<>();
  private List<TilePosition> startingLocations = new ArrayList<>();
  private List<Pair<Pair<Integer, Integer>, WalkPosition>> rawFrontier = new ArrayList<>();

  private List<Tile> tiles = new ArrayList<>();
  private List<MiniTile> miniTiles = new ArrayList<>();

  private XYCropper tileSizeCropper;
  private XYCropper walkSizeCropper;
  private XYCropper pixelSizeCropper;

  private native void Initialize_native();

  private native int[] getInitializedData_native();

  private void readInitializedData() {
    final DataBuffer data = new DataBuffer(getInitializedData_native());

    tileCount = data.readInt();
    tileSize = BwapiDataBuffer.readTilePosition(data);

    walkTileCount = data.readInt();
    walkSize = BwapiDataBuffer.readWalkPosition(data);

    pixelSize = tileSize.toPosition();
    pixelTileCount = pixelSize.getX() * pixelSize.getY();

    center = BwapiDataBuffer.readPosition(data);

    maxAltitude = data.readInt();

    final int startingLocationsCount = data.readInt();
    for (int i = 0; i < startingLocationsCount; ++i) {
      startingLocations.add(BwapiDataBuffer.readTilePosition(data));
    }

    tileSizeCropper = new XYCropper(0, 0, Size().getX() - 1, Size().getY() - 1);
    walkSizeCropper = new XYCropper(0, 0, WalkSize().getX() - 1, WalkSize().getY() - 1);
    pixelSizeCropper = new XYCropper(0, 0, PixelSize().getX() - 1, PixelSize().getY() - 1);

    {
      final int tileCount = data.readInt();
      for (int i = 0; i < tileCount; ++i) {
        final Tile tile = BwemDataBuffer.readTile(data);
        tiles.add(tile);
      }
    }

    {
      final int miniTileCount = data.readInt();
      for (int i = 0; i < miniTileCount; ++i) {
        final MiniTile miniTile = BwemDataBuffer.readMiniTile(data);
        miniTiles.add(miniTile);
      }
    }
  }

  /**
   * This has to be called before any other function is called. A good place to do this is in
   * ExampleAIModule::onStart()
   */
  public void Initialize(final BW bw) {
    this.bw = bw;

    Initialize_native();

    readInitializedData();
  }

  /** Will return true once Initialize() has been called. */
  public boolean Initialized() {
    return tileCount != 0;
  }

  /**
   * Returns the status of the automatic path update (off (false) by default). When on, each time a
   * blocking Neutral (either Mineral or StaticBuilding) is destroyed, any information relative to
   * the paths through the Areas is updated accordingly. For this to function, the Map still needs
   * to be informed of such destructions (by calling OnMineralDestroyed and
   * OnStaticBuildingDestroyed).
   */
  public boolean AutomaticPathUpdate() {
    return isAutomaticPathUpdateEnabled;
  }

  private native boolean EnableAutomaticPathAnalysis_native();

  /**
   * Enables the automatic path update (Cf. AutomaticPathUpdate()). One might NOT want to call this
   * function, in order to make the accessibility between Areas remain the same throughout the game.
   * Even in this case, one should keep calling OnMineralDestroyed and OnStaticBuildingDestroyed.
   */
  public void EnableAutomaticPathAnalysis() {
    if (!isAutomaticPathUpdateEnabled) {
      isAutomaticPathUpdateEnabled = EnableAutomaticPathAnalysis_native();
    }
  }

  /**
   * Tries to assign one Base for each starting Location in StartingLocations(). Only nearby Bases
   * can be assigned (Cf. detail::max_tiles_between_StartingLocation_and_its_AssignedBase). Each
   * such assigned Base then has Starting() == true, and its Location() is updated. Returns whether
   * the function succeeded (a fail may indicate a failure in BWEM's Base placement analysis or a
   * suboptimal placement in one of the starting Locations). You normally should call this function,
   * unless you want to compare the StartingLocations() with BWEM's suggested locations for the
   * Bases.
   */
  public native boolean FindBasesForStartingLocations();

  /** Returns the size of the Map in Tiles. */
  public TilePosition Size() {
    return tileSize;
  }

  /** Returns the size of the Map in MiniTiles. */
  public WalkPosition WalkSize() {
    return walkSize;
  }

  /** Returns the size of the Map in pixels. */
  public Position PixelSize() {
    return pixelSize;
  }

  /** Returns the center of the Map in pixels. */
  public Position Center() {
    return center;
  }

  /** Returns a random position in the Map in pixels. */
  public Position RandomPosition() {
    final int x = randomNumberGenerator.nextInt(PixelSize().getX());
    final int y = randomNumberGenerator.nextInt(PixelSize().getY());
    return new Position(x, y);
  }

  /** Returns the maximum altitude in the whole Map (Cf. MiniTile::Altitude()). */
  public int MaxAltitude() {
    return maxAltitude;
  }

  //  // Returns the number of Bases.
  //  virtual int							BaseCount() const = 0;
  //
  //  // Returns the number of ChokePoints.
  //  virtual int							ChokePointCount() const = 0;
  //
  //  // Returns a Tile, given its position.
  //	const Tile &						GetTile(const BWAPI::TilePosition & p, utils::check_t checkMode =
  // utils::check_t::check) const	{ bwem_assert((checkMode == utils::check_t::no_check) ||
  // Valid(p)); utils::unused(checkMode); return m_Tiles[Size().x * p.y + p.x]; }
  //
  //  // Returns a MiniTile, given its position.
  //	const MiniTile &					GetMiniTile(const BWAPI::WalkPosition & p, utils::check_t checkMode =
  // utils::check_t::check) const	{ bwem_assert((checkMode == utils::check_t::no_check) ||
  // Valid(p)); utils::unused(checkMode); return m_MiniTiles[WalkSize().x * p.y + p.x]; }
  //
  //  // Returns a Tile or a MiniTile, given its position.
  //  // Provided as a support of generic algorithms.
  //  template<class TPosition>
  //      const typename utils::TileOfPosition<TPosition>::type & GetTTile(const TPosition & p,
  // utils::check_t checkMode = utils::check_t::check) const;

  // Provides access to the internal array of Tiles.
  public List<Tile> Tiles() {
    return tiles;
  }

  // Provides access to the internal array of MiniTiles.
  public List<MiniTile> MiniTiles() {
    return miniTiles;
  }

  public boolean Valid(final TilePosition tilePosition) {
    final int x = tilePosition.getX();
    final int y = tilePosition.getY();
    return (0 <= x) && (x < Size().getX()) && (0 <= y) && (y < Size().getY());
  }

  public boolean Valid(final WalkPosition walkPosition) {
    final int x = walkPosition.getX();
    final int y = walkPosition.getY();
    return (0 <= x) && (x < WalkSize().getX()) && (0 <= y) && (y < WalkSize().getY());
  }

  public boolean Valid(final Position position) {
    final int x = position.getX();
    final int y = position.getY();
    return (0 <= x) && (x < PixelSize().getX()) && (0 <= y) && (y < PixelSize().getY());
  }

  public TilePosition Crop(final TilePosition tilePosition) {
    final int[] xy = tileSizeCropper.crop(tilePosition.getX(), tilePosition.getY());
    return new TilePosition(xy[0], xy[1]);
  }

  public WalkPosition Crop(final WalkPosition walkPosition) {
    final int[] xy = walkSizeCropper.crop(walkPosition.getX(), walkPosition.getY());
    return new WalkPosition(xy[0], xy[1]);
  }

  public Position Crop(final Position position) {
    final int[] xy = pixelSizeCropper.crop(position.getX(), position.getY());
    return new Position(xy[0], xy[1]);
  }

  /**
   * Returns a reference to the starting Locations. Note: these correspond to
   * BWAPI::getStartLocations().
   */
  public List<TilePosition> StartingLocations() {
    return startingLocations;
  }

  //  // Returns a reference to the Minerals (Cf. Mineral).
  //  virtual const std::vector<std::unique_ptr<Mineral>> &			Minerals() const = 0;
  //
  //  // Returns a reference to the Geysers (Cf. Geyser).
  //  virtual const std::vector<std::unique_ptr<Geyser>> &			Geysers() const = 0;
  //
  //  // Returns a reference to the StaticBuildings (Cf. StaticBuilding).
  //  virtual const std::vector<std::unique_ptr<StaticBuilding>> &	StaticBuildings() const = 0;
  //
  //  // If a Mineral wrappers the given BWAPI unit, returns a pointer to it.
  //  // Otherwise, returns nullptr.
  //  virtual Mineral *					GetMineral(BWAPI::Unit u) const = 0;
  //
  //  // If a Geyser wrappers the given BWAPI unit, returns a pointer to it.
  //  // Otherwise, returns nullptr.
  //  virtual Geyser *					GetGeyser(BWAPI::Unit g) const = 0;

  private native void OnMineralDestroyed_native(int unitId);

  private native void OnStaticBuildingDestroyed_native(int unitId);

  public void onUnitDestroy(final Unit unit) {
    if (unit.getType().isMineralField()) {
      OnMineralDestroyed_native(unit.getID());
    } else if (unit.getType().isBuilding()) {
      // TODO: Loop through static buildings and compare against this unit's ID.

      // OnStaticBuildingDestroyed_native();

      throw new UnsupportedOperationException("TODO"); // TODO
    }
  }

  //
  //  // Returns a reference to the Areas.
  //  virtual const std::vector<Area> &	Areas() const = 0;
  //
  //  // Returns an Area given its id.
  //  virtual const Area *				GetArea(Area::id id) const = 0;
  //
  //  // If the MiniTile at w is walkable and is part of an Area, returns that Area.
  //  // Otherwise, returns nullptr;
  //  // Note: because of the lakes, GetNearestArea should be prefered over GetArea.
  //  virtual const Area *				GetArea(BWAPI::WalkPosition w) const = 0;
  //
  //  // If the Tile at t contains walkable sub-MiniTiles which are all part of the same Area,
  // returns that Area.
  //  // Otherwise, returns nullptr;
  //  // Note: because of the lakes, GetNearestArea should be prefered over GetArea.
  //  virtual const Area *				GetArea(BWAPI::TilePosition t) const = 0;
  //
  //  // Returns the nearest Area from w.
  //  // Returns nullptr only if Areas().empty()
  //  // Note: Uses a breadth first search.
  //  virtual const Area *				GetNearestArea(BWAPI::WalkPosition w) const = 0;
  //
  //  // Returns the nearest Area from t.
  //  // Returns nullptr only if Areas().empty()
  //  // Note: Uses a breadth first search.
  //  virtual const Area *				GetNearestArea(BWAPI::TilePosition t) const = 0;
  //
  //
  //  // Returns a list of ChokePoints, which is intended to be the shortest walking path from 'a'
  // to 'b'.
  //  // Furthermore, if pLength != nullptr, the pointed integer is set to the corresponding length
  // in pixels.
  //  // If 'a' is not accessible from 'b', the empty Path is returned, and -1 is put in *pLength
  // (if pLength != nullptr).
  //  // If 'a' and 'b' are in the same Area, the empty Path is returned, and a.getApproxDistance(b)
  // is put in *pLength (if pLength != nullptr).
  //  // Otherwise, the function relies on ChokePoint::GetPathTo.
  //  // Cf. ChokePoint::GetPathTo for more information.
  //  // Note: in order to retrieve the Areas of 'a' and 'b', the function starts by calling
  //  //       GetNearestArea(TilePosition(a)) and GetNearestArea(TilePosition(b)).
  //  //       While this brings robustness, this could yield surprising results in the case where
  // 'a' and/or 'b' are in the Water.
  //  //       To avoid this and the potential performance penalty, just make sure GetArea(a) !=
  // nullptr and GetArea(b) != nullptr.
  //  //       Then GetPath should perform very quick.
  //  virtual const CPPath &				GetPath(const BWAPI::Position & a, const BWAPI::Position & b, int *
  // pLength = nullptr) const = 0;

  // TODO: Create a ChokePoint class and return a list of chokepoints.
  private native int[] GetPath_native(int ax, int ay, int bx, int by);

  public List<WalkPosition> GetPath(
      final Position a, final Position b, final PathLength outputLength) {
    final DataBuffer data = new DataBuffer(GetPath_native(a.getX(), a.getY(), b.getX(), b.getY()));

    final List<WalkPosition> path = new ArrayList<>();

    final int chokepointCount = data.readInt();

    for (int i = 0; i < chokepointCount; ++i) {
      final WalkPosition chokepoint = BwapiDataBuffer.readWalkPosition(data);
      path.add(chokepoint);
    }

    final int length = data.readInt();
    if (outputLength != null) {
      outputLength.setValue(length);
    }

    return path;
  }

  public List<WalkPosition> GetPath(final Position a, final Position b) {
    return GetPath(a, b, null);
  }

  //
  //  // Generic algorithm for breadth first search in the Map.
  //  // See the several use cases in BWEM source files.
  //  template<class TPosition, class Pred1, class Pred2>
  //  TPosition							BreadthFirstSearch(TPosition start, Pred1 findCond, Pred2 visitCond, bool
  // connect8 = true) const;
  //
  //
  //  // Returns the union of the geometry of all the ChokePoints. Cf. ChokePoint::Geometry()
  //  virtual const std::vector<std::pair<std::pair<Area::id, Area::id>, BWAPI::WalkPosition>> &
  // RawFrontier() const = 0;
}
