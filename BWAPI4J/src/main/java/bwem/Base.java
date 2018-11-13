package bwem;

import bwem.unit.Geyser;
import bwem.unit.Mineral;
import java.util.List;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.type.UnitType;

public interface Base {
  Position RESOURCE_DEPOT_SIZE_IN_PIXELS = UnitType.Terran_Command_Center.tileSize().toPosition();
  Position RESOURCE_DEPOT_CENTER_OFFSET_IN_PIXELS =
      new Position(
          RESOURCE_DEPOT_SIZE_IN_PIXELS.getX() / 2, RESOURCE_DEPOT_SIZE_IN_PIXELS.getY() / 2);

  // Tells whether this Base's location is contained in Map::StartingLocations()
  // Note: all players start at locations taken from Map::StartingLocations(),
  //       which doesn't mean all the locations in Map::StartingLocations() are actually used.
  boolean Starting();

  // Returns the Area this Base belongs to.
  Area GetArea();

  // Returns the location of this Base (top left Tile position).
  // If Starting() == true, it is guaranteed that the loction corresponds exactly to one of
  // Map::StartingLocations().
  TilePosition Location();

  // Returns the location of this Base (center in pixels).
  Position Center();

  // Returns the available Minerals.
  // These Minerals are assigned to this Base (it is guaranteed that no other Base provides them).
  // Note: The size of the returned list may decrease, as some of the Minerals may get destroyed.
  List<Mineral> Minerals();

  // Returns the available Geysers.
  // These Geysers are assigned to this Base (it is guaranteed that no other Base provides them).
  // Note: The size of the returned list may NOT decrease, as Geysers never get destroyed.
  List<Geyser> Geysers();

  // Returns the blocking Minerals.
  // These Minerals are special ones: they are placed at the exact location of this Base (or very
  // close),
  // thus blocking the building of a Command Center, Nexus, or Hatchery.
  // So before trying to build this Base, one have to finish gathering these Minerals first.
  // Fortunately, these are guaranteed to have their InitialAmount() <= 8.
  // As an example of blocking Minerals, see the two islands in Andromeda.scx.
  // Note: if Starting() == true, an empty list is returned.
  // Note Base::BlockingMinerals() should not be confused with ChokePoint::BlockingNeutral() and
  // Neutral::Blocking():
  //      the last two refer to a Neutral blocking a ChokePoint, not a Base.
  List<Mineral> BlockingMinerals();
}
