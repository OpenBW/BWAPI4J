package org.openbw.bwapi4j.unit;

import java.util.Objects;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.type.UpgradeType;

public class UnitCommand {
  public static class Unused {
    private Unused() throws InstantiationException {
      throw new InstantiationException("This constructor is disabled.");
    }

    public static final int INTEGER = -1;
    public static final Unit UNIT = null;
    public static final TechType TECH_TYPE = TechType.None;
    public static final UnitCommandType UNIT_COMMAND_TYPE = UnitCommandType.None;
    public static final UnitType UNIT_TYPE = UnitType.None;
    public static final UpgradeType UPGRADE_TYPE = UpgradeType.None;
  }

  Unit unit;
  UnitCommandType unitCommandType;
  Unit target;
  int x;
  int y;
  int extra;

  public UnitCommand() {
    this.unit = Unused.UNIT;
    this.unitCommandType = Unused.UNIT_COMMAND_TYPE;
    this.target = Unused.UNIT;
    this.x = Unused.INTEGER;
    this.y = Unused.INTEGER;
    this.extra = Unused.INTEGER;
  }

  public UnitCommand(
      final Unit unit,
      final UnitCommandType unitCommandType,
      final Unit target,
      final int x,
      final int y,
      final int extra) {
    this.unit = unit;
    this.unitCommandType = unitCommandType;
    this.target = target;
    this.x = x;
    this.y = y;
    this.extra = extra;
  }

  public UnitCommand(final Unit unit, final UnitCommandType type) {
    this();
    this.unit = unit;
    this.unitCommandType = type;
  }

  private void setTargetUnit(final Unit targetUnit) {
    target = targetUnit;
    x = Unused.INTEGER;
    y = Unused.INTEGER;
  }

  private void setTargetPosition(final Position targetPosition) {
    target = Unused.UNIT;

    if (targetPosition == null) {
      x = Unused.INTEGER;
      y = Unused.INTEGER;
    } else {
      x = targetPosition.getX();
      y = targetPosition.getY();
    }
  }

  private void setTargetTilePosition(final TilePosition targetPosition) {
    target = Unused.UNIT;

    if (targetPosition == null) {
      x = Unused.INTEGER;
      y = Unused.INTEGER;
    } else {
      x = targetPosition.getX();
      y = targetPosition.getY();
    }
  }

  private void setShiftQueueCommand(final boolean shiftQueueCommand) {
    extra = shiftQueueCommand ? 1 : 0;
  }

  private void setExtra(final UnitType type) {
    extra = type.getID();
  }

  private void setExtra(final TechType type) {
    extra = type.getID();
  }

  private void setExtra(final UpgradeType type) {
    extra = type.getID();
  }

  public static UnitCommand attack(Unit unit, Unit target) {
    return attack(unit, target, false);
  }

  public static UnitCommand attack(Unit unit, Unit target, boolean shiftQueueCommand) {
    final UnitCommand unitCommand = new UnitCommand();
    unitCommand.unit = unit;
    unitCommand.unitCommandType = UnitCommandType.Attack_Unit;
    unitCommand.setTargetUnit(target);
    unitCommand.setShiftQueueCommand(shiftQueueCommand);
    return unitCommand;
  }

  public static UnitCommand attack(Unit unit, Position target) {
    return attack(unit, target, false);
  }

  public static UnitCommand attack(Unit unit, Position target, boolean shiftQueueCommand) {
    final UnitCommand unitCommand = new UnitCommand();
    unitCommand.unit = unit;
    unitCommand.unitCommandType = UnitCommandType.Attack_Move;
    unitCommand.setTargetPosition(target);
    unitCommand.setShiftQueueCommand(shiftQueueCommand);
    return unitCommand;
  }

  public static UnitCommand build(Unit unit, TilePosition target, UnitType unitType) {
    final UnitCommand unitCommand = new UnitCommand();
    unitCommand.unit = unit;
    unitCommand.unitCommandType = UnitCommandType.Build;
    unitCommand.setTargetTilePosition(target);
    unitCommand.setExtra(unitType);
    return unitCommand;
  }

  public static UnitCommand buildAddon(Unit unit, UnitType unitType) {
    final UnitCommand unitCommand = new UnitCommand();
    unitCommand.unit = unit;
    unitCommand.unitCommandType = UnitCommandType.Build_Addon;
    unitCommand.setExtra(unitType);
    return unitCommand;
  }

  public static UnitCommand train(Unit unit, UnitType unitType) {
    final UnitCommand unitCommand = new UnitCommand();
    unitCommand.unit = unit;
    unitCommand.unitCommandType = UnitCommandType.Train;
    unitCommand.setExtra(unitType);
    return unitCommand;
  }

  public static UnitCommand morph(Unit unit, UnitType unitType) {
    final UnitCommand unitCommand = new UnitCommand();
    unitCommand.unit = unit;
    unitCommand.unitCommandType = UnitCommandType.Morph;
    unitCommand.setExtra(unitType);
    return unitCommand;
  }

  public static UnitCommand research(Unit unit, TechType tech) {
    final UnitCommand unitCommand = new UnitCommand();
    unitCommand.unit = unit;
    unitCommand.unitCommandType = UnitCommandType.Research;
    unitCommand.setExtra(tech);
    return unitCommand;
  }

  public static UnitCommand upgrade(Unit unit, UpgradeType upgrade) {
    final UnitCommand unitCommand = new UnitCommand();
    unitCommand.unit = unit;
    unitCommand.unitCommandType = UnitCommandType.Upgrade;
    unitCommand.setExtra(upgrade);
    return unitCommand;
  }

  public static UnitCommand setRallyPoint(Unit unit, Position target) {
    final UnitCommand unitCommand = new UnitCommand();
    unitCommand.unit = unit;
    unitCommand.unitCommandType = UnitCommandType.Set_Rally_Position;
    unitCommand.setTargetPosition(target);
    return unitCommand;
  }

  public static UnitCommand setRallyPoint(Unit unit, Unit target) {
    final UnitCommand unitCommand = new UnitCommand();
    unitCommand.unit = unit;
    unitCommand.unitCommandType = UnitCommandType.Set_Rally_Unit;
    unitCommand.setTargetUnit(target);
    return unitCommand;
  }

  public static UnitCommand move(Unit unit, Position target) {
    return move(unit, target, false);
  }

  public static UnitCommand move(Unit unit, Position target, boolean shiftQueueCommand) {
    final UnitCommand unitCommand = new UnitCommand();
    unitCommand.unit = unit;
    unitCommand.unitCommandType = UnitCommandType.Move;
    unitCommand.setTargetPosition(target);
    unitCommand.setShiftQueueCommand(shiftQueueCommand);
    return unitCommand;
  }

  public static UnitCommand patrol(Unit unit, Position target) {
    return patrol(unit, target, false);
  }

  public static UnitCommand patrol(Unit unit, Position target, boolean shiftQueueCommand) {
    final UnitCommand unitCommand = new UnitCommand();
    unitCommand.unit = unit;
    unitCommand.unitCommandType = UnitCommandType.Patrol;
    unitCommand.setTargetPosition(target);
    unitCommand.setShiftQueueCommand(shiftQueueCommand);
    return unitCommand;
  }

  public static UnitCommand holdPosition(Unit unit) {
    return holdPosition(unit, false);
  }

  public static UnitCommand holdPosition(Unit unit, boolean shiftQueueCommand) {
    final UnitCommand unitCommand = new UnitCommand();
    unitCommand.unit = unit;
    unitCommand.unitCommandType = UnitCommandType.Hold_Position;
    unitCommand.setShiftQueueCommand(shiftQueueCommand);
    return unitCommand;
  }

  public static UnitCommand stop(Unit unit) {
    return stop(unit, false);
  }

  public static UnitCommand stop(Unit unit, boolean shiftQueueCommand) {
    final UnitCommand unitCommand = new UnitCommand();
    unitCommand.unit = unit;
    unitCommand.unitCommandType = UnitCommandType.Stop;
    unitCommand.setShiftQueueCommand(shiftQueueCommand);
    return unitCommand;
  }

  public static UnitCommand follow(Unit unit, Unit target) {
    return follow(unit, target, false);
  }

  public static UnitCommand follow(Unit unit, Unit target, boolean shiftQueueCommand) {
    final UnitCommand unitCommand = new UnitCommand();
    unitCommand.unit = unit;
    unitCommand.unitCommandType = UnitCommandType.Follow;
    unitCommand.setTargetUnit(target);
    unitCommand.setShiftQueueCommand(shiftQueueCommand);
    return unitCommand;
  }

  public static UnitCommand gather(Unit unit, Unit target) {
    return gather(unit, target, false);
  }

  public static UnitCommand gather(Unit unit, Unit target, boolean shiftQueueCommand) {
    final UnitCommand unitCommand = new UnitCommand();
    unitCommand.unit = unit;
    unitCommand.unitCommandType = UnitCommandType.Gather;
    unitCommand.setTargetUnit(target);
    unitCommand.setShiftQueueCommand(shiftQueueCommand);
    return unitCommand;
  }

  public static UnitCommand returnCargo(Unit unit) {
    return returnCargo(unit, false);
  }

  public static UnitCommand returnCargo(Unit unit, boolean shiftQueueCommand) {
    final UnitCommand unitCommand = new UnitCommand();
    unitCommand.unit = unit;
    unitCommand.unitCommandType = UnitCommandType.Return_Cargo;
    unitCommand.setShiftQueueCommand(shiftQueueCommand);
    return unitCommand;
  }

  public static UnitCommand repair(Unit unit, Unit target) {
    return repair(unit, target, false);
  }

  public static UnitCommand repair(Unit unit, Unit target, boolean shiftQueueCommand) {
    final UnitCommand unitCommand = new UnitCommand();
    unitCommand.unit = unit;
    unitCommand.unitCommandType = UnitCommandType.Repair;
    unitCommand.setTargetUnit(target);
    unitCommand.setShiftQueueCommand(shiftQueueCommand);
    return unitCommand;
  }

  public static UnitCommand burrow(Unit unit) {
    final UnitCommand unitCommand = new UnitCommand();
    unitCommand.unit = unit;
    unitCommand.unitCommandType = UnitCommandType.Burrow;
    return unitCommand;
  }

  public static UnitCommand unburrow(Unit unit) {
    final UnitCommand unitCommand = new UnitCommand();
    unitCommand.unit = unit;
    unitCommand.unitCommandType = UnitCommandType.Unburrow;
    return unitCommand;
  }

  public static UnitCommand cloak(Unit unit) {
    final UnitCommand unitCommand = new UnitCommand();
    unitCommand.unit = unit;
    unitCommand.unitCommandType = UnitCommandType.Cloak;
    return unitCommand;
  }

  public static UnitCommand decloak(Unit unit) {
    final UnitCommand unitCommand = new UnitCommand();
    unitCommand.unit = unit;
    unitCommand.unitCommandType = UnitCommandType.Decloak;
    return unitCommand;
  }

  public static UnitCommand siege(Unit unit) {
    final UnitCommand unitCommand = new UnitCommand();
    unitCommand.unit = unit;
    unitCommand.unitCommandType = UnitCommandType.Siege;
    return unitCommand;
  }

  public static UnitCommand unsiege(Unit unit) {
    final UnitCommand unitCommand = new UnitCommand();
    unitCommand.unit = unit;
    unitCommand.unitCommandType = UnitCommandType.Unsiege;
    return unitCommand;
  }

  public static UnitCommand lift(Unit unit) {
    final UnitCommand unitCommand = new UnitCommand();
    unitCommand.unit = unit;
    unitCommand.unitCommandType = UnitCommandType.Lift;
    return unitCommand;
  }

  public static UnitCommand land(Unit unit, TilePosition target) {
    final UnitCommand unitCommand = new UnitCommand();
    unitCommand.unit = unit;
    unitCommand.unitCommandType = UnitCommandType.Land;
    unitCommand.setTargetTilePosition(target);
    return unitCommand;
  }

  public static UnitCommand load(Unit unit, Unit target) {
    return load(unit, target, false);
  }

  public static UnitCommand load(Unit unit, Unit target, boolean shiftQueueCommand) {
    final UnitCommand unitCommand = new UnitCommand();
    unitCommand.unit = unit;
    unitCommand.unitCommandType = UnitCommandType.Load;
    unitCommand.setTargetUnit(target);
    unitCommand.setShiftQueueCommand(shiftQueueCommand);
    return unitCommand;
  }

  public static UnitCommand unload(Unit unit, Unit target) {
    final UnitCommand unitCommand = new UnitCommand();
    unitCommand.unit = unit;
    unitCommand.unitCommandType = UnitCommandType.Unload;
    unitCommand.setTargetUnit(target);
    return unitCommand;
  }

  public static UnitCommand unloadAll(Unit unit) {
    return unloadAll(unit, false);
  }

  public static UnitCommand unloadAll(Unit unit, boolean shiftQueueCommand) {
    final UnitCommand unitCommand = new UnitCommand();
    unitCommand.unit = unit;
    unitCommand.unitCommandType = UnitCommandType.Unload_All;
    unitCommand.setShiftQueueCommand(shiftQueueCommand);
    return unitCommand;
  }

  public static UnitCommand unloadAll(Unit unit, Position target) {
    return unloadAll(unit, target, false);
  }

  public static UnitCommand unloadAll(Unit unit, Position target, boolean shiftQueueCommand) {
    final UnitCommand unitCommand = new UnitCommand();
    unitCommand.unit = unit;
    unitCommand.unitCommandType = UnitCommandType.Unload_All_Position;
    unitCommand.setTargetPosition(target);
    unitCommand.setShiftQueueCommand(shiftQueueCommand);
    return unitCommand;
  }

  public static UnitCommand rightClick(Unit unit, Position target) {
    return rightClick(unit, target, false);
  }

  public static UnitCommand rightClick(Unit unit, Position target, boolean shiftQueueCommand) {
    final UnitCommand unitCommand = new UnitCommand();
    unitCommand.unit = unit;
    unitCommand.unitCommandType = UnitCommandType.Right_Click_Position;
    unitCommand.setTargetPosition(target);
    unitCommand.setShiftQueueCommand(shiftQueueCommand);
    return unitCommand;
  }

  public static UnitCommand rightClick(Unit unit, Unit target) {
    return rightClick(unit, target, false);
  }

  public static UnitCommand rightClick(Unit unit, Unit target, boolean shiftQueueCommand) {
    final UnitCommand unitCommand = new UnitCommand();
    unitCommand.unit = unit;
    unitCommand.unitCommandType = UnitCommandType.Right_Click_Unit;
    unitCommand.setTargetUnit(target);
    unitCommand.setShiftQueueCommand(shiftQueueCommand);
    return unitCommand;
  }

  public static UnitCommand haltConstruction(Unit unit) {
    final UnitCommand unitCommand = new UnitCommand();
    unitCommand.unit = unit;
    unitCommand.unitCommandType = UnitCommandType.Halt_Construction;
    return unitCommand;
  }

  public static UnitCommand cancelConstruction(Unit unit) {
    final UnitCommand unitCommand = new UnitCommand();
    unitCommand.unit = unit;
    unitCommand.unitCommandType = UnitCommandType.Cancel_Construction;
    return unitCommand;
  }

  public static UnitCommand cancelAddon(Unit unit) {
    final UnitCommand unitCommand = new UnitCommand();
    unitCommand.unit = unit;
    unitCommand.unitCommandType = UnitCommandType.Cancel_Addon;
    return unitCommand;
  }

  public static UnitCommand cancelTrain(Unit unit) {
    return cancelTrain(unit, -2);
  }

  public static UnitCommand cancelTrain(Unit unit, int slot) {
    final UnitCommand unitCommand = new UnitCommand();
    unitCommand.unit = unit;
    unitCommand.unitCommandType = UnitCommandType.Cancel_Train_Slot;
    unitCommand.extra = slot;
    return unitCommand;
  }

  public static UnitCommand cancelMorph(Unit unit) {
    final UnitCommand unitCommand = new UnitCommand();
    unitCommand.unit = unit;
    unitCommand.unitCommandType = UnitCommandType.Cancel_Morph;
    return unitCommand;
  }

  public static UnitCommand cancelResearch(Unit unit) {
    final UnitCommand unitCommand = new UnitCommand();
    unitCommand.unit = unit;
    unitCommand.unitCommandType = UnitCommandType.Cancel_Research;
    return unitCommand;
  }

  public static UnitCommand cancelUpgrade(Unit unit) {
    final UnitCommand unitCommand = new UnitCommand();
    unitCommand.unit = unit;
    unitCommand.unitCommandType = UnitCommandType.Cancel_Upgrade;
    return unitCommand;
  }

  public static UnitCommand useTech(Unit unit, TechType tech) {
    final UnitCommand unitCommand = new UnitCommand();
    unitCommand.unit = unit;
    unitCommand.unitCommandType = UnitCommandType.Use_Tech;
    unitCommand.setExtra(tech);
    return unitCommand;
  }

  public static UnitCommand useTech(Unit unit, TechType tech, Position target) {
    final UnitCommand unitCommand = new UnitCommand();
    unitCommand.unit = unit;
    unitCommand.unitCommandType = UnitCommandType.Use_Tech_Position;
    unitCommand.setTargetPosition(target);
    unitCommand.setExtra(tech);
    return unitCommand;
  }

  public static UnitCommand useTech(Unit unit, TechType tech, Unit target) {
    final UnitCommand unitCommand = new UnitCommand();
    unitCommand.unit = unit;
    unitCommand.unitCommandType = UnitCommandType.Use_Tech_Unit;
    unitCommand.setTargetUnit(target);
    unitCommand.setExtra(tech);
    return unitCommand;
  }

  public static UnitCommand placeCOP(Unit unit, TilePosition target) {
    final UnitCommand unitCommand = new UnitCommand();
    unitCommand.unit = unit;
    unitCommand.unitCommandType = UnitCommandType.Place_COP;
    unitCommand.setTargetTilePosition(target);
    return unitCommand;
  }

  public final UnitCommandType getType() {
    return unitCommandType;
  }

  public Unit getUnit() {
    return unit;
  }

  public Unit getTarget() {
    return target;
  }

  public Position getTargetPosition() {
    final boolean hasValue =
        unitCommandType == UnitCommandType.Build
            || unitCommandType == UnitCommandType.Land
            || unitCommandType == UnitCommandType.Place_COP;
    return hasValue ? new TilePosition(x, y).toPosition() : new Position(x, y);
  }

  public TilePosition getTargetTilePosition() {
    final boolean hasValue =
        unitCommandType == UnitCommandType.Build
            || unitCommandType == UnitCommandType.Land
            || unitCommandType == UnitCommandType.Place_COP;
    return hasValue ? new TilePosition(x, y) : new Position(x, y).toTilePosition();
  }

  public UnitType getUnitType() {
    final boolean hasValue =
        unitCommandType == UnitCommandType.Build
            || unitCommandType == UnitCommandType.Build_Addon
            || unitCommandType == UnitCommandType.Train
            || unitCommandType == UnitCommandType.Morph;
    return hasValue ? UnitType.withId(extra) : Unused.UNIT_TYPE;
  }

  public TechType getTechType() {
    final boolean hasValue =
        unitCommandType == UnitCommandType.Research
            || unitCommandType == UnitCommandType.Use_Tech
            || unitCommandType == UnitCommandType.Use_Tech_Position
            || unitCommandType == UnitCommandType.Use_Tech_Unit;
    return hasValue ? TechType.withId(extra) : Unused.TECH_TYPE;
  }

  public UpgradeType getUpgradeType() {
    final boolean hasValue = unitCommandType == UnitCommandType.Upgrade;
    return hasValue ? UpgradeType.withId(extra) : Unused.UPGRADE_TYPE;
  }

  public int getSlot() {
    final boolean hasValue = unitCommandType == UnitCommandType.Cancel_Train_Slot;
    return hasValue ? extra : Unused.INTEGER;
  }

  public boolean isQueued() {
    final boolean hasValue =
        unitCommandType == UnitCommandType.Attack_Move
            || unitCommandType == UnitCommandType.Attack_Unit
            || unitCommandType == UnitCommandType.Move
            || unitCommandType == UnitCommandType.Patrol
            || unitCommandType == UnitCommandType.Hold_Position
            || unitCommandType == UnitCommandType.Stop
            || unitCommandType == UnitCommandType.Follow
            || unitCommandType == UnitCommandType.Gather
            || unitCommandType == UnitCommandType.Return_Cargo
            || unitCommandType == UnitCommandType.Repair
            || unitCommandType == UnitCommandType.Load
            || unitCommandType == UnitCommandType.Unload_All
            || unitCommandType == UnitCommandType.Unload_All_Position
            || unitCommandType == UnitCommandType.Right_Click_Position
            || unitCommandType == UnitCommandType.Right_Click_Unit;
    return hasValue && extra == 1;
  }

  @Override
  public boolean equals(final Object object) {
    if (object instanceof UnitCommand) {
      final UnitCommand that = (UnitCommand) object;
      return this.unit.equals(that.unit)
          && this.unitCommandType == that.unitCommandType
          && this.target.equals(that.target)
          && this.x == that.x
          && this.y == that.y
          && this.extra == that.extra;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(unit, unitCommandType, target, x, y, extra);
  }
}
