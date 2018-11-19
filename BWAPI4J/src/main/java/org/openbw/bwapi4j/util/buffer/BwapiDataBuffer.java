package org.openbw.bwapi4j.util.buffer;

import org.openbw.bwapi4j.BW;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.unit.Unit;
import org.openbw.bwapi4j.unit.UnitCommand;

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
}
