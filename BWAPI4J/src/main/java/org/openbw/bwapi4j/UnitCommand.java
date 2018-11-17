package org.openbw.bwapi4j;

import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.type.UpgradeType;
import org.openbw.bwapi4j.unit.Unit;

public class UnitCommand {
  public static class Unused {
    private Unused() throws InstantiationException {
      throw new InstantiationException("This constructor is disabled.");
    }

    public static final int INTEGER = -1;
    public static final Unit UNIT = null;
    public static final UnitCommandType UNIT_COMMAND_TYPE = UnitCommandType.None;
  }

  private Unit unit;
  private UnitCommandType unitCommandType;
  private Unit target;
  private int x;
  private int y;
  private int extra;

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

  public static UnitCommand attack(Unit unit, Unit target) {
    return attack(unit, target, false);
  }

  public static UnitCommand attack(Unit unit, Unit target, boolean shiftQueueCommand) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public static UnitCommand build(Unit unit, TilePosition target, UnitType type) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public static UnitCommand buildAddon(Unit unit, UnitType type) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public static UnitCommand train(Unit unit, UnitType type) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public static UnitCommand morph(Unit unit, UnitType type) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public static UnitCommand research(Unit unit, TechType tech) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public static UnitCommand upgrade(Unit unit, UpgradeType upgrade) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public static UnitCommand setRallyPoint(Unit unit, Position target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public static UnitCommand setRallyPoint(Unit unit, Unit target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public static UnitCommand move(Unit unit, Position target) {
    return move(unit, target, false);
  }

  public static UnitCommand move(Unit unit, Position target, boolean shiftQueueCommand) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public static UnitCommand patrol(Unit unit, Position target) {
    return patrol(unit, target, false);
  }

  public static UnitCommand patrol(Unit unit, Position target, boolean shiftQueueCommand) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public static UnitCommand holdPosition(Unit unit) {
    return holdPosition(unit, false);
  }

  public static UnitCommand holdPosition(Unit unit, boolean shiftQueueCommand) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public static UnitCommand stop(Unit unit) {
    return stop(unit, false);
  }

  public static UnitCommand stop(Unit unit, boolean shiftQueueCommand) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public static UnitCommand follow(Unit unit, Unit target) {
    return follow(unit, target, false);
  }

  public static UnitCommand follow(Unit unit, Unit target, boolean shiftQueueCommand) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public static UnitCommand gather(Unit unit, Unit target) {
    return gather(unit, target, false);
  }

  public static UnitCommand gather(Unit unit, Unit target, boolean shiftQueueCommand) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public static UnitCommand returnCargo(Unit unit) {
    return returnCargo(unit, false);
  }

  public static UnitCommand returnCargo(Unit unit, boolean shiftQueueCommand) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public static UnitCommand repair(Unit unit, Unit target) {
    return repair(unit, target, false);
  }

  public static UnitCommand repair(Unit unit, Unit target, boolean shiftQueueCommand) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public static UnitCommand burrow(Unit unit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public static UnitCommand unburrow(Unit unit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public static UnitCommand cloak(Unit unit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public static UnitCommand decloak(Unit unit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public static UnitCommand siege(Unit unit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public static UnitCommand unsiege(Unit unit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public static UnitCommand lift(Unit unit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public static UnitCommand land(Unit unit, TilePosition target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public static UnitCommand load(Unit unit, Unit target) {
    return load(unit, target, false);
  }

  public static UnitCommand load(Unit unit, Unit target, boolean shiftQueueCommand) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public static UnitCommand unload(Unit unit, Unit target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public static UnitCommand unloadAll(Unit unit) {
    return unloadAll(unit, false);
  }

  public static UnitCommand unloadAll(Unit unit, boolean shiftQueueCommand) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public static UnitCommand unloadAll(Unit unit, Position target) {
    return unloadAll(unit, target, false);
  }

  public static UnitCommand unloadAll(Unit unit, Position target, boolean shiftQueueCommand) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public static UnitCommand rightClick(Unit unit, Position target) {
    return rightClick(unit, target, false);
  }

  public static UnitCommand rightClick(Unit unit, Position target, boolean shiftQueueCommand) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public static UnitCommand rightClick(Unit unit, Unit target) {
    return rightClick(unit, target, false);
  }

  public static UnitCommand rightClick(Unit unit, Unit target, boolean shiftQueueCommand) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public static UnitCommand haltConstruction(Unit unit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public static UnitCommand cancelConstruction(Unit unit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public static UnitCommand cancelAddon(Unit unit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public static UnitCommand cancelTrain(Unit unit) {
    return cancelTrain(unit, -2);
  }

  public static UnitCommand cancelTrain(Unit unit, int slot) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public static UnitCommand cancelMorph(Unit unit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public static UnitCommand cancelResearch(Unit unit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public static UnitCommand cancelUpgrade(Unit unit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public static UnitCommand useTech(Unit unit, TechType tech) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public static UnitCommand useTech(Unit unit, TechType tech, Position target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public static UnitCommand useTech(Unit unit, TechType tech, Unit target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public static UnitCommand placeCOP(Unit unit, TilePosition target) {
    throw new UnsupportedOperationException("TODO"); // TODO
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
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public TilePosition getTargetTilePosition() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public UnitType getUnitType() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public TechType getTechType() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public UpgradeType getUpgradeType() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public int getSlot() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean isQueued() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  // To be implemented, when there will be an abstract point class
  // void assignTarget(Point<T, S> target)

  // TODO: equals and hashCode overrides
}
