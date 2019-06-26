package bwem.unit;

import bwapi.Position;
import bwapi.TilePosition;
import bwapi.Unit;
import org.openbw.bwapi4j.type.UnitType;

public interface Neutral {
  // If this Neutral is a Ressource, returns a typed pointer to this Ressource.
  // Otherwise, returns nullptr.
  Resource IsResource();

  // If this Neutral is a Mineral, returns a typed pointer to this Mineral.
  // Otherwise, returns nullptr.
  Mineral IsMineral();

  // If this Neutral is a Geyser, returns a typed pointer to this Geyser.
  // Otherwise, returns nullptr.
  Geyser IsGeyser();

  // If this Neutral is a StaticBuilding, returns a typed pointer to this StaticBuilding.
  // Otherwise, returns nullptr.
  StaticBuilding IsStaticBuilding();

  // Returns the BWAPI::Unit this Neutral is wrapping around.
  Unit Unit();

  // Returns the BWAPI::UnitType of the BWAPI::Unit this Neutral is wrapping around.
  UnitType Type();

  // Returns the center of this Neutral, in pixels (same as Unit()->getInitialPosition()).
  Position Pos();

  // Returns the top left Tile position of this Neutral (same as Unit()->getInitialTilePosition()).
  TilePosition TopLeft();

  // Returns the bottom right Tile position of this Neutral
  TilePosition BottomRight();

  // Returns the size of this Neutral, in Tiles (same as Type()->tileSize())
  TilePosition Size();

  // Tells whether this Neutral is blocking some ChokePoint.
  // This applies to Minerals and StaticBuildings only.
  // For each blocking Neutral, a pseudo ChokePoint (which is Blocked()) is created on top of it,
  // with the exception of stacked blocking Neutrals for which only one pseudo ChokePoint is
  // created.
  // Cf. definition of pseudo ChokePoints in class ChokePoint comment.
  // Cf. ChokePoint::BlockingNeutral and ChokePoint::Blocked.
  boolean Blocking();

  // TODO
  // If Blocking() == true, returns the set of Areas blocked by this Neutral.
  //  std::vector<const Area *>		BlockedAreas() const;

  // Returns the next Neutral stacked over this Neutral, if ever.
  // To iterate through the whole stack, one can use the following:
  // for (const Neutral * n = Map::GetTile(TopLeft()).GetNeutral() ; n ; n = n->NextStacked())
  //  Neutral *						NextStacked() const			{ return m_pNextStacked; }
  Neutral NextStacked();

  // Returns the last Neutral stacked over this Neutral, if ever.
  //  Neutral *						LastStacked()				{ Neutral * pTop = this; while (pTop->m_pNextStacked) pTop =
  // pTop->m_pNextStacked; return pTop; }
  Neutral LastStacked();
}
