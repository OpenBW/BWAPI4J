package bwem.util.buffer;

import bwapi.Unit;
import bwapi.UnitType;
import bwem.tile.MiniTile;
import bwem.tile.MiniTileImpl;
import bwem.tile.Tile;
import bwem.tile.TileImpl;
import bwem.unit.Geyser;
import bwem.unit.Mineral;
import bwem.unit.Neutral;
import bwem.unit.StaticBuilding;
import java.util.Collection;
import org.openbw.bwapi4j.util.buffer.DataBuffer;

public class BwemDataBuffer {
  public static Tile readTile(final DataBuffer data, final Collection<Neutral> neutrals) {
    final boolean isBuildable = data.readBoolean();
    final int groundHeight = data.readInt();
    final boolean isDoodad = data.readBoolean();
    final int minAltitude = data.readInt();
    final int areaId = data.readInt();
    final int neutralId = data.readInt();

    final TileImpl tile = new TileImpl();

    tile.buildable = isBuildable;
    tile.groundHeight = groundHeight;
    tile.doodad = isDoodad;
    tile.minAltitude = minAltitude;
    tile.areaId = areaId;

    if (neutralId != -1) {
      for (final Neutral neutral : neutrals) {
        if (neutral.Unit().getID() == neutralId) {
          tile.neutral = neutral;
          break;
        }
      }
    }

    return tile;
  }

  public static MiniTile readMiniTile(final DataBuffer data) {
    final int altitude = data.readInt();
    final int areaId = data.readInt();

    return new MiniTileImpl(altitude, areaId);
  }

  public static Mineral readMineral(final DataBuffer data, final Collection<Unit> units) {
    final int unitId = data.readInt();

    Unit mineralUnit = null;
    for (final Unit unit : units) {
      if (unit.getID() == unitId) {
        mineralUnit = unit;
        break;
      }
    }

    if (mineralUnit == null) {
      throw new IllegalStateException(
          "Failed to create Mineral for unitId=" + unitId + ": not found");
    } else if (!mineralUnit.getType().isMineralField()) {
      throw new IllegalStateException(
          "Failed to create Mineral for unitId=" + unitId + ": unit is not a mineral field");
    } else {
      return new Mineral(mineralUnit);
    }
  }

  public static Geyser readGeyser(final DataBuffer data, final Collection<Unit> units) {
    final int unitId = data.readInt();

    Unit geyserUnit = null;
    for (final Unit unit : units) {
      if (unit.getID() == unitId) {
        geyserUnit = unit;
        break;
      }
    }

    if (geyserUnit == null) {
      throw new IllegalStateException(
          "Failed to create Geyser for unitId=" + unitId + ": not found");
    } else if (geyserUnit.getType() != UnitType.Resource_Vespene_Geyser) {
      throw new IllegalStateException(
          "Failed to create Geyser for unitId=" + unitId + ": unit is not a vespene geyser");
    } else {
      return new Geyser(geyserUnit);
    }
  }

  public static StaticBuilding readStaticBuilding(
      final DataBuffer data, final Collection<Unit> units) {
    final int unitId = data.readInt();

    Unit staticBuilding = null;
    for (final Unit unit : units) {
      if (unit.getID() == unitId) {
        staticBuilding = unit;
        break;
      }
    }

    if (staticBuilding == null) {
      throw new IllegalStateException(
          "Failed to create StaticBuilding for unitId=" + unitId + ": not found");
    } else if (!staticBuilding.getType().isBuilding() || !staticBuilding.getPlayer().isNeutral()) {
      throw new IllegalStateException(
          "Failed to create StaticBuilding for unitId="
              + unitId
              + ": unit is not a neutral building");
    } else {
      return new StaticBuilding(staticBuilding);
    }
  }
}
