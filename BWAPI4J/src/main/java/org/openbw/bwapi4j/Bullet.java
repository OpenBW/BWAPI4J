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

package org.openbw.bwapi4j;

import org.openbw.bwapi4j.ap.BridgeValue;
import org.openbw.bwapi4j.ap.LookedUp;
import org.openbw.bwapi4j.ap.Named;
import org.openbw.bwapi4j.ap.NativeClass;
import org.openbw.bwapi4j.type.BulletType;
import org.openbw.bwapi4j.unit.Unit;

@LookedUp(method = "getBullet")
@NativeClass(name = "BWAPI::Bullet")
public class Bullet {

  @BridgeValue(accessor = "exists()")
  boolean exists;
  @BridgeValue
  double angle;
  @BridgeValue(initializeOnly = true)
  @Named(name = "ID")
  int iD;
  @BridgeValue
  Player player;
  @BridgeValue
  Position position;
  @BridgeValue
  int removeTimer;
  @BridgeValue
  Unit source;
  @BridgeValue
  Unit target;
  @BridgeValue
  Position targetPosition;
  @Named(name = "TYPE")
  @BridgeValue(initializeOnly = true)
  BulletType type;
  @BridgeValue
  double velocityX;
  @BridgeValue
  double velocityY;
  @BridgeValue
  boolean visible;

  private BW bw;

  Bullet(BW bw) {
    this.bw = bw;
  }

  public Player getPlayer() {
    return this.player;
  }

  public Unit getSource() {
    return source;
  }

  public Unit getTarget() {
    return target;
  }

  public boolean isExists() {
    return exists;
  }

  public double getAngle() {
    return angle;
  }

  public int getId() {
    return iD;
  }

  public Position getPosition() {
    return position;
  }

  public int getRemoveTimer() {
    return removeTimer;
  }

  public Position getTargetPosition() {
    return targetPosition;
  }

  public BulletType getType() {
    return type;
  }

  public double getVelocityX() {
    return velocityX;
  }

  public double getVelocityY() {
    return velocityY;
  }

  public boolean isVisible() {
    return visible;
  }
}
