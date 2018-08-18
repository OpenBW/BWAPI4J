package org.openbw.bwapi4j.unit;

import java.util.Collection;
import org.openbw.bwapi4j.Player;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.type.Order;
import org.openbw.bwapi4j.type.UnitCommandType;

public interface PlayerUnit extends Unit {
  void initialize(int[] unitData, int index, int frame);

  PlayerUnit getBuildUnit();

  /**
   * Convenience method to be used e.g. when doing target-fire micro (just provide unit weapon range
   * as radius).
   *
   * @param radius pixel radius
   * @param units units to check for distance
   * @return weakest unit within given radius
   */
  <T extends PlayerUnit> T getWeakestUnitInRadius(int radius, Collection<T> units);

  /**
   * Perform the right-click command on a given position.
   *
   * @param position the position to right-click to
   * @param queued true if command is queued, false else
   * @return true if command is successful, false else
   */
  boolean rightClick(Position position, boolean queued);

  /**
   * Perform the right-click command on a given unit.
   *
   * @param target the unit to right-click to
   * @param queued true if command is queued, false else
   * @return true if command is successful, false else
   */
  boolean rightClick(Unit target, boolean queued);

  boolean isCompleted();

  int maxHitPoints();

  int getHitPoints();

  int getArmor();

  int maxShields();

  int getShields();

  int getSpellCooldown();

  Player getPlayer();

  int getInitialHitPoints();

  int getMineralPrice();

  int getGasPrice();

  int getLastKnownHitPoints();

  Position getLastKnownPosition();

  TilePosition getLastKnownTilePosition();

  int getSightRange();

  boolean isDetector();

  boolean isDetected();

  boolean isCloaked();

  boolean isFlyer();

  boolean isInterruptible();

  int tileWidth();

  int tileHeight();

  double getVelocityX();

  double getVelocityY();

  boolean isIdle();

  boolean isAccelerating();

  boolean isAttacking();

  boolean isAttackFrame();

  boolean isBeingConstructed();

  boolean isBeingHealed();

  boolean isIrradiated();

  boolean isLockedDown();

  boolean isMaelstrommed();

  boolean isStartingAttack();

  boolean isUnderAttack();

  boolean isPowered();

  int getDamageTo(PlayerUnit to);

  int getDamageFrom(PlayerUnit from);

  Order getOrder();

  Unit getOrderTarget();

  Position getOrderTargetPosition();

  Order getSecondaryOrder();

  int getLastCommandFrame();

  UnitCommandType getLastCommand();
}
