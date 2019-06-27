package org.openbw.bwapi4j.util.buffer;

import bwapi.BW;
import bwapi.Player;
import bwapi.Position;
import bwapi.TilePosition;
import bwapi.Unit;
import bwapi.UnitCommand;
import bwapi.UnitCommandType;
import bwapi.WalkPosition;
import java.util.ArrayList;
import java.util.List;

public class BwapiDataBuffer {
  public static TilePosition readTilePosition(final DataBuffer data) {
    final int x = data.readInt();
    final int y = data.readInt();
    return new TilePosition(x, y);
  }

  public static WalkPosition readWalkPosition(final DataBuffer data) {
    final int x = data.readInt();
    final int y = data.readInt();
    return new WalkPosition(x, y);
  }

  public static Position readPosition(final DataBuffer data) {
    final int x = data.readInt();
    final int y = data.readInt();
    return new Position(x, y);
  }

  public static List<Position> readPositions(final DataBuffer data) {
    final List<Position> positions = new ArrayList<>(data.size());

    while (data.hasNext()) {
      positions.add(readPosition(data));
    }

    return positions;
  }

  public static UnitCommand readUnitCommand(final DataBuffer data, final BW bw) {
    final int unitId = data.readInt();
    final int unitCommandTypeId = data.readInt();
    final int targetUnitId = data.readInt();
    final int x = data.readInt();
    final int y = data.readInt();
    final int extra = data.readInt();

    final Unit unit = bw.getUnit(unitId);
    final UnitCommandType unitCommandType = UnitCommandType.withId(unitCommandTypeId);
    final Unit targetUnit = bw.getUnit(targetUnitId);

    return new UnitCommand(unit, unitCommandType, targetUnit, x, y, extra);
  }

  public static Unit getUnitById(final DataBuffer data, final BW bw) {
    final int unitId = data.readInt();
    return bw.getUnit(unitId);
  }

  public static List<Unit> getUnitsByIds(final DataBuffer data, final BW bw) {
    final List<Unit> units = new ArrayList<>(data.size());

    while (data.hasNext()) {
      final Unit unit = getUnitById(data, bw);

      if (unit != null) {
        units.add(unit);
      }
    }

    return units;
  }

  public static Player getPlayerById(final DataBuffer data, final BW bw) {
    final int playerId = data.readInt();
    return bw.getPlayer(playerId);
  }

  public static List<Player> getPlayersByIds(final DataBuffer data, final BW bw) {
    final List<Player> players = new ArrayList<>(data.size());

    while (data.hasNext()) {
      final Player player = getPlayerById(data, bw);

      if (player != null) {
        players.add(player);
      }
    }

    return players;
  }
}
