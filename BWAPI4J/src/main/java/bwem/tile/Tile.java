package bwem.tile;

import bwem.unit.Neutral;

public interface Tile {
  // Corresponds to BWAPI::isBuildable
  // Note: BWEM enforces the relation buildable ==> walkable (Cf. MiniTile::Walkable)
  boolean Buildable();

  // Tile::AreaId() somewhat aggregates the MiniTile::AreaId() values of the 4 x 4 sub-MiniTiles.
  // Let S be the set of MiniTile::AreaId() values for each walkable MiniTile in this Tile.
  // If empty(S), returns 0. Note: in this case, no contained MiniTile is walkable, so all of them
  // have their AreaId() == 0.
  // If S = {a}, returns a (whether positive or negative).
  // If size(S) > 1 returns -1 (note that -1 is never returned by MiniTile::AreaId()).
  int AreaId();

  // Tile::MinAltitude() somewhat aggregates the MiniTile::Altitude() values of the 4 x 4
  // sub-MiniTiles.
  // Returns the minimum value.
  int MinAltitude();

  // Tells if at least one of the sub-MiniTiles is Walkable.
  boolean Walkable();

  // Tells if at least one of the sub-MiniTiles is a Terrain-MiniTile.
  boolean Terrain();

  // 0: lower ground    1: high ground    2: very high ground
  // Corresponds to BWAPI::getGroundHeight / 2
  int GroundHeight();

  // Tells if this Tile is part of a doodad.
  // Corresponds to BWAPI::getGroundHeight % 2
  boolean Doodad();

  // If any Neutral occupies this Tile, returns it (note that all the Tiles it occupies will then
  // return it).
  // Otherwise, returns nullptr.
  // Neutrals are Minerals, Geysers and StaticBuildings (Cf. Neutral).
  // In some maps (e.g. Benzene.scx), several Neutrals are stacked at the same location.
  // In this case, only the "bottom" one is returned, while the other ones can be accessed using
  // Neutral::NextStacked().
  // Because Neutrals never move on the Map, the returned value is guaranteed to remain the same,
  // unless some Neutral
  // is destroyed and BWEM is informed of that by a call of Map::OnMineralDestroyed(BWAPI::Unit u)
  // for exemple. In such a case,
  // BWEM automatically updates the data by deleting the Neutral instance and clearing any reference
  // to it such as the one
  // returned by Tile::GetNeutral(). In case of stacked Neutrals, the next one is then returned.
  Neutral GetNetural();

  // Returns the number of Neutrals that occupy this Tile (Cf. GetNeutral).
  int StackedNeutrals();
}
