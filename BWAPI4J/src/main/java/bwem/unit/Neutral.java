package bwem.unit;

import java.util.ArrayList;
import java.util.List;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.unit.Unit;

public abstract class Neutral {
  private final Unit bwapiUnit;
  protected Neutral nextStacked;
  private final List<WalkPosition> blockedAreas = new ArrayList<>();

  protected Neutral(final Unit unit) {
    this.bwapiUnit = unit;
  }

  // If this Neutral is a Ressource, returns a typed pointer to this Ressource.
  // Otherwise, returns nullptr.
  public Resource IsResource() {
    return null;
  }

  // If this Neutral is a Mineral, returns a typed pointer to this Mineral.
  // Otherwise, returns nullptr.
  public Mineral IsMineral() {
    return null;
  }

  // If this Neutral is a Geyser, returns a typed pointer to this Geyser.
  // Otherwise, returns nullptr.
  public Geyser IsGeyser() {
    return null;
  }

  // If this Neutral is a StaticBuilding, returns a typed pointer to this StaticBuilding.
  // Otherwise, returns nullptr.
  public StaticBuilding IsStaticBuilding() {
    return null;
  }

  // Returns the BWAPI::Unit this Neutral is wrapping around.
  public Unit Unit() {
    return bwapiUnit;
  }

  // Returns the BWAPI::UnitType of the BWAPI::Unit this Neutral is wrapping around.
  public UnitType Type() {
    return Unit().getType();
  }

  // Returns the center of this Neutral, in pixels (same as Unit()->getInitialPosition()).
  public Position Pos() {
    return Unit().getPosition();
  }

  // Returns the top left Tile position of this Neutral (same as Unit()->getInitialTilePosition()).
  public TilePosition TopLeft() {
    return Unit().getTilePosition();
  }

  // Returns the bottom right Tile position of this Neutral
  public TilePosition BottomRight() {
    return TopLeft().add(Size());
  }

  // Returns the size of this Neutral, in Tiles (same as Type()->tileSize())
  public TilePosition Size() {
    return Type().tileSize();
  }

  // Tells whether this Neutral is blocking some ChokePoint.
  // This applies to Minerals and StaticBuildings only.
  // For each blocking Neutral, a pseudo ChokePoint (which is Blocked()) is created on top of it,
  // with the exception of stacked blocking Neutrals for which only one pseudo ChokePoint is
  // created.
  // Cf. definition of pseudo ChokePoints in class ChokePoint comment.
  // Cf. ChokePoint::BlockingNeutral and ChokePoint::Blocked.
  public boolean Blocking() {
    return !blockedAreas.isEmpty();
  }

  // If Blocking() == true, returns the set of Areas blocked by this Neutral.
  //  std::vector<const Area *>		BlockedAreas() const;

  // Returns the next Neutral stacked over this Neutral, if ever.
  // To iterate through the whole stack, one can use the following:
  // for (const Neutral * n = Map::GetTile(TopLeft()).GetNeutral() ; n ; n = n->NextStacked())
  //  Neutral *						NextStacked() const			{ return m_pNextStacked; }
  public Neutral NextStacked() {
    return nextStacked;
  }

  // Returns the last Neutral stacked over this Neutral, if ever.
  //  Neutral *						LastStacked()				{ Neutral * pTop = this; while (pTop->m_pNextStacked) pTop =
  // pTop->m_pNextStacked; return pTop; }
  public Neutral LastStacked() {
    Neutral top = this;
    while (top.NextStacked() != null) {
      top = top.NextStacked();
    }
    return top;
  }

  ////////////////////////////////////////////////////////////////////////////
  //	Details: The functions below are used by the BWEM's internals

  //  void							SetBlocking(const std::vector<BWAPI::WalkPosition> & blockedAreas);
}
