////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (C) 2017-2018 OpenBW Team
//
//    This file is part of BWAPI4J.
//
//    BWAPI4J is free software: you can redistribute it and/or modify
//    it under the terms of the Lesser GNU General Public License as published
//    by the Free Software Foundation, version 3 only.
//
//    BWAPI4J is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with BWAPI4J.  If not, see <http://www.gnu.org/licenses/>.
//
////////////////////////////////////////////////////////////////////////////////

package org.openbw.bwapi4j.unit;

import static org.openbw.bwapi4j.type.UnitCommandType.Right_Click_Position;
import static org.openbw.bwapi4j.type.UnitCommandType.Right_Click_Unit;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import org.openbw.bwapi4j.Player;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.UnitStatCalculator;
import org.openbw.bwapi4j.type.Order;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public abstract class PlayerUnitImpl extends UnitImpl {

  private UnitStatCalculator unitStatCalculator;

  protected PlayerUnitImpl(int id, UnitType unitType) {

    super(id, unitType);
  }

  @Override
  public void initialize(int[] unitData, int index, int frame) {

    super.initialize(unitData, index, frame);
    this.unitStatCalculator = this.getPlayer(playerId).getUnitStatCalculator();
  }

  public PlayerUnit getBuildUnit() {

    final Unit unit = this.getUnit(builderId);
    if (unit == null) {
      return null;
    } else {
      if (unit instanceof PlayerUnit) {
        return (PlayerUnit) unit;
      } else {
        throw new IllegalStateException(
            "build unit for "
                + this.toString()
                + " should be PlayerUnit but is "
                + unit.toString()
                + ".");
      }
    }
  }

  protected Unit getTargetUnit() {

    return this.getUnit(this.targetId);
  }

  /**
   * Convenience method to be used e.g. when doing target-fire micro (just provide unit weapon range
   * as radius).
   *
   * @param radius pixel radius
   * @param units units to check for distance
   * @return weakest unit within given radius
   */
  public <T extends PlayerUnit> T getWeakestUnitInRadius(int radius, Collection<T> units) {

    List<T> inRange = this.getUnitsInRadius(radius, units);
    T weakestUnit;
    if (inRange.isEmpty()) {

      weakestUnit = this.getClosest(units);
    } else {

      Comparator<T> comp = Comparator.comparingInt(PlayerUnit::getHitPoints);
      weakestUnit = inRange.stream().min(comp).get();
    }
    return weakestUnit;
  }

  /**
   * Perform the right-click command on a given position.
   *
   * @param position the position to right-click to
   * @param queued true if command is queued, false else
   * @return true if command is successful, false else
   */
  public boolean rightClick(Position position, boolean queued) {

    return issueCommand(
        this.id, Right_Click_Position, -1, position.getX(), position.getY(), queued ? 1 : 0);
  }

  /**
   * Perform the right-click command on a given unit.
   *
   * @param target the unit to right-click to
   * @param queued true if command is queued, false else
   * @return true if command is successful, false else
   */
  public boolean rightClick(Unit target, boolean queued) {

    return issueCommand(this.id, Right_Click_Unit, target.getId(), -1, -1, queued ? 1 : 0);
  }

  public boolean isCompleted() {

    return this.isCompleted;
  }

  public int maxHitPoints() {

    return this.type.maxHitPoints();
  }

  public int getHitPoints() {

    return this.hitPoints;
  }

  protected UnitStatCalculator getUnitStatCalculator() {

    return this.unitStatCalculator;
  }

  protected int getMaxEnergy() {

    return getUnitStatCalculator().maxEnergy(type);
  }

  public int getArmor() {

    return getUnitStatCalculator().armor(type);
  }

  public int maxShields() {

    return this.type.maxShields();
  }

  public int getShields() {

    return this.shields;
  }

  public int getSpellCooldown() {

    return this.spellCooldown;
  }

  public Player getPlayer() {

    return super.getPlayer();
  }

  public int getInitialHitPoints() {

    return this.initialHitPoints;
  }

  public int getMineralPrice() {

    return this.type.mineralPrice();
  }

  public int getGasPrice() {

    return this.type.gasPrice();
  }

  public int getLastKnownHitPoints() {

    return this.lastKnownHitPoints;
  }

  public Position getLastKnownPosition() {

    return this.lastKnownPosition;
  }

  public TilePosition getLastKnownTilePosition() {

    return this.lastKnownTilePosition;
  }

  public int getSightRange() {

    return getUnitStatCalculator().sightRange(type);
  }

  public boolean isDetector() {
    return type.isDetector();
  }

  public boolean isDetected() {

    return this.isDetected;
  }

  public boolean isCloaked() {

    return this.isCloaked;
  }

  public boolean isFlyer() {

    return this.type.isFlyer();
  }

  public boolean isInterruptible() {

    return isInterruptible;
  }

  @Override
  public int tileWidth() {

    return this.type.tileWidth();
  }

  @Override
  public int tileHeight() {

    return this.type.tileHeight();
  }

  protected double getTopSpeed() {

    return getUnitStatCalculator().topSpeed(type);
  }

  protected int getMaxGroundHits() {

    return this.type.maxGroundHits();
  }

  protected int getMaxAirHits() {

    return this.type.maxAirHits();
  }

  public double getVelocityX() {

    return this.velocityX;
  }

  public double getVelocityY() {

    return this.velocityY;
  }

  public boolean isIdle() {

    return this.isIdle;
  }

  public boolean isAccelerating() {

    return this.isAccelerating;
  }

  public boolean isAttacking() {

    return this.isAttacking;
  }

  public boolean isAttackFrame() {

    return this.isAttackFrame;
  }

  public boolean isBeingConstructed() {

    return this.isBeingConstructed;
  }

  public boolean isBeingHealed() {

    return this.isBeingHealed;
  }

  public boolean isIrradiated() {

    return this.isIrradiated;
  }

  public boolean isLockedDown() {

    return this.isLockedDown;
  }

  public boolean isMaelstrommed() {

    return this.isMaelstrommed;
  }

  public boolean isStartingAttack() {

    return this.isStartingAttack;
  }

  public boolean isUnderAttack() {

    return this.isUnderAttack;
  }

  public boolean isPowered() {

    return this.isPowered;
  }

  protected int getGroundWeaponMaxRange() {

    return getUnitStatCalculator().weaponMaxRange(type.groundWeapon());
  }

  protected int getGroundWeaponMaxCooldown() {

    return getUnitStatCalculator().groundWeaponDamageMaxCooldown(type);
  }

  protected int getGroundWeaponCooldown(GroundAttacker unit) {

    // Only ground weapons have varied cooldowns.
    return getUnitStatCalculator().groundWeaponDamageCooldown(unit);
  }

  protected int getGroundWeaponDamage() {

    return getUnitStatCalculator().damage(type.groundWeapon());
  }

  protected int getAirWeaponMaxRange() {

    return getUnitStatCalculator().weaponMaxRange(type.airWeapon());
  }

  protected int getAirWeaponMaxCooldown() {

    return type.airWeapon().damageCooldown();
  }

  protected int getAirWeaponCooldown(AirAttacker unit) {

    return getUnitStatCalculator().airWeaponDamageCooldown(unit);
  }

  protected int getAirWeaponDamage() {

    return getUnitStatCalculator().damage(type.airWeapon());
  }

  public int getDamageTo(PlayerUnit to) {

    return this.getDamageEvaluator()
        .getDamageTo(to.getInitialType(), this.initialType, to.getPlayer(), this.getPlayer());
  }

  public int getDamageFrom(PlayerUnit from) {

    return this.getDamageEvaluator()
        .getDamageFrom(from.getInitialType(), this.initialType, from.getPlayer(), this.getPlayer());
  }

  @Override
  public Order getOrder() {

    return super.getOrder();
  }

  @Override
  public Unit getOrderTarget() {

    return super.getOrderTarget();
  }

  @Override
  public Position getOrderTargetPosition() {

    return super.getOrderTargetPosition();
  }

  @Override
  public Order getSecondaryOrder() {

    return super.getSecondaryOrder();
  }

  public int getLastCommandFrame() {
    return lastCommandFrame;
  }

  public UnitCommandType getLastCommand() {
    return lastCommand;
  }
}
