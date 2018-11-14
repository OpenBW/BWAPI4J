package bwem;

import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;

public class Area {
  private final int id;
  private final int groupId;
  private WalkPosition top;
  private TilePosition topLeft;
  private TilePosition bottomRight;
  private int maxAltitude;
  private int tileCount;
  private int miniTileCount;
  private int buildableTileCount;
  private int highGroundTileCount;
  private int veryHighGroundTileCount;

  public Area(final int id, final int groupId) {
    this.id = id;
    this.groupId = groupId;
  }

  // Unique id > 0 of this Area. Range = 1 .. Map::Areas().size()
  // this == Map::GetArea(Id())
  // Id() == Map::GetMiniTile(w).AreaId() for each walkable MiniTile w in this Area.
  // Area::ids are guaranteed to remain unchanged.
  public int Id() {
    return id;
  }

  // Unique id > 0 of the group of Areas which are accessible from this Area.
  // For each pair (a, b) of Areas: a->GroupId() == b->GroupId()  <==>  a->AccessibleFrom(b)
  // A groupId uniquely identifies a maximum set of mutually accessible Areas, that is, in the
  // absence of blocking ChokePoints, a continent.
  public int GroupId() {
    return groupId;
  }

  public TilePosition TopLeft() {
    return topLeft;
  }

  public TilePosition BottomRight() {
    return bottomRight;
  }

  //  BWAPI::TilePosition				BoundingBoxSize() const;

  // Position of the MiniTile with the highest Altitude() value.
  public WalkPosition Top() {
    return top;
  }

  // Returns Map::GetMiniTile(Top()).Altitude().
  public int MaxAltitude() {
    return maxAltitude;
  }

  // Returns the number of MiniTiles in this Area.
  // This most accurately defines the size of this Area.
  public int MiniTiles() {
    return miniTileCount;
  }

  // Returns the percentage of low ground Tiles in this Area.
  public int LowGroundPercentage() {
    return (tileCount - highGroundTileCount - veryHighGroundTileCount) * 100 / tileCount;
  }

  // Returns the percentage of high ground Tiles in this Area.
  public int HighGroundPercentage() {
    return highGroundTileCount * 100 / tileCount;
  }

  // Returns the percentage of very high ground Tiles in this Area.
  public int VeryHighGroundPercentage() {
    return veryHighGroundTileCount * 100 / tileCount;
  }

  // Returns the ChokePoints between this Area and the neighbouring ones.
  // Note: if there are no neighbouring Areas, then an empty set is returned.
  // Note there may be more ChokePoints returned than the number of neighbouring Areas, as there may
  // be several ChokePoints between two Areas (Cf. ChokePoints(const Area * pArea)).
  //	const std::vector<const ChokePoint *> &	ChokePoints() const		{ return m_ChokePoints; }

  //  // Returns the ChokePoints between this Area and pArea.
  //  // Assumes pArea is a neighbour of this Area, i.e. ChokePointsByArea().find(pArea) !=
  // ChokePointsByArea().end()
  //  // Note: there is always at least one ChokePoint between two neighbouring Areas.
  //	const std::vector<ChokePoint> &	ChokePoints(const Area * pArea) const;
  //
  //  // Returns the ChokePoints of this Area grouped by neighbouring Areas
  //  // Note: if there are no neighbouring Areas, than an empty set is returned.
  //	const std::map<const Area *, const std::vector<ChokePoint> *> &	ChokePointsByArea() const	{
  // return m_ChokePointsByArea; }
  //
  //  // Returns the accessible neighbouring Areas.
  //  // The accessible neighbouring Areas are a subset of the neighbouring Areas (the neighbouring
  // Areas can be iterated using ChokePointsByArea()).
  //  // Two neighbouring Areas are accessible from each over if at least one the ChokePoints they
  // share is not Blocked (Cf. ChokePoint::Blocked).
  //	const std::vector<const Area *>&AccessibleNeighbours() const	{ return m_AccessibleNeighbours; }
  //
  //  // Returns whether this Area is accessible from pArea, that is, if they share the same
  // GroupId().
  //  // Note: accessibility is always symmetrical.
  //  // Note: even if a and b are neighbouring Areas,
  //  //       we can have: a->AccessibleFrom(b)
  //  //       and not:     contains(a->AccessibleNeighbours(), b)
  //  // See also GroupId()
  //  bool							AccessibleFrom(const Area * pArea) const	{ return GroupId() == pArea->GroupId(); }
  //
  //  // Returns the Minerals contained in this Area.
  //  // Note: only a call to Map::OnMineralDestroyed(BWAPI::Unit u) may change the result (by
  // removing eventually one element).
  //	const std::vector<Mineral *> &	Minerals() const		{ return m_Minerals; }
  //
  //  // Returns the Geysers contained in this Area.
  //  // Note: the result will remain unchanged.
  //	const std::vector<Geyser *> &	Geysers() const			{ return m_Geysers; }
  //
  //  // Returns the Bases contained in this Area.
  //  // Note: the result will remain unchanged.
  //	const std::vector<Base> &		Bases() const			{ return m_Bases; }
}
